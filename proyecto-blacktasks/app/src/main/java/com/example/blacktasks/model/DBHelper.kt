package com.example.blacktasks.model

import android.annotation.SuppressLint
import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper (context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {
    companion object {
        const val DB_NAME = "tareas.db"
        const val DB_VERSION = 1
        const val TABLE_NAME = "tareas"
        const val COLUMN_ID = "id"
        const val COLUMN_TITULO = "titulo"
        const val COLUMN_DESCRIPCION = "descripcion"
        const val COLUMN_REALIZADA = "realizada"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = """
            CREATE TABLE $TABLE_NAME (
                $COLUMN_ID INTEGER PRIMARY KEY AUTOINCREMENT,
                $COLUMN_TITULO TEXT,
                $COLUMN_DESCRIPCION TEXT,
                $COLUMN_REALIZADA INTEGER
            )
        """.trimIndent()

        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        onCreate(db)
    }

    // Insertar una tarea
    fun insertarTarea(tarea: Task): Long {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_TITULO, tarea.titulo)
            put(COLUMN_DESCRIPCION, tarea.descripcion)
            put(COLUMN_REALIZADA, if (tarea.realizada) 1 else 0)
        }

        return db.insert(TABLE_NAME, null, contentValues)
    }

    // Obtener todas las tareas
    @SuppressLint("Range")
    fun obtenerTareas(): List<Task> {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            null,
            null,
            null,
            null,
            null,
            null
        )

        val tareas = mutableListOf<Task>()
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndex(COLUMN_ID))
            val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
            val descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION))
            val realizada = cursor.getInt(cursor.getColumnIndex(COLUMN_REALIZADA)) == 1

            val tarea = Task(id, titulo, descripcion, realizada)
            tareas.add(tarea)
        }
        cursor.close()
        return tareas
    }

    // Obtener una tarea por ID
    @SuppressLint("Range")
    fun obtenerTareaPorId(id: Int): Task? {
        val db = readableDatabase
        val cursor: Cursor = db.query(
            TABLE_NAME,
            null,
            "$COLUMN_ID = ?",
            arrayOf(id.toString()),
            null,
            null,
            null
        )

        if (cursor.moveToFirst()) {
            val titulo = cursor.getString(cursor.getColumnIndex(COLUMN_TITULO))
            val descripcion = cursor.getString(cursor.getColumnIndex(COLUMN_DESCRIPCION))
            val realizada = cursor.getInt(cursor.getColumnIndex(COLUMN_REALIZADA)) == 1

            val tarea = Task(id, titulo, descripcion, realizada)
            cursor.close()
            return tarea
        }

        cursor.close()
        return null
    }

    // Eliminar una tarea
    fun eliminarTarea(id: Int): Int {
        val db = writableDatabase
        return db.delete(
            TABLE_NAME,
            "$COLUMN_ID = ?",
            arrayOf(id.toString())
        )
    }
}