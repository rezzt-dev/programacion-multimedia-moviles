package com.example.blacktasks.model

/**
 * Representa una tarea dentro de la aplicación.
 * Una tarea tiene un identificador único, un título, una descripción y un estado que indica si está completada o no.
 *
 * @property id El identificador único de la tarea.
 * @property title El título de la tarea.
 * @property description La descripción detallada de la tarea.
 * @property isCompleted Indica si la tarea está completada o no. El valor predeterminado es 'false'.
 */
data class Task (
    val id: Int = 0,
    val titulo: String,
    val descripcion: String,
    var realizada: Boolean
)
