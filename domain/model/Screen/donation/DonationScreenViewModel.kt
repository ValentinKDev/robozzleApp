package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Context.CLIPBOARD_SERVICE
import android.widget.Toast
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch


class DonationScreenViewModel(): ViewModel() {

    val logic = DonationScreenLogic()
    val data = DonationScreenData()
    val dimensions = DonationScreenDimensions()

}