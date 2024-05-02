package com.commitfest.workmanagerwithroom.workers

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import kotlinx.coroutines.delay

class GetDataWorker(appContext: Context, workerParams: WorkerParameters) :
    CoroutineWorker(appContext, workerParams) {
    override suspend fun doWork(): Result {

        println("Obtaining data from source...")

        delay(1000L)

        println("Saving data on DB...")

        delay(1000L)

        println("Data saved successfully!")

        // Indicate whether the work finished successfully with the Result
        return Result.success()
    }
}
