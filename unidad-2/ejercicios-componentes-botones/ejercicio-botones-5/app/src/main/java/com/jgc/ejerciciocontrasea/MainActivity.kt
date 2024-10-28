package com.jgc.ejerciciocontrasea

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.CheckBox
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejerciciocontrasea.databinding.ActivityMainBinding
import android.widget.EditText

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var showPasswordCheckBox: CheckBox
  private lateinit var passwordEditText: EditText

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    passwordEditText = findViewById(R.id.editTextTextPassword)
    showPasswordCheckBox = findViewById(R.id.checkBox)

    showPasswordCheckBox.setOnCheckedChangeListener { _, isChecked ->
      if (isChecked) {
        // Mostrar contraseña
        passwordEditText.transformationMethod = HideReturnsTransformationMethod.getInstance()
      } else {
        // Ocultar contraseña
        passwordEditText.transformationMethod = PasswordTransformationMethod.getInstance()
      }
      // Mover el cursor al final del texto
      passwordEditText.setSelection(passwordEditText.text.length)
    }
  }
}