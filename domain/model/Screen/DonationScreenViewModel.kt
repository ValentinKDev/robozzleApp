package com.mobilegame.robozzle.domain.model.Screen

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.compose.ui.platform.LocalContext
import androidx.core.content.ContextCompat.getSystemService
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DonationScreenViewModel(): ViewModel() {
    private val _scrolingBar = MutableStateFlow<Boolean>(false)
    val scrolingBar: StateFlow<Boolean> = _scrolingBar
    fun foldUnfold() { _scrolingBar.value = !_scrolingBar.value}

    val loremIpsum = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Sed non risus. Suspendisse lectus tortor, dignissim sit amet, adipiscing nec, ultricies sed, dolor. Cras elementum ultrices diam. Maecenas ligula massa, varius a, semper congue, euismod non, mi. Proin porttitor, orci nec nonummy molestie, enim est eleifend mi, non fermentum diam nisl sit amet erat. Duis semper. Duis arcu massa, scelerisque vitae, consequat in, pretium a, enim. Pellentesque congue. Ut in risus volutpat libero pharetra tempor. Cras vestibulum bibendum augue. Praesent egestas leo in pede. Praesent blandit odio eu enim. Pellentesque sed dui ut augue blandit sodales. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae; Aliquam nibh. Mauris ac mauris sed pede pellentesque fermentum. Maecenas adipiscing ante non diam sodales hendrerit."

    private val _textSelected = MutableStateFlow<String>(loremIpsum.substring(startIndex = 0, endIndex = 50))
    val textSelected: StateFlow<String> = _textSelected
    fun setTextSelectedTo(text: String) {
        _textSelected.value = text
    }

    private val _snackShared = MutableSharedFlow<Int>()
    val snackShared = _snackShared.asSharedFlow()
//    private val _snackShared = MutableStateFlow<Int>(0)
//    val snackShared: StateFlow<Int> = _snackShared
    

    val list = listOf<String>("one", "two", "tree", "four", "five", "six", "seven", "eight", "nine", "ten", "eleven", "twelve", "thirteen", "fourteen", "fithteen", "sixteen", "seventeen", "eighteen", "nineteenl", "twenty")

    fun clipAndToast(context: Context) {
        val clip = ClipData.newPlainText("address", textSelected.value)
        val myClipboard : ClipboardManager = context.getSystemService(CLIPBOARD_SERVICE) as ClipboardManager //as ClipboardManager

        myClipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    fun triggerSnackBar() {
        viewModelScope.launch {
            _snackShared.emit(1)
        }
    }
}