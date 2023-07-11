package com.awesomeapp.xscaner.content

fun ByteArray.toHexString() = joinToString("") { "%02X".format(it) }