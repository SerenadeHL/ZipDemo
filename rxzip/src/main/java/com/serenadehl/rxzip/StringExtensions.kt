package com.serenadehl.rxzip

fun String.suffix(): String? {
    val index = lastIndexOf(".").takeIf { it != -1 } ?: return null
    return substring(index + 1)
}

fun String.nameWithoutSuffix(): String? {
    return substringAfterLast("/").substringBeforeLast(".")
}