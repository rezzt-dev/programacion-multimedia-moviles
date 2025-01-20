package com.example.blacktasks.view.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.adapter.TaskAdapter // Asegúrate de tener el adaptador adecuado

/**
 * Fragmento que muestra todas las tareas en una lista utilizando un RecyclerView.
 * Este fragmento está diseñado para mostrar todas las tareas sin aplicar ningún filtro.
 */
class AllTasksFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas en el RecyclerView
    private lateinit var adapter: TaskAdapter // Adaptador para gestionar las tareas

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
        // Infla el layout del fragmento (fragment_all_notes)
        val view = inflater.inflate(R.layout.fragment_all_notes, container, false)

        // Inicializa el RecyclerView encontrando el componente por su ID
        recyclerView = view.findViewById(R.id.recycler_view_all_notes)

        // Inicializa el adaptador con una lista vacía de tareas (se puede actualizar más tarde)
        adapter = TaskAdapter(mutableListOf())

        // Asigna el adaptador al RecyclerView para que pueda mostrar los datos
        recyclerView.adapter = adapter

        // Establece el LayoutManager para el RecyclerView, en este caso, un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Devuelve la vista inflada que representa el fragmento
        return view
    }
}
