package com.example.ejemplo.model

object ProveedorCitas {
    fun getCitaRandom(): CitaModel {
        val position = (0 until citas.size).random()
        return citas[position]
    }

    private val citas = listOf(
        CitaModel(
            cita = "If debugging is the process of removing software bugs, then programming must be the process of putting them in",
            autor = "Edgar Dijkstra"
        ),
        CitaModel(
            cita = "A user interface is like a joke. If you have to explain it, it's not that good.",
            autor = "Anonymous"
        ),
        CitaModel(
            cita = "I don't care if it works on your machine! We are not shipping your machine!",
            autor = "Vidiu Platon"
        ),
        CitaModel(
            cita = "Measuring programming progress by lines of code is like measuring aircraft building progress by weight.",
            autor = "Bill Gates"
        )
    )
}