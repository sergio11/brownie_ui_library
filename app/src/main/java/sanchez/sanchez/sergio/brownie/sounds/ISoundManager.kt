package sanchez.sanchez.sergio.brownie.sounds

import androidx.annotation.RawRes

interface ISoundManager {

    fun playSound(@RawRes sound: Int): Int

    fun playSound(@RawRes sound: Int, loop: Boolean): Int

    fun stopSound(streamId: Int)
}