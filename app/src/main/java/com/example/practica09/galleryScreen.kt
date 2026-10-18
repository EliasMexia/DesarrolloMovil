package com.example.practica09

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import coil.compose.AsyncImage

@Composable
fun GaleriaScreen() {
    val imagenes = listOf(
        "https://picsum.photos/id/1018/400/400",
        "https://picsum.photos/id/1015/400/400",
        "https://picsum.photos/id/1019/400/400",
        "https://picsum.photos/id/1020/400/400",
        "https://picsum.photos/id/1021/400/400",
        "https://picsum.photos/id/1022/400/400",
        "https://picsum.photos/id/1023/400/400",
        "https://picsum.photos/id/1024/400/400"
    )

    var imagenSeleccionada by remember { mutableStateOf<String?>(null) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(8.dp)
            .padding(top = 32.dp)
    ) {
        Text(
            text = "Mi Galeria",
            style = MaterialTheme.typography.headlineMedium,
            modifier = Modifier.padding(8.dp)
        )

        LazyVerticalGrid(
            columns = GridCells.Fixed(2),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            modifier = Modifier.fillMaxSize()
        ) {
            items(imagenes) { url ->
                ImagenItem(
                    url = url,
                    onClick = { imagenSeleccionada = url }
                )
            }
        }
    }

    if (imagenSeleccionada != null) {
        Dialog(onDismissRequest = { imagenSeleccionada = null }) {
            Card(
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier.padding(16.dp)
                ) {
                    Card(
                        shape = RoundedCornerShape(12.dp),
                        modifier = Modifier
                            .fillMaxWidth()
                            .aspectRatio(1f)
                    ) {
                        AsyncImage(
                            model = imagenSeleccionada,
                            contentDescription = "Imagen ampliada",
                            contentScale = ContentScale.Crop,
                            modifier = Modifier.fillMaxSize()
                        )
                    }
                    Spacer(modifier = Modifier.height(16.dp))
                    Button(
                        onClick = { imagenSeleccionada = null },
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Text(text = "Cerrar vista previa")
                    }
                }
            }
        }
    }
}

@Composable
fun ImagenItem(url: String, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .aspectRatio(1f)
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        shape = RoundedCornerShape(8.dp)
    ) {
        AsyncImage(
            model = url,
            contentDescription = "Imagen de la galeria",
            contentScale = ContentScale.Crop,
            modifier = Modifier.fillMaxSize()
        )
    }
}