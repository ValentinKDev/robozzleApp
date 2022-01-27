package com.mobilegame.robozzle.domain

sealed class DataStoredProviding(val key: String) {
    object NAME: DataStoredProviding("name_key_datastore")
    object PASSWORD: DataStoredProviding("password_key_datastore")
    object ID: DataStoredProviding("id_key_datastore")
}