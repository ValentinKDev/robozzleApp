package com.mobilegame.robozzle.presentation.ui.Screen.Profil.register_login

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.ButtonColors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.RegisterLoginViewModel
import com.mobilegame.robozzle.domain.model.Screen.Tuto.Tuto
import com.mobilegame.robozzle.domain.model.Screen.Tuto.matchStep
import com.mobilegame.robozzle.presentation.res.MyColor
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.utils.CenterText
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun ButtonRegisterLogin(enable: Boolean, text: String, clickable: Modifier) {
    Box(Modifier.fillMaxWidth()) {
        Box(
            Modifier
                .clip(RoundedCornerShape(5.dp))
                .height(50.dp)
                .width(150.dp)
                .align(Alignment.Center)
                .background(Color.Gray)
                .then(clickable)
                .background(if (enable) MaterialTheme.colors.secondaryVariant else MyColor.grayDark4)
        ) {
            Box( Modifier.fillMaxSize().background(MyColor.black6))
            PaddingComposable(
                topPaddingRatio = 0.1F,
                bottomPaddingRatio = 0.1F,
                startPaddingRatio = 0.03F,
                endPaddingRatio = 0.03F,
            ) {
                Box(
                    Modifier
                        .clip(RoundedCornerShape(5.dp))
                        .fillMaxSize()
                        .background(if (enable) MyColor.white9 else Color.Transparent)
                ) {
                    CenterText(
                        text = text,
                        color = if (enable) MyColor.whiteDark3 else MyColor.whiteDark6
                    )
                }
            }
        }
    }
}