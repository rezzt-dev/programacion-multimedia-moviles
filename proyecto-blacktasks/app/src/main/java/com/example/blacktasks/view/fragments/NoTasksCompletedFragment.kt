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
 * Fragmento que muestra las tareas no completadas en una lista utilizando un RecyclerView.
 * Este fragmento está diseñado para mostrar las tareas que aún están pendientes de completar.
 */
class NoTasksCompletedFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas no completadas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas no completadas en el RecyclerView
    private lateinit var adapter: TaskAdapter

    // ID del usuario que se utiliza para cargar las tareas
    private var idUsuario: Int = -1

    companion object {
        /**
         * Método para crear una instancia de NoTasksCompletedFragment con el `idUsuario` como argumento.
         * @param idUsuario El ID del usuario para cargar las tareas no completadas.
         * @return Una nueva instancia del fragmento con el argumento configurado.
         */
        fun newInstance(idUsuario: Int): NoTasksCompletedFragment {
            val fragment = NoTasksCompletedFragment()
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
        // Infla el layout del fragmento (fragment_no_tasks_completed)
        val view = inflater.inflate(R.layout.fragment_no_tasks_completed, container, false)

        // Inicializa el RecyclerView encontrando el componente por su ID
        recyclerView = view.findViewById(R.id.recycler_view_incomplete_notes)

        // Inicializa el adaptador con una lista mutable vacía de tareas no completadas
        adapter = TaskAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Configura el RecyclerView con un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Carga las tareas no completadas desde la base de datos
        loadIncompleteTasks()

        // Devuelve la vista inflada que representa el fragmento
        return view
    }

    /**
     * Método que recupera las tareas no completadas desde la base de datos y actualiza el adaptador.
     */
    private fun loadIncompleteTasks() {
        if (idUsuario == -1) {
            // Si no se pasó un ID válido, no se cargan tareas y se registra un error
            Log.e("NoTasksFragment", "ID de usuario no válido. No se pueden cargar tareas.")
            return
        }

        // Ejecuta la operación de carga en un hilo de fondo
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                val ctx = context ?: return@launch

                // Filtra solo las tareas con `realizada = false` desde la base de datos para el usuario
                val allTasks: List<Tarea> = TareaConexionHelper.obtenerTareasPorUsuario(ctx, idUsuario)
                val incompleteTasks = allTasks.filter { !it.realizada }

                // Actualiza el adaptador en el hilo principal
                withContext(Dispatchers.Main) {
                    adapter.updateTasks(incompleteTasks)
                }
            } catch (e: Exception) {
                // Maneja cualquier error durante la carga
                Log.e("NoTasksFragment", "Error al cargar tareas no completadas: ${e.message}", e)
            }
        }
    }
}
