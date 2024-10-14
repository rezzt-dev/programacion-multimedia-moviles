package com.jgc.ejerciciosbotones

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Patterns
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejerciciosbotones.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.button.setOnClickListener() {
      // validarTelefono(binding.telefono.text.toString())
      val Intent = Intent(this, MainActivity2::class.java)
      startActivity(Intent)
    }
  }

  fun validarTelefono (telefono: String) {
    if (Patterns.PHONE.matcher(telefono).matches()) {
      binding.telefono.setError(null)
    } else {
      binding.telefono.setError("Telefono invalido")
    }
  }
}