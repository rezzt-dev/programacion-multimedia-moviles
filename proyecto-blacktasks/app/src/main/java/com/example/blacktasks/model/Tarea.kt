package com.example.blacktasks.model

data class Tarea(
    var id: Int,
    var titulo: String,
    var descripcion: String,
    var realizada: Boolean,
    var idusuario: Int
)
