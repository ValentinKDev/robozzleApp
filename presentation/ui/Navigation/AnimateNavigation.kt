package com.mobilegame.robozzle.presentation.ui.Navigation

import androidx.compose.animation.*
import androidx.compose.runtime.*
import com.mobilegame.robozzle.domain.model.Screen.Navigation.AnimateNavViewModel
import com.mobilegame.robozzle.presentation.ui.Screen.Screens

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun AnimateNavigation(
    vm: AnimateNavViewModel,
    enterTransition: EnterTransition = fadeIn(),
    exitTransition: ExitTransition = fadeOut(),
    onBackTransition: ExitTransition = fadeOut(),
    element: Screens,
    content: @Composable () -> Unit,
) {
    val visibleElement by remember(vm) {vm.visibleElement}.collectAsState(false)

    LaunchedEffect(true) {
        vm.updateVisibleElementAs(element)
    }
    AnimatedVisibility(
        visible = visibleElement == element,
        enter = enterTransition,
        exit = exitTransition,
    ){
        content.invoke()
    }
}