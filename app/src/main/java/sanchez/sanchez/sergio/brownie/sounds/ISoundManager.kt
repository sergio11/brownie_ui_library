package sanchez.sanchez.sergio.brownie.sounds

import androidx.annotation.RawRes
import sanchez.sanchez.sergio.brownie.R

interface ISoundManager {

    companion object {
        val DIALOG_CONFIRM: Int
            get() = R.raw.dialog_confirm_sound
        val DIALOG_ERROR: Int
            get() = R.raw.dialog_error_sound
        val DIALOG_SUCCESS_SOUND: Int
            get() = R.raw.dialog_success_sound
    }

    fun playSound(@RawRes sound: Int): Int

    fun playSound(@RawRes sound: Int, loop: Boolean): Int

    fun stopSound(streamId: Int)
}