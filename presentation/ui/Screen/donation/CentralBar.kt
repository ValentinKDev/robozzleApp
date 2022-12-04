package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.TextField
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel

@Composable
fun centralBar(vm: DonationScreenViewModel) {
    val output by remember { vm.logic.output }.collectAsState()
//    val unfold by remember { vm.logic.unfold }.collectAsState()
    val selection by remember { vm.logic.selection }.collectAsState()

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(vm.ui.selector.sizes.heightDp)
            .onFocusChanged {
                infoLog("centralBar", "on focus changed $it")
                if (it.hasFocus) {
                    infoLog("centralBar", "unfold")
//                    vm.logic.unfold()
                    vm.logic.handleSelectorFocus()
                } else {

                    infoLog("centralBar", "fold")
                    vm.logic.handleSelectorUnfocus()
                }
            }
            .background(vm.ui.selector.colors.background)
        ,
        value = output,
        onValueChange = { vm.logic.handleInput(it) },
        trailingIcon = {
            IconButton(
                onClick = {
                    if (selection) {
                        vm.logic.handleCopy()
                    }
                }
            ){
                val image = if (selection) Icons.Default.ContentCopy else Icons.Outlined.Edit
                val description = if (selection) "copyIcon" else "editIcon"
                Icon(imageVector  = image, contentDescription = description)
            }
        }
//        textStyle = TextStyle(
//            textAlign = TextAlign.Center
//        ),
    )
}