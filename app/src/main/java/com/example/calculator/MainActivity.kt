package com.example.calculator

import android.annotation.SuppressLint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var currentText: TextView
    private lateinit var prevText: TextView
    private lateinit var signText: TextView

    private lateinit var digitAndCommaButtons: Array<Button>
    private lateinit var signButtons: Array<Button>

    private val maxQuantitySign: Int = 9
    private val crowdedAnnotation: String = "ПЕРЕПОЛНЕНО"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Объявление переменных
        currentText = findViewById(R.id.currentText)
        prevText = findViewById(R.id.prevText)
        signText = findViewById(R.id.signText)

        digitAndCommaButtons = arrayOf(
            findViewById(R.id.commaButton),
            findViewById(R.id.oneButton),
            findViewById(R.id.twoButton),
            findViewById(R.id.threeButton),
            findViewById(R.id.fourButton),
            findViewById(R.id.fiveButton),
            findViewById(R.id.sixButton),
            findViewById(R.id.sevenButton),
            findViewById(R.id.eightButton),
            findViewById(R.id.nineButton),
            findViewById(R.id.zeroButton),
        )

        signButtons = arrayOf(
            findViewById(R.id.clearButton),
            findViewById(R.id.changeSignButton),
            findViewById(R.id.divisionButton),
            findViewById(R.id.percentButton),
            findViewById(R.id.multipleButton),
            findViewById(R.id.minusButton),
            findViewById(R.id.plusButton),
            findViewById(R.id.equalButton),
        )

        // Присваивание листнеров
        for (digitAndCommaButton in digitAndCommaButtons) {
            digitAndCommaButton.setOnClickListener(this)
        }

        for (signButton in signButtons) {
            signButton.setOnClickListener(this)
        }
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (view?.id ?: return) {
            R.id.commaButton -> { addCommaToNumber() }

            R.id.oneButton -> { getNewNumber((view as TextView).text as String) }
            R.id.twoButton -> { getNewNumber((view as TextView).text as String) }
            R.id.threeButton -> { getNewNumber((view as TextView).text as String) }
            R.id.fourButton -> { getNewNumber((view as TextView).text as String) }
            R.id.fiveButton -> { getNewNumber((view as TextView).text as String) }
            R.id.sixButton -> { getNewNumber((view as TextView).text as String) }
            R.id.sevenButton -> { getNewNumber((view as TextView).text as String) }
            R.id.eightButton -> { getNewNumber((view as TextView).text as String) }
            R.id.nineButton -> { getNewNumber((view as TextView).text as String) }
            R.id.zeroButton -> { getNewNumber((view as TextView).text as String) }

            R.id.clearButton -> { clearLine() }

            R.id.changeSignButton -> { changeSignToNumber() }

            R.id.divisionButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.percentButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.multipleButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.minusButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.plusButton -> { chooseSecondNumber((view as TextView).text as String) }

            R.id.equalButton -> { equalSign() }
        }
    }

    // Внутрение функции
    @SuppressLint("SetTextI18n")
    private fun getNewNumber(buttonText: String) {
        if(currentText.text.toString() == crowdedAnnotation){
            clearLine()
        }
        if(currentText.text.toString().length < maxQuantitySign) {
            currentText.text = currentText.text.toString() + buttonText
        } else {
            currentText.text = crowdedAnnotation
        }
    }

    private fun clearLine() {
        currentText.text = ""
        digitAndCommaButtons[0].isClickable = true
    }

    @SuppressLint("SetTextI18n")
    private fun addCommaToNumber() {
        if(currentText.text.toString() == "" || currentText.text.toString() == crowdedAnnotation){
            currentText.text = currentText.text.toString() + "0."
            digitAndCommaButtons[0].isClickable = false
        }else{
            if(currentText.text.toString().length < maxQuantitySign) {
                currentText.text = currentText.text.toString() + "."
                digitAndCommaButtons[0].isClickable = false
            } else {
                currentText.text = crowdedAnnotation
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun changeSignToNumber() {
        if(currentText.text.toString() != ""){
            if(currentText.text.toString()[0] == '-') {
                currentText.text = currentText.text.substring(1)
            } else {
                currentText.text = "-" + currentText.text
            }
        }
    }

    private fun chooseSecondNumber(buttonText: String) {
        if (currentText.text != "" && prevText.text == ""){
            if(currentText.text[currentText.text.lastIndex] == '.'){
                currentText.text = currentText.text.toString().substringBefore('.')
            }
            prevText.text = currentText.text
            signText.text = buttonText
            currentText.text = ""
            digitAndCommaButtons[0].isClickable = true
        } else {
            if (currentText.text != "") {
                calculate()
                chooseSecondNumber(buttonText)
            }
        }
    }

    private fun equalSign() {
        if(currentText.text != "" && prevText.text != "" && signText.text != "") {
            calculate()
            clearSignAndPrevNumber()
        }
    }

    @SuppressLint("SetTextI18n")
    private fun calculate() {
        val prevNumber:Float = prevText.text.toString().toFloat()
        val currentNumber:Float = currentText.text.toString().toFloat()

        when (signText.text) {
            "/" -> {
                clearSignAndPrevNumber()
                pasteAnswerInCurrentText((prevNumber / currentNumber).toString())
            }
            "%" -> {
                clearSignAndPrevNumber()
                pasteAnswerInCurrentText((prevNumber / (currentNumber / 100)).toString())
            }
            "*" -> {
                clearSignAndPrevNumber()
                pasteAnswerInCurrentText((prevNumber * currentNumber).toString())
            }
            "-" -> {
                clearSignAndPrevNumber()
                pasteAnswerInCurrentText((prevNumber - currentNumber).toString())
            }
            "+" -> {
                clearSignAndPrevNumber()
                pasteAnswerInCurrentText((prevNumber + currentNumber).toString())
            }
        }
    }

    private fun clearSignAndPrevNumber() {
        prevText.text = ""
        signText.text = ""
    }

    private fun pasteAnswerInCurrentText(verifiableLine:String) {
        if (verifiableLine.substringAfter('.') == "0") {
            currentText.text = verifiableLine.substringBefore('.')
        } else {
            currentText.text = verifiableLine
        }
    }
}
