package com.mobilegame.robozzle.domain.InGame.animation

import android.graphics.Point
import com.mobilegame.robozzle.domain.InGame.Breadcrumb
import com.mobilegame.robozzle.domain.InGame.Stars
import com.mobilegame.robozzle.domain.InGame.initialPreloadActionsNumber
import com.mobilegame.robozzle.domain.InGame.res.ColorsMaps
import com.mobilegame.robozzle.domain.InGame.res.UNKNOWN
import com.mobilegame.robozzle.utils.Extensions.copy

class AnimationLogicData(bd: Breadcrumb) {
    var stars = Stars(toRemove = bd.starsRemovalMap.copy())
    var colorSwitches = ColorsMaps(toRemove  = bd.colorChangeMap.copy())
    var actionIndexEnd = bd.lastActionNumber
    private var starsRemovedMap = mutableMapOf<Int, Point>()
    private var addAction = initialPreloadActionsNumber
}