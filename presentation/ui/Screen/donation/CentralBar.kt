package com.mobilegame.robozzle.presentation.ui.Screen.donation

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ContentCopy
import androidx.compose.material.icons.outlined.Edit
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.mobilegame.robozzle.domain.model.Screen.donation.DonationScreenViewModel
import com.mobilegame.robozzle.presentation.res.MyColor

@Composable
fun centralBar(vm: DonationScreenViewModel) {
    val output by remember { vm.logic.output }.collectAsState()
    val unfold by remember { vm.logic.unfold }.collectAsState()
    val selection by remember { vm.logic.selection }.collectAsState()
    val context = LocalContext.current
    val focusManager = LocalFocusManager.current

    TextField(
        modifier = Modifier
            .fillMaxWidth()
            .height(vm.ui.selector.sizes.heightDp)
//            .shadow(20.dp)
            .onFocusChanged {
                if (it.hasFocus)
                    vm.logic.handleSelectorFocus()
                else
                    vm.logic.handleSelectorUnfocus()
            }
        ,
        value = output,
        onValueChange = { vm.logic.handleInput(it) },
        trailingIcon = {
            IconButton( onClick = { if (selection) vm.logic.handleCopy(context, focusManager) } ) {
                val image = if (selection) Icons.Default.ContentCopy else Icons.Outlined.Edit
                val description = if (selection) "copyIcon" else "editIcon"
                Icon(imageVector  = image, contentDescription = description, tint = MyColor.greenSecondVariant)
            }
        },
        singleLine = true,
        colors = TextFieldDefaults.textFieldColors(
            backgroundColor = vm.ui.selector.colors.background,
            focusedIndicatorColor = vm.ui.selector.colors.focusIndicator,
            cursorColor = if (selection) Color.Transparent else vm.ui.selector.colors.cursor,
            unfocusedIndicatorColor = Color.Transparent,
        ),
        shape = RoundedCornerShape(
            topStart = if (unfold) 0.dp.value else vm.ui.selector.shape.corner.value,
            topEnd = if (unfold) 0.dp.value else vm.ui.selector.shape.corner.value,
            bottomStart = vm.ui.selector.shape.corner.value,
            bottomEnd = vm.ui.selector.shape.corner.value,
        ),
        textStyle = TextStyle(color = vm.ui.selector.colors.text)
    )
}