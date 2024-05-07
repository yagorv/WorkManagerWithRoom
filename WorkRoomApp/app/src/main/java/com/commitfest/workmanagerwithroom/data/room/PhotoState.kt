package com.commitfest.workmanagerwithroom.data.room

data class PhotoState (
    val photos: List<PhotosDBEntity> = emptyList(),
    val id: String = "",
    val albumId: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)