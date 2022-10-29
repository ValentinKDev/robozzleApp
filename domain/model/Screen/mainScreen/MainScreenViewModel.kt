package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.ButtonState
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.Screens.Companion.isLevelByDiff
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import com.mobilegame.robozzle.utils.Extensions.addNavArg
import io.ktor.util.date.*
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
    fun changeVisibility() {
        _visibleElements.value = !_visibleElements.value
    }

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

    var timerEndAnim: Long? = null
    fun changeScreen(navigator: Navigator, info: NavigationButtonInfo) {
        verbalLog("MainScreenViewModel::clickHandler", "info.button = ${info.button.route}")

        NavViewModel(navigator).navigateToScreen( screen = info.button, )
    }
    fun clickHandler(navigator: Navigator, info: NavigationButtonInfo) {
        updateButtonSelected(info.button)
        animTriggeredButton.setAnimationTime(info.button)
        changeVisibility()
    }

}