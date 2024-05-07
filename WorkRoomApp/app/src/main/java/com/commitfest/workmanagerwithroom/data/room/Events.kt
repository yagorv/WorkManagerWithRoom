package com.commitfest.workmanagerwithroom.data.room

sealed interface Events {
    object SavePhotosInRoom: Events
    data class SetTitle(val title: String): Events
    object DeletePhotos : Events
    object ShowDialog: Events
    object HideDialog: Events
}