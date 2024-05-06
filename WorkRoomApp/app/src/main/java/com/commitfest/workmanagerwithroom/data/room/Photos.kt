package com.commitfest.workmanagerwithroom.data.room

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "photos")
data class Photos (
    @PrimaryKey(autoGenerate = false)
    val id: String = "",
    val albumId: String,
    val title: String,
    val url: String,
    val thumbnailUrl: String
)