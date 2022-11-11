package com.mobilegame.robozzle.domain.model.Screen.Config

import androidx.compose.animation.core.MutableTransitionState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.runBlocking

class AnimateHeaderAndListViewModel {
    private val _visibleHeaderState = MutableStateFlow(MutableTransitionState(true))
    val visibleHeaderState = _visibleHeaderState.asStateFlow()
    fun setVisibleHeaderTargetStateAs(state: Boolean) {_visibleHeaderState.value.targetState = state}
    fun headerAnimationEnd(): Boolean = !_visibleHeaderState.value.targetState && !_visibleHeaderState.value.currentState

    private val _visibleListState = MutableStateFlow(MutableTransitionState(false))
    val visibleListState = _visibleListState.asStateFlow()
    fun setVisibleListTargetStateAs(state: Boolean) {_visibleListState.value.targetState = state}
    fun listAnimationEnd(): Boolean = !_visibleListState.value.targetState && !_visibleListState.value.currentState

    fun setVisible() {
        setVisibleHeaderTargetStateAs(true)
        setVisibleListTargetStateAs(true)
    }
}