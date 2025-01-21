package com.example.blacktasks.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.graphics.Typeface
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import com.example.blacktasks.R
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
                mostrarDialogoError("Por favor, completa todos los campos")
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
                mostrarDialogoError("Usuario o contraseña incorrectos")
            }
        }
    }

    private fun comprobarCredenciales(username: String, password: String): Usuario? {
        val usuarios = UsuarioConexionHelper.obtenerUsuarios(this)
        return usuarios.find { it.username == username && it.password == password }
    }

    private fun mostrarDialogoError(mensaje: String) {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("Error")
        builder.setIcon(R.drawable.error_icon)

        // Crear un TextView personalizado para el mensaje
        val messageView = TextView(this)
        messageView.text = mensaje
        messageView.textSize = 18f
        messageView.setTypeface(Typeface.SANS_SERIF, Typeface.BOLD)
        messageView.setPadding(50, 20, 50, 20)
        messageView.textAlignment = TextView.TEXT_ALIGNMENT_CENTER

        // Añadir el TextView al diálogo
        builder.setView(messageView)

        // Botón de aceptar
        builder.setPositiveButton("Aceptar") { dialog, _ ->
            dialog.dismiss()
        }

        // Mostrar el diálogo
        val alertDialog = builder.create()
        alertDialog.show()
    }
}
