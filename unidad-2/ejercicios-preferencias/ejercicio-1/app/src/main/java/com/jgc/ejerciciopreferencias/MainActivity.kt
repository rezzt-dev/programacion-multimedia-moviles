package com.jgc.ejerciciopreferencias

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceManager
import com.jgc.ejerciciopreferencias.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
    val notificacionHabilitada = sharedPreferences.getBoolean("pref_checkbox", true)
    val nombreUser = sharedPreferences.getString("pref_texto", "Usuario")
  }
}