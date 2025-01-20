package com.example.blacktasks.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.model.Task // Asegúrate de tener una clase Task
import com.example.blacktasks.adapter.TaskAdapter

/**
 * Fragmento que muestra las tareas completadas en una lista utilizando un RecyclerView.
 * Este fragmento está diseñado para mostrar una lista de tareas completadas en la interfaz de usuario.
 */
class CompletedTasksFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas completadas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas completadas en el RecyclerView
    private lateinit var adapter: TaskAdapter

    /**
     * Método que se llama cuando se crea la vista del fragmento.
     * Infla el diseño del fragmento y configura el RecyclerView.
     *
     * @param inflater El objeto LayoutInflater que infla el diseño del fragmento.
     * @param container El contenedor en el que se colocará la vista del fragmento.
     * @param savedInstanceState El estado guardado del fragmento, si existe.
     * @return La vista inflada que representa el fragmento.
     */
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Infla el layout del fragmento (fragment_completed_tasks)
        val view = inflater.inflate(R.layout.fragment_completed_tasks, container, false)

        // Inicializa el RecyclerView encontrando el componente por su ID
        recyclerView = view.findViewById(R.id.recycler_view_completed_notes)

        // Inicializa el adaptador con una lista mutable vacía de tareas completadas
        adapter = TaskAdapter(mutableListOf())

        // Asigna el adaptador al RecyclerView para que pueda mostrar los datos
        recyclerView.adapter = adapter

        // Establece el LayoutManager para el RecyclerView, en este caso, un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Filtra las tareas completadas usando el método en el adaptador
        adapter.filterCompletedTasks()

        // Devuelve la vista inflada que representa el fragmento
        return view
    }
}
