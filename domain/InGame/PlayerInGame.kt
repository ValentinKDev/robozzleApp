package com.mobilegame.robozzle.domain.InGame

import android.util.Log
import com.mobilegame.robozzle.Extensions.toInt
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_BORDER
import com.mobilegame.robozzle.domain.InGame.res.OUT_OF_MAP_PATH
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

class PlayerInGame(var pos: Position, var direction: Direction = Direction(0, 0) ) {
    var isInsideMapBorder = true

    fun ChangeDirectionPlayer(instruction: Char): PlayerInGame {
        when {
            direction.x == 0 -> {
                when (instruction) {
                    'r' -> direction = Direction(direction.y, 0)
                    'l' -> direction = Direction(direction.OppositeOf_y(),0)
                }
            }
            direction.y == 0 -> {
                when (instruction) {
                    'r' -> direction = Direction(0, direction.OppositeOf_x())
                    'l' -> direction = Direction(0, direction.x)
                }
            }
            else -> {
                Log.e("ChangeDirectionPlayer", "Something went wrong on rotation")
            }
        }
        return this
    }

    fun ApplyTheMovement(map: List<String>): Int {
        var ret : Int = 0
        ret = when (direction.ToChar()) {
            'u' -> GoUp(map).toInt()
            'd' -> GoDown(map).toInt()
            'r' -> GoRight(map).toInt()
            'l' -> GoLeft(map).toInt()
            else -> -2
        }
//        if (!IsThePlayerOnMapPath(map)) ret = OUT_OF_MAP_PATH
        if (IsThePlayerOutMapPath(map)) ret = OUT_OF_MAP_PATH
        return ret
    }

    fun GoLeft(map: List<String>): Boolean {
        print("GOLEFT true")
        if (0 < pos.column){
            pos.column -= 1
            return true
        }
        else {return false}
    }

    fun GoRight(map: List<String>): Boolean{
        print("GORIGHT true")
        if (pos.column < map[0].length - 1){
            pos.column += 1
            return true
        }
        else {return false}
    }

    fun GoDown(map: List<String>): Boolean{
        print("GODOWN true")
        if (pos.line < map.size - 1){
            pos.line += 1
            return true
        }
        else {return false}
    }

    fun GoUp(map: List<String>): Boolean {
        print("GOUP true")
        if (pos.line > 0){
            pos.line -= 1
            return true
        }
        else{return false}
    }

    fun IsThePlayerOutMapPath(map: List<String>): Boolean {
        return (map[pos.line].get(pos.column) == '.')
    }
    fun IsThePlayerOnMapPath(map: List<String>):Boolean {
        return (map[pos.line].get(pos.column) != '.')
    }

    fun copy(): PlayerInGame {
        return PlayerInGame(this.pos.copy(), this.direction.copy())
    }

    fun equals(playerInGame: PlayerInGame): Boolean = this.pos == playerInGame.pos && this.direction == playerInGame.direction
}
