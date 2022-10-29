package com.mobilegame.robozzle.domain.model.data.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.runtime.getValue
import androidx.compose.ui.geometry.Offset
import androidx.core.graphics.scaleMatrix
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenWindowsInfos
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.goingTopTiming
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.utils.Extensions.IsPair
import io.ktor.util.date.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class MainMenuAnimationViewModel(): ViewModel() {

    private val _visibleButton = MutableStateFlow(MutableTransitionState(false))
    val visibleButton= _visibleButton.asStateFlow()
    fun setVisibleButtonTargetSate(state: Boolean) {_visibleButton.value.targetState = state
        time1 = getTimeMillis()
    }
    fun animationEnd() = !visibleButton.value.currentState && !visibleButton.value.targetState

    var time1 = 0L
    var time2 = 0L

    private val _animationTime = MutableStateFlow<Long>(500)
    val animationTime: StateFlow<Long> = _animationTime

    fun getAState(button: Screens, fromScreens: Screens): ButtonState {
        return when  {
            button == fromScreens -> ButtonState.From
            else -> ButtonState.NotSelected
        }
    }

    fun setAnimationTime(button: Screens) {
        _animationTime.value = when (button) {
            Screens.Difficulty1 -> 450
            Screens.Difficulty2 -> 520
            Screens.Difficulty3 -> 600
            Screens.Difficulty4 -> 680
            Screens.Difficulty5 -> 730
            else -> 500
        }
    }
    fun getAnimationTime(buttonId: Int): Int {
        return when (buttonId) {
            Screens.Difficulty1.key -> 450
            Screens.Difficulty2.key -> 520
            Screens.Difficulty3.key -> 600
            Screens.Difficulty4.key -> 680
            Screens.Difficulty5.key -> 730
            else -> 500
        }
    }

    @ExperimentalAnimationApi
    fun enterTransitionByFrom(buttonsId: Int, fromScreenId: Int, offset: Offset): EnterTransition = runBlocking {
        when (buttonsId) {
            fromScreenId -> {
                when (buttonsId) {
                    Screens.Profil.key ->
                        slideInHorizontally( initialOffsetX = {(0).toInt()}, animationSpec = tween(400) ) +
                                fadeIn( initialAlpha = 0.02F, animationSpec = tween(400) )
                    in Screens.Difficulty1.key..Screens.Difficulty5.key ->
                        slideInVertically(initialOffsetY = {offset.y.toInt() * 1}, animationSpec = tween(getAnimationTime(buttonsId)))
                    else -> fadeIn(initialAlpha = 0F, animationSpec = tween(500))
                }
            }
            Screens.Profil.key ->
                fadeIn(initialAlpha = 0.05F, tween(500))
            in Screens.Difficulty1.key..Screens.Difficulty5.key -> {
                if (buttonsId.IsPair())
                    slideInHorizontally(initialOffsetX = { +500 }, animationSpec = tween(400)) +
                            fadeIn(initialAlpha = 0.05F, animationSpec = tween(400))
                else
                    slideInHorizontally(initialOffsetX = { -500 }, animationSpec = tween(400)) +
                            fadeIn(initialAlpha = 0.05F, animationSpec = tween(400))
            }
            Screens.Creator.key ->
                slideInHorizontally( initialOffsetX = {(-300).toInt()}, animationSpec = tween(300) ) +
                        fadeIn( initialAlpha = 0.02F, animationSpec = tween(400) )
            Screens.Donation.key ->
                slideInVertically( initialOffsetY = {(300).toInt()}, animationSpec = tween(300) ) +
                        fadeIn(initialAlpha = 0F, animationSpec = tween(500))
            Screens.Config.key ->
                slideInHorizontally( initialOffsetX = {(150).toInt()}, animationSpec = tween(300) ) +
                        fadeIn( initialAlpha = 0.02F, animationSpec = tween(400) )
            else -> {
                errorLog("MainMenuAnimationViewModel::enterTransitionByFrom", "ERROR buttonId $buttonsId fromScreen $fromScreenId")
                fadeIn(animationSpec = tween(500))
            }
        }
    }

    @ExperimentalAnimationApi
    fun exitTransitionByState(buttonSelected: Int, button: Int, offset: Offset, value: Long): ExitTransition = runBlocking {
        if (buttonSelected == button){
            when (button) {
                Screens.Profil.key -> slideOutHorizontally( targetOffsetX = {(0).toInt()}, animationSpec = tween(340) ) + fadeOut( targetAlpha = 0.02F, animationSpec = tween(400) )
                in Screens.Difficulty1.key..Screens.Difficulty5.key -> slideOutVertically(targetOffsetY = {offset.y.toInt() * -1}, animationSpec = tween(value.toInt()))
                else -> fadeOut(targetAlpha = 0F, animationSpec = tween(310))
            }
        }
        else {
            when (button) {
                Screens.Profil.key -> {
                    fadeOut(animationSpec = tween(250))
                }
                in Screens.Difficulty1.key..Screens.Difficulty5.key -> {
                    if (buttonSelected in Screens.Difficulty1.key..Screens.Difficulty5.key) {
                        if (buttonSelected > button) fadeOut(animationSpec = tween(250))
                        else {
                            if (button == Screens.Difficulty2.key || button == Screens.Difficulty4.key ) slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                            else slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                        }
                    }
                    else {
                        if (button == Screens.Difficulty2.key || button == Screens.Difficulty4.key ) slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                        else slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
                    }
                }
                Screens.Creator.key -> {
                    slideOutHorizontally( targetOffsetX = {(-300).toInt()}, animationSpec = tween(300) ) + fadeOut( targetAlpha = 0.02F, animationSpec = tween(400) )
                }
                Screens.Donation.key -> {
                    slideOutVertically(targetOffsetY = { 350}, animationSpec = tween(300)) + fadeOut( targetAlpha = 0.02F, animationSpec = tween(400) )
                }
                Screens.Config.key -> {
                    slideOutHorizontally( targetOffsetX = {(150).toInt()}, animationSpec = tween(300) ) + fadeOut( targetAlpha = 0.02F, animationSpec = tween(400) )
                }
                else -> fadeOut(animationSpec = tween(250))
            }
        }
    }
}