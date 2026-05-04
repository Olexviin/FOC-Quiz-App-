package com.example.focquizapp

import android.graphics.Color
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.focquizapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    private val questions = arrayOf(
        "Using toothpaste to fix a scratched CD/DVD can make it readable again?",
        "You only use 10% of your brain?",
        "You can fully charge your phone in 30 seconds by putting it in a microwave?",
        "Putting a wooden spoon over a boiling pot prevents it from boiling over?",
        "Cracking your knuckles gives you arthritis?",
        "Putting your phone in airplane mode before charging makes it charge faster?",
        "Swallowing gum takes 7 years to digest?",
        "If you drop food on the floor and pick it up within 5 seconds, it’s safe to eat?",
        "A pinch of salt in coffee before brewing reduces bitterness?",
        "Running hot water over a dull razor blade makes it sharper?",
        "Using a vacuum cleaner to remove a bee/wasp sting after it’s been pulled out helps with venom?")

    private val options = arrayOf(
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Myth", "Hack"),
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Hack", "Myth"),
        arrayOf("Myth", "Hack"),
        arrayOf("Hack", "Myth")
    )

    // 0 for Hack, 1 for Myth
    private val correctAnswers = arrayOf(0, 1, 1, 0, 1, 0, 1, 1, 0, 1, 1)

    private var currentQuestionIndex = 0
    private var score = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        displayQuestion()

        binding.ButtonOption1.setOnClickListener {
            checkAnswer(0)
        }

        binding.ButtonOption2.setOnClickListener {
            checkAnswer(1)
        }

        binding.RestartButton.setOnClickListener {
            restartQuiz()
        }
    }

    private fun correctButtonColors(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.ButtonOption1.setBackgroundColor(Color.GREEN)
            1 -> binding.ButtonOption2.setBackgroundColor(Color.GREEN)
        }
    }

    private fun wrongButtonColor(buttonIndex: Int) {
        when (buttonIndex) {
            0 -> binding.ButtonOption1.setBackgroundColor(Color.RED)
            1 -> binding.ButtonOption2.setBackgroundColor(Color.RED)
        }
    }

    private fun resetButtonColors() {
        val defaultColor = Color.rgb(50, 59, 96)
        binding.ButtonOption1.setBackgroundColor(defaultColor)
        binding.ButtonOption2.setBackgroundColor(defaultColor)
    }

    private fun showResults() {
        Toast.makeText(this, "Your score is $score out of ${questions.size}", Toast.LENGTH_LONG).show()
        binding.RestartButton.isEnabled = true
    }

    private fun displayQuestion() {
        resetButtonColors()
        binding.ButtonOption1.isEnabled = true
        binding.ButtonOption2.isEnabled = true
        binding.QuestionText.text = questions[currentQuestionIndex]
        binding.ButtonOption1.text = options[currentQuestionIndex][0]
        binding.ButtonOption2.text = options[currentQuestionIndex][1]
    }

    private fun checkAnswer(selectedAnswerIndex: Int) {
        val correctAnswerIndex = correctAnswers[currentQuestionIndex]

        binding.ButtonOption1.isEnabled = false
        binding.ButtonOption2.isEnabled = false

        if (selectedAnswerIndex == correctAnswerIndex) {
            score++
            correctButtonColors(selectedAnswerIndex)
        } else {
            wrongButtonColor(selectedAnswerIndex)
            correctButtonColors(correctAnswerIndex)
        }

        if (currentQuestionIndex < questions.size - 1) {
            currentQuestionIndex++
            binding.root.postDelayed({
                displayQuestion()
            }, 1000)
        } else {
            showResults()
        }
    }

    private fun restartQuiz() {
        currentQuestionIndex = 0
        score = 0
        displayQuestion()
        binding.RestartButton.isEnabled = false
    }
}
