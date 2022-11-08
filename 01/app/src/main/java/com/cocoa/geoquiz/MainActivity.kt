package com.cocoa.geoquiz

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast

private const val TAG = "MainActivity"

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var preButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var questionTextView: TextView

    private val questionBank = listOf(
        Question(R.string.question_australia, true),
        Question(R.string.question_oceans, true),
        Question(R.string.question_asia, true),
        Question(R.string.question_africa, false),
        Question(R.string.question_mideast, false))

    private var currentIndex = 0

    // activity的状态方法
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        android.util.Log.d(TAG, "onCreate: called")
        setContentView(R.layout.activity_main)

        trueButton = findViewById<Button>(R.id.true_button)
        falseButton = findViewById<Button>(R.id.false_button)
        preButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        questionTextView = findViewById(R.id.question_text_view)

        // 添加控件监听器
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        preButton.setOnClickListener {
            if (currentIndex > 0) {
                currentIndex -= 1
            }
            else {
                currentIndex = questionBank.size - 1
            }
            updateQuestion()
        }

        nextButton.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            currentIndex = (currentIndex + 1) % questionBank.size
            updateQuestion()
        }

        updateQuestion()
    }

    override fun onStart() {
        super.onStart()
        android.util.Log.d(TAG, "onStart: called")
    }

    override fun onResume() {
        super.onResume()
        android.util.Log.d(TAG, "onResume: called")
    }

    override fun onPause() {
        super.onPause()
        android.util.Log.d(TAG, "onPause: called")
    }

    override fun onStop() {
        super.onStop()
        android.util.Log.d(TAG, "onStop: called")
    }

    override fun onDestroy() {
        super.onDestroy()
        android.util.Log.d(TAG, "onDestroy: called")
    }

    // 更新问题
    private fun updateQuestion() {
        val questionTextResId = questionBank[currentIndex].textResId
        questionTextView.setText(questionTextResId)
    }

    // 根据用户输入答案和标准答案进行对比, 显示结果提示
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = questionBank[currentIndex].answer

        val toastMsgResId = if (userAnswer == correctAnswer) {
            R.string.correct_toast
        }
        else {
            R.string.incorrect_toast
        }

        var toast = Toast.makeText(this, toastMsgResId, Toast.LENGTH_SHORT)
        toast.setGravity(Gravity.BOTTOM, 0, 0)
        toast.show()
    }
}

/**
 *  关于图片大小的设置,有几个大小的单位
 *  px : short for pixel.
 *  dp : short for density-independent(与密度无关的像素) pixel. One dp is always 1/160 of an inch on a device’s screen.
 *  sp : short for scale-independent(与缩放无关的像素) pixel. 关联到了用户偏好的字体大小.设置字体使用 sp.
 */