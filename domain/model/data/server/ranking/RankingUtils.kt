package com.mobilegame.robozzle.domain.model.data.server.ranking

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.domain.Player.LevelWin
import com.mobilegame.robozzle.domain.Player.PlayerWin

private val ListPlayerWinType = object : TypeToken<List<PlayerWin>>() {}.type!!
private val ListLevelWinType = object : TypeToken<List<LevelWin>>() {}.type!!
//private val ListLe = object : TypeToken<List<PlayerWin>>() {}.type!!

internal fun String?.toListPlayerWin(): List<PlayerWin> {
    return Gson().fromJson(this ?: Gson().toJson(emptyList<PlayerWin>()), ListPlayerWinType)
}

internal fun String?.toListLevelWin(): List<LevelWin> {
    return Gson().fromJson(this ?: Gson().toJson(emptyList<LevelWin>()), ListLevelWinType)
}

internal fun List<LevelWin>.toJsonString(): String {
    return Gson().toJson(this, ListLevelWinType)
}