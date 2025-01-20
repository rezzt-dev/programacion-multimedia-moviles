package com.jgc.database.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jgc.database.R
import com.jgc.database.model.Note

class NoteAdapter (private val notas: List<Note>): RecyclerView.Adapter<NoteViewHolder>() {
  private var data: List<Note>
  init {
    data = notas
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
    // Inflamos el layout de cada elemento
    val layoutInflater = LayoutInflater.from(parent.context)
    return NoteViewHolder(layoutInflater.inflate(R.layout.item_note, parent, false))
  }

  override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
    // Inicializamos la lista
    val nota = data[position]
    holder.titulo.text = nota.titulo
    holder.contenido.text = nota.contenido
  }

  override fun getItemCount(): Int {
    return data.size
  }

  fun updateData(newData: List<Note>) {
    this.data = newData
    notifyItemChanged(0, data.size-1)
    notifyDataSetChanged()
  }
}