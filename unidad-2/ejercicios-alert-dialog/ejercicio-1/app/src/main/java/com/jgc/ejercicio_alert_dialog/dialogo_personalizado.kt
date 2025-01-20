package com.jgc.ejercicio_alert_dialog

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejercicio_alert_dialog.databinding.ActivityDialogoPersonalizadoBinding

class dialogo_personalizado : AppCompatActivity() {
  private lateinit var binding: ActivityDialogoPersonalizadoBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityDialogoPersonalizadoBinding.inflate(layoutInflater)
    setContentView(binding.root)

  }
}