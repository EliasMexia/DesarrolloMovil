package com.example.practica03

import android.app.Activity
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.practica03.ui.theme.Practica03Theme

class DetailActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        val nombre = intent.getStringExtra("EXTRA_NOMBRE") ?: "Sin nombre"
        val correo = intent.getStringExtra("EXTRA_CORREO") ?: "Sin correo"
        val mensaje = intent.getStringExtra("EXTRA_MENSAJE") ?: "Sin mensaje"

        setContent {
            Practica03Theme {
                DetailScreen(
                    nombre = nombre,
                    correo = correo,
                    mensaje = mensaje
                )
            }
        }
    }
}

@Composable
fun DetailScreen(nombre: String, correo: String, mensaje: String) {
    val context = LocalContext.current

    Scaffold { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(24.dp),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "Datos Recibidos (Intent)",
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(24.dp))

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Text(text = "Nombre:", fontWeight = FontWeight.Bold)
                    Text(text = nombre, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Correo:", fontWeight = FontWeight.Bold)
                    Text(text = correo, fontSize = 16.sp)
                    Spacer(modifier = Modifier.height(12.dp))

                    Text(text = "Mensaje:", fontWeight = FontWeight.Bold)
                    Text(text = mensaje, fontSize = 16.sp)
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            Button(
                onClick = {
                    (context as? Activity)?.finish()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(text = "Regresar a la Pantalla Principal")
            }
        }
    }
}