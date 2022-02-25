package com.mobilegame.robozzle.domain.model.Screen

import androidx.compose.animation.core.tween
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(): ViewModel() {
    private val _visibleElements = MutableStateFlow<Boolean>(false)
    val visibleElements: StateFlow<Boolean> = _visibleElements.asStateFlow()
    fun changeVisibility() {_visibleElements.value = !_visibleElements.value}

    private val _buttonSelected = MutableStateFlow<Int>(ButtonId.None.key)
    val buttonSelected: StateFlow<Int> = _buttonSelected.asStateFlow()
    fun updateButtonSelected(buttonSelected: Int) {
        _buttonSelected.value = buttonSelected
    }

    private val _animationTime = MutableStateFlow<Long>(500)
    val animationTime: StateFlow<Long> = _animationTime
    fun setAnimationTime(buttonId: Int) {
        _animationTime.value = when (buttonId) {
            ButtonId.LevelDiff1.key -> 450
            ButtonId.LevelDiff2.key -> 520
            ButtonId.LevelDiff3.key -> 600
            ButtonId.LevelDiff4.key -> 680
            ButtonId.LevelDiff5.key -> 730
            else -> 500
        }
    }

    private val _offsetMap = MutableStateFlow<MutableMap<Int, Offset>>(mapOf<Int, Offset>().toMutableMap())
    val offsetMap = _offsetMap.asStateFlow()
    fun getOffset(buttonId: Int): Offset = offsetMap.value[buttonId] ?: Offset(0f,0f)
    fun setOffset(buttonId: Int, offset: Offset) {
        _offsetMap.value[buttonId] = offset
    }

    fun updateButtonStates(buttonStateById: Int): ButtonState {
        return when (buttonStateById) {
            ButtonId.LevelDiff1.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            ButtonId.LevelDiff2.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            ButtonId.LevelDiff3.key-> { stateFunctionOfSelectedButton(buttonStateById) }
            ButtonId.LevelDiff4.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            ButtonId.LevelDiff5.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            else -> if (buttonSelected.value == ButtonId.None.key) ButtonState.OnPlace else  ButtonState.Fade
        }
    }
    fun stateFunctionOfSelectedButton(button: Int): ButtonState {
        return when {
            buttonSelected.value == ButtonId.None.key -> ButtonState.OnPlace
//            button == buttonSelected.value -> ButtonState.OnTop
            button < buttonSelected.value -> ButtonState.Fade
            button > buttonSelected.value -> {
                if (button == 2 || button == 4)
                    ButtonState.OnRightSide
                else
                    ButtonState.OnLeftSide
            }
            else -> ButtonState.OnTop
        }
    }
}