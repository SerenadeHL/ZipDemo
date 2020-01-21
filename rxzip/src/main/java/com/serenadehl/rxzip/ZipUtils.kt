package com.serenadehl.rxzip

import com.serenadehl.rxzip.bean.FileEntry
import com.serenadehl.rxzip.strategy.ConflictStrategy
import com.serenadehl.rxzip.task.UnzipTask
import io.reactivex.Observable
import io.reactivex.ObservableSource
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.util.zip.ZipEntry
import java.util.zip.ZipFile

object ZipUtils {
    fun zip(src: File, dest: File) {

    }

    fun unzip(task: UnzipTask, onProgress: (Int) -> Unit) {
        val zipFile = ZipFile(task.src)
        val entrySize = zipFile.size()//entrySize包含空文件夹

        var current = 0

        zipFile.entries()
            .asSequence()
            .map { FileEntry(it, File(task.dest, it.name)) }
            .forEach {
                if (it.entry.isDirectory) {

                } else {
                    
                }
            }

        //创建所有文件夹
        zipFile.entries()
            .asSequence()
            .filter { it.isDirectory }
            .map { File(task.dest, it.name) }
            .forEach {
                if (!it.exists()) {
                    it.mkdirs()
                }
            }

        //创建所有文件
        zipFile.entries()
            .asSequence()
            .filter { !it.isDirectory }
            .map { FileEntry(it, File(task.dest, it.name)) }
            .onEach { it.file.parentFile?.mkdirs() }//创建所有父文件夹
            .onEach {
                if (it.file.exists() && task.strategy == ConflictStrategy.KEEP_BOTH) {
                    it.file = renameWhenConflict(
                        it.file,
                        it.file.name.nameWithoutSuffix(),
                        it.file.name.suffix(),
                        1
                    )
                }
            }
            .filter {
                !(it.file.exists() && task.strategy == ConflictStrategy.KEEP_OLD)
            }
            .forEach {
                it.file.absolutePath.log()
                val inputStream = zipFile.getInputStream(it.entry)
                val bufferedOutputStream = BufferedOutputStream(it.file.outputStream())
                val buffer = ByteArray(2048)
                var len = inputStream.read(buffer)
                while (len != -1) {
                    bufferedOutputStream.write(buffer)
                    len = inputStream.read(buffer)
                }
                bufferedOutputStream.flush()
                bufferedOutputStream.close()
                inputStream.close()
            }

        zipFile.close()
    }

    private fun renameWhenConflict(file: File, name: String?, suffix: String?, startNo: Int): File {
        val extension = if (suffix == null) "" else ".${suffix}"
        val newNameFile = File(file.parentFile, "${name}${startNo}${extension}")
        return if (newNameFile.exists()) {
            renameWhenConflict(
                newNameFile,
                name,
                suffix,
                startNo + 1
            )
        } else {
            newNameFile
        }
    }
}