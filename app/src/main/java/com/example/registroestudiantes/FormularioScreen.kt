package com.example.registroestudiantes

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun FormularioScreen(
    prefsManager: PreferencesManager,
    onRegistroExitoso: (String, String, String, String, Boolean) -> Unit
) {
    val carreras = listOf(
        "Ingenieria de Software",
        "Ingenieria Civil",
        "Ingenieria en Procesos Industriales",
        "Licenciatura en Informatica"
    )
    val turnos = listOf("Matutino", "Vespertino")

    var matricula by remember { mutableStateOf(prefsManager.obtenerMatricula()) }
    var nombre by remember { mutableStateOf(prefsManager.obtenerNombre()) }
    var carreraSeleccionada by remember { mutableStateOf(prefsManager.obtenerCarrera(carreras.first())) }
    var menuExpandido by remember { mutableStateOf(false) }
    var turnoSeleccionado by remember { mutableStateOf(prefsManager.obtenerTurno(turnos.first())) }
    var activo by remember { mutableStateOf(prefsManager.obtenerActivo()) }

    var errorMatricula by remember { mutableStateOf(false) }
    var errorNombre by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Alta de Alumno") },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 24.dp, vertical = 16.dp)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            OutlinedTextField(
                value = matricula,
                onValueChange = {
                    matricula = it
                    if (errorMatricula) errorMatricula = false
                },
                label = { Text("Matricula escolar") },
                isError = errorMatricula,
                supportingText = {
                    if (errorMatricula) Text("Campo obligatorio")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            OutlinedTextField(
                value = nombre,
                onValueChange = {
                    nombre = it
                    if (errorNombre) errorNombre = false
                },
                label = { Text("Nombre completo") },
                isError = errorNombre,
                supportingText = {
                    if (errorNombre) Text("Campo obligatorio")
                },
                singleLine = true,
                modifier = Modifier.fillMaxWidth()
            )

            ExposedDropdownMenuBox(
                expanded = menuExpandido,
                onExpandedChange = { menuExpandido = !menuExpandido },
                modifier = Modifier.fillMaxWidth()
            ) {
                OutlinedTextField(
                    value = carreraSeleccionada,
                    onValueChange = {},
                    readOnly = true,
                    label = { Text("Programa academico") },
                    trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = menuExpandido) },
                    modifier = Modifier
                        .menuAnchor()
                        .fillMaxWidth()
                )
                ExposedDropdownMenu(
                    expanded = menuExpandido,
                    onDismissRequest = { menuExpandido = false }
                ) {
                    carreras.forEach { opcion ->
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

            Text(
                text = "Turno asignado",
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .selectableGroup(),
                horizontalArrangement = Arrangement.spacedBy(24.dp)
            ) {
                turnos.forEach { opcionTurno ->
                    Row(
                        modifier = Modifier
                            .selectable(
                                selected = (opcionTurno == turnoSeleccionado),
                                onClick = { turnoSeleccionado = opcionTurno },
                                role = Role.RadioButton
                            ),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        RadioButton(
                            selected = (opcionTurno == turnoSeleccionado),
                            onClick = null
                        )
                        Spacer(modifier = Modifier.width(6.dp))
                        Text(text = opcionTurno, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }

            HorizontalDivider()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Column {
                    Text(text = "Estatus academico", style = MaterialTheme.typography.bodyLarge)
                    Text(
                        text = if (activo) "Vigente / Regular" else "Baja temporal",
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.colorScheme.outline
                    )
                }
                Switch(
                    checked = activo,
                    onCheckedChange = { activo = it }
                )
            }

            Spacer(modifier = Modifier.height(8.dp))

            Button(
                onClick = {
                    val matValida = matricula.isNotBlank()
                    val nomValido = nombre.isNotBlank()

                    if (!matValida || !nomValido) {
                        errorMatricula = !matValida
                        errorNombre = !nomValido
                        return@Button
                    }

                    prefsManager.guardarUltimoRegistro(
                        matricula = matricula.trim(),
                        nombre = nombre.trim(),
                        carrera = carreraSeleccionada,
                        turno = turnoSeleccionado,
                        activo = activo
                    )

                    onRegistroExitoso(
                        matricula.trim(),
                        nombre.trim(),
                        carreraSeleccionada,
                        turnoSeleccionado,
                        activo
                    )
                },
                modifier = Modifier.fillMaxWidth()
            ) {
                Text("Procesar registro")
            }
        }
    }
}