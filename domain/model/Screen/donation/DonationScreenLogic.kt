package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.infoLog
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DonationScreenLogic(): ViewModel() {

    val scroll: ScrollState = ScrollState(0)
    private val ref = DonationScreenText.inputField + " "
    private val ref1 = DonationScreenText.ref1
    private val ref2 = DonationScreenText.ref2


    private val _input = MutableStateFlow<String>("")
    val input: StateFlow<String> = _input.asStateFlow()
    fun handleInput(input: String) {
        viewModelScope.launch(Dispatchers.Default) {
//            if (input.contains(ref1) || input.contains(ref2)) {
//                input.forEachIndexed { index, c ->
//                    infoLog(index.toString(), c.toString())
//                    if (c != ref[index]) {
//                        _input.value = c.toString()
//                        return@forEachIndexed
//                    }
//                }
//            }
//            else
                _input.value = input.trim()
        }
    }

    fun showList(): List<String> {
        return if (_input.value.isEmpty()) {
            DonationScreenText.listNetworkCoin
        } else {
            DonationScreenText.listNetworkCoin.filter { it.contains(_input.value, ignoreCase = true) }
        }
    }

    private val _snackShared = MutableSharedFlow<Int>()
    val snackShared = _snackShared.asSharedFlow()

    fun triggerSnackBar() {
        viewModelScope.launch {
            _snackShared.emit(1)
        }
    }

    private val _textSelected = MutableStateFlow<String>(DonationScreenText.textSelection)
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
    fun fold() {_unfold.value = false}

}