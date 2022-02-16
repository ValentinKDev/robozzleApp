package com.mobilegame.robozzle.domain.model.Screen

import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonId
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonSelected
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.ButtonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class MainScreenViewModel(): ViewModel() {
    private val _visibleElements = MutableStateFlow<Boolean>(false)
    val visibleElements: StateFlow<Boolean> = _visibleElements
    fun changeVisibility() {_visibleElements.value = !_visibleElements.value}

    private val _buttonSelected = MutableStateFlow<Int>(ButtonId.None.key)
    val buttonSelected: StateFlow<Int> = _buttonSelected
    fun updateButtonSelected(buttonSelected: Int) {
        _buttonSelected.value = buttonSelected
    }

    private var animationEnd =false

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