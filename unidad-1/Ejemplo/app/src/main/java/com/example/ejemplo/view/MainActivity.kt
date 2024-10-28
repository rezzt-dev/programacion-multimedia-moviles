package com.example.ejemplo.view

import android.os.Bundle
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.ejemplo.R
import com.example.ejemplo.viewModel.CitasViewModel
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recuperar viewModel
        val viewModel: CitasViewModel by viewModels()

        //Pintar pantallas
        val valorCita = findViewById<TextView>(R.id.tvCita)
        valorCita.text = viewModel.cita.cita

        val valorAutor = findViewById<TextView>(R.id.tvAutor)
        valorAutor.text = viewModel.cita.autor
    }
}