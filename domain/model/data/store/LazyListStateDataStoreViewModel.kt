package com.mobilegame.robozzle.domain.model.data.store

import android.content.Context
import androidx.compose.foundation.lazy.LazyListState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mobilegame.robozzle.data.store.DataStoreService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

class LazyListStateDataStoreViewModel (
    context: Context,
    val id: Int
): ViewModel() {
    val service = DataStoreService.createLazyListStateService(context, id)

    private fun getSavedIndex(): Int? = runBlocking(Dispatchers.IO) {
        service.getInt( key = KeyProvider.getLazyListStartIndexKeyOf(id))
    }

    private fun saveIndex(newIndex: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            service.putInt( key = KeyProvider.getLazyListStartIndexKeyOf(id), value = newIndex)
        }
    }

    private fun getSavedOffset(): Int? = runBlocking(Dispatchers.IO) {
        service.getInt( key = KeyProvider.getLazyListOffset(id) )
    }

    private fun saveOffset(newOffset: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            service.putInt( key = KeyProvider.getLazyListOffset(id) , value = newOffset)
        }
    }

    fun getState(): LazyListState {
        return LazyListState(
            getSavedIndex() ?: 0,
            getSavedOffset() ?: 0
        )
    }

    fun saveState(lastIndex: Int, lastOffset: Int) {
        saveIndex(lastIndex)
        saveOffset(lastOffset)
    }
}
