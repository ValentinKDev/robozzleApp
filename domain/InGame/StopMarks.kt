package com.mobilegame.robozzle.domain.InGame

data class StopMarks (
    override var toRemove: MutableMap<Int, ColorSwitch> = mutableMapOf(),
    override var removed: MutableMap<Int, ColorSwitch> = mutableMapOf(),
): MapElementTracker <ColorSwitch> (toRemove, removed)
