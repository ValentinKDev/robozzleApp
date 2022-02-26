package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.ViewModel
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.analyse.verbalLog
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ScreenDimensionsDataStoreViewModel(
    context: Context
): ViewModel() {
    val service = DataStoreService.createScreenDataService(context)

    val widthPixels = context.resources.displayMetrics.widthPixels
    val heightPixels = context.resources.displayMetrics.heightPixels
    val density = context.resources.displayMetrics.density

    init {
        errorLog("Store Dimension Density", "start")
        verbalLog("widthPixels", context.resources.displayMetrics.widthPixels.toString())
        verbalLog("heightPixels", context.resources.displayMetrics.heightPixels.toString())
        verbalLog("density", context.resources.displayMetrics.density.toString())
        verbalLog("scaleDensity", context.resources.displayMetrics.scaledDensity.toString())
        verbalLog("xdpi", context.resources.displayMetrics.xdpi.toString())
        verbalLog("ydpi", context.resources.displayMetrics.ydpi.toString())
        verbalLog("densityDpi", context.resources.displayMetrics.densityDpi.toString())
    }

    fun storeDimensionDensity() = runBlocking(Dispatchers.IO) {
        service.putInt(KeyProvider.WidthPixels.key, widthPixels)
        service.putInt(KeyProvider.HeigthPixel.key, heightPixels)
        service.putFloat(KeyProvider.Density.key, density)
    }
}