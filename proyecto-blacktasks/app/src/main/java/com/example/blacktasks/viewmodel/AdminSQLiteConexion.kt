package com.example.blacktasks.viewmodel

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class AdminSQLIteConexion(context: Context) : SQLiteOpenHelper(context, "blacktasks.db", null, 1) {
    override fun onCreate(db: SQLiteDatabase) {
        // Crear tablas de la base de datos
        db.execSQL("CREATE TABLE usuario (id INTEGER PRIMARY KEY AUTOINCREMENT, username TEXT UNIQUE, password TEXT)")
        db.execSQL("CREATE TABLE tarea (id INTEGER PRIMARY KEY AUTOINCREMENT, titulo TEXT, descripcion TEXT, realizada INTEGER, idusuario INTEGER)")
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Actualizar esquema de la base de datos si es necesario
        db.execSQL("DROP TABLE IF EXISTS usuario")
        db.execSQL("DROP TABLE IF EXISTS tarea")
        onCreate(db)
    }
}
