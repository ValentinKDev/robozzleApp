package com.mobilegame.robozzle.domain.model.Screen.utils

import android.icu.util.Calendar
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.elements.OnTouchBounceState
import kotlinx.coroutines.flow.MutableStateFlow

class RankingIconViewModel(): ViewModel() {
    private val _rankingIconTouchState = MutableStateFlow(OnTouchBounceState.Released)
    val rankingIconTouchState: MutableStateFlow<OnTouchBounceState> = _rankingIconTouchState

    private var onTouchStart = Calendar.getInstance().timeInMillis
    private var onTouchEnd = Calendar.getInstance().timeInMillis

    fun rankingIconIsPressed() {
        if (_rankingIconTouchState.value == OnTouchBounceState.Released) {
            onTouchStart = Calendar.getInstance().timeInMillis
            _rankingIconTouchState.value = OnTouchBounceState.Pressed
        }
    }
    fun rankingIconIsReleased(navigator: Navigator, levelId: Int) {
        if (_rankingIconTouchState.value == OnTouchBounceState.Pressed) {
            _rankingIconTouchState.value = OnTouchBounceState.Released
            onTouchEnd = Calendar.getInstance().timeInMillis
            val diff = onTouchEnd - onTouchStart
            errorLog("diff ", "$diff")
            NavViewModel(navigator).navigateTo(
                destination = Screens.RanksLevel,
                argStr = levelId.toString(),
                delayTiming = when (diff) {
                    in 0..20 -> 100
                    in 21..50 -> 250
                    in 51..80 -> 400
                    in 81..120 -> 400
                    in 121..180 -> 450
                    in 181..300 -> 550
                    in 301..600 -> 600
                    else -> 650
                }
            )
        }
    }
}