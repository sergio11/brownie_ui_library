package sanchez.sanchez.sergio.brownie.di.modules.notifications

import android.content.Context
import dagger.Module
import dagger.Provides
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper
import sanchez.sanchez.sergio.brownie.notification.impl.NotificationHelperImpl
import javax.inject.Singleton

@Module
class NotificationsModule {

    /**
     * Provide Notification Helper
     */
    @Provides
    @Singleton
    fun provideNotificationHelper(context: Context): INotificationHelper =
        NotificationHelperImpl(context)


}