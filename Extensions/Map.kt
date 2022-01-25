package com.mobilegame.robozzle.Extensions


//fun MutableMap<Int, Position>.copy(): MutableMap<Int, Position> {
//    var ret = mutableMapOf<Int, Position>()
//    this.forEach { key, value -> ret.put(key, value) }
//    return ret
//}

fun <K, T> MutableMap<K, T>.copy(): MutableMap<K, T> {
    var ret = mutableMapOf<K, T>()
    this.forEach { key, value -> ret.put(key, value) }
    return ret
}
