package com.cocoa.geoquiz

import android.util.Log
import androidx.lifecycle.ViewModel

private const val TAG = "QuizViewModel"

class QuizViewModel: ViewModel() {

//    init {
//        Log.d(TAG, "ViewModel instance created")
//    }
//
//    override fun onCleared() {
//        super.onCleared()
//        Log.d(TAG, "onCleared: ViewModel instance about to be destroyed.")
//    }

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false))

    var currentIndex = 0
    var isCheater = false

    val currentQuestionAnswer: Boolean
        get() = questionBank[currentIndex].answer

    val currentQuestionText: Int
        get() = questionBank[currentIndex].textResId

    var currentQuestionIsCheating: Boolean
        get() = questionBank[currentIndex].cheating
        set(value) {
            questionBank[currentIndex].cheating = value
        }

    fun moveToNext() {
        currentIndex = (currentIndex + 1) % questionBank.size
    }

    fun moveToPrev() {
        currentIndex = if (currentIndex > 0) {
            currentIndex - 1
        }
        else {
            questionBank.size - 1
        }
    }
}
