package com.jgc.ejerciciotoolbar

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.jgc.ejerciciotoolbar.databinding.ActivityEjemploFragmentosBinding

class ejemploFragmentos : AppCompatActivity() {
  private lateinit var binding: ActivityEjemploFragmentosBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityEjemploFragmentosBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val toolbar: MaterialToolbar = binding.materialToolbar
    setSupportActionBar(toolbar)
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.mi_menu, menu)
    return true
  }

  override fun onOptionsItemSelected(item: MenuItem): Boolean {
    return when (item.itemId) {
      R.id.menu_search -> {
        val fragmentoEjemplo = EjemploFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.miFragmento, fragmentoEjemplo)
        fragmentTransaction.commit()
        true
      }

      R.id.menu_info -> {
        val fragmentoEjemplo = infoFragment()
        val fragmentTransaction = supportFragmentManager.beginTransaction()

        fragmentTransaction.replace(R.id.miFragmento, fragmentoEjemplo)
        fragmentTransaction.commit()
        true
      }

      else -> false
    }
  }
}