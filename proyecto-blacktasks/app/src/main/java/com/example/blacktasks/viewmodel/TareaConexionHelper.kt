package com.example.blacktasks.viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.blacktasks.model.Tarea

object TareaConexionHelper {

    fun addTarea(context: Context, tarea: Tarea): Long {
        val admin = AdminSQLIteConexion(context)
        val db: SQLiteDatabase = admin.writableDatabase

        val values = ContentValues().apply {
            put("titulo", tarea.titulo)
            put("descripcion", tarea.descripcion)
            put("realizada", if (tarea.realizada) 1 else 0)
            put("idusuario", tarea.idusuario)
        }

        val result = db.insert("tarea", null, values)
        db.close()
        return result
    }

    fun obtenerTareasPorUsuario(context: Context, idUsuario: Int): List<Tarea> {
        val admin = AdminSQLIteConexion(context)
        val db: SQLiteDatabase = admin.readableDatabase
        val tareas = mutableListOf<Tarea>()

        val cursor = db.rawQuery(
            "SELECT id, titulo, descripcion, realizada, idusuario FROM tarea WHERE idusuario = ?",
            arrayOf(idUsuario.toString())
        )

        while (cursor.moveToNext()) {
            val tarea = Tarea(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2),
                cursor.getInt(3) == 1,
                cursor.getInt(4)
            )
            tareas.add(tarea)
        }
        cursor.close()
        db.close()
        return tareas
    }

    fun delTarea(context: Context, id: Int): Int {
        val admin = AdminSQLIteConexion(context)
        val db = admin.writableDatabase
        val rowsDeleted = db.delete("tarea", "id=?", arrayOf(id.toString()))
        db.close()
        return rowsDeleted
    }


    fun modTarea(context: Context, id: Int, tarea: Tarea): Int {
        val admin = AdminSQLIteConexion(context)
        val db: SQLiteDatabase = admin.writableDatabase

        val values = ContentValues().apply {
            put("titulo", tarea.titulo)
            put("descripcion", tarea.descripcion)
            put("realizada", if (tarea.realizada) 1 else 0)
        }

        val rowsUpdated = db.update("tarea", values, "id = ?", arrayOf(id.toString()))
        db.close()
        return rowsUpdated
    }
}
