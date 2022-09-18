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

    private lateinit var commaButton: Button

    

    private val maxQuantitySign: Int = 9
    private val crowdedAnnotation: String = "ПЕРЕПОЛНЕНО"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Объявление переменных
        currentText = findViewById(R.id.currentText)
        prevText = findViewById(R.id.prevText)
        signText = findViewById(R.id.signText)

        commaButton = findViewById(R.id.commaButton)

        // Присваивание листнеров
        findViewById<Button>(R.id.oneButton).setOnClickListener(this)
        findViewById<Button>(R.id.twoButton).setOnClickListener(this)
        findViewById<Button>(R.id.threeButton).setOnClickListener(this)
        findViewById<Button>(R.id.fourButton).setOnClickListener(this)
        findViewById<Button>(R.id.fiveButton).setOnClickListener(this)
        findViewById<Button>(R.id.sixButton).setOnClickListener(this)
        findViewById<Button>(R.id.sevenButton).setOnClickListener(this)
        findViewById<Button>(R.id.eightButton).setOnClickListener(this)
        findViewById<Button>(R.id.nineButton).setOnClickListener(this)
        findViewById<Button>(R.id.zeroButton).setOnClickListener(this)

        findViewById<Button>(R.id.clearButton).setOnClickListener(this)

        commaButton.setOnClickListener(this)

        findViewById<Button>(R.id.changeSignButton).setOnClickListener(this)

        findViewById<Button>(R.id.divisionButton).setOnClickListener(this)
        findViewById<Button>(R.id.percentButton).setOnClickListener(this)
        findViewById<Button>(R.id.multipleButton).setOnClickListener(this)
        findViewById<Button>(R.id.minusButton).setOnClickListener(this)
        findViewById<Button>(R.id.plusButton).setOnClickListener(this)

        findViewById<Button>(R.id.equalButton).setOnClickListener(this)
    }

    @SuppressLint("SetTextI18n")
    override fun onClick(view: View?) {
        when (view?.id ?: return) {
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

            R.id.commaButton -> { addCommaToNumber() }

            R.id.changeSignButton -> { changeSignToNumber() }

            R.id.divisionButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.percentButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.multipleButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.minusButton -> { chooseSecondNumber((view as TextView).text as String) }
            R.id.plusButton -> { chooseSecondNumber((view as TextView).text as String) }

            R.id.equalButton -> { equalSign() }
        }
    }

    // Костыльное производство
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
        commaButton.isClickable = true
    }

    @SuppressLint("SetTextI18n")
    private fun addCommaToNumber() {
        if(currentText.text.toString() == "" || currentText.text.toString() == crowdedAnnotation){
            currentText.text = currentText.text.toString() + "0."
        }else{
            if(currentText.text.toString().length < maxQuantitySign) {
                currentText.text = currentText.text.toString() + "."
                commaButton.isClickable = false
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
            "x" -> {
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
