package sanchez.sanchez.sergio.brownie.di.modules

import android.content.Context
import dagger.Module
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager
import javax.inject.Singleton
import dagger.Provides
import sanchez.sanchez.sergio.brownie.BrownieApp
import sanchez.sanchez.sergio.brownie.di.modules.notifications.NotificationsModule
import sanchez.sanchez.sergio.brownie.sounds.impl.SoundPoolManagerImpl
import sanchez.sanchez.sergio.brownie.notification.impl.NotificationHelperImpl
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper



/**
 * Application Module
 */
@Module(includes = [NotificationsModule::class])
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