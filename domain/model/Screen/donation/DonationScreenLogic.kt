package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.ScrollState
import androidx.compose.ui.focus.FocusManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreenText
import com.mobilegame.robozzle.utils.Extensions.getDiffChar
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class DonationScreenLogic(): ViewModel() {

    val scroll: ScrollState = ScrollState(0)
    val text = DonationScreenText

    private val _output = MutableStateFlow<String>("")
    val output: StateFlow<String> = _output.asStateFlow()
    private fun setInputTo(str: String) { _output.value = str }

    fun handleInput(input: String) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isSelectionActive()) {
                setInputTo( input.getDiffChar(selectedItem.second.cutString()).toString() )
                setSelectionTo(false)
                unfold()
            }
            else setInputTo(input.trim())
            updateList()
            verbalLog("DonationScreenVMlogic::handleInput", "input -$input-")
        }
    }

    private val _list = MutableStateFlow<List<String>>(text.listNetworkCoin)
    val list: StateFlow<List<String>> = _list.asStateFlow()
    fun updateList() {
        _list.value = text.listNetworkCoin.filter { it.contains(_output.value, ignoreCase = true) }
    }

    private val _errorInput = MutableStateFlow<Boolean>(false)
    val errorInput: StateFlow<Boolean> = _errorInput.asStateFlow()
    fun isInputError() {
        viewModelScope.launch(Dispatchers.IO) {
            //find input in list
        }
    }

    private val _selection = MutableStateFlow<Boolean>(false)
    val selection: StateFlow<Boolean> = _selection.asStateFlow()
    private fun setSelectionTo(state: Boolean) {_selection.value = state}
    private fun isSelectionActive(): Boolean = _selection.value
    var selectedItem: Pair<String, String> = Pair("", "")
    private fun String.cutString(): String = this.filterIndexed {index, c -> index < 30 } + "..."

    private val _unfold = MutableStateFlow<Boolean>(false)
    val unfold: StateFlow<Boolean> = _unfold.asStateFlow()

    fun foldUnfold() { _unfold.value = !_unfold.value}
    fun fold() {_unfold.value = false}
    fun unfold() {_unfold.value = true}

    fun handleSelectorFocus() { unfold() }
    fun handleSelectorUnfocus() { fold() }

    fun handleCopy(context: Context, focusManager: FocusManager) {
        val clip = ClipData.newPlainText("address", selectedItem.second)
        val myClipboard : ClipboardManager = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        focusManager.clearFocus()
        myClipboard.setPrimaryClip(clip)
        Toast.makeText(context, "Full address copied", Toast.LENGTH_SHORT).show()
    }

    fun handleItemClick(element: String) {
        verbalLog("DonationScreenVMlogic::handleItemClick", "Start")
        setSelectionTo(true)
        selectedItem = Pair(element, text.mapWalletAddress.getValue(key = element))
        setInputTo(selectedItem.second.cutString())
        foldUnfold()
//        setTextSelectedTo(element)
    }
}