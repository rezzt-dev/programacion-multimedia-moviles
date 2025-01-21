package com.example.blacktasks.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.blacktasks.R
import com.example.blacktasks.databinding.ActivityInfoBinding
import com.google.android.material.appbar.MaterialToolbar

class InfoActivity : AppCompatActivity() {
    private lateinit var binding: ActivityInfoBinding
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInfoBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUsuario = intent.getIntExtra("USER_ID", -1)
        if (idUsuario == -1) {
            showToast("Error: Usuario no válido.")
            finish()
            return
        }

        val toolbar: MaterialToolbar = binding.mainMenu
        setSupportActionBar(toolbar)

        val aboutText = getString(R.string.about_text) + idUsuario
        binding.textInfo.text = aboutText
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_main_window -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_ID", idUsuario)
                startActivity(intent)
                return true
            }
            R.id.ic_preferences -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                intent.putExtra("USER_ID", idUsuario)
                startActivity(intent)
                return true
            }
            R.id.ic_list_window -> {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("USER_ID", idUsuario)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
