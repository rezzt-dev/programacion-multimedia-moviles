package com.jgc.ejercicio_calculadora_2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.jgc.ejercicio_calculadora_2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding
  private lateinit var display: EditText
  private var currentInput = StringBuilder()
  private var currentOperator: String? = null
  private var firstOperand: Double? = null

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    display = findViewById(R.id.editTextNumberDecimal3)
    setButtonListeners()
  }

  private fun setButtonListeners() {
    val buttonIds = listOf(
      R.id.button, R.id.button5, R.id.button6, R.id.button8, R.id.button9,
      R.id.button10, R.id.button12, R.id.button13, R.id.button14, R.id.button16
    )
    buttonIds.forEach { id ->
      findViewById<Button>(id).setOnClickListener { onNumberClick(it as Button) }
    }

    findViewById<Button>(R.id.button17).setOnClickListener { onDecimalClick() }
    findViewById<Button>(R.id.button7).setOnClickListener { onOperatorClick("/") }
    findViewById<Button>(R.id.button11).setOnClickListener { onOperatorClick("x") }
    findViewById<Button>(R.id.button15).setOnClickListener { onOperatorClick("-") }
    findViewById<Button>(R.id.button19).setOnClickListener { onOperatorClick("+") }
    findViewById<Button>(R.id.button18).setOnClickListener { onEqualsClick() }
  }

  private fun onNumberClick(button: Button) {
    currentInput.append(button.text)
    updateDisplay()
  }

  private fun onDecimalClick() {
    if (!currentInput.contains(".")) {
      if (currentInput.isEmpty()) currentInput.append("0")
      currentInput.append(".")
      updateDisplay()
    }
  }

  private fun onOperatorClick(operator: String) {
    if (currentInput.isNotEmpty()) {
      if (firstOperand == null) {
        firstOperand = currentInput.toString().toDouble()
        currentInput.clear()
      } else {
        onEqualsClick()
      }
      currentOperator = operator
    }
  }

  private fun onEqualsClick() {
    if (firstOperand != null && currentOperator != null && currentInput.isNotEmpty()) {
      val secondOperand = currentInput.toString().toDouble()
      val result = when (currentOperator) {
        "+" -> firstOperand!! + secondOperand
        "-" -> firstOperand!! - secondOperand
        "x" -> firstOperand!! * secondOperand
        "/" -> firstOperand!! / secondOperand
        else -> return
      }
      display.setText(result.toString())
      firstOperand = result
      currentInput.clear()
      currentOperator = null
    }
  }

  private fun updateDisplay() {
    display.setText(currentInput.toString())
  }
}