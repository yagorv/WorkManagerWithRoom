package com.commitfest.workmanagerwithroom.usecases

import androidx.work.ListenableWorker
import androidx.work.ListenableWorker.Result.Failure
import com.commitfest.workmanagerwithroom.data.api.ApiService
import com.commitfest.workmanagerwithroom.data.entity.PhotoEntity
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class GetDataUseCase(val photoId: Int) {

    fun invoke(): PhotoEntity? {
        // Crear una instancia de Retrofit
        val retrofit = Retrofit.Builder()
            .baseUrl("https://jsonplaceholder.typicode.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        // Crear una instancia del servicio API
        val service = retrofit.create(ApiService::class.java)

        // Hacer la llamada a la API
        val call = service.getPostById(photoId)

        return try {
            val response = call.execute()
            if (response.isSuccessful && response.body() != null) {
                response.body()
            } else {
                null
            }
        } catch (e: Exception) {
            e.printStackTrace()
            null
        }
    }
}