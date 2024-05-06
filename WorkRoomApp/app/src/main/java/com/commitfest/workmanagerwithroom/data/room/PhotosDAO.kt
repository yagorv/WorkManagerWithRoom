package com.commitfest.workmanagerwithroom.data.room

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface PhotosDAO {

    //Al hacerlas suspend pueden correr en una corrutina
    @Upsert
    suspend fun insertString(string: Photos)

    //generacion automática de código sql
    //tiene flows, le pones las anotaciones, te crea el flow por detrás para que siempre esté escuchando etc
    @Delete
    suspend fun deleteString(string: Photos)

    @Query("SELECT * FROM photos ORDER BY id ASC")
    fun getStringsOrderedByValue() : Flow<List<PhotoEntity>>
}