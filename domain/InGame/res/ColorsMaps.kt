package com.mobilegame.robozzle.domain.InGame.res

import com.mobilegame.robozzle.domain.InGame.ColorSwitch
import com.mobilegame.robozzle.domain.InGame.MapElementTracker

data class ColorsMaps (
    override var toRemove: MutableMap<Int, ColorSwitch> = mutableMapOf(),
    override var removed: MutableMap<Int, ColorSwitch> = mutableMapOf(),
): MapElementTracker <ColorSwitch> (toRemove, removed)