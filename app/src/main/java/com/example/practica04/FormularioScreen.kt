package com.example.practica04

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen() {
    val scrollState = rememberScrollState()

    // 1. Spinner (Dropdown)
    val opcionesCarrera = listOf("Ingenieria de Software", "Informatica", "Sistemas Computacionales", "Ciberseguridad")
    var carreraSeleccionada by remember { mutableStateOf(opcionesCarrera[0]) }
    var menuExpandido by remember { mutableStateOf(false) }

    // 2. RadioGroup (Modalidad)
    val modalidades = listOf("Presencial", "En linea", "Hibrida")
    var modalidadSeleccionada by remember { mutableStateOf(modalidades[0]) }

    // 3. CheckBox (Intereses multiples)
    var interesDesarrollo by remember { mutableStateOf(true) }
    var interesRedes by remember { mutableStateOf(false) }
    var interesIA by remember { mutableStateOf(false) }

    // 4. Switch (Recibir notificaciones)
    var notificacionesActivas by remember { mutableStateOf(true) }

    // 5. SeekBar / Slider (Nivel de experiencia 1 a 10)
    var nivelExperiencia by remember { mutableFloatStateOf(5f) }

    // 6. Contenedor de resumen
    var resumenTexto by remember { mutableStateOf("") }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Componentes Avanzados") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp)
                .verticalScroll(scrollState)
        ) {
            // Control 1: Spinner (Dropdown)
            Text("Selecciona una carrera (Spinner):", fontWeight = FontWeight.Bold)
            Spacer(modifier = Modifier.height(6.dp))
            ExposedDropdownMenuBox(
                expanded = menuExpandido,
                onExpandedChange = { menuExpandido = !menuExpandido }
            ) {
                OutlinedTextField(
                    value = carreraSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    opcionesCarrera.forEach { opcion ->
                        DropdownMenuItem(
                            text = { Text(opcion) },
                            onClick = {
                                carreraSeleccionada = opcion
                                menuExpandido = false
                            }
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Control 2: RadioGroup (RadioButton)
            Text("Modalidad de estudio (RadioGroup):", fontWeight = FontWeight.Bold)
            modalidades.forEach { modalidad ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickable { modalidadSeleccionada = modalidad }
                ) {
                    RadioButton(
                        selected = (modalidad == modalidadSeleccionada),
                        onClick = { modalidadSeleccionada = modalidad }
                    )
                    Text(text = modalidad, modifier = Modifier.padding(start = 8.dp))
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Control 3: CheckBox
            Text("Areas de interes (CheckBox):", fontWeight = FontWeight.Bold)
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = interesDesarrollo, onCheckedChange = { interesDesarrollo = it })
                Text("Desarrollo Web/Movil")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = interesRedes, onCheckedChange = { interesRedes = it })
                Text("Redes y Servidores")
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Checkbox(checked = interesIA, onCheckedChange = { interesIA = it })
                Text("Inteligencia Artificial")
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Control 4: Switch
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text("Activar notificaciones (Switch):", fontWeight = FontWeight.Bold)
                Switch(
                    checked = notificacionesActivas,
                    onCheckedChange = { notificacionesActivas = it }
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Control 5: Slider (SeekBar)
            Text(
                "Nivel de experiencia (SeekBar): ${nivelExperiencia.toInt()} / 10",
                fontWeight = FontWeight.Bold
            )
            Slider(
                value = nivelExperiencia,
                onValueChange = { nivelExperiencia = it },
                valueRange = 1f..10f,
                steps = 8
            )

            Spacer(modifier = Modifier.height(20.dp))

            // Control 6: Boton de procesamiento
            Button(
                onClick = {
                    val interesesSeleccionados = mutableListOf<String>()
                    if (interesDesarrollo) interesesSeleccionados.add("Desarrollo")
                    if (interesRedes) interesesSeleccionados.add("Redes")
                    if (interesIA) interesesSeleccionados.add("IA")

                    resumenTexto = """
                        Carrera: $carreraSeleccionada
                        Modalidad: $modalidadSeleccionada
                        Intereses: ${if (interesesSeleccionados.isNotEmpty()) interesesSeleccionados.joinToString(", ") else "Ninguno"}
                        Notificaciones: ${if (notificacionesActivas) "Activadas" else "Desactivadas"}
                        Nivel de Experiencia: ${nivelExperiencia.toInt()}
                    """.trimIndent()
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Procesar Datos")
            }

            // Resumen de salida
            if (resumenTexto.isNotBlank()) {
                Spacer(modifier = Modifier.height(16.dp))
                Card(
                    modifier = Modifier.fillMaxWidth(),
                    elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                    colors = CardDefaults.cardColors(
                        containerColor = MaterialTheme.colorScheme.surfaceVariant
                    )
                ) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(
                            text = "Resumen de Informacion Capturada:",
                            fontWeight = FontWeight.Bold,
                            fontSize = 16.sp
                        )
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = resumenTexto)
                    }
                }
            }

            Spacer(modifier = Modifier.height(24.dp))
        }
    }
}