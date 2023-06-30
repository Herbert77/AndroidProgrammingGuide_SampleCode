package com.example.beatbox

import com.example.beatbox.model.Sound

class SoundViewModel {

    var sound: Sound? = null
        set(value) {
            field = value
        }

    val title: String?
        get() = sound?.name
}