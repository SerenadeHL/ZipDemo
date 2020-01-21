package com.serenadehl.rxzip.strategy

enum class ConflictStrategy {
    KEEP_OLD,//保存之前的
    KEEP_NEW,//保存新的
    KEEP_BOTH//两个文件都保存(新文件会被重命名)
}