package com.example.ejemplo.viewModel

import androidx.lifecycle.ViewModel
import com.example.ejemplo.model.CitaModel
import com.example.ejemplo.model.ProveedorCitas

class CitasViewModel: ViewModel() {

    //Lógica para interactuar con el Modelo y preparar datos
    var cita: CitaModel

    init {
        cita = ProveedorCitas.getCitaRandom()
    }
}