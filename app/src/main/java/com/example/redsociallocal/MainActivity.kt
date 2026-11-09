package com.example.redsociallocal

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.room.Room
import coil.compose.AsyncImage
import com.example.redsociallocal.ui.theme.RedSocialLocalTheme
import java.io.File
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val db = Room.databaseBuilder(
            applicationContext,
            AppDatabase::class.java, "red_social_db"
        ).build()

        val viewModel = PublicacionViewModel(db.publicacionDao())

        setContent {
            RedSocialLocalTheme {
                Surface(modifier = Modifier.fillMaxSize()) {
                    PantallaRedSocial(viewModel)
                }
            }
        }
    }
}

fun crearArchivoImagen(context: Context): File {
    val timeStamp = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault()).format(Date())
    val dir = File(context.cacheDir, "images").apply { mkdirs() }
    return File.createTempFile("JPEG_${timeStamp}_", ".jpg", dir)
}

@Composable
fun PantallaRedSocial(viewModel: PublicacionViewModel) {
    val context = LocalContext.current
    var titulo by remember { mutableStateOf("") }
    var descripcion by remember { mutableStateOf("") }
    var fotoUri by remember { mutableStateOf<Uri?>(null) }
    var uriTemporalCamara by remember { mutableStateOf<Uri?>(null) }

    val publicaciones by viewModel.publicaciones.collectAsState()

    val launcherGaleria = rememberLauncherForActivityResult(ActivityResultContracts.GetContent()) { uri: Uri? ->
        if (uri != null) fotoUri = uri
    }

    val launcherCamara = rememberLauncherForActivityResult(ActivityResultContracts.TakePicture()) { exito ->
        if (exito && uriTemporalCamara != null) {
            fotoUri = uriTemporalCamara
        }
    }

    Column(modifier = Modifier.padding(16.dp)) {
        OutlinedTextField(
            value = titulo,
            onValueChange = { titulo = it },
            label = { Text("Titulo") },
            modifier = Modifier.fillMaxWidth()
        )

        OutlinedTextField(
            value = descripcion,
            onValueChange = { descripcion = it },
            label = { Text("Descripcion") },
            modifier = Modifier.fillMaxWidth()
        )

        Row(
            modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Button(onClick = { launcherGaleria.launch("image/*") }) {
                Text("Galeria")
            }
            Button(onClick = {
                val archivo = crearArchivoImagen(context)
                val uri = FileProvider.getUriForFile(
                    context,
                    "com.example.redsociallocal.provider",
                    archivo
                )
                uriTemporalCamara = uri
                launcherCamara.launch(uri)
            }) {
                Text("Camara")
            }
        }

        if (fotoUri != null) {
            Text("Imagen lista para publicar", color = MaterialTheme.colorScheme.primary)
        }

        Button(
            onClick = {
                if (titulo.isNotBlank() && fotoUri != null) {
                    viewModel.agregarPublicacion(titulo, descripcion, fotoUri.toString())
                    titulo = ""
                    descripcion = ""
                    fotoUri = null
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Publicar")
        }

        Spacer(modifier = Modifier.height(16.dp))
        HorizontalDivider()

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(publicaciones) { pub ->
                Card(modifier = Modifier.fillMaxWidth().padding(vertical = 8.dp)) {
                    Column(modifier = Modifier.padding(16.dp)) {
                        Text(text = pub.titulo, style = MaterialTheme.typography.titleLarge)
                        Text(text = pub.fecha, style = MaterialTheme.typography.bodySmall)
                        Spacer(modifier = Modifier.height(8.dp))
                        Text(text = pub.descripcion)
                        Spacer(modifier = Modifier.height(8.dp))
                        AsyncImage(
                            model = pub.fotoUri,
                            contentDescription = "Foto de publicacion",
                            modifier = Modifier.height(200.dp).fillMaxWidth()
                        )
                    }
                }
            }
        }
    }
}