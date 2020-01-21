package com.serenadehl.rxzip.task

import com.serenadehl.rxzip.interfaces.Task
import com.serenadehl.rxzip.interfaces.TaskBuilder
import com.serenadehl.rxzip.strategy.ConflictStrategy
import java.io.File

data class ZipTask(
    val src: File,
    val dest: File,
    val password: String?,
    val strategy: ConflictStrategy
) : Task {
    class Builder : TaskBuilder {
        private var builderSrc: File? = null
        private var builderDest: File? = null
        private var builderPassword: String? = null
        private var builderStrategy: ConflictStrategy = ConflictStrategy.KEEP_BOTH

        /**
         * 要压缩的源文件（可以是文件夹）
         */
        override fun src(src: File): Builder {
            builderSrc = src
            return this
        }

        /**
         * 要压缩的成的目标文件
         */
        override fun dest(dest: File): Builder {
            builderDest = dest
            return this
        }

        /**
         * 解压密码
         */
        override fun password(password: String?): Builder {
            builderPassword = password
            return this
        }

        /**
         * 文件冲突时解决策略
         */
        override fun conflictStrategy(strategy: ConflictStrategy): Builder {
            builderStrategy = strategy
            return this
        }

        override fun build(): ZipTask {
            val src = builderSrc ?: throw RuntimeException("必须设置源文件！")
            val dest = builderDest ?: throw RuntimeException("必须设置压缩文件夹！")
            val password = builderPassword
            val strategy = builderStrategy
            return ZipTask(src, dest, password, strategy)
        }

    }
}