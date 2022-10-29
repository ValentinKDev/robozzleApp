package com.mobilegame.robozzle.presentation.ui

import android.util.Log
import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.core.Transition
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.size
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import backColor
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.inGame.layouts.InGameFirstPart.size
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.presentation.res.darkerGreen
import com.mobilegame.robozzle.presentation.res.gray9
import com.mobilegame.robozzle.presentation.res.green10
import com.mobilegame.robozzle.presentation.res.redDark4
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

//todo : use a SaveStateViewModel to access and quickly display the app configuration that will be saved an other way more durably and load at app launch
@OptIn(ExperimentalAnimationApi::class)
@Composable
fun ConfigScreen(navigator: Navigator, vm: TestVM = viewModel()) {
//    val animVisibleState = remember { MutableTransitionState(false) }
    val animVisibleState = remember {vm.visibleState}.collectAsState()

    LaunchedEffect(key1 = true) {
        vm.setVisibleTargetStateAs(true)
//        animVisibleState.targetState = true
        Log.e("ConfigScreen", "Start")
        vm.update()
    }
    val backClicked by remember {vm.onBackClick}.collectAsState()
    BackHandler {
        vm.setVisibleTargetStateAs(false)
//        animVisibleState.targetState = false
        vm.setOnBackClickTo(true)
    }

    val visible by remember(vm){vm.visible}.collectAsState()
    val visible2 by remember(vm){vm.visible2}.collectAsState()

//    if (backClicked && !animVisibleState.targetState && !animVisibleState.currentState ) {
    if (backClicked && !animVisibleState.value.targetState && !animVisibleState.value.currentState ) {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Config.route)
    }
//    else infoLog("animVisibleState", ".\ntargetState\t${animVisibleState.targetState}\ncurrent\t${animVisibleState.currentState}\nonBack\t$backClicked")
    else infoLog("animVisibleState", ".\ntargetState\t${animVisibleState.value.targetState}\ncurrent\t${animVisibleState.value.currentState}\nonBack\t$backClicked")

    Row {
        Column() {
            Text(text = "Config")
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                onClick = {
                    vm.changeVisible()
//                vm.setVisible(false)
                },
            ) {
                Text(text = "1")
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                onClick = {
                    vm.changeVisible2()
//                vm.setVisible2(false)
                },
            ) {
                Text(text = "2")
            }
            Button(
                colors = ButtonDefaults.buttonColors(backgroundColor = Color.Transparent),
                onClick = {
//                vm.setVisibleTargetStateAs(false)
//                animVisibleState.targetState = false
//                animVisibleState.currentState = true
//                vm.handler()
                },
            ) {
                Text(text = "3")
            }
//        AnimatedVisibility(visibleState = animVisibleState) {
            AnimatedVisibility(visibleState = animVisibleState.value) {
                Column() {
                    AnimatedVisibility( visible = visible, enter = fadeIn(), exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .backColor(redDark4)
                            ,
                        ) {
                        }
                    }
                    AnimatedVisibility( visible = visible2, enter = fadeIn(), exit = fadeOut()
                    ) {
                        Box(
                            modifier = Modifier
                                .size(100.dp)
                                .backColor(darkerGreen)
                            ,
                        ) {
                        }
                    }
                }
            }
        }
        Column {
            Box(
                modifier = Modifier
                    .size(100.dp)
                    .backColor(gray9)
                ,
            ) { }
            Constraints()
        }
    }

}

class TestVM(): ViewModel() {
    private val _visibleState = MutableStateFlow(MutableTransitionState(false))
    val visibleState = _visibleState.asStateFlow()
    fun setVisibleTargetStateAs(state: Boolean) {_visibleState.value.targetState = state}

    private val _visible = MutableStateFlow<Boolean>(false)
    val visible: StateFlow<Boolean> = _visible.asStateFlow()
    fun setVisible(state: Boolean) { _visible.value = state }
    fun changeVisible() { _visible.value = !_visible.value }
    fun updateVisible() { _visible.value = true }

    private val _visible2 = MutableStateFlow<Boolean>(false)
    val visible2: StateFlow<Boolean> = _visible2.asStateFlow()
    fun setVisible2(state: Boolean) { _visible2.value = state }
    fun changeVisible2() { _visible2.value = !_visible2.value }
    fun updateVisible2() { _visible2.value = true }

    fun update(){
        updateVisible()
        updateVisible2()
    }

    fun handler() {
        setVisible(false)
    }

    private val _onBackClick = MutableStateFlow<Boolean>(false)
    val onBackClick: StateFlow<Boolean> = _onBackClick.asStateFlow()
    fun setOnBackClickTo(state: Boolean) { _onBackClick.value = state }

}