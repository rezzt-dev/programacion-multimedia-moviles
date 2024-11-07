package com.jgc.ejerciciotoolbar

import android.os.Bundle
import android.view.Menu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.google.android.material.appbar.MaterialToolbar
import com.jgc.ejerciciotoolbar.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    val toolbar: MaterialToolbar = binding.materialToolbar
    setSupportActionBar(toolbar)

    binding.btAddFragment.setOnClickListener() {
      val fragmentoEjemplo = EjemploFragment()

      val fragmentTransaction = supportFragmentManager.beginTransaction()
      fragmentTransaction.replace(R.id.miFragmento, fragmentoEjemplo)
      fragmentTransaction.commit()
    }
  }

  override fun onCreateOptionsMenu(menu: Menu?): Boolean {
    menuInflater.inflate(R.menu.mi_menu, menu)
    return true
  }

  

}