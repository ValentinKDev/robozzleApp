package com.mobilegame.robozzle.data.store

internal sealed class DataStoreNameProvider(val pref: String) {
//    object Name: KeyProvider("name_key_datastore")
//    object Password: KeyProvider("password_key_datastore")
//    object Id: KeyProvider("id_key_datastore")
    object User: DataStoreNameProvider("pref_data_store")
    object Token: DataStoreNameProvider("pref_token_data_store")
    object App: DataStoreNameProvider("app_general_data_store")
    object Screen: DataStoreNameProvider("screen_dimensions_data_store")
    object Arg: DataStoreNameProvider("arg_solution_data_store")
    object LazyListState1: DataStoreNameProvider("lazy_list1_data_store")
    object LazyListState2: DataStoreNameProvider("lazy_list2_data_store")
    object LazyListState3: DataStoreNameProvider("lazy_list3_data_store")
    object LazyListState4: DataStoreNameProvider("lazy_list4_data_store")
    object LazyListState5: DataStoreNameProvider("lazy_list5_data_store")
}
