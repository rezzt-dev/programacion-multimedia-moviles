package com.example.blacktasks.view.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.blacktasks.R
import com.example.blacktasks.adapter.TaskAdapter
import com.example.blacktasks.model.Tarea
import com.example.blacktasks.viewmodel.TareaConexionHelper
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class AllTasksFragment : Fragment() {

    // Referencia al RecyclerView que mostrará las tareas
    private lateinit var recyclerView: RecyclerView

    // Adaptador que gestiona la visualización de las tareas en el RecyclerView
    private lateinit var adapter: TaskAdapter

    // ID del usuario que se utiliza para cargar las tareas
    private var idUsuario: Int = -1

    companion object {
        /**
         * Método para crear una instancia de AllTasksFragment con el `idUsuario` como argumento.
         * @param idUsuario El ID del usuario para cargar las tareas.
         * @return Una nueva instancia del fragmento con el argumento configurado.
         */
        fun newInstance(idUsuario: Int): AllTasksFragment {
            val fragment = AllTasksFragment()
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
        // Infla el layout del fragmento
        val view = inflater.inflate(R.layout.fragment_all_notes, container, false)

        // Inicializa el RecyclerView
        recyclerView = view.findViewById(R.id.recycler_view_all_notes)

        // Inicializa el adaptador con una lista vacía de tareas
        adapter = TaskAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Configura el RecyclerView con un LinearLayoutManager
        recyclerView.layoutManager = LinearLayoutManager(context)

        // Carga las tareas desde la base de datos
        loadTasks()

        return view
    }

    /**
     * Método que recupera todas las tareas desde la base de datos y actualiza el adaptador.
     */
    private fun loadTasks() {
        // Verifica si el `idUsuario` es válido
        if (idUsuario == -1) {
            // Si no se pasó un ID válido, muestra un mensaje de error (opcional)
            Log.e("LoadTasks", "ID de usuario no válido. No se pueden cargar tareas.")
            return
        }

        // Ejecuta la operación de carga en un hilo de fondo
        viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
            try {
                // Contexto no debe ser nulo en este punto si el fragmento está activo
                val ctx = context ?: return@launch

                // Recupera las tareas asociadas al usuario
                val tasks: List<Tarea> = TareaConexionHelper.obtenerTareasPorUsuario(ctx, idUsuario)

                // Actualiza el adaptador en el hilo principal
                withContext(Dispatchers.Main) {
                    adapter.updateTasks(tasks)
                }
            } catch (e: Exception) {
                // Maneja cualquier error durante la carga
                Log.e("LoadTasks", "Error al cargar tareas: ${e.message}", e)
            }
        }
    }

}
