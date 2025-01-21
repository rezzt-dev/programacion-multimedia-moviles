package com.example.blacktasks.view.activities

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.viewpager2.widget.ViewPager2
import com.example.blacktasks.R
import com.example.blacktasks.adapter.TaskPagerAdapter
import com.example.blacktasks.databinding.ActivityListBinding
import com.google.android.material.appbar.MaterialToolbar
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator

/**
 * Actividad que maneja la lista de tareas con diferentes pestañas (todas, incompletas, completadas).
 */
class ListActivity : AppCompatActivity() {
    private lateinit var binding: ActivityListBinding
    private lateinit var viewPager: ViewPager2
    private lateinit var tabLayout: TabLayout
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityListBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Obtén el ID del usuario desde el Intent
        idUsuario = intent.getIntExtra("USER_ID", -1)
        if (idUsuario == -1) {
            // Si no se recibió un ID válido, finaliza la actividad
            finish()
            return
        }

        // Configura la barra de herramientas
        val toolbar: MaterialToolbar = binding.mainMenu
        setSupportActionBar(toolbar)

        // Configura el ViewPager2 y el TabLayout
        viewPager = binding.viewPager
        tabLayout = binding.optionsTabs

        // Configura el adaptador del ViewPager con el ID del usuario
        val pagerAdapter = TaskPagerAdapter(supportFragmentManager, lifecycle, idUsuario)
        viewPager.adapter = pagerAdapter

        // Conecta el TabLayout con el ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> getString(R.string.list_all_task) // Todas las tareas
                1 -> getString(R.string.list_incompleted_task) // Tareas incompletas
                2 -> getString(R.string.list_completed_task) // Tareas completadas
                else -> null
            }
        }.attach()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_main_window -> {
                val intent = Intent(this, MainActivity::class.java)
                intent.putExtra("USER_ID", idUsuario) // Pasa el ID del usuario
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                startActivity(intent)
                return true
            }
            R.id.ic_preferences -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                intent.putExtra("USER_ID", idUsuario) // Pasa el ID del usuario
                startActivity(intent)
                return true
            }
            R.id.ic_acerca_de -> {
                val intent = Intent(this, InfoActivity::class.java)
                intent.putExtra("USER_ID", idUsuario) // Pasa el ID del usuario
                startActivity(intent)
                return true
            }
            R.id.ic_web_page -> {
                // Abre una página web en el navegador
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educamosclm.castillalamancha.es/"))
                startActivity(webIntent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
