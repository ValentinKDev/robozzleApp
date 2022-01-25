package com.mobilegame.robozzle.toREMOVE

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf

class PlayerData {
    var name: String = "Player"
    private val _playerName: MutableState<String> = mutableStateOf("player")
    val playerName = _playerName
    fun setplayerName(newName: String) { _playerName.value = newName}

    private val _levelsStats: MutableList<LevelStat> = mutableListOf()
    val levelsStats: MutableList<LevelStat>  = _levelsStats
    //todo: add protection to not duplicate levelsStat and only keep the one with less instructions and then less actions
    fun addLevelWin(stats: LevelStat) {_levelsStats.add(stats) }
    fun WinsContain(num: Int): Boolean {
        var ret = false
        for (index in 0 until _levelsStats.size) {
            if (_levelsStats[index].levelNumber == num) ret = true
        }
        return ret
    }
}
