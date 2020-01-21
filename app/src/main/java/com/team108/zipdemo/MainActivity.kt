package com.team108.zipdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import androidx.core.app.ActivityCompat
import com.serenadehl.rxzip.ZipManager
import com.serenadehl.rxzip.log
import com.serenadehl.rxzip.strategy.ConflictStrategy
import com.serenadehl.rxzip.task.UnzipTask
import kotlinx.android.synthetic.main.activity_main.*
import java.io.File

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val zipFile = File(Environment.getExternalStorageDirectory(), "test.zip")
        val destDir = File(Environment.getExternalStorageDirectory(), "testUnzipTmp")
        "zipFile exists => ${zipFile.exists()}".log()
        "destDir exists => ${destDir.exists()}".log()
        btnUnzip.setOnClickListener {
            val task = UnzipTask.Builder()
                .src(zipFile)
                .dest(destDir)
                .conflictStrategy(ConflictStrategy.KEEP_BOTH)
                .build()
            ZipManager.unzip(task)
            "解压完成".log()
        }

        requestPermissions()
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this,
            arrayOf(
                android.Manifest.permission.READ_EXTERNAL_STORAGE,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ),
            100
        )
    }
}
