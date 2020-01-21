package com.serenadehl.rxzip

import com.serenadehl.rxzip.task.UnzipTask
import com.serenadehl.rxzip.task.ZipTask

object ZipManager {
    fun zip(task: ZipTask){

    }

    fun unzip(task: UnzipTask) {
        ZipUtils.unzip(task){

        }
    }
}