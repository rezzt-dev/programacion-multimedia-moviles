package com.example.blacktasks

import android.content.SharedPreferences
import android.os.Bundle
import android.widget.Toast
import androidx.preference.PreferenceFragmentCompat

class PrefsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preferences, rootKey)
    }

    override fun onResume() {
        super.onResume()
        preferenceManager.sharedPreferences?.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onPause() {
        super.onPause()
        preferenceManager.sharedPreferences?.unregisterOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        when (key) {
            "pref_username" -> {
                val isNotificacionEnabled = sharedPreferences?.getBoolean(key, true) ?: true
                Toast.makeText(
                    this.context,
                    "Notificaciones: ${if (isNotificacionEnabled) "Activadas" else "Desactivadas"}",
                    Toast.LENGTH_LONG).show()
            }

            "pref_notifications" -> {
                val userName = sharedPreferences?.getString(key, "Usuario") ?: "Usuario"
                Toast.makeText(this.context,
                    "UserName: $userName",
                    Toast.LENGTH_LONG).show()
            }
        }
    }
}