package com.mobilegame.robozzle.presentation.ui.Screen.Profil.userInfoScreen

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.mobilegame.robozzle.domain.model.User.UserInfosScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.grayDark4
import com.mobilegame.robozzle.presentation.res.MyColor.Companion.green10
import com.mobilegame.robozzle.presentation.ui.utils.CenterComposableVertically

@Composable
fun UserInfoScreenSecondPart(vm: UserInfosScreenViewModel) {

    Row(
        horizontalArrangement = Arrangement.SpaceEvenly,
        modifier = Modifier
            .fillMaxSize()
//            .background(green10)
    ) {
        CenterComposableVertically {
            listSorter(
                vm = vm,
                text = "${vm.logic.allLevelWinSize} level resolved",
                type = ListSorterType.All
            )
        }
        CenterComposableVertically {
            listSorter( vm = vm, text = "1", type = ListSorterType.Diff1 )
        }
        CenterComposableVertically {
            listSorter( vm = vm, text = "2", type = ListSorterType.Diff2 )
        }
        CenterComposableVertically {
            listSorter( vm = vm, text = "3", type = ListSorterType.Diff3 )
        }
        CenterComposableVertically {
            listSorter( vm = vm, text = "4", type = ListSorterType.Diff4 )
        }
        CenterComposableVertically {
            listSorter( vm = vm, text = "5", type = ListSorterType.Diff5 )
        }
    }
}

enum class ListSorterType {
    All, Diff1, Diff2, Diff3, Diff4, Diff5
}

@Composable
fun listSorter(vm: UserInfosScreenViewModel, text: String, type: ListSorterType ) {
    CenterComposableVertically {
        Card(
            shape = MaterialTheme.shapes.medium,
            elevation = vm.uiData.secondPart.allLevel.padidng.bordersDp,
            backgroundColor = grayDark4,
            modifier = Modifier.clickable {
                vm.logic.handleClickListSorter(type)
            }
        ) {
            Text(
                text = text,
                fontSize = vm.uiData.secondPart.allLevel.sizes.textSp,
                color = vm.uiData.secondPart.allLevel.colors.text,
                modifier = Modifier
                    .padding(
                        start = vm.uiData.secondPart.allLevel.padidng.bordersDp,
                        end = vm.uiData.secondPart.allLevel.padidng.bordersDp,
                        top = vm.uiData.secondPart.allLevel.padidng.bordersVertical,
                        bottom = vm.uiData.secondPart.allLevel.padidng.bordersVertical,
                    )
            )
        }
    }
}

