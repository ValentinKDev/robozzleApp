package com.mobilegame.robozzle.presentation.ui.Screen.Creator

import android.content.Context
import android.os.Build.VERSION.SDK_INT
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.material.Button
import androidx.compose.ui.geometry.Size
//import androidx.compose.material.icons.Icons
//import androidx.compose.material.icons.outlined.North
//import androidx.compose.material.icons.outlined.Update
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.ImageLoader
import coil.compose.rememberAsyncImagePainter
import coil.decode.GifDecoder
import coil.decode.ImageDecoderDecoder
import coil.request.ImageRequest
import com.mobilegame.robozzle.R
import com.mobilegame.robozzle.domain.model.Screen.Navigation.NavViewModel
import com.mobilegame.robozzle.domain.model.data.general.RankVM
import com.mobilegame.robozzle.domain.model.data.room.LevelWins.LevelWinRoomViewModel
import com.mobilegame.robozzle.presentation.ui.Navigation.Navigator
import com.mobilegame.robozzle.presentation.ui.Screen.Screens
import com.mobilegame.robozzle.presentation.ui.utils.GifImage
import io.ktor.http.ContentDisposition.Parameters.Size
import kotlinx.coroutines.launch


@Composable
fun CreatorScreen(navigator: Navigator, vm: TestVM = viewModel()) {
    BackHandler {
        NavViewModel(navigator).navigateToMainMenu(fromScreen = Screens.Donation.route)
    }

    val ctxt = LocalContext.current
//    val vm = TestVM(ctxt)

    Column(Modifier.fillMaxSize()) {
        Button(onClick = { }) {
        }
//        GifImage(data = R.drawable.tenor)
    }
}

class TestVM() : ViewModel() {
}
