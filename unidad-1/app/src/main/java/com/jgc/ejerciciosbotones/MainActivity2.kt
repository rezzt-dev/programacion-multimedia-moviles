package com.jgc.ejerciciosbotones

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejerciciosbotones.databinding.ActivityMain2Binding

class MainActivity2 : AppCompatActivity() {
  private lateinit var binding: ActivityMain2Binding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMain2Binding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.button2.setOnClickListener() {
      // validarTelefono(binding.telefono.text.toString())
      val Intent = Intent(this, MainActivity::class.java)
      startActivity(Intent)
    }
  }
}