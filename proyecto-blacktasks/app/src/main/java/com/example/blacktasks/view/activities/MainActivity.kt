package com.example.blacktasks.view.activities

import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.preference.PreferenceManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.blacktasks.R
import com.example.blacktasks.adapter.TaskAdapter
import com.example.blacktasks.databinding.ActivityMainBinding
import com.example.blacktasks.model.Tarea
import com.example.blacktasks.viewmodel.TareaConexionHelper
import java.util.Locale

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var adapter: TaskAdapter
    private var idUsuario: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.mainMenu)

        // Inicializa SharedPreferences
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this)

        // Obtén el ID del usuario desde SharedPreferences
        idUsuario = sharedPreferences.getInt("USER_ID", -1)

        if (idUsuario == -1) {
            showToast("Error: Usuario no válido.")
            finish()
            return
        }

        applyLanguage()
        setupRecyclerView()
        loadUserTasks()
        setupViews()
    }

    private fun setupViews() {
        binding.addTask.setOnClickListener { showAddTaskDialog() }
        binding.deleteTask.setOnClickListener { showDeleteConfirmationDialog() }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
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

            R.id.ic_list_window -> {
                val intent = Intent(this, ListActivity::class.java)
                intent.putExtra("USER_ID", idUsuario) // Pasa el ID del usuario
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
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
        adapter = TaskAdapter(mutableListOf())
        binding.recyclerViewDrivers.adapter = adapter
        binding.recyclerViewDrivers.layoutManager = LinearLayoutManager(this)
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
                    addNewTask(title, description)
                } else {
                    showToast("El título no puede estar vacío.")
                }
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun addNewTask(title: String, description: String) {
        val newTask = Tarea(0, title, description, false, idUsuario)
        val taskId = TareaConexionHelper.addTarea(this, newTask)
        if (taskId != -1L) {
            newTask.id = taskId.toInt()
            adapter.addTask(newTask)
            binding.recyclerViewDrivers.scrollToPosition(adapter.itemCount - 1)
            showToast("Tarea añadida con éxito.")
        } else {
            showToast("Error al añadir la tarea.")
        }
    }

    private fun showDeleteConfirmationDialog() {
        AlertDialog.Builder(this)
            .setTitle(getString(R.string.delete_selected))
            .setMessage(getString(R.string.confirm))
            .setPositiveButton(getString(R.string.confirm)) { _, _ ->
                val tasksToRemove = adapter.getCheckedItems() // Obtiene las tareas seleccionadas
                for (task in tasksToRemove) {
                    val rowsDeleted = TareaConexionHelper.delTarea(this, task.id) // Elimina cada tarea por ID
                    if (rowsDeleted > 0) {
                        adapter.removeTask(task) // Actualiza el adaptador eliminando la tarea
                    } else {
                        showToast("Error al eliminar la tarea con ID: ${task.id}")
                    }
                }
                loadUserTasks() // Recarga las tareas desde la base de datos
            }
            .setNegativeButton(getString(R.string.cancel), null)
            .show()
    }

    private fun loadUserTasks() {
        val userTasks = TareaConexionHelper.obtenerTareasPorUsuario(this, idUsuario)
        adapter.updateTasks(userTasks)
    }

    private fun showToast(message: String) {
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}
