package sanchez.sanchez.sergio.brownie.di.modules

import android.content.Context
import dagger.Module
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager
import javax.inject.Singleton
import dagger.Provides
import sanchez.sanchez.sergio.brownie.BrownieApp
import sanchez.sanchez.sergio.brownie.sounds.impl.SoundPoolManagerImpl
import sanchez.sanchez.sergio.brownie.notification.impl.NotificationHelperImpl
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper




/**
 * Application Module
 */
@Module
class ApplicationModule constructor(private val application: BrownieApp) {

    /**
     * Provide Application Context
     * @return
     */
    @Provides
    @Singleton
    fun provideApplicationContext(): Context {
        return this.application
    }

    /**
     * Provide Notification Helper
     * @param appContext
     * @return
     */
    @Provides
    @Singleton
    fun provideNotificationHelper(appContext: Context): INotificationHelper {
        return NotificationHelperImpl(appContext)
    }


    /**
     * Provide Sound Manager
     * @return
     */
    @Provides
    @Singleton
    fun provideSoundManager(
        context: Context
    ): ISoundManager {
        return SoundPoolManagerImpl(context)
    }
}