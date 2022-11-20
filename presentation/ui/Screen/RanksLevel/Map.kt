package com.mobilegame.robozzle.presentation.ui.Screen.RanksLevel

import androidx.compose.runtime.Composable
import com.mobilegame.robozzle.domain.model.Screen.LevelsScreenByDiff.MapViewParam
import com.mobilegame.robozzle.domain.model.Screen.RanksLevelScreenViewModel
import com.mobilegame.robozzle.presentation.ui.elements.MapView
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable

@Composable
internal fun map(vm: RanksLevelScreenViewModel) {
    CenterComposable {
        vm.level?.let {
            MapView(
                param = MapViewParam(
                    it.map,
                    vm.ui.map.sizes.widhtInt,
                    vm.ui.map.colors.background
                )
            )
        }
    }
}