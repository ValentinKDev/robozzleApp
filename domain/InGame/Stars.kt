package com.mobilegame.robozzle.domain.InGame

import com.mobilegame.robozzle.utils.Extensions.copy
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.RobuzzleLevel.Position

open class MapElementTracker <T>(
//    var toRemoveMap: MutableMap<Int, T> = emptyMap<Int, T>().toMutableMap(),
//    var removedMap: MutableMap<Int, T> = emptyMap<Int, T>().toMutableMap(),
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
//        verbalLog("Expand start", "${toRemove}")
        toRemove = newToRemoveMap.copy()
//        verbalLog("Expand copy", "${toRemove}")
        toRemove = toRemove.filterNot { removed.containsKey(it.key) } as MutableMap<Int, T>
//        verbalLog("Expand wash", "${toRemove}")
//        infoLog("removed", "${removed}")
//        infoLog("toRemove", "${toRemove}")
        toRemove.forEach { infoLog("", "${it.key}") }
    }
}

data class Stars(
//    var toRemove: MutableMap<Int, Position> = emptyMap<Int, Position>().toMutableMap(),
//    var removed: MutableMap<Int, Position> = emptyMap<Int, Position>().toMutableMap(),
    override var toRemove: MutableMap<Int, Position> = mutableMapOf(),
    override var removed: MutableMap<Int, Position> = mutableMapOf(),
): MapElementTracker <Position> (toRemove, removed) {
//    override fun FromRemovedMapToToRemoveMap(key: Int) {
//        var element: Position? = try {
//            removedMap.getValue(key)
//        } catch (e: NoSuchElementException) { null }
//        if (element == null) {
//            errorLog("ERROR", "MapElementTracker::FromRemovedMapToToRemoveMap")
//            errorLog("toRemoveMap", " ${toRemoveMap}")
//            errorLog("removedMap", "${removedMap}")
//            errorLog("key to get in removedMap", "${key}")
//        } else {
//            toRemoveMap.put(key, removedMap.getValue(key))
//            removedMap.remove(key)
//        }
//    }
//
//    override fun FromToRemoveMapToRemovedMap(key: Int) {
//        var element: Position? = try {
//            toRemoveMap.getValue(key)
//        } catch (e: NoSuchElementException) { null }
//        if (element == null) {
//            errorLog("ERROR", "MapElementTracker::FromRemoveMapToRemoveMap")
//            errorLog("toRemoveMap", " ${toRemoveMap}")
//            errorLog("removedMap", "${removedMap}")
//            errorLog("key to get in toRemovedMap", "${key}")
//        } else {
//            removedMap.put(key, toRemoveMap.getValue(key))
//            toRemoveMap.remove(key)
//        }
//    }
//    override fun Expand(newToRemoveMap: MutableMap<Int, Position>) {
//        verbalLog("Expand start", "${toRemoveMap}")
//        toRemoveMap = newToRemoveMap.copy()
//        verbalLog("Expand copy", "${toRemoveMap}")
//        toRemoveMap = toRemoveMap.filterNot { removedMap.containsKey(it.key) } as MutableMap<Int, Position>
//        verbalLog("Expand wash", "${toRemoveMap}")
//        infoLog("removedMap", "${removedMap}")
//        infoLog("toRemoveMap", "${toRemoveMap}")
//        toRemoveMap.forEach { infoLog("", "${it.key}") }
//    }
}