package com.mobilegame.robozzle.domain.model.level

sealed class LevelState(val key: String) {
    object Win: LevelState("win_key")
    object Opened: LevelState("opened_key")
    object NeverOpened: LevelState("never_opened_key")
    object Unknown: LevelState("unknown_key")

    fun isWin(): Boolean = this.key == Win.key

    companion object {
        fun findState(keyToFind: String): LevelState {
            return LevelState::class.sealedSubclasses
                .firstOrNull { _it ->
                    keyToFind.let { _it.objectInstance?.key == keyToFind }
                }?.objectInstance ?: Unknown
        }
        fun neverOpenedKey(): String = NeverOpened.key
    }
}