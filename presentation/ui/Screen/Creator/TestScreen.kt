package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import androidx.compose.animation.*
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.GenericShape
import androidx.compose.material.Button
import androidx.compose.material.Shapes
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Alignment.Companion.CenterHorizontally
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.geometry.center
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Outline
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.DonationScreenViewModel
import com.mobilegame.robozzle.domain.model.Screen.NavViewModel
import com.mobilegame.robozzle.presentation.ui.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.NavigationDestination
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import kotlinx.coroutines.flow.*
import kotlin.math.cos
import kotlin.math.sin

@ExperimentalAnimationApi
@Composable
fun TestScreen(navigator: Navigator, animator: Animator, levelDifficulty: Int) {
    val listVisible by remember { mutableStateOf(true) }
    val headerVisible by remember { mutableStateOf(true)}
//    val listVisible by animator.state.collectAsState()

    LaunchedEffect(key1 = "TestScreen") {
        animator.activate()
    }
    Column( modifier = Modifier
        .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .align(CenterHorizontally)
        ) {
            AnimatedVisibility(
                visible = headerVisible,
                enter = slideInVertically(initialOffsetY = {it + 100}),
                exit = slideOutVertically(targetOffsetY = {it - 100}),
                initiallyVisible = false,

                ) {
                Button(
                    modifier = Modifier
                        .width(450.dp)
                        .height(50.dp),
                    onClick = {
                        NavViewModel(navigator).navigateTo(Screens.Creator)
                    }
                ) {
                    Text("button ${levelDifficulty}")
                }
            }
            AnimatedVisibility(
                visible = listVisible,
                enter = expandVertically(),
                exit = slideOutVertically(),
                initiallyVisible = false,

            ) {

            LazyColumn {
                itemsIndexed(DonationScreenViewModel().list) { _, _element ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(60.dp)
                            .clickable {
                            }
                    ) { Text(text = _element) }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(2.dp)
                            .background(Color.Black)
                    ) { }
                }
            }
            }
        }
    }
}

class Animator {
//    private var _state: MutableSharedFlow<Boolean> = MutableSharedFlow()
//    var state: SharedFlow<Boolean> = _state.asSharedFlow()
    private val _state = MutableStateFlow<Boolean>(true)
    val state : StateFlow<Boolean> = _state.asStateFlow()

//    val buttonOrigin:

    fun activate() {
//        _state.tryEmit(true)
        _state.value = true
    }
//    suspend fun navig(destination: NavigationDestination, argumentStr: String = "") {
//        if (argumentStr.isEmpty()) _state.emit(destination.route)
//        else _state.emit(destination.route + "/" + argumentStr)
//    }
}
