package com.serenadehl.rxzip.bean

import java.io.File
import java.util.zip.ZipEntry

internal data class FileEntry(val entry: ZipEntry, var file: File)