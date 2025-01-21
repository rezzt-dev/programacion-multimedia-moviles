package com.example.blacktasks.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.adapter.TaskAdapter
import com.example.blacktasks.model.Tarea
import com.example.blacktasks.viewmodel.TareaConexionHelper
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import androidx.lifecycle.lifecycleScope

/**
 * Fragmento que muestra las tareas completadas en una lista utilizando un RecyclerView.
 * Este fragmento está diseñado para mostrar una lista de tareas completadas en la interfaz de usuario.
 */
class CompletedTasksFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas completadas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas completadas en el RecyclerView
    private lateinit var adapter: TaskAdapter

    // ID del usuario que se utiliza para cargar las tareas completadas
    private var idUsuario: Int = -1

    companion object {
        /**
         * Método para crear una instancia de CompletedTasksFragment con el `idUsuario` como argumento.
         * @param idUsuario El ID del usuario para cargar las tareas completadas.
         * @return Una nueva instancia del fragmento con el argumento configurado.
         */
        fun newInstance(idUsuario: Int): CompletedTasksFragment {
            val fragment = CompletedTasksFragment()
            val args = Bundle()
            args.putInt("USER_ID", idUsuario)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Obtén el ID del usuario desde los argumentos
        arguments?.let {
            idUsuario = it.getInt("USER_ID", -1)
        }
    }

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
        recyclerView.adapter = adapter

        // Configura el RecyclerView con un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Carga las tareas completadas desde la base de datos
        loadCompletedTasks()

        // Devuelve la vista inflada que representa el fragmento
        return view
    }

    /**
     * Método que recupera las tareas completadas desde la base de datos y actualiza el adaptador.
     */
    private fun loadCompletedTasks() {
        if (idUsuario == -1) {
            // Si no se pasó un ID válido, muestra un error y no carga las tareas
            Log.e("CompletedTasksFragment", "ID de usuario no válido. No se pueden cargar tareas.")
            return
        }

        // Ejecuta la operación de carga en un hilo de fondo
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val ctx = context ?: return@launch

                // Filtra solo las tareas con `realizada = true` desde la base de datos para el usuario
                val allTasks: List<Tarea> = TareaConexionHelper.obtenerTareasPorUsuario(ctx, idUsuario)
                val completedTasks = allTasks.filter { it.realizada }

                // Actualiza el adaptador en el hilo principal
                withContext(Dispatchers.Main) {
                    adapter.updateTasks(completedTasks)
                }
            } catch (e: Exception) {
                // Maneja cualquier error durante la carga
                Log.e("CompletedTasksFragment", "Error al cargar tareas completadas: ${e.message}", e)
            }
        }
    }
}
