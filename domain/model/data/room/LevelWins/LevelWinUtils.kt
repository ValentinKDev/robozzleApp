package com.mobilegame.robozzle.domain.model.data.room.level

import com.mobilegame.robozzle.domain.WinDetails.WinDetails


fun WinDetails.isBetterThan(winDetails: WinDetails): Boolean {
    val ret = true
    if (this.instructionsNumber <= winDetails.instructionsNumber) {

    }
    return ret
}

