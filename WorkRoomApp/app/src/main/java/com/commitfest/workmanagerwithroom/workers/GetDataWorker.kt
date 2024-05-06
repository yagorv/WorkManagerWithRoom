package com.commitfest.workmanagerwithroom.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.commitfest.workmanagerwithroom.MainActivity
import com.commitfest.workmanagerwithroom.PhotoApplication
import com.commitfest.workmanagerwithroom.data.room.Photo
import com.commitfest.workmanagerwithroom.usecases.GetDataUseCase

class GetDataWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {
        GetDataUseCase(4).invoke()?.let {
            try {
                val photoDao = PhotoApplication().database.photoDao()
                val photo = Photo(
                    id = it.id.toString(),
                    albumId = it.userId.toString(),
                    title = it.title.toString(),
                    url = "www.blabla/${it.userId}",
                    thumbnailUrl = ""
                )
                photoDao.insertPhoto(photo)
                return Result.success()
            } catch (e: Exception) {
                println(e)
                println(e.message)
                println(e.cause)
                return Result.failure()
            }
        } ?: kotlin.run {
            return Result.failure()
        }
    }
}
