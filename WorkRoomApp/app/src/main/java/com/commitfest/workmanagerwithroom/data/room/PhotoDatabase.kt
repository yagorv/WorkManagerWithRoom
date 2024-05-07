package com.commitfest.workmanagerwithroom.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
@Database(
    entities = [PhotosDBEntity::class],
    version = 1
)
abstract class PhotoDatabase : RoomDatabase(){
    abstract val dao: PhotosDAO

}