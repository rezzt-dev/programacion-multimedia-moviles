package com.example.blacktasks.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.taskmanager.Task // Asegúrate de tener una clase Task
import com.example.blacktasks.taskmanager.TaskAdapter // Cambia a TaskAdapter

/**
 * Fragmento que muestra las tareas no completadas en una lista utilizando un RecyclerView.
 * Este fragmento está diseñado para mostrar las tareas que aún están pendientes de completar.
 */
class NoTasksCompletedFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas no completadas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas no completadas en el RecyclerView
    private lateinit var adapter: TaskAdapter // Cambia esto a tu adaptador de tareas

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
        // Infla el layout del fragmento (fragment_no_tasks_completed)
        val view = inflater.inflate(R.layout.fragment_no_tasks_completed, container, false)

        // Inicializa el RecyclerView encontrando el componente por su ID
        recyclerView = view.findViewById(R.id.recycler_view_incomplete_notes)

        // Inicializa el adaptador con una lista mutable vacía de tareas no completadas
        adapter = TaskAdapter(mutableListOf()) // Inicializa tu adaptador con las notas no completadas

        // Asigna el adaptador al RecyclerView para que pueda mostrar los datos
        recyclerView.adapter = adapter

        // Establece el LayoutManager para el RecyclerView, en este caso, un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Aquí puedes cargar las tareas no completadas desde tu base de datos o fuente de datos
        // Ejemplo: adapter.submitList(cargarNotasNoCompletadas())

        // Devuelve la vista inflada que representa el fragmento
        return view
    }
}
