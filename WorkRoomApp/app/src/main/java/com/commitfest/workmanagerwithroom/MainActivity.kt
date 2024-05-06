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
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.workDataOf
import com.commitfest.workmanagerwithroom.ui.theme.MyApplicationTheme
import java.util.concurrent.TimeUnit

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val workManager = WorkManager.getInstance(applicationContext)
        val viewModel by viewModels<PruebaViewModel>()

        setContent {
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
                        BotonWorkManager(workManager, viewModel, datoResultado)
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
fun BotonWorkManager(workManager: WorkManager, viewModel: PruebaViewModel, buttonText: String){
    val request = OneTimeWorkRequestBuilder<Prueba>()
        .setConstraints(
            Constraints(requiresCharging = true)
        )
        .setInputData(workDataOf(Prueba.MAX_NUM to 10000L))
        .setInitialDelay(10, TimeUnit.SECONDS) // Broma de estoy esperando a ver si funciona el de dentro de 100 días.
        .build()
    viewModel.updateWorkerId(request.id)
    Button(
        //modifier = Modifier.fillMaxWidth(),
        onClick = {
            workManager.enqueue(request)
        }
    ) {
        Text(text = buttonText)
    }
}