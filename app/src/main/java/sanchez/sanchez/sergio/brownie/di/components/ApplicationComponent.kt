package sanchez.sanchez.sergio.brownie.di.components

import android.content.Context
import dagger.Component
import sanchez.sanchez.sergio.brownie.di.modules.ApplicationModule
import javax.inject.Singleton
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager
import sanchez.sanchez.sergio.brownie.ui.dialogs.impl.NoticeDialogFragment


/**
 * A component whose lifetime is the life of the application.
 */
@Singleton
@Component(modules = [ ApplicationModule::class ])
interface ApplicationComponent {

    fun inject(noticeDialogFragment: NoticeDialogFragment)

    //Exposed to sub-graphs.
    fun context(): Context
    fun notificationHelper(): INotificationHelper
    fun soundManager(): ISoundManager
}