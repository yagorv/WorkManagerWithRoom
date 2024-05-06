package com.commitfest.workmanagerwithroom

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkRequest
import androidx.work.workDataOf
import com.commitfest.workmanagerwithroom.ui.theme.MyApplicationTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workManager = WorkManager.getInstance(applicationContext)
        val viewModel by viewModels<PruebaViewModel>()

        setContent {
            val request = OneTimeWorkRequestBuilder<Prueba>()
                .setConstraints(
                    Constraints(requiresCharging = true)
                )
                .setInputData(workDataOf(Prueba.MAX_NUM to 10000L))
                .setInitialDelay(10, TimeUnit.SECONDS) // Broma de estoy esperando a ver si funciona el de dentro de 100 días.
                .build()
            viewModel.updateWorkerId(request.id)

            val workerResult = viewModel.workerId?.let {
                workManager.getWorkInfoByIdLiveData(it).observeAsState().value
            }

            var datoResultado by remember {
                mutableStateOf("Try WorkManager")
            }

            LaunchedEffect(key1 = workerResult) {
                datoResultado = workerResult?.outputData?.getString(Prueba.RESULT) ?: "Try WorkManager"
            }

            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                    ) {
                        Greeting("Android")
                        //ButtonConstrains(workManager)
                        //ButtonIntervalos(workManager)
                        //ButtonTrabajosAcelerados(workManager)
                        BotonWorkManager(request, workManager, viewModel, datoResultado)
                    }
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
    println("hola estoy en la main actividad")
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    Greeting("Android")
}

@Composable
fun BotonWorkManager(request: WorkRequest, workManager: WorkManager, viewModel: PruebaViewModel, buttonText: String){

    Button(
        //modifier = Modifier.fillMaxWidth(),
        onClick = {
            workManager.enqueue(request)
        }
    ) {
        Text(text = buttonText)
    }
}

@Composable
fun ButtonConstrains(workManager: WorkManager){
    val request = OneTimeWorkRequestBuilder<Prueba>()
        .setConstraints(
            Constraints(
                requiresCharging = true,
                requiresStorageNotLow = true,
                requiresBatteryNotLow = true,
                requiresDeviceIdle = true,
                requiredNetworkType = NetworkType.METERED
            )
        ).build()

    Button(
        onClick = {
            workManager.enqueue(request)
        }
    ) {
        Text(text = "Try Constrains")
    }
}

@Composable
fun ButtonIntervalos(workManager: WorkManager){
    val myUploadWork = PeriodicWorkRequestBuilder<Prueba>(
        1, TimeUnit.HOURS, // repeatInterval (the period cycle)
        15, TimeUnit.MINUTES) // flexInterval
        .build()

    Button(
        onClick = {
            workManager.enqueue(myUploadWork)
        }
    ) {
        Text(text = "Try Intervalos")
    }
}

@Composable
fun ButtonTrabajosAcelerados(workManager: WorkManager) {
    val firstRequest = OneTimeWorkRequestBuilder<Prueba>()
        .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
        .build()

    val secondRequest = OneTimeWorkRequestBuilder<Prueba>()
        .setExpedited(OutOfQuotaPolicy.DROP_WORK_REQUEST)
        .build()

    Button(
        onClick = {
            workManager.enqueue(firstRequest)
            workManager.enqueue(secondRequest)
        }
    ) {
        Text(text = "Try Trabajos acelerados")
    }
}

