package com.jgc.database.adapter

import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.jgc.database.databinding.ItemNoteBinding

class NoteViewHolder (itemView: View) : RecyclerView.ViewHolder(itemView) {
  val binding = ItemNoteBinding.bind(itemView)
  val titulo: TextView = binding.title
  val contenido: TextView = binding.content
}