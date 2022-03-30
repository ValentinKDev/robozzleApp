package com.mobilegame.robozzle.domain.InGame

import com.mobilegame.robozzle.utils.Extensions.copy
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

open class MapElementTracker <T>(
    open var toRemove: MutableMap<Int, T> = mutableMapOf(),
    open var removed: MutableMap<Int, T> = mutableMapOf(),
) {
    open fun FromToRemoveMapToRemovedMap(key: Int) {
        var element: T? = try {
            toRemove.getValue(key)
        } catch (e: NoSuchElementException) { null }
        if (element == null) {
            errorLog("ERROR", "MapElementTracker::FromRemoveMapToRemoveMap")
            errorLog("toRemoveMap", " ${toRemove}")
            errorLog("removedMap", "${removed}")
            errorLog("key to get in toRemovedMap", "${key}")
        } else {
            removed.put(key, toRemove.getValue(key))
            toRemove.remove(key)
        }
    }

    open fun FromRemovedMapToToRemoveMap(key: Int) {
        var element: T? = try {
            removed.getValue(key)
        } catch (e: NoSuchElementException) { null }
        if (element == null) {
            errorLog("ERROR", "MapElementTracker::FromRemovedMapToToRemoveMap")
            errorLog("toRemove", " ${toRemove}")
            errorLog("removed", "${removed}")
            errorLog("key to get in removed", "${key}")
        } else {
            toRemove.put(key, removed.getValue(key))
            removed.remove(key)
        }
    }

    open fun expand(newToRemoveMap: MutableMap<Int, T>) {
        toRemove = newToRemoveMap.copy()
        toRemove = toRemove.filterNot { removed.containsKey(it.key) } as MutableMap<Int, T>
        toRemove.forEach { infoLog("", "${it.key}") }
    }
}

data class Stars(
    override var toRemove: MutableMap<Int, Position> = mutableMapOf(),
    override var removed: MutableMap<Int, Position> = mutableMapOf(),
): MapElementTracker <Position> (toRemove, removed)