package com.example.practica05

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.SeekBar
import android.widget.Switch
import android.widget.TextView
import android.widget.Toast

class MainActivity : Activity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val checkWeb = findViewById<CheckBox>(R.id.checkWeb)
        val checkRedes = findViewById<CheckBox>(R.id.checkRedes)
        val checkIa = findViewById<CheckBox>(R.id.checkIa)
        val switchNotif = findViewById<Switch>(R.id.switchNotif)
        val barraExperiencia = findViewById<SeekBar>(R.id.barraExperiencia)
        val textoExperiencia = findViewById<TextView>(R.id.textoExperiencia)

        val btnGuardar = findViewById<Button>(R.id.btnGuardar)
        val btnCargar = findViewById<Button>(R.id.btnCargar)
        val btnBorrar = findViewById<Button>(R.id.btnBorrar)

        val prefs = getSharedPreferences("ConfiguracionApp", Context.MODE_PRIVATE)

        fun actualizarTextoExperiencia(valor: Int) {
            textoExperiencia.text = "Nivel de experiencia: $valor / 10"
        }

        fun cargarDatosGuardados() {
            checkWeb.isChecked = prefs.getBoolean("web", false)
            checkRedes.isChecked = prefs.getBoolean("redes", false)
            checkIa.isChecked = prefs.getBoolean("ia", false)
            switchNotif.isChecked = prefs.getBoolean("notificaciones", false)

            val nivel = prefs.getInt("experiencia", 0)
            barraExperiencia.progress = nivel
            actualizarTextoExperiencia(nivel)
        }

        cargarDatosGuardados()

        barraExperiencia.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                actualizarTextoExperiencia(progress)
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        btnGuardar.setOnClickListener {
            val editor = prefs.edit()
            editor.putBoolean("web", checkWeb.isChecked)
            editor.putBoolean("redes", checkRedes.isChecked)
            editor.putBoolean("ia", checkIa.isChecked)
            editor.putBoolean("notificaciones", switchNotif.isChecked)
            editor.putInt("experiencia", barraExperiencia.progress)
            editor.apply()

            Toast.makeText(this, "Configuracion guardada", Toast.LENGTH_SHORT).show()
        }

        btnCargar.setOnClickListener {
            cargarDatosGuardados()
            Toast.makeText(this, "Datos cargados correctamente", Toast.LENGTH_SHORT).show()
        }

        btnBorrar.setOnClickListener {
            prefs.edit().clear().apply()
            cargarDatosGuardados()
            Toast.makeText(this, "Datos borrados", Toast.LENGTH_SHORT).show()
        }
    }
}