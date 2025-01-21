package com.example.blacktasks.viewmodel

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import com.example.blacktasks.model.Usuario

object UsuarioConexionHelper {

    fun initUsuarios(context: Context) {
        val admin = AdminSQLIteConexion(context)
        val db: SQLiteDatabase = admin.writableDatabase

        // Verifica si ya existen usuarios en la tabla
        val cursor = db.rawQuery("SELECT COUNT(*) FROM usuario", null)
        cursor.moveToFirst()
        val count = cursor.getInt(0)
        cursor.close()

        if (count == 0) {
            val usuarios = listOf(
                Usuario(0, "admin", "admin"),
                Usuario(0, "juan", "1234"),
                Usuario(0, "santa", "1234")
            )

            for (usuario in usuarios) {
                val values = ContentValues().apply {
                    put("username", usuario.username)
                    put("password", usuario.password)
                }
                db.insert("usuario", null, values)
            }
        }
        db.close()
    }

    fun obtenerUsuarios(context: Context): List<Usuario> {
        val admin = AdminSQLIteConexion(context)
        val db: SQLiteDatabase = admin.readableDatabase
        val usuarios = mutableListOf<Usuario>()

        val cursor = db.rawQuery("SELECT id, username, password FROM usuario", null)
        while (cursor.moveToNext()) {
            val usuario = Usuario(
                cursor.getInt(0),
                cursor.getString(1),
                cursor.getString(2)
            )
            usuarios.add(usuario)
        }
        cursor.close()
        db.close()
        return usuarios
    }
}
