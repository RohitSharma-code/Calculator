package com.example.calculator

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class MainActivity : AppCompatActivity() {
    lateinit var txtview: TextView
    lateinit var equal: Button
    lateinit var clear: Button
    lateinit var back: Button

    var currentInput: String = ""
    var firstNumber: String = ""
    var secondNumber: String = ""
    var operator: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtview = findViewById(R.id.txtview)
        clear = findViewById(R.id.del)
        equal = findViewById(R.id.equal)
        back = findViewById(R.id.back)

        val buttonNumbers = listOf(
            findViewById<Button>(R.id.z),
            findViewById<Button>(R.id.one),
            findViewById<Button>(R.id.two),
            findViewById<Button>(R.id.three),
            findViewById<Button>(R.id.four),
            findViewById<Button>(R.id.five),
            findViewById<Button>(R.id.six),
            findViewById<Button>(R.id.seven),
            findViewById<Button>(R.id.eight),
            findViewById<Button>(R.id.nine),
            findViewById<Button>(R.id.d_z),
            findViewById<Button>(R.id.dot)
        )

        val buttonOp = mapOf(
            "+" to findViewById<Button>(R.id.add),
            "-" to findViewById<Button>(R.id.sub),
            "x" to findViewById<Button>(R.id.mul),
            "÷" to findViewById<Button>(R.id.div),
        )

        //Numbers buttons
        buttonNumbers.forEach { button ->
            button.setOnClickListener {
                val value = (it as Button).text.toString()
                currentInput += value
                if (operator.isEmpty()) {
                    firstNumber = currentInput
                } else {
                    secondNumber = currentInput
                }
                updateDisplay()
            }
        }

        //operator buttons
        buttonOp.forEach { (op, button) ->
            button.setOnClickListener {
                if (firstNumber.isNotEmpty() && currentInput.isNotEmpty()) {
                    currentInput = ""
                    operator = op
                    updateDisplay()
                }
            }
        }

        //equal button
        equal.setOnClickListener {
            if (operator.isNotEmpty() && secondNumber.isNotEmpty()) {
                val result = when (operator) {
                    "+" -> firstNumber.toDouble() + secondNumber.toDouble()
                    "-" -> firstNumber.toDouble() - secondNumber.toDouble()
                    "x" -> firstNumber.toDouble() * secondNumber.toDouble()
                    "÷" -> if (secondNumber.toDouble() != 0.0) {
                        firstNumber.toDouble() / secondNumber.toDouble()
                    } else {
                        Double.NaN
                    }


                    else -> 0.0
                }
                txtview.text = result.toString()
                firstNumber = result.toString()
                secondNumber = ""
                operator = ""
                currentInput = ""

            }
        }

        //clear button
        clear.setOnClickListener {
            currentInput = ""
            firstNumber = ""
            secondNumber = ""
            operator = ""
            txtview.text = ""
        }

        //Backspace button
        back.setOnClickListener() {
            when{
                secondNumber.isNotEmpty() -> {
                    secondNumber = secondNumber.dropLast(1)
                    currentInput = secondNumber
                }

                operator.isNotEmpty() -> {
                    operator = ""
                    currentInput = firstNumber
                }

                firstNumber.isNotEmpty() -> {
                    firstNumber = firstNumber.dropLast(1)
                    currentInput = firstNumber
                }
            }
            updateDisplay()
        }

    }

    //Hold
    fun updateDisplay() {
        txtview.text = "$firstNumber $operator $secondNumber"
    }
}

