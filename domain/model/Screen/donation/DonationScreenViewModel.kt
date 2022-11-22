package com.mobilegame.robozzle.domain.model.Screen.donation

import android.app.Application
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.presentation.ui.Screen.donation.DonationScreenLayout
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DonationScreenViewModel(application: Application): AndroidViewModel(application) {

    val logic = DonationScreenLogic()
    val data = DonationScreenData()
    val ui = DonationScreenLayout.create(getApplication())
//    val dimensions = DonationScreenRatios
}