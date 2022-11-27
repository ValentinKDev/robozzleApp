package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.provider.Settings
import android.content.Context
import android.content.Intent
import androidx.activity.compose.BackHandler
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.*
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.North
//import androidx.compose.material.icons.outlined.Update
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.R
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.LevelsScreenByDifficulty
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.MainScreen.MainScreenLayout
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.GifImage
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable


@Composable
fun CreatorScreen(navigator: Navigator, vm: TestVM = viewModel()) {
    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Donation.route)
    }

    val ctxt = LocalContext.current
//    val vm = TestVM(ctxt)
    val transition = rememberInfiniteTransition()
    val translateAnim by transition.animateColor(
        initialValue = MyColor.grayDark3,
        targetValue = MyColor.whiteDark4,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 1000, easing = LinearEasing),
            RepeatMode.Reverse
        )
    )

    Box (
        Modifier
            .fillMaxSize()
//            .background(MyColor.white0)
    ) {
        Row {
            PaddingComposable(
                topPaddingRatio = 0.3F,
                bottomPaddingRatio = 0.5F,
                startPaddingRatio = 0.1F,
                endPaddingRatio = 0.1F
            ) {
                Box(
                    modifier = Modifier.background(Color.Transparent)
                ) {
                    Box {
                        GifImage(data = R.drawable.shialabeouf)
                            CenterComposable {
                                Text(
                                    modifier = Modifier,
                                    color = MyColor.whiteDark4,
                                    text = "Congrats win level truc machin"
                                )
                            }
                    }
                }
            }
        }
    }
}

class TestVM() : ViewModel() {
    fun up(context: Context, number: Int) {
        var permission = Settings.System.canWrite(context)
        if (!permission) {
            errorLog("persmission" , "$permission")
            val intent = Intent(Settings.ACTION_MANAGE_WRITE_SETTINGS)
            context.startActivity(intent)
        }
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS_MODE,
            Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL
        )
        Settings.System.putInt(
            context.contentResolver,
            Settings.System.SCREEN_BRIGHTNESS,
            number
        )
//        Settings.System.SCREEN_BRIGHTNESS
    }
}
