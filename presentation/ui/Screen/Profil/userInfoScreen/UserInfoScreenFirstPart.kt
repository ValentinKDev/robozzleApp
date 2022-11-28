package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import backColor
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.redDark3
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposable
import com.mobilegame.robozzle.presentation.ui.utils.padding.PaddingComposable

@Composable
fun UserInfoScreenFirstPart(vm: UserInfosScreenViewModel, navigator: Navigator) {
    Column(Modifier.fillMaxSize()) {
        /** Logout Button **/
        PaddingComposable(
            startPaddingRatio = vm.uiData.firstPart.buttonLogOut.padding.start,
        ) {
            CenterComposable {
                Card(
                    shape = MaterialTheme.shapes.large,
                    elevation = vm.uiData.firstPart.buttonLogOut.sizes.elevation,
                    backgroundColor = vm.uiData.firstPart.buttonLogOut.colors.background,
                    modifier = Modifier
                        .backColor(redDark3)
                        .height(vm.uiData.firstPart.buttonLogOut.sizes.heightDp)
                        .width(vm.uiData.firstPart.buttonLogOut.sizes.widthDp)
                        .clickable {
                            vm.logic.loggingOut(navigator)
                        }
                ) {
                    CenterComposable {
                        Text(
                            text = vm.uiData.firstPart.buttonLogOut.text,
                            color = vm.uiData.firstPart.buttonLogOut.colors.text,
                            fontSize = vm.uiData.firstPart.buttonLogOut.sizes.textSp,
                        )
                    }
                }
            }
        }
    }
    /** User name header **/
    CenterComposable {
        Card(
            shape = MaterialTheme.shapes.medium,
            backgroundColor = vm.uiData.firstPart.header.colors.background,
            elevation = vm.uiData.firstPart.header.sizes.elevation,
            modifier = Modifier
                .height(vm.uiData.firstPart.header.sizes.heightDp)
                .width(vm.uiData.firstPart.header.sizes.widthDp)
            ,
        ) {
            CenterComposable {
                Text(
                    text = "${vm.logic.name}",
                    color = vm.uiData.firstPart.header.colors.text,
                )
            }
        }
    }
}