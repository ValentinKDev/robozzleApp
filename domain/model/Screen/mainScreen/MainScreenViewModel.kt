package com.mobilegame.robozzle.domain.model.Screen.mainScreen

import android.app.Application
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.AndroidViewModel
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.TutoViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.domain.model.data.animation.MainMenuAnimationViewModel
import com.mobilegame.robozzle.domain.model.data.store.PopUpState
import com.mobilegame.robozzle.domain.model.data.store.ScreenDataStoreViewModel
import com.mobilegame.robozzle.domain.model.data.store.UserDataStoreViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenLayout
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.button.MainScreenButtonStyle
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.Screen.matchKey
import com.mobilegame.robozzle.presentation.ui.button.NavigationButtonInfo
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class MainScreenViewModel(application: Application): AndroidViewModel(application) {
    //todo: protect the user from pressed Back button during the animation
    val data = MainScreenData()
    val ui = MainScreenLayout.create()
    val tutoVM = TutoViewModel(application)
    val popupState: PopUpState = ScreenDataStoreViewModel(getApplication<Application>().applicationContext).getPopupState()
    val popup = PopupViewModel(popupState != PopUpState.None)
    val animTriggeredButton = MainMenuAnimationViewModel()

    //todo: make Not registered illegal name
    val noUser = "Not registered"
    val userName = UserDataStoreViewModel(getApplication()).getName() ?: noUser
    fun getName(): String = if (userCreated()) userName else noUser
    fun getNavInfoToUser(): NavigationButtonInfo =
        if (userName == noUser) MainScreenButtonStyle.RegisterLogin.type
        else MainScreenButtonStyle.UserInfos.type
    fun userCreated(): Boolean = userName != noUser

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

    fun changeScreen(navigator: Navigator, info: NavigationButtonInfo) {
        verbalLog("MainScreenViewModel::clickHandler", "info.button = ${info.button.route}")
        upDateTuto()
        NavViewModel(navigator).navigateToScreen( screen = info.button, )
    }

    fun clickHandler(info: NavigationButtonInfo) {
        updateButtonSelected(info.button)
        animTriggeredButton.setAnimationTime(info.button)
        changeVisibility()
    }

    fun getPopupText(): String {
        return when (popupState) {
            PopUpState.None -> {
                "Popup"
            }
            PopUpState.Update -> {
                "An update is available"
            }
            PopUpState.ServerIssue -> {
                "There is an issue with our servers"
            }
        }
    }

    fun getButtonBackgroundColor(button: Screens, enable: Boolean): Color? {
        return if (tutoVM.isMainScreenTutoActivated()) {
            when {
//                button.matchKey(Screens.Profile) && tutoVM.tuto.matchStep(Tuto.ClickOnProfile) -> null
                button.matchKey(Screens.Profile) && tutoVM.getTuto().matchStep(Tuto.ClickOnProfile) -> null
                button.matchKey(Screens.Difficulty1)
//                        && ( tutoVM.tuto != Tuto.ClickOnProfile && tutoVM.tuto != Tuto.End ) -> null
                        && ( tutoVM.getTuto() != Tuto.ClickOnProfile && tutoVM.getTuto() != Tuto.End ) -> null
//                button.matchKey(Screens.Difficulty1) && tutoVM.tuto.matchStep(Tuto.ClickOnDifficultyOne) -> null
                else -> ui.button.colors.enableBackground
            }
        } else {
            if (enable)
                ui.button.colors.enableBackground
            else
                ui.button.colors.disableBackground
        }
    }

    fun getButtonTextColor(button: Screens, enable: Boolean): Color? {
        return if (tutoVM.isMainScreenTutoActivated()) {
            when {
//                button.matchKey(Screens.Profile) && tutoVM.tuto.matchStep(Tuto.ClickOnProfile) -> null
                button.matchKey(Screens.Profile) && tutoVM.getTuto().matchStep(Tuto.ClickOnProfile) -> null
                button.matchKey(Screens.Difficulty1)
//                        && ( tutoVM.tuto != Tuto.ClickOnProfile && tutoVM.tuto != Tuto.End ) -> null
                        && ( tutoVM.getTuto() != Tuto.ClickOnProfile && tutoVM.getTuto() != Tuto.End ) -> null
                else -> ui.button.colors.enableText
            }
        }
        else {
            if (enable)
                ui.button.colors.enableText
            else
                ui.button.colors.disableText
        }
    }

    fun isButtonClickEnable(button: Screens): Boolean {
       return if (tutoVM.isMainScreenTutoActivated()) {
           when {
//               button.matchKey(Screens.Profile) && tutoVM.tuto.matchStep(Tuto.ClickOnProfile) -> true
               button.matchKey(Screens.Profile) && tutoVM.getTuto().matchStep(Tuto.ClickOnProfile) -> true
               button.matchKey(Screens.Difficulty1)
//                       && ( tutoVM.tuto != Tuto.ClickOnProfile && tutoVM.tuto != Tuto.End ) -> true
                       && ( tutoVM.getTuto() != Tuto.ClickOnProfile && tutoVM.getTuto() != Tuto.End ) -> true
               else -> false
           }
       } else
           true
    }

    fun upDateTuto() {
//        if (tutoVM.tuto.matchStep(Tuto.ClickOnProfile) && userCreated()) tutoVM.nextStep()
//        if (tutoVM.tuto.matchStep(Tuto.ClickOnDifficultyOne) && buttonSelected.value == Screens.Difficulty1) tutoVM.nextStep()
        if (tutoVM.getTuto().matchStep(Tuto.ClickOnProfile) && userCreated()) tutoVM.nextTuto()
        if (tutoVM.getTuto().matchStep(Tuto.ClickOnDifficultyOne) && buttonSelected.value == Screens.Difficulty1) tutoVM.nextTuto()
    }
}