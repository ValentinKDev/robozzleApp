package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    //todo: protect the user from pressed Back button during the animation
    val data = MainScreenData()
    val popupState: PopUpState = ScreenDataStoreViewModel(getApplication<Application>().applicationContext).getPopupState()
    val popup = PopupViewModel(popupState != PopUpState.None)
    val animTriggeredButton = MainMenuAnimationViewModel()

    //todo: make Not registered illegal name
    val noUser = "Not registered"
    val userName = UserDataStoreViewModel(getApplication()).getName() ?: noUser
    fun getName(): String = if (userName != noUser) userName else noUser
    fun getNavInfoToUser(): NavigationButtonInfo =
        if (userName == noUser) MainScreenButtonStyle.RegisterLogin.type
        else MainScreenButtonStyle.UserInfos.type

    private val _visibleElements = MutableStateFlow<Boolean>(false)
    val visibleElements: StateFlow<Boolean> = _visibleElements.asStateFlow()
    fun changeVisibility() {_visibleElements.value = !_visibleElements.value}


    private val _buttonSelected = MutableStateFlow<Screens>(Screens.None)
    val buttonSelected : StateFlow<Screens> = _buttonSelected.asStateFlow()
    private val _buttonSelectedId = MutableStateFlow<Int>(Screens.None.key)
    val buttonSelectedId: StateFlow<Int> = _buttonSelectedId.asStateFlow()
    fun updateButtonSelected(buttonSelected: Screens) {
        _buttonSelected.value = buttonSelected
        _buttonSelectedId.value = buttonSelected.key
    }

    private val _offsetMap = MutableStateFlow(mapOf<Screens, Offset>().toMutableMap())
    val offsetMap = _offsetMap.asStateFlow()
    fun getOffset(button: Screens): Offset = offsetMap.value[button] ?: Offset(0f,0f)
    fun setOffset(button: Screens, offset: Offset) {
        _offsetMap.value[button] = offset
    }

    fun clickHandler(navigator: Navigator, info: NavigationButtonInfo) {
        updateButtonSelected(info.button)
        animTriggeredButton.setAnimationTime(info.button)
        changeVisibility()
        verbalLog("MainScreenViewModel::clickHandler", "info.button = ${info.button.route}")
        NavViewModel(navigator).navigateTo(
            destination = info.route,
            argStr = info.arg,
            delayTiming = animTriggeredButton.animationTime.value
        )
    }

    fun stateFunctionOfSelectedButton(button: Screens, fromScreens: Screens): ButtonState {
        val buttonId = button.key
        var buttonState = ButtonState.Unknown

        infoLog("MainScreenVM::stateFunctionOfSelectedButton", "from ${fromScreens.key} button ${button.key} ")
        when {
            buttonId == buttonSelectedId.value -> buttonState = ButtonState.Selected
            button == fromScreens -> buttonState = ButtonState.From
            else -> buttonState = ButtonState.NotSelected
        }
//        when (buttonSelectedId.value) {
//            fromScreens.key -> {
//                buttonState = ButtonState.From
//            }
//            buttonId -> {
//                buttonState = ButtonState.Selected
//            }
//            in 1..5 -> {
//                buttonState = if (buttonId > buttonSelectedId.value) ButtonState.Fade else ButtonState.OnLeftSide
//            }
//            in 6..8 -> {
//                ButtonState.Fade
//            }
//            else -> {
//                errorLog("MainScreenViewModel::stateFunctionOfSelectionButton", "ERROR buttonSelected.value = ${buttonSelectedId.value}")
//            }
//        }
        return buttonState
    }

}