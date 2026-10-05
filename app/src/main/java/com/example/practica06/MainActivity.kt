package com.example.practica06

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.practica06.databinding.ActivityMainBinding
import com.google.android.material.snackbar.Snackbar


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: ProductAdapter
    private val productList = mutableListOf<Product>()
    private var sequenceId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        loadInitialData()
        setupRecyclerView()
        setupListeners()
    }

    private fun loadInitialData() {
        val initialData = listOf(
            Product(sequenceId++, "Teclado Mecanico", "Hardware", 850.0, 10),
            Product(sequenceId++, "Monitor 24 Pulgadas", "Pantallas", 2600.0, 5),
            Product(sequenceId++, "Mouse Optico USB", "Hardware", 250.0, 25),
            Product(sequenceId++, "Hub USB-C 6 en 1", "Accesorios", 620.0, 14),
            Product(sequenceId++, "Auriculares con Cancelacion", "Audio", 1850.0, 8)
        )
        productList.addAll(initialData)
    }

    private fun setupRecyclerView() {
        adapter = ProductAdapter(productList) { selectedProduct ->
            Snackbar.make(
                binding.root,
                "Seleccionaste: ${selectedProduct.name} ($${selectedProduct.price})",
                Snackbar.LENGTH_SHORT
            ).show()
        }

        binding.recyclerViewProducts.layoutManager = LinearLayoutManager(this)
        binding.recyclerViewProducts.adapter = adapter
    }

    private fun setupListeners() {
        binding.fabAddProduct.setOnClickListener {
            val newProduct = Product(
                id = sequenceId++,
                name = "Item Dinamico #${sequenceId - 1}",
                category = "General",
                price = (150..1200).random().toDouble(),
                stock = (1..50).random()
            )
            adapter.addProduct(newProduct)
            binding.recyclerViewProducts.smoothScrollToPosition(productList.size - 1)
        }
    }
}