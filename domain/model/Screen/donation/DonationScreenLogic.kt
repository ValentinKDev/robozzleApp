package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.foundation.ScrollState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreenText
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

//    private val _visibleListState = MutableStateFlow(MutableTransitionState(false))
//    val visibleListState = _visibleListState.asStateFlow()
//    fun setVisibleListTargetStateAs(state: Boolean) {_visibleListState.value.targetState = state}
//    fun listAnimationEnd(): Boolean = !_visibleListState.value.targetState && !_visibleListState.value.currentState
//private val _unfoldState = MutableStateFlow(MutableTransitionState(false))
//    val unfoldState = _unfoldState.asStateFlow()
    private val _unfold = MutableStateFlow(false)
    val unfold = _unfold.asStateFlow()

//    fun foldAnimationEnd(): Boolean = !_unfold.value.targetState && !_unfold.value.currentState
//    fun unfoldAnimationEnd(): Boolean = _unfold.value.targetState && _unfold.value.currentState
//    fun foldUnfold() { _unfold.value.targetState = !_unfold.value.targetState}
//    fun fold() {_unfold.value.targetState = false}
    fun foldUnfold() { _unfold.value = !_unfold.value}
    fun fold() {_unfold.value = false}

}