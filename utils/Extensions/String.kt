package com.mobilegame.robozzle.utils.Extensions

import com.mobilegame.robozzle.analyse.verbalLog


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
    return this.replaceRange(index..index, char.toString())
}

fun String.getNavArguement(keyStr: String): String = this.plus("/{$keyStr}")
fun String.addNavArg(arg: String): String = this.plus("/$arg")

fun String.getDiffChar(str: String): Char {
    if (this.isEmpty())
        return '0'
    if (str.isEmpty())
        return '0'
    return when {
        this.length >= str.length -> {
            var ret = '0'
            for (i in 0 .. str.lastIndex) {
                if (str[i] != this[i]) {
                    ret = this[i]
                    break
                }
            }
            ret
        }
        this.length <= str.length -> {
            var ret = '0'
            for (i in 0 .. this.lastIndex) {
                if (str[i] != this[i]) {
                    ret = str[i]
                    break
                }
            }
            ret
        }
        else -> '0'
    }
}