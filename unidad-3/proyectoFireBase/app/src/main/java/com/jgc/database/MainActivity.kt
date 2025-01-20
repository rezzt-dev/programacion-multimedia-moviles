package com.jgc.database

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.jgc.database.adapter.NoteAdapter
import com.jgc.database.databinding.ActivityMainBinding
import com.jgc.database.viewmodel.NoteViewModel

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var myAdapter: NoteAdapter
  val viewmodel: NoteViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    // Configuramos el RecyclerView
    initRecyclerView(viewmodel)

    //si cambian los datos, actualizar rv
    viewmodel.notesLiveData.observe(this) { notes ->
      myAdapter.updateData(notes)
    }

    binding.addButton.setOnClickListener {
      val title = binding.titleEditText.text.toString()
      val content = binding.contentEditText.text.toString()
      viewmodel.addNote(title, content)
    }

  }

  private fun initRecyclerView(viewmodel: NoteViewModel) {
    val manager = LinearLayoutManager(this)
    binding.notesRecyclerView.layoutManager = manager
    myAdapter = NoteAdapter(mutableListOf())
    binding.notesRecyclerView.adapter = myAdapter

    // Crear un DividerItemDecoration y agregarlo al RecyclerView
    val decoration = DividerItemDecoration(this, manager.orientation)
    binding.notesRecyclerView.addItemDecoration(decoration)
  }
}