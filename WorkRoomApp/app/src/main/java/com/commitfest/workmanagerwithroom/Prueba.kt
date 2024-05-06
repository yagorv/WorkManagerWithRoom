package com.commitfest.workmanagerwithroom

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import androidx.work.workDataOf

class Prueba(
    private val appContext: Context,
    private val workerParams: WorkerParameters
) : CoroutineWorker(
    appContext,
    workerParams
) {
    override suspend fun doWork(): Result {
        for (i in 0..workerParams.inputData.getLong(MAX_NUM, 5000)) {
            println("Prueba: $i")
        }
        return Result.success(workDataOf(RESULT to "Prueba exitosa"))
    }

    companion object {
        const val MAX_NUM = "max_num"
        const val RESULT = "result"
    }
}