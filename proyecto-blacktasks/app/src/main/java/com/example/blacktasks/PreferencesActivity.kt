package com.example.blacktasks

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.blacktasks.databinding.ActivityMainBinding
import com.example.blacktasks.databinding.ActivityPreferencesBinding
import com.google.android.material.appbar.MaterialToolbar

class PreferencesActivity : AppCompatActivity() {
    private lateinit var binding: ActivityPreferencesBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPreferencesBinding.inflate(layoutInflater)
        setContentView(binding.root)

    }
}