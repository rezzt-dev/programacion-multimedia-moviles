package com.jgc.database.persistence

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.ValueEventListener
import com.jgc.database.model.Note

class FirebaseDataListener (var dataList: MutableLiveData<List<Note>>) : ValueEventListener {
  override fun onDataChange(snapshot: DataSnapshot) {
    val notes = mutableListOf<Note>()

    for (childSnapshot in snapshot.children) {
      val note = childSnapshot.getValue(Note::class.java)
      if (null != note) {
        notes.add(note)
      }
    }

    dataList.postValue(notes)
  }

  override fun onCancelled(error: DatabaseError) {
    Log.e("FirebaseDataListener", "Error: ${error.message}")
  }
}