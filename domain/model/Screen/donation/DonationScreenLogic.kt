package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DonationScreenLogic(): ViewModel() {
    private val _snackShared = MutableSharedFlow<Int>()
    val snackShared = _snackShared.asSharedFlow()

    fun triggerSnackBar() {
        viewModelScope.launch {
            _snackShared.emit(1)
        }
    }

    private val _textSelected = MutableStateFlow<String>("")
    val textSelected: StateFlow<String> = _textSelected.asStateFlow()
    fun setTextSelectedTo(text: String) {
        _textSelected.value = text
    }

    fun clipAndToast(context: Context) {
        val clip = ClipData.newPlainText("address", textSelected.value)
        val myClipboard : ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager //as ClipboardManager

        myClipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Copied", Toast.LENGTH_SHORT).show()
    }

    private val _unfold = MutableStateFlow<Boolean>(false)
    val unfold: StateFlow<Boolean> = _unfold
    fun foldUnfold() { _unfold.value = !_unfold.value}

}