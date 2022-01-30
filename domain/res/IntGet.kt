package com.mobilegame.robozzle.domain.res

sealed class IntGet(val value: Int) {
    object Error: IntGet(-42)
    object True: IntGet(1)
    object False: IntGet(0)
    object Unknown: IntGet(-1)
}
