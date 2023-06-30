package com.example.beatbox.viewmodel

import androidx.databinding.BaseObservable
import com.example.beatbox.model.BeatBox
import com.example.beatbox.model.Sound

class SoundViewModel(private val beatBox: BeatBox) : BaseObservable() {
    fun onButtonClicked() {
        sound?.let {
            beatBox.play(it)
        }
    }

    var sound: Sound? = null
        set(value) {
            field = value
        }

    val title: String?
        get() = sound?.name
}