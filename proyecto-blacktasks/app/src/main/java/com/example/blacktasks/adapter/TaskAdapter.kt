package com.example.blacktasks.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckBox
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.model.Tarea
import com.example.blacktasks.viewmodel.TareaConexionHelper

/**
 * Adaptador para mostrar y gestionar una lista de tareas en un RecyclerView.
 * Este adaptador se encarga de enlazar las tareas con las vistas y permitir la interacción con ellas,
 * como marcar tareas como completadas o filtrar las tareas por título.
 *
 * @property tasks La lista mutable de tareas que se mostrará en el RecyclerView.
 */
class TaskAdapter(private val tasks: MutableList<Tarea>) : RecyclerView.Adapter<TaskAdapter.ViewHolder>() {

    // Lista de tareas filtradas para mostrar en el RecyclerView.
    private var filteredTasks: List<Tarea> = tasks.toList()

    /**
     * Clase que representa un elemento de la vista en el RecyclerView.
     * Contiene referencias a las vistas que mostrarán los detalles de cada tarea.
     *
     * @param view La vista de un ítem individual del RecyclerView.
     */
    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val checkBox: CheckBox = view.findViewById(R.id.taskCheckBox) // Checkbox para marcar si la tarea está completada
        val titleTextView: TextView = view.findViewById(R.id.taskTitle) // TextView para mostrar el título de la tarea
        val descriptionTextView: TextView = view.findViewById(R.id.taskDescription) // TextView para mostrar la descripción de la tarea
    }

    /**
     * Crea una nueva vista para un elemento del RecyclerView.
     * Se llama cuando se necesita una nueva vista para un ítem en la lista.
     *
     * @param parent El contenedor que albergará el nuevo ítem.
     * @param viewType El tipo de vista, que se puede usar para manejar diferentes tipos de ítems.
     * @return Un nuevo ViewHolder con la vista inflada.
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_task, parent, false) // Infla el layout para un ítem de tarea
        return ViewHolder(view)
    }

    /**
     * Asocia los datos de una tarea a las vistas correspondientes en el ViewHolder.
     * Se llama para cada ítem cuando el RecyclerView necesita mostrarlo.
     *
     * @param holder El ViewHolder donde se deben asignar los datos.
     * @param position La posición de la tarea dentro de la lista.
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val task = tasks[position] // Obtiene la tarea en la posición dada

        holder.titleTextView.text = task.titulo // Establece el título de la tarea
        holder.descriptionTextView.text = task.descripcion // Establece la descripción de la tarea

        // Configurar el estado del CheckBox
        holder.checkBox.setOnCheckedChangeListener(null) // Desvincular cualquier listener previo
        holder.checkBox.isChecked = task.realizada // Establecer el estado actual

        // Establecer nuevo listener
        holder.checkBox.setOnCheckedChangeListener { _, isChecked ->
            task.realizada = isChecked // Actualizar el estado de la tarea

            // Opcional: guardar el cambio en la base de datos
            guardarEstadoTarea(holder.itemView.context as AppCompatActivity, task)
        }
    }

    private fun guardarEstadoTarea(context: AppCompatActivity, task: Tarea) {
        TareaConexionHelper.modTarea(context, task.id, task) // Actualiza el estado en la base de datos
    }



    /**
     * Devuelve el número total de ítems en la lista de tareas.
     *
     * @return El número de tareas en la lista.
     */
    override fun getItemCount() = tasks.size

    /**
     * Filtra la lista de tareas según el título de la tarea.
     * Si el texto de búsqueda es vacío, se muestra todas las tareas.
     *
     * @param query El texto con el cual se filtrarán las tareas por título.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun filter(query: String) {
        filteredTasks = if (query.isEmpty()) {
            tasks.toList() // Si la búsqueda está vacía, muestra todas las tareas
        } else {
            tasks.filter {
                it.titulo.contains(query, ignoreCase = true) // Filtra por el título de la tarea
            }
        }
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    /**
     * Añade una nueva tarea a la lista de tareas y notifica al RecyclerView que un ítem ha sido insertado.
     *
     * @param task La tarea a añadir.
     */
    fun addTask(task: Tarea) {
        tasks.add(task) // Añade la tarea a la lista
        notifyItemInserted(tasks.size - 1) // Notifica que se ha añadido un nuevo ítem
    }

    /**
     * Elimina todas las tareas que están marcadas como completadas.
     * Luego, notifica al RecyclerView que los datos han cambiado.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun removeCheckedItems() {
        tasks.removeAll { it.realizada } // Elimina las tareas completadas
        notifyDataSetChanged() // Notifica que los datos han cambiado
    }

    /**
     * Actualiza la lista de tareas y notifica al RecyclerView que los datos han cambiado.
     *
     * @param newTasks La nueva lista de tareas que se mostrará en el RecyclerView.
     */
    fun submitList(newTasks: List<Tarea>) {
        tasks.clear() // Limpia la lista actual de tareas
        tasks.addAll(newTasks) // Agrega todas las nuevas tareas a la lista
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    /**
     * Filtra solo las tareas que no están completadas.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun filterIncompleteTasks() {
        filteredTasks = tasks.filter { !it.realizada } // Filtra las tareas incompletas
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    /**
     * Filtra solo las tareas que están completadas.
     */
    @SuppressLint("NotifyDataSetChanged")
    fun filterCompletedTasks() {
        filteredTasks = tasks.filter { it.realizada } // Filtra las tareas completadas
        notifyDataSetChanged() // Notifica al RecyclerView que los datos han cambiado
    }

    fun getCheckedItems(): List<Tarea> {
        return tasks.filter { it.realizada } // Suponiendo que `isChecked` es una propiedad de cada tarea
    }

    fun removeTask(task: Tarea) {
        val position = tasks.indexOf(task)
        if (position != -1) {
            tasks.removeAt(position)
            notifyItemRemoved(position) // Notifica que el elemento fue eliminado
        }
    }

    fun updateTasks(newTasks: List<Tarea>) {
        tasks.clear()
        tasks.addAll(newTasks)
        notifyDataSetChanged() // Refresca la vista con las nuevas tareas
    }
}
