package com.example.registroestudiantes

import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {
    private val prefs: SharedPreferences =
        context.getSharedPreferences("student_records_prefs", Context.MODE_PRIVATE)

    fun guardarUltimoRegistro(
        matricula: String,
        nombre: String,
        carrera: String,
        turno: String,
        activo: Boolean
    ) {
        prefs.edit()
            .putString("key_matricula", matricula)
            .putString("key_nombre", nombre)
            .putString("key_carrera", carrera)
            .putString("key_turno", turno)
            .putBoolean("key_activo", activo)
            .apply()
    }

    fun obtenerMatricula(): String = prefs.getString("key_matricula", "") ?: ""

    fun obtenerNombre(): String = prefs.getString("key_nombre", "") ?: ""

    fun obtenerCarrera(default: String): String = prefs.getString("key_carrera", default) ?: default

    fun obtenerTurno(default: String): String = prefs.getString("key_turno", default) ?: default

    fun obtenerActivo(): Boolean = prefs.getBoolean("key_activo", true)
}