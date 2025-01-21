package com.example.blacktasks.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.blacktasks.databinding.ActivityLoginBinding
import com.example.blacktasks.model.Usuario
import com.example.blacktasks.viewmodel.UsuarioConexionHelper

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Inicializa SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Inicializa los usuarios predeterminados si no existen
        UsuarioConexionHelper.initUsuarios(this)

        binding.btnLogin.setOnClickListener {
            val username = binding.txtUsername.text.toString()
            val password = binding.editTextTextPassword.text.toString()

            if (username.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

            // Comprobar las credenciales del usuario
            val usuario = comprobarCredenciales(username, password)

            if (usuario != null) {
                // Guardar el ID del usuario en SharedPreferences
                sharedPreferences.edit().putInt("USER_ID", usuario.id).apply()

                // Credenciales correctas, abrir MainActivity
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_ID", usuario.id)
                startActivity(intent)
                finish()
            } else {
                // Credenciales incorrectas
                Toast.makeText(this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun comprobarCredenciales(username: String, password: String): Usuario? {
        val usuarios = UsuarioConexionHelper.obtenerUsuarios(this)
        return usuarios.find { it.username == username && it.password == password }
    }
}
