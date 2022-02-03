package com.mobilegame.robozzle.domain.model.data.server.level

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.domain.Player.PlayerWin

private val ListIntType = object : TypeToken<List<Int>>() {}.type!!

internal fun String?.toIntList(): List<Int> {
    return Gson().fromJson(this ?: Gson().toJson(emptyList<Int>()), ListIntType)
}
