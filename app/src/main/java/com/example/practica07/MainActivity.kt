package com.example.practica07

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.PopupMenu
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.practica07.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Configurar la MaterialToolbar como ActionBar oficial de soporte
        setSupportActionBar(binding.topAppBar)

        // Manejar evento del icono de navegacion (icono hamburguesa)
        binding.topAppBar.setNavigationOnClickListener {
            Toast.makeText(this, "Navegacion lateral presionada", Toast.LENGTH_SHORT).show()
            binding.tvStatus.text = "Accion: Icono de navegacion"
        }

        // Manejar el boton que despliega el PopupMenu
        binding.btnShowPopup.setOnClickListener { view ->
            showCustomPopupMenu(view)
        }
    }

    // Inflar el menu de opciones superior (OptionsMenu)
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    // Capturar clics sobre los elementos de la Toolbar
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_search -> {
                binding.tvStatus.text = "Seleccionaste: Buscar"
                Toast.makeText(this, "Buscar presionado", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_favorite -> {
                binding.tvStatus.text = "Seleccionaste: Favoritos"
                Toast.makeText(this, "Favoritos presionado", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_settings -> {
                binding.tvStatus.text = "Seleccionaste: Configuracion"
                Toast.makeText(this, "Configuracion abierta", Toast.LENGTH_SHORT).show()
                true
            }
            R.id.action_about -> {
                binding.tvStatus.text = "Seleccionaste: Acerca de"
                Toast.makeText(this, "Practica 07 - Desarrollo Movil", Toast.LENGTH_SHORT).show()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    // Funcion para inflar y mostrar el PopupMenu anclado a una vista
    private fun showCustomPopupMenu(anchorView: android.view.View) {
        val popup = PopupMenu(this, anchorView)
        popup.menuInflater.inflate(R.menu.popup_menu, popup.menu)

        popup.setOnMenuItemClickListener { menuItem ->
            when (menuItem.itemId) {
                R.id.pop_share -> {
                    binding.tvStatus.text = "Popup: Compartir enlace"
                    Toast.makeText(this, "Compartiendo...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.pop_download -> {
                    binding.tvStatus.text = "Popup: Descargar reporte"
                    Toast.makeText(this, "Descargando reporte...", Toast.LENGTH_SHORT).show()
                    true
                }
                R.id.pop_delete -> {
                    binding.tvStatus.text = "Popup: Eliminar elemento"
                    Toast.makeText(this, "Elemento eliminado", Toast.LENGTH_SHORT).show()
                    true
                }
                else -> false
            }
        }
        popup.show()
    }
}