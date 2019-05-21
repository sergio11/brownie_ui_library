package sanchez.sanchez.sergio.brownie.sounds.impl

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.os.Build
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager


private const val MAX_STREAMS = 2
private const val DEFAULT_SOUND = 1.0f


/**
 Sound Pool Manager Impl
 **/
class SoundPoolManagerImpl(
    context: Context
): ISoundManager {

    /**
     * Sound Pool
     */
    private var soundPool: SoundPool

    /**
     * Sound Map
     */
    private var soundMap: Map<Int, Int>

    init {


        soundPool = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {

            val audioAttrib = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            SoundPool.Builder().setAudioAttributes(audioAttrib)
                .setMaxStreams(MAX_STREAMS).build()
        } else {

            SoundPool(MAX_STREAMS, AudioManager.STREAM_MUSIC, 0)
        }


        // Sound Map
        soundMap = hashMapOf()

    }



    /**
     * Play Sound
     * @param sound
     */
    override fun playSound(sound: Int): Int = playSound(sound, false)

    /**
     * Play Sound
     * @parma sound
     * @parma loop
     */
    override fun playSound(sound: Int, loop: Boolean): Int {
        var streamId: Int = -1
        if(soundMap.isNotEmpty() && soundMap.containsKey(sound)) {
            streamId = soundPool.play(
                soundMap[sound] ?: error(""), DEFAULT_SOUND, DEFAULT_SOUND,
                1, if(loop) -1 else 0, 1f)
        }
        return streamId
    }

    /**
     * Stop Sound
     * @param streamId
     */
    override fun stopSound(streamId: Int) {
        if(streamId != -1)
            soundPool.stop(streamId)
    }


}