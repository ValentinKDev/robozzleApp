package com.mobilegame.robozzle.domain.InGame

sealed class PlayerAnimationState(val key: String) {
    object NotStarted: PlayerAnimationState("not_started")

    object OnPause: PlayerAnimationState("on_pause")
    object IsPlaying: PlayerAnimationState("is_playing")
    object GoBack: PlayerAnimationState("go_back")
    object GoNext: PlayerAnimationState("go_next")

    //    val animationRunningInBackground = animationIsPlaying || animationIsOnPause
    fun runningInBackground(): Boolean = (key == IsPlaying.key) || (key == OnPause.key) || (key == GoBack.key) || (key == GoNext.key)

//    fun RunningInBackground(): Boolean = (key == IsPlaying.key) || (key == OnPause.key)
}

infix fun PlayerAnimationState.runningInBackgroundIs(toCompare: Boolean):Boolean = runningInBackground() == toCompare

infix fun Boolean.Is(toCompare: Boolean) = this == toCompare
