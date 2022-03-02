package com.mobilegame.robozzle.domain.model.Screen.donation

import android.content.Context
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.boundsInRoot
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.infoLog
import com.mobilegame.robozzle.data.configuration.donation.DonationScreenColors
import com.mobilegame.robozzle.data.configuration.donation.DonationScreenRatios
import com.mobilegame.robozzle.data.configuration.donation.DonationScreenText
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class DonationScreenData(): ViewModel() {

    val text = DonationScreenText

    val color = DonationScreenColors

    val ratios = DonationScreenRatios

    var listSize: Float? = null
    fun setListSize(layoutCoordinates: LayoutCoordinates, context: Context) = runBlocking(Dispatchers.IO) {
        listSize ?: run {
//        if (listSize == null) run {
            listSize = layoutCoordinates.boundsInRoot().height / context.resources.displayMetrics.density
        }
        infoLog("listSize", "$listSize")
    }

    init {
    }
}