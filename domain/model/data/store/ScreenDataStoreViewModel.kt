package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.compose.ui.geometry.Rect
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.mobilegame.robozzle.analyse.errorLog
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

class ScreenDataStoreViewModel(
    context: Context,
//    val layoutCoordinates: LayoutCoordinates
): ViewModel() {
    val service = DataStoreService.createScreenDataService(context)

    val widthPixels = context.resources.displayMetrics.widthPixels
    val heightPixels = context.resources.displayMetrics.heightPixels
    val density = context.resources.displayMetrics.density
    val densityDpi = context.resources.displayMetrics.densityDpi

    init {
//        verbalLog("widthPixels", context.resources.displayMetrics.widthPixels.toString())
//        verbalLog("heightPixels", context.resources.displayMetrics.heightPixels.toString())
//        verbalLog("density", context.resources.displayMetrics.density.toString())
//        verbalLog("scaleDensity", context.resources.displayMetrics.scaledDensity.toString())
//        verbalLog("xdpi", context.resources.displayMetrics.xdpi.toString())
//        verbalLog("ydpi", context.resources.displayMetrics.ydpi.toString())
//        verbalLog("densityDpi", context.resources.displayMetrics.densityDpi.toString())
    }

//    fun configure(layoutCoordinates: LayoutCoordinates) = runBlocking(Dispatchers.IO) {
//        infoLog("configure", "start")
//        storeDimensionDensity()
//        storeWindowCoordinates(layoutCoordinates)
//        infoLog("configure", "end")
//    }

    fun storePopUpState(popupState: PopUpState) = runBlocking(Dispatchers.IO) {
        val popUpStateJson: String = Gson().toJson(popupState, PopUpState::class.java)
        service.putString(KeyProvider.PopupState.key, popUpStateJson)
    }

    fun getPopupState(): PopUpState = runBlocking(Dispatchers.IO) {
        errorLog("ScreenDataVM", "get PopupState")
        val popupStateJson = service.getString(KeyProvider.PopupState.key)
        storePopUpState(PopUpState.None)
        val statestr: PopUpState? = Gson().fromJson(popupStateJson, PopupState)
        errorLog("ScreenDataVM", "get PopupState = ${statestr.toString()}")
        statestr ?: PopUpState.None
    }

//    private suspend fun storeWindowCoordinates(layoutCoordinates: LayoutCoordinates) {
//        errorLog("storeLayout", "---------------------------------------------------------------------------")

//        val rect: Rect = layoutCoordinates.boundsInRoot()

//        val layoutCoordinatesJson: String = Gson().toJson(rect, Rect::class.java)
//        errorLog("storeLayout", "${layoutCoordinates.boundsInRoot()}")
//        errorLog("storeLayout", "$layoutCoordinatesJson")
//        service.putString(KeyProvider.WindowCoordinates.key, layoutCoordinatesJson)
//    }

//    private fun storeDimensionDensity() = runBlocking(Dispatchers.IO) {
//        service.putInt(KeyProvider.WidthPixels.key, widthPixels)
//        service.putInt(KeyProvider.HeigthPixel.key, heightPixels)
//        service.putFloat(KeyProvider.Density.key, density)
//        service.putInt(KeyProvider.DensityDpi.key, densityDpi)
//    }
//
//    fun getDensty(): Float = runBlocking(Dispatchers.IO) {
//        service.getFloat(KeyProvider.Density.key) ?: 0F
//    }
//
//    fun getWidthPixel(): Int = runBlocking(Dispatchers.IO) {
//        service.getInt(KeyProvider.WidthPixels.key) ?: 0
//    }
//
//    fun getHeightPixel(): Int = runBlocking(Dispatchers.IO) {
//        service.getInt(KeyProvider.HeigthPixel.key) ?: 0
//    }
//
//    fun getDenstyDpi(): Int = runBlocking(Dispatchers.IO) {
//        service.getInt(KeyProvider.DensityDpi.key) ?: 0
//    }
//
//    fun getWindowCoordinates(): Rect? = runBlocking(Dispatchers.IO) {
////        val layoutCoordinatesJson = service.getString(KeyProvider.LayoutCoordinates.key)
////        Gson().fromJson(layoutCoordinatesJson, LayoutCoordinatesType)
//        service.getString(KeyProvider.WindowCoordinates.key)?.let {
////            errorLog("getLayoutCooJson", "$it")
//            Gson().fromJson(it, RectType)
//        }
//    }

}

private val RectType = object : TypeToken<Rect>() {}.type!!
private val PopupState = object : TypeToken<PopUpState>() {}.type!!

enum class PopUpState {
    Update, ServerIssue, None
}
