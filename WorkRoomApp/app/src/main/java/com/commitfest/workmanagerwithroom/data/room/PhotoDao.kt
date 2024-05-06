package com.commitfest.workmanagerwithroom.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotoDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertPhoto(photo: Photo)

    @Query("SELECT * FROM photos")
    suspend fun getAllPhotos():List<Photo>
}