package com.mobilegame.robozzle.domain.model.data.room.Config

import android.content.pm.ActivityInfo
import com.mobilegame.robozzle.data.room.Config.ConfigData

internal val firstConfig = ConfigData(
    id = 0,
    lightTheme = false,
    trashesInGame = true,
    displayLevelWinInList = true,
    orientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT
)