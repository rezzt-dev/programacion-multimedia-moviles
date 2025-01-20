package com.example.blacktasks.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.InputType
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blacktasks.R
import com.example.blacktasks.databinding.ActivityMainBinding
import com.example.blacktasks.adapter.TaskAdapter
import com.example.blacktasks.model.Task
import java.util.Locale


class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: TaskAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)
        applyLanguage() // Aplica el idioma configurado en las preferencias

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainMenu) // Establece la barra de herramientas como ActionBar

        setupViews() // Configura los botones y acciones
        setupRecyclerView() // Configura el RecyclerView para mostrar las tareas
    }

    private fun setupViews() {
        binding.deleteTask.setOnClickListener { showDeleteConfirmationDialog() } // Acción para eliminar tareas
        binding.addTask.setOnClickListener { showAddTaskDialog() } // Acción para agregar nuevas tareas
    }

    private fun applyLanguage() {
        val languageCode = sharedPreferences.getString("pref_language", "en") ?: "en"
        val locale = Locale(languageCode)
        Locale.setDefault(locale)
        val config = resources.configuration
        config.setLocale(locale)
        createConfigurationContext(config)
        resources.updateConfiguration(config, resources.displayMetrics)
    }

    private fun setupRecyclerView() {
        adapter = TaskAdapter(mutableListOf()) // Crea un adaptador vacío
        binding.recyclerViewDrivers.adapter = adapter // Asigna el adaptador al RecyclerView
        binding.recyclerViewDrivers.layoutManager = LinearLayoutManager(this) // Establece el layout manager
    }

    private fun showAddTaskDialog() {
        val dialogView = layoutInflater.inflate(R.layout.dialog_add_task, null)
        val titleEditText = dialogView.findViewById<EditText>(R.id.taskTitleEditText)
        val descriptionEditText = dialogView.findViewById<EditText>(R.id.taskDescriptionEditText)

        AlertDialog.Builder(this).setView(dialogView)
            .setPositiveButton(getString(R.string.add_task)) { _, _ ->
                val title = titleEditText.text.toString()
                val description = descriptionEditText.text.toString()
                if (title.isNotEmpty()) {
                    addNewTask(title, description) // Si el título no está vacío, agrega la tarea
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_selected))
            .setMessage(getString(R.string.confirm))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                adapter.removeCheckedItems() // Elimina las tareas seleccionadas
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun showSearchDialog() {
        val input = EditText(this)
        input.inputType = InputType.TYPE_CLASS_TEXT

        AlertDialog.Builder(this)
            .setTitle(getString(R.string.search))
            .setView(input)
            .setPositiveButton(getString(R.string.search)) { _, _ ->
                val searchQuery = input.text.toString()
                performSearch(searchQuery) // Realiza la búsqueda con la consulta proporcionada
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun performSearch(query: String) {
        adapter.filter(query) // Filtra las tareas en el adaptador
    }

    private fun addNewTask(title: String, description: String) {
        val newTask = Task(adapter.itemCount + 1, title, description, false)
        adapter.addTask(newTask)
        binding.recyclerViewDrivers.scrollToPosition(adapter.itemCount - 1)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.ic_preferences -> {
                val intent = Intent(this, PreferencesActivity::class.java)
                startActivity(intent) // Inicia la actividad de preferencias
            }
            R.id.ic_web_page -> {
                val webIntent = Intent(Intent.ACTION_VIEW, Uri.parse("https://educamosclm.castillalamancha.es/"))
                startActivity(webIntent) // Abre la página web en el navegador
            }
            R.id.ic_acerca_de -> {
                val intent = Intent(this, InfoActivity::class.java)
                startActivity(intent) // Inicia la actividad de información
            }
            R.id.ic_list_window -> {
                val intent = Intent(this, ListActivity::class.java)
                startActivity(intent) // Inicia la actividad de lista de tareas
            }
        }
        return super.onOptionsItemSelected(item)
    }
}
