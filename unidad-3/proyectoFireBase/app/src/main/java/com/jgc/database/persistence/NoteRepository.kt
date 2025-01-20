package com.jgc.database.persistence

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.jgc.database.model.Note

class NoteRepository {
  private var databaseReference: DatabaseReference
  init {
    databaseReference = FirebaseDatabase.getInstance("https://escomolon45-default-rtdb.europe-west1.firebasedatabase.app/").reference
  }

  fun addNote (note: Note) {
    val key = databaseReference.child("notes").push().key
    note.idNote = key
    databaseReference.child("notes").child(key!!).setValue(note)
  }

  fun getNotes(): MutableLiveData<List<Note>> {
    val notes = MutableLiveData<List<Note>>()

    val firebaseDataListener = FirebaseDataListener(notes)
    databaseReference.child("notes").addValueEventListener(firebaseDataListener)

    return notes
  }
}