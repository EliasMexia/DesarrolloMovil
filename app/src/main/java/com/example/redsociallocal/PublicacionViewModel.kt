package com.example.redsociallocal

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class PublicacionViewModel(private val dao: PublicacionDao) : ViewModel() {

    val publicaciones = dao.obtenerTodas().stateIn(
        scope = viewModelScope,
        started = SharingStarted.Lazily,
        initialValue = emptyList()
    )

    fun agregarPublicacion(titulo: String, descripcion: String, fotoUri: String) {
        val fechaActual = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale.getDefault()).format(Date())
        viewModelScope.launch {
            dao.insertar(Publicacion(
                titulo = titulo,
                descripcion = descripcion,
                fotoUri = fotoUri,
                fecha = fechaActual
            ))
        }
    }
}