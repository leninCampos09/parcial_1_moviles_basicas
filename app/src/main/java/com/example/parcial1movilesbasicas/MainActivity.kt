package com.example.parcial1movilesbasicas

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioGroup
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        // Configurando la toolbar
        val toolbar: Toolbar = findViewById(R.id.toolbar)
        toolbar.title = "Mi Café"
        setSupportActionBar(toolbar)

        // Elementos de la interfaz
        val radioGroup = findViewById<RadioGroup>(R.id.rgTamañoCafe)
        val cbCrema = findViewById<CheckBox>(R.id.cbCrema)
        val cbAzucar = findViewById<CheckBox>(R.id.cbAzucar)
        val cbDescafeinado = findViewById<CheckBox>(R.id.cbDescafeinado)
        val btnEnviarPedido = findViewById<Button>(R.id.btnEnviarPedido)
        val tvResumenPedido = findViewById<TextView>(R.id.tvResumenPedido)  // Referencia al TextView

        // Precios para las opciones de personalización
        val precioCrema = 0.5
        val precioAzucar = 0.3
        val precioDescafeinado = 0.7

        btnEnviarPedido.setOnClickListener {
            // Obtener tamaño seleccionado y asignar precio
            var precioTamaño = 0.0
            val tamaño = when (radioGroup.checkedRadioButtonId) {
                R.id.rbPequeño -> {
                    precioTamaño = 1.0
                    "Pequeño"
                }
                R.id.rbMediano -> {
                    precioTamaño = 1.5
                    "Mediano"
                }
                R.id.rbGrande -> {
                    precioTamaño = 2.0
                    "Grande"
                }
                else -> "No seleccionado"
            }

            // Obtener personalización y calcular el total
            val personalizacion = mutableListOf<String>()
            var total = precioTamaño

            if (cbCrema.isChecked) {
                personalizacion.add("Crema extra")
                total += precioCrema
            }
            if (cbAzucar.isChecked) {
                personalizacion.add("Azúcar extra")
                total += precioAzucar
            }
            if (cbDescafeinado.isChecked) {
                personalizacion.add("Sin cafeína")
                total += precioDescafeinado
            }

            // Crear un resumen
            val resumen = "Tamaño del café: $tamaño\n" +
                    "Personalización: ${personalizacion.joinToString(", ")}\n" +
                    "Total: $${String.format("%.2f", total)}"

            // Mostrar el resumen en el TextView
            tvResumenPedido.text = resumen
        }
    }

    // Inflar el menú de opciones
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    // Manejar la opción de salir
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_exit -> {
                // Acción para cerrar la aplicación
                finish() // Cierra la actividad actual, terminando la app
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}

