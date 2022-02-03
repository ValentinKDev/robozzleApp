package com.mobilegame.robozzle.domain.model.data.store

internal sealed class KeyProvider(val key: String) {
    object Name: KeyProvider("name_key_datastore")
    object Password: KeyProvider("password_key_datastore")
    object Id: KeyProvider("id_key_datastore")
    object ConnectionState: KeyProvider("connection_State_key_datastore")
    object Version: KeyProvider("version_key_datastore")
}