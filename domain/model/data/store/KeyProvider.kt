package com.mobilegame.robozzle.domain.model.data.store

internal sealed class KeyProvider(val key: String) {
    object Name: KeyProvider("name_key_datastore")
    object Password: KeyProvider("password_key_datastore")
    object Id: KeyProvider("id_key_datastore")
    object ConnectionState: KeyProvider("connection_State_key_datastore")
    object Version: KeyProvider("version_key_datastore")
    object Token: KeyProvider("token_key_datastore")
    object WidthPixels: KeyProvider("widht_pixels_key_datastore")
    object HeigthPixel: KeyProvider("height_pixels_key_datastore")
    object Density: KeyProvider("density_key_datastore")
    object DensityDpi: KeyProvider("density_key_datastore")
    object WindowCoordinates: KeyProvider("layout_key_datastore")
    object PopupState: KeyProvider("popup_state_key_datastore")
    object LevelArg: KeyProvider("level_argument_datastore")
}