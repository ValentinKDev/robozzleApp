package com.mobilegame.robozzle.domain.InGame.res

import com.mobilegame.robozzle.domain.InGame.ColorSwitch
import com.mobilegame.robozzle.domain.InGame.MapElementTracker

data class ColorsMaps (
//    var toRemove: MutableMap<Int, ColorSwitch> = emptyMap<Int, ColorSwitch>().toMutableMap(),
//    var removed: MutableMap<Int, ColorSwitch> = emptyMap<Int, ColorSwitch>().toMutableMap()
    override var toRemove: MutableMap<Int, ColorSwitch> = mutableMapOf(),
    override var removed: MutableMap<Int, ColorSwitch> = mutableMapOf(),
): MapElementTracker <ColorSwitch> (toRemove, removed) {
//    override fun FromRemovedMapToToRemoveMap(key: Int) {
//        var element: ColorSwitch? = try {
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
//    override fun FromToRemoveMapToRemovedMap(key: Int) {
//        var element: ColorSwitch? = try {
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
//
//    override fun Expand(newToRemoveMap: MutableMap<Int, ColorSwitch>) {
//        verbalLog("Expand start", "${toRemoveMap}")
//        toRemoveMap = newToRemoveMap.copy()
//        verbalLog("Expand copy", "${toRemoveMap}")
//        toRemoveMap = toRemoveMap.filterNot { removedMap.containsKey(it.key) } as MutableMap<Int, ColorSwitch>
//        verbalLog("Expand wash", "${toRemoveMap}")
//        infoLog("removedMap", "${removedMap}")
//        infoLog("toRemoveMap", "${toRemoveMap}")
//        toRemoveMap.forEach { infoLog("", "${it.key}") }
//    }
}
//{
//    fun FromToRemoveMapToRemovedMap(key: Int) {
//        removedMap.put(key, toRemoveMap.getValue(key))
//        toRemoveMap.remove(key)
//    }
//    fun FromRemovedMapToToRemoveMap(key: Int) {
//        toRemoveMap.put(key, removedMap.getValue(key))
//        removedMap.remove(key)
//    }
//}