package com.jgc.ejercicio_calculadora_1

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.jgc.ejercicio_calculadora_1.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var inputNum1: EditText
  private lateinit var inputNum2: EditText
  private lateinit var resultNum: EditText
  private lateinit var btnEstadoOper: Button

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    inputNum1 = findViewById(R.id.inputNum1)
    inputNum2 = findViewById(R.id.inputNum2)
    resultNum = findViewById(R.id.resultNum)
    btnEstadoOper = findViewById(R.id.btnEstadoOper)

    findViewById<Button>(R.id.btnSumar).setOnClickListener{operar(::sumar)}
    findViewById<Button>(R.id.btnRestar).setOnClickListener{operar(::restar)}
    findViewById<Button>(R.id.btnMultiplicar).setOnClickListener{operar(::multiplicar)}
    findViewById<Button>(R.id.btnDividir).setOnClickListener{operar(::dividir)}

    findViewById<Button>(R.id.btnClean).setOnClickListener{limpiar()}
  }

  private fun operar (operacion: (Double, Double) -> Double) {
    val num1 = inputNum1.text.toString().toDoubleOrNull()
    val num2 = inputNum2.text.toString().toDoubleOrNull()

    if (num1 != null && num2 != null) {
      val resultado = operacion(num1, num2)
      resultNum.setText(resultado.toString())
      btnEstadoOper.text = "Operación exitosa"
    } else {
      btnEstadoOper.text = "Error: Ingrese números válidos"
    }
  }

  private fun sumar(a: Double, b: Double) = a + b
  private fun restar(a: Double, b: Double) = a - b

  private fun multiplicar(a: Double, b: Double) = a * b

  private fun dividir(a: Double, b: Double): Double {
    return if (b != 0.0) a / b else {
      btnEstadoOper.text = "Error: División por cero"
      Double.NaN
    }
  }

  private fun limpiar () {
    val intent = Intent(this, MainActivity::class.java)
    startActivity(intent)
    finish()
  }
}