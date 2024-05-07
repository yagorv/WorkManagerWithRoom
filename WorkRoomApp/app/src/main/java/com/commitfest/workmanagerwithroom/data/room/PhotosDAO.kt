package com.commitfest.workmanagerwithroom.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import com.commitfest.workmanagerwithroom.domain.model.PhotoModel
import kotlinx.coroutines.flow.Flow
import retrofit2.http.GET

@Dao
interface PhotosDAO {

    //generacion automática de código sql
    //tiene flows, le pones las anotaciones, te crea el flow por detrás para que siempre esté escuchando etc
    @Delete
    suspend fun deletePhotos(photos: List<PhotosDBEntity>)

    @Upsert
    suspend fun savePhotos(photos: List<PhotosDBEntity>)

    @Query("SELECT * FROM photos ORDER BY id ASC")
    fun retrievePhotos() : Flow<List<PhotosDBEntity>>
}