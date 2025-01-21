package com.example.blacktasks.view.activities

import android.app.Activity
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.preference.ListPreference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import androidx.preference.SwitchPreferenceCompat
import com.example.blacktasks.R
import com.example.blacktasks.databinding.ActivityPreferencesBinding
import com.google.android.material.appbar.MaterialToolbar
import java.util.Locale

class PreferencesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreferencesBinding
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idUsuario = intent.getIntExtra("USER_ID", -1)
        if (idUsuario == -1) {
            showToast("Error: Usuario no válido.")
            finish()
            return
        }

        val toolbar: MaterialToolbar = binding.mainMenu
        setSupportActionBar(toolbar)

        supportFragmentManager.beginTransaction()
            .replace(R.id.mainFragment, SettingsFragment())
            .commit()
    }

    class SettingsFragment : PreferenceFragmentCompat() {
        override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
            setPreferencesFromResource(R.xml.preferences, rootKey)

            val languagePreference = findPreference<ListPreference>("pref_language")
            languagePreference?.setOnPreferenceChangeListener { _, newValue ->
                val languageCode = newValue as String
                updateLanguage(languageCode)
                true
            }

            val themeSwitch = findPreference<SwitchPreferenceCompat>("pref_dark_mode")
            themeSwitch?.setOnPreferenceChangeListener { _, newValue ->
                setThemeMode(newValue as Boolean)
                true
            }
        }

        private fun updateLanguage(languageCode: String) {
            val locale = Locale(languageCode)
            Locale.setDefault(locale)
            val config = resources.configuration
            config.setLocale(locale)
            activity?.createConfigurationContext(config)
            resources.updateConfiguration(config, resources.displayMetrics)
            activity?.recreate()
        }

        private fun setThemeMode(isDarkMode: Boolean) {
            if (isDarkMode) {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            } else {
                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
            }
        }
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
            R.id.ic_acerca_de -> {
                val intent = Intent(this, InfoActivity::class.java)
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
