package com.mobilegame.robozzle.domain.model.data.server.ranking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.domain.Player.PlayerWin

private val ListPlayerWinType = object : TypeToken<List<PlayerWin>>() {}.type!!

internal fun String?.toListPlayerWin(): List<PlayerWin> {
    return Gson().fromJson(this ?: Gson().toJson(emptyList<PlayerWin>()), ListPlayerWinType)
}
