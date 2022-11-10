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
    object LinkState: KeyProvider("link_state_datastore")
    object LevelArg: KeyProvider("level_argument_datastore")

    object startIndexLazyList1: KeyProvider("index_lazy_list1_datastore")
    object offsetLazyList1: KeyProvider("offset_X_lazy_list1_datastore")
//    object offsetYLazyList1: KeyProvider("offset_Y_lazy_list1_datastore")
    object startIndexLazyList2: KeyProvider("index_lazy_list2_datastore")
    object offsetLazyList2: KeyProvider("offset_X_lazy_list2_datastore")
//    object offsetYLazyList2: KeyProvider("offset_Y_lazy_list2_datastore")
    object startIndexLazyList3: KeyProvider("index_lazy_list3_datastore")
    object offsetLazyList3: KeyProvider("offset_X_lazy_list3_datastore")
//    object offsetYLazyList3: KeyProvider("offset_Y_lazy_list3_datastore")
    object startIndexLazyList4: KeyProvider("index_lazy_list4_datastore")
    object offsetLazyList4: KeyProvider("offset_X_lazy_list4_datastore")
//    object offsetYLazyList4: KeyProvider("offset_Y_lazy_list4_datastore")
    object startIndexLazyList5: KeyProvider("index_lazy_list5_datastore")
    object offsetLazyList5: KeyProvider("offset_X_lazy_list5_datastore")
//    object offsetYLazyList5: KeyProvider("offset_Y_lazy_list5_datastore")

    companion object {
        fun getLazyListStartIndexKeyOf(id: Int): String {
            return when (id) {
                1 -> startIndexLazyList1.key
                2 -> startIndexLazyList2.key
                3 -> startIndexLazyList3.key
                4 -> startIndexLazyList4.key
                else -> startIndexLazyList5.key
            }
        }

        fun getLazyListOffset(id: Int): String {
            return when (id) {
                1 -> offsetLazyList1.key
                2 -> offsetLazyList2.key
                3 -> offsetLazyList3.key
                4 -> offsetLazyList4.key
                else -> offsetLazyList5.key
            }
        }

//        fun getLazyListOffsetY(id: Int): String {
//            return when (id) {
//                1 -> offsetYLazyList1.key
//                2 -> offsetYLazyList2.key
//                3 -> offsetYLazyList3.key
//                4 -> offsetYLazyList4.key
//                else -> offsetYLazyList5.key
//            }
//        }
    }
}