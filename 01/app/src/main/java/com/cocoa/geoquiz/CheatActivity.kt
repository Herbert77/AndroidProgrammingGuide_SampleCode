package com.cocoa.geoquiz

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

const val EXTRA_ANSWER_SHOWN = "com.cocoa.geoquiz.answer_shown"
private const val EXTRA_ANSWER_IS_TRUE = "com.cocoa.geoquiz.answer_is_true"

class CheatActivity : AppCompatActivity() {

    private val cheatViewModel: CheatViewModel by lazy {
        ViewModelProvider(this).get(CheatViewModel::class.java)
    }

    private lateinit var answerTextView: TextView
    private lateinit var showAnswerButton: Button
    private var answerIsTrue = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cheat)

        val answerHasShown = savedInstanceState?.getBoolean(EXTRA_ANSWER_SHOWN) ?: false
        cheatViewModel.answerHasShown = answerHasShown

        answerIsTrue = intent.getBooleanExtra(EXTRA_ANSWER_IS_TRUE, false)
        answerTextView = findViewById(R.id.answer_text_view)
        showAnswerButton = findViewById(R.id.show_answer_button)

        showAnswerButton.setOnClickListener {
            setContentForAnswerTextView()
            setAnswerShownResult(true)
        }

        if (cheatViewModel.answerHasShown) {
            setContentForAnswerTextView()
            setAnswerShownResult(true)
        }
    }

    private fun setContentForAnswerTextView() {
        val answerText = when {
            answerIsTrue -> "true"
            else -> "false"
        }
        answerTextView.text = answerText
    }

    private fun setAnswerShownResult(isAnswerShown: Boolean) {

        cheatViewModel.answerHasShown = isAnswerShown

        val data = Intent().apply {
            putExtra(EXTRA_ANSWER_SHOWN, isAnswerShown)
        }
        setResult(Activity.RESULT_OK, data)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putBoolean(EXTRA_ANSWER_SHOWN, cheatViewModel.answerHasShown)
    }

    companion object {
        fun newIntent(packageContext: Context, answerIsTrue: Boolean): Intent {
            return Intent(packageContext, CheatActivity::class.java).apply {
                putExtra(EXTRA_ANSWER_IS_TRUE, answerIsTrue)
            }
        }
    }

}