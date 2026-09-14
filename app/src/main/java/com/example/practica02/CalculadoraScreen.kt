package com.example.practica02

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CalculadoraScreen() {
    var display by remember { mutableStateOf("0") }
    var primerOperando by remember { mutableStateOf<Double?>(null) }
    var operacionPendiente by remember { mutableStateOf<String?>(null) }
    var nuevaEntrada by remember { mutableStateOf(true) }

    fun formatearResultado(valor: Double): String {
        return if (valor % 1.0 == 0.0) {
            valor.toLong().toString()
        } else {
            valor.toString()
        }
    }

    fun presionarNumero(numero: String) {
        if (nuevaEntrada || display == "0" || display == "Error") {
            display = numero
            nuevaEntrada = false
        } else {
            display += numero
        }
    }

    fun presionarPunto() {
        if (nuevaEntrada || display == "Error") {
            display = "0."
            nuevaEntrada = false
        } else if (!display.contains(".")) {
            display += "."
        }
    }

    fun calcular(): Double? {
        val num1 = primerOperando ?: return null
        val num2 = display.toDoubleOrNull() ?: return null
        return when (operacionPendiente) {
            "+" -> num1 + num2
            "-" -> num1 - num2
            "*" -> num1 * num2
            "/" -> {
                if (num2 == 0.0) null else num1 / num2
            }
            else -> num2
        }
    }

    fun presionarOperacion(op: String) {
        if (display == "Error") return
        val numActual = display.toDoubleOrNull() ?: 0.0

        if (primerOperando == null) {
            primerOperando = numActual
        } else if (operacionPendiente != null && !nuevaEntrada) {
            val res = calcular()
            if (res == null) {
                display = "Error"
                primerOperando = null
                operacionPendiente = null
                nuevaEntrada = true
                return
            } else {
                display = formatearResultado(res)
                primerOperando = res
            }
        }
        operacionPendiente = op
        nuevaEntrada = true
    }

    fun presionarIgual() {
        if (operacionPendiente == null || display == "Error") return
        val res = calcular()
        if (res == null) {
            display = "Error"
        } else {
            display = formatearResultado(res)
        }
        primerOperando = null
        operacionPendiente = null
        nuevaEntrada = true
    }

    fun limpiar() {
        display = "0"
        primerOperando = null
        operacionPendiente = null
        nuevaEntrada = true
    }

    Scaffold(
        containerColor = Color(0xFF17171C)
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding)
                .padding(16.dp),
            verticalArrangement = Arrangement.Bottom
        ) {
            // Pantalla de resultados
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 24.dp),
                contentAlignment = Alignment.BottomEnd
            ) {
                Text(
                    text = display,
                    fontSize = if (display.length > 8) 40.sp else 64.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.White,
                    textAlign = TextAlign.End,
                    maxLines = 1
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Fila 1: C, (vacios o soporte), /
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonCalculadora("C", Color(0xFFA5A5A5), Color.Black, Modifier.weight(3f)) { limpiar() }
                BotonCalculadora("/", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperacion("/") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila 2: 7, 8, 9, *
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonCalculadora("7", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("7") }
                BotonCalculadora("8", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("8") }
                BotonCalculadora("9", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("9") }
                BotonCalculadora("*", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperacion("*") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila 3: 4, 5, 6, -
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonCalculadora("4", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("4") }
                BotonCalculadora("5", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("5") }
                BotonCalculadora("6", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("6") }
                BotonCalculadora("-", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperacion("-") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila 4: 1, 2, 3, +
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonCalculadora("1", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("1") }
                BotonCalculadora("2", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("2") }
                BotonCalculadora("3", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarNumero("3") }
                BotonCalculadora("+", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarOperacion("+") }
            }

            Spacer(modifier = Modifier.height(12.dp))

            // Fila 5: 0, ., =
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                BotonCalculadora("0", Color(0xFF2E2F3E), Color.White, Modifier.weight(2f)) { presionarNumero("0") }
                BotonCalculadora(".", Color(0xFF2E2F3E), Color.White, Modifier.weight(1f)) { presionarPunto() }
                BotonCalculadora("=", Color(0xFFFF9F0A), Color.White, Modifier.weight(1f)) { presionarIgual() }
            }

            Spacer(modifier = Modifier.height(8.dp))
        }
    }
}

@Composable
fun BotonCalculadora(
    texto: String,
    colorFondo: Color,
    colorTexto: Color,
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Button(
        onClick = onClick,
        modifier = modifier.height(72.dp),
        shape = CircleShape,
        colors = ButtonDefaults.buttonColors(
            containerColor = colorFondo,
            contentColor = colorTexto
        )
    ) {
        Text(
            text = texto,
            fontSize = 26.sp,
            fontWeight = FontWeight.SemiBold
        )
    }
}