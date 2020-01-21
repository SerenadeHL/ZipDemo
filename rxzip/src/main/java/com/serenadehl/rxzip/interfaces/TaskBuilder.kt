package com.serenadehl.rxzip.interfaces

import com.serenadehl.rxzip.strategy.ConflictStrategy
import java.io.File

internal interface TaskBuilder {
    fun src(src: File): TaskBuilder

    fun dest(dest: File): TaskBuilder

    fun password(password: String?): TaskBuilder

    fun conflictStrategy(strategy: ConflictStrategy): TaskBuilder

    fun build(): Task
}