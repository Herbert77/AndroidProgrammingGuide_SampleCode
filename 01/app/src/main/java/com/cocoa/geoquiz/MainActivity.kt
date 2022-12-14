package com.cocoa.geoquiz

import android.app.Activity
import android.app.ActivityOptions
import android.app.Instrumentation
import android.content.Intent
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.PersistableBundle
import android.util.Log
import android.view.Gravity
import android.widget.Button
import android.widget.ImageButton
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import java.lang.Exception
import java.security.Key

private const val TAG = "MainActivity"
private const val KEY_INDEX = "index"
private const val REQUEST_CODE_CHEAT = 0

class MainActivity : AppCompatActivity() {

    private lateinit var trueButton: Button
    private lateinit var falseButton: Button
    private lateinit var preButton: ImageButton
    private lateinit var nextButton: ImageButton
    private lateinit var cheatButton: Button
    private lateinit var questionTextView: TextView

    // 把viewModel声明为懒加载属性
    private val quizViewModel: QuizViewModel by lazy {
        // 把 viewModel 和当前 Activity 关联起来
        ViewModelProvider(this).get(QuizViewModel::class.java)
    }

    // activity的状态方法
    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Log.d(TAG, "onCreate: called")
        setContentView(R.layout.activity_main)

        // 从 savedInstanceState 中取出可能存在的 bundle 数据
        val currentIndex = savedInstanceState?.getInt(KEY_INDEX) ?: 0
        quizViewModel.currentIndex = currentIndex

        trueButton = findViewById<Button>(R.id.true_button)
        falseButton = findViewById<Button>(R.id.false_button)
        preButton = findViewById(R.id.previous_button)
        nextButton = findViewById(R.id.next_button)
        cheatButton = findViewById(R.id.cheat_button)
        questionTextView = findViewById(R.id.question_text_view)

        // 添加控件监听器
        trueButton.setOnClickListener {
            checkAnswer(true)
        }

        falseButton.setOnClickListener {
            checkAnswer(false)
        }

        preButton.setOnClickListener {
            quizViewModel.moveToPrev()
            updateQuestion()
        }

        nextButton.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        questionTextView.setOnClickListener {
            quizViewModel.moveToNext()
            updateQuestion()
        }

        cheatButton.setOnClickListener {
            // start cheatActivity
            val intent = CheatActivity.newIntent(this, quizViewModel.currentQuestionAnswer)
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val options = ActivityOptions.makeClipRevealAnimation(it, 0, 0, it.width, it.height)
//            startActivity(intent)
                startActivityForResult(intent, REQUEST_CODE_CHEAT, options.toBundle())
            } else {
                startActivityForResult(intent, REQUEST_CODE_CHEAT)
            }
        }

        updateQuestion()
    }

    // 覆写该方法,接收来自子activity返回的结果
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode != Activity.RESULT_OK) {
            return
        }

        if (requestCode == REQUEST_CODE_CHEAT) {
//            quizViewModel.isCheater = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
            quizViewModel.currentQuestionIsCheating = data?.getBooleanExtra(EXTRA_ANSWER_SHOWN, false) ?: false
        }
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

    // 覆写该方法,当该 activity 进入 Stoped 状态时, 该方法会被调用, 把 活动的 数据保存在 bundle 中, 如果该 activity 被重新创建时, 会通过 onCreate() 方法,恢复保存的数据.
    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        Log.i(TAG, "onSaveInstanceState: ")
        outState.putInt(KEY_INDEX, quizViewModel.currentIndex)
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
//        Log.d(TAG, "updateQuestion: ", Exception())
        val questionTextResId = quizViewModel.currentQuestionText
        questionTextView.setText(questionTextResId)
    }

    // 根据用户输入答案和标准答案进行对比, 显示结果提示
    private fun checkAnswer(userAnswer: Boolean) {
        val correctAnswer = quizViewModel.currentQuestionAnswer

//        val toastMsgResId = if (userAnswer == correctAnswer) {
//            R.string.correct_toast
//        }
//        else {
//            R.string.incorrect_toast
//        }

        val messageResId = when {
//            quizViewModel.isCheater -> R.string.judgement_toast
            quizViewModel.currentQuestionIsCheating -> R.string.judgement_toast
            (userAnswer == correctAnswer) -> R.string.correct_toast
            else -> R.string.incorrect_toast
        }

        var toast = Toast.makeText(this, messageResId, Toast.LENGTH_SHORT)
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
