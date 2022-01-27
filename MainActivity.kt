package com.mobilegame.robozzle

import android.app.Application
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.navigation.compose.rememberNavController
import com.mobilegame.robozzle.domain.model.DataViewModel
import com.mobilegame.robozzle.domain.model.MainMenuViewModel
import com.mobilegame.robozzle.domain.model.UserViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi

class MainActivity : ComponentActivity() {

//    private val datastoreVM by viewModels<DataViewModel>()

//    val application: Application = getApplication().onCreate()
//    val Application.dataStore: DataStore<Preferences> by preferencesDataStore(name = "version")
//    @InternalCoroutinesApi
//    private val mainMenuVM = MainMenuViewModel(application)
//    private val mainMenuVM by viewModels<MainMenuViewModel>()

//    @InternalCoroutinesApi
//    private val userVM = UserViewModel(application, datastoreVM)
//    private val userVM by viewModels<UserViewModel>()

//    val mLevelViewModel = MainMenuViewModel(application = ctxt.applicationContext as Application)
//    val mUserViewModel = UserViewModel(application = ctxt.applicationContext as Application, mLevelViewModel.dataStore)

    @DelicateCoroutinesApi
    @InternalCoroutinesApi
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val navController = rememberNavController()
            Navigation()
        }
    }
}


