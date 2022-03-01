package com.mobilegame.robozzle.domain.model.data.animation

import androidx.compose.animation.*
import androidx.compose.animation.core.tween
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import kotlinx.coroutines.runBlocking

class MainMenuAnimationViewModel(): ViewModel() {
    @ExperimentalAnimationApi
    fun enterTransitionByFrom(id: Int, from: Int): EnterTransition = runBlocking {
        when (from) {
            MainMenuButton.None.key -> {
                when (id) {
                    0   ->       { slideInHorizontally( initialOffsetX = { +500 }, animationSpec = tween(400) ) }
                    in 1..5 ->   { slideInHorizontally( initialOffsetX = {if (id == 4 || id == 2) +500 else -500 }, animationSpec = tween (500) ) }
                    6 -> { slideInHorizontally( initialOffsetX = { -200 }, animationSpec = tween(400) ) }
                    7 -> { slideInVertically( initialOffsetY = { -200 }, animationSpec = tween(400) ) }
                    8 -> { slideInHorizontally( initialOffsetX = { +200 }, animationSpec = tween(400) ) }
                    else -> {
                        fadeIn()
                    }
                }
            }
            else -> {
                when {
                    id == from -> {
                        slideInVertically(
                            animationSpec = tween (500),
                            initialOffsetY = {
                                -150 }
                        ) + fadeIn(animationSpec = tween(300))
                    }
                    id in (from + 1)..5 -> {
                        slideInHorizontally(
                            initialOffsetX = {if (id == 4 || id == 2) +500 else -500 },
                            animationSpec = tween (300)
                        ) + fadeIn(animationSpec = tween(300))
                    }
                    else -> fadeIn(animationSpec = tween(500))
                }
            }
        }
    }

    @ExperimentalAnimationApi
    fun exitTransitionByState(buttonState: ButtonState, id: Int, offset: Offset, animationTime: Long): ExitTransition = runBlocking {
        when (buttonState) {
            ButtonState.OnBottom -> slideOutVertically(
                targetOffsetY = {it + 50},
                animationSpec = tween(
                    when (id) {
                        in MainMenuButton.LevelDiff1.key..MainMenuButton.LevelDiff5.key -> animationTime
                        else -> -500
                    }.toInt()
                )
            )
            ButtonState.OnTop -> slideOutVertically(
                targetOffsetY = {
                    when (id) {
                        in MainMenuButton.LevelDiff1.key..MainMenuButton.LevelDiff5.key -> offset.y.toInt() * -1
                        else -> -500
                    }
                },
                animationSpec = tween(
                    when (id) {
                        in MainMenuButton.LevelDiff1.key..MainMenuButton.LevelDiff5.key -> animationTime
                        else -> -500
                    }.toInt()
                )
            )
            ButtonState.OnRightSide -> slideOutHorizontally(targetOffsetX = { +500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
            ButtonState.OnLeftSide -> slideOutHorizontally(targetOffsetX = { -500 }, animationSpec = tween(400)) + fadeOut(animationSpec = tween(400))
            else -> fadeOut(animationSpec = tween(250))
        }
    }
}