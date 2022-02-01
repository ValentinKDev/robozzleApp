package com.mobilegame.robozzle.domain.model.server.level

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.data.server.dto.LevelRequest
import com.mobilegame.robozzle.domain.model.level.Level

private val ListIntType = object : TypeToken<List<Int>>() {}.type!!

internal fun String?.toIntList(): List<Int> {
    return Gson().fromJson(this, ListIntType)
}

internal fun LevelRequest.toLevel(): Level {
    return Gson().fromJson(this.descriptionJson, Level::class.java)
}

internal fun List<LevelRequest>.toLevelList(): List<Level> {
    val mutableList: MutableList<Level> = mutableListOf()
    this.forEach {
        mutableList.add(element = it.toLevel())
    }
    return mutableList.toList()
}
