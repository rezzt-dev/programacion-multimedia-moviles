package com.jgc.modelovistabinding.viewModel

import androidx.lifecycle.ViewModel
import com.jgc.vistamodelobinding.model.CitaModel
import com.jgc.vistamodelobinding.model.ProveedorCitas

class CitasViewModel: ViewModel() {

    //Lógica para interactuar con el Modelo y preparar datos
    var cita: CitaModel

    init {
        cita = ProveedorCitas.getCitaRandom()
    }
}