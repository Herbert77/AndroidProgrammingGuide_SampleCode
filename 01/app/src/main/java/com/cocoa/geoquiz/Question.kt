package com.cocoa.geoquiz

import androidx.annotation.StringRes

// data 关键字作用: 自动实现类的 equals(), hashCode(), toString()
data class Question(@StringRes val textResId: Int, val answer: Boolean, var cheating: Boolean = false)


