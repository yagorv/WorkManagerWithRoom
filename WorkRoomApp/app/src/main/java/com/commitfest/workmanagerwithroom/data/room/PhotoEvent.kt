package com.commitfest.workmanagerwithroom.data.room

sealed interface PhotoEvent {
    object SavePhoto: PhotoEvent
    data class SetTitle(val title: String): PhotoEvent
    object ShowDialog: PhotoEvent
    object HideDialog: PhotoEvent
}