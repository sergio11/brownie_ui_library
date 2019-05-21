package sanchez.sanchez.sergio.brownie.sounds

import androidx.annotation.RawRes

/**
    Sound Manager

 **/
interface ISoundManager {

    /**
     * Play Sound
     * @param sound
     */
    fun playSound(@RawRes sound: Int): Int

    /**
     * Play Sound
     * @param sound
     */
    fun playSound(@RawRes sound: Int, loop: Boolean): Int

    /**
     * Stop Sound
     * @param streamId
     */
    fun stopSound(streamId: Int)
}