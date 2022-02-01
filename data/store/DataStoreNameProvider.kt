package com.mobilegame.robozzle.data.store

internal sealed class DataStoreNameProvider(val pref: String) {
//    object Name: KeyProvider("name_key_datastore")
//    object Password: KeyProvider("password_key_datastore")
//    object Id: KeyProvider("id_key_datastore")
    object User: DataStoreNameProvider("pref_data_store")
    object Token: DataStoreNameProvider("pref_token_data_store")
    object App: DataStoreNameProvider("app_general_data_store")
//    object State: DataStoreNameProvider("pref_state_data_store")
}
