package com.example.blacktasks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.blacktasks.databinding.ActivityMainBinding
import com.google.android.material.appbar.MaterialToolbar

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val toolbar : MaterialToolbar = binding.mainMenu
        setSupportActionBar(toolbar)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_main_window -> {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }

            R.id.ic_preferences -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent)
            }

            R.id.ic_web_page -> {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educamosclm.castillalamancha.es/"))
                startActivity(webIntent)
            }

            R.id.ic_acerca_de -> {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Acerca de")
                builder.setMessage(getString(R.string.about_text))
                builder.setPositiveButton("OK", null)
                builder.show()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}