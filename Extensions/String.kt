package com.mobilegame.robozzle.Extensions



fun String.notEqual(str: String): Boolean {
    return !this.equals(str)
}

fun String.isAlphaNum(): Boolean {
    var ret = true
    for (index in this.indices) {
        if (!"[a-zA-Z0-9]".toRegex().matches(this[index].toString()))
            ret = false
    }
    return ret
}

fun String.noUpperCase(): Boolean {
    var ret = true
    for (index in this.indices) {
        if (!"[A-Z]".toRegex().matches(this[index].toString()))
            ret = false
    }
    return ret
}

fun String.containLetter(): Boolean {
    return this.contains("[a-zA-Z]".toRegex())
}

fun String.replaceAt(index: Int, char: Char): String {
    return this.replaceRange(index, index + 1, char.toString())
}