package com.example.practica13
import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import kotlin.math.sqrt

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MaterialTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    PantallaSensores()
                }
            }
        }
    }
}

@Composable
fun PantallaSensores() {
    val contexto = LocalContext.current

    var ejeX by remember { mutableStateOf(0f) }
    var ejeY by remember { mutableStateOf(0f) }
    var ejeZ by remember { mutableStateOf(0f) }
    var haySacudida by remember { mutableStateOf(false) }

    DisposableEffect(Unit) {
        val sensorManager = contexto.getSystemService(Context.SENSOR_SERVICE) as SensorManager
        val acelerometro = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)

        val listener = object : SensorEventListener {
            override fun onSensorChanged(event: SensorEvent?) {
                if (event?.sensor?.type == Sensor.TYPE_ACCELEROMETER) {
                    val x = event.values[0]
                    val y = event.values[1]
                    val z = event.values[2]

                    ejeX = x
                    ejeY = y
                    ejeZ = z

                    val gX = x / SensorManager.GRAVITY_EARTH
                    val gY = y / SensorManager.GRAVITY_EARTH
                    val gZ = z / SensorManager.GRAVITY_EARTH

                    val fuerzaG = sqrt(gX * gX + gY * gY + gZ * gZ)

                    if (fuerzaG > 2.5f) {
                        haySacudida = true
                    }
                }
            }

            override fun onAccuracyChanged(sensor: Sensor?, accuracy: Int) {
                // No se necesita implementar para esta practica
            }
        }

        sensorManager.registerListener(
            listener,
            acelerometro,
            SensorManager.SENSOR_DELAY_UI
        )

        onDispose {
            sensorManager.unregisterListener(listener)
        }
    }

    LaunchedEffect(haySacudida) {
        if (haySacudida) {
            delay(500)
            haySacudida = false
        }
    }

    val colorFondo = if (haySacudida) Color.Red else Color.White
    val colorTexto = if (haySacudida) Color.White else Color.Black

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(colorFondo)
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Datos del Acelerometro",
            fontSize = 24.sp,
            fontWeight = FontWeight.Bold,
            color = colorTexto
        )

        Spacer(modifier = Modifier.height(32.dp))

        Text(text = "Eje X: ${String.format("%.2f", ejeX)}", fontSize = 20.sp, color = colorTexto)
        Text(text = "Eje Y: ${String.format("%.2f", ejeY)}", fontSize = 20.sp, color = colorTexto)
        Text(text = "Eje Z: ${String.format("%.2f", ejeZ)}", fontSize = 20.sp, color = colorTexto)

        Spacer(modifier = Modifier.height(48.dp))

        if (haySacudida) {
            Text(
                text = "SACUDIDA DETECTADA",
                fontSize = 22.sp,
                fontWeight = FontWeight.ExtraBold,
                color = Color.White
            )
        }
    }
}