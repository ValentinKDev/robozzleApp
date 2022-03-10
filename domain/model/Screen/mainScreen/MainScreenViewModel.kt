package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.button.MainMenuButton
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    val popup = PopupViewModel()
    val data = MainScreenData()

    val popupState: PopUpState = ScreenDataStoreViewModel(getApplication<Application>().applicationContext).getPopupState()
//    val popupState = ScreenDataStoreViewModel(context)

    private val _visibleElements = MutableStateFlow<Boolean>(false)
    val visibleElements: StateFlow<Boolean> = _visibleElements.asStateFlow()
    fun changeVisibility() {_visibleElements.value = !_visibleElements.value}

    private val _buttonSelected = MutableStateFlow<Int>(MainMenuButton.None.key)
    val buttonSelected: StateFlow<Int> = _buttonSelected.asStateFlow()
    fun updateButtonSelected(buttonSelected: Int) {
        _buttonSelected.value = buttonSelected
    }

    private val _animationTime = MutableStateFlow<Long>(500)
    val animationTime: StateFlow<Long> = _animationTime
    fun setAnimationTime(buttonId: Int) {
        _animationTime.value = when (buttonId) {
            MainMenuButton.LevelDiff1.key -> 450
            MainMenuButton.LevelDiff2.key -> 520
            MainMenuButton.LevelDiff3.key -> 600
            MainMenuButton.LevelDiff4.key -> 680
            MainMenuButton.LevelDiff5.key -> 730
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
            MainMenuButton.LevelDiff1.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            MainMenuButton.LevelDiff2.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            MainMenuButton.LevelDiff3.key-> { stateFunctionOfSelectedButton(buttonStateById) }
            MainMenuButton.LevelDiff4.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            MainMenuButton.LevelDiff5.key -> { stateFunctionOfSelectedButton(buttonStateById) }
            else -> if (buttonSelected.value == MainMenuButton.None.key) ButtonState.OnPlace else  ButtonState.Fade
        }
    }
    fun stateFunctionOfSelectedButton(button: Int): ButtonState {
        return when {
            buttonSelected.value == MainMenuButton.None.key -> ButtonState.OnPlace
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