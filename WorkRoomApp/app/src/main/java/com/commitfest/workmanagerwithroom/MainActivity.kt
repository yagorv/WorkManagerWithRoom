package com.commitfest.workmanagerwithroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.work.Constraints
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import com.commitfest.workmanagerwithroom.ui.theme.MyApplicationTheme
import com.commitfest.workmanagerwithroom.workers.GetDataWorker
import com.commitfest.workmanagerwithroom.workers.UpdateDataWorker
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Content()
        }
    }

    override fun onStart() {
        super.onStart()
        // This worker are fake and only simulate obtaining data
        // In future this could save some data on Room instance
        enqueueData(createOneTimeGetDataWorkerRequest(delay = 1))
        enqueueData(createPeriodicUpdateDataWorkerRequest(delay = 3))
    }

    override fun onResume() {
        super.onResume()
        // here we could do obtain data from room to show it
    }

    private fun enqueueData(request: WorkRequest) {
        WorkManager
            .getInstance(this)
            .enqueue(request)
    }

    private fun createOneTimeGetDataWorkerRequest(
        delay: Long,
        timeUnit: TimeUnit = TimeUnit.MINUTES
    ): WorkRequest =
        OneTimeWorkRequestBuilder<GetDataWorker>()
            .setConstraints(
                Constraints.Builder()
                    .setRequiresBatteryNotLow(true)
                    .build()
            )
            .setInitialDelay(delay, timeUnit).build()

    private fun createPeriodicUpdateDataWorkerRequest(
        periodicityInterval: Long = 16, // Minimum accepted interval are 15 minutes
        delay: Long = 1,
        timeUnit: TimeUnit = TimeUnit.MINUTES
    ): WorkRequest =
        PeriodicWorkRequestBuilder<UpdateDataWorker>(periodicityInterval, timeUnit)
            .setConstraints(
                Constraints.Builder()
                    .setRequiresStorageNotLow(true)
                    .build()
            )
            .setInitialDelay(delay, timeUnit)
            .build()

    @Composable
    fun Content() {
        MyApplicationTheme {
            Surface(
                modifier = Modifier.fillMaxSize(),
                color = MaterialTheme.colorScheme.background
            ) {
                Text(
                    text = "Hello!"
                )
            }
        }
    }
}

