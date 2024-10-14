package com.jgc.ejerciciosvistas

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejerciciosvistas.databinding.ActivityMainBinding
import java.util.regex.PatternSyntaxException

class actividadValidar : AppCompatActivity() {
  private lateinit var binding: ActividadValidarBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActividadValidarBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.button.setOnClickListener() {
      validarTelefono(binding.telefono.text.toString())
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