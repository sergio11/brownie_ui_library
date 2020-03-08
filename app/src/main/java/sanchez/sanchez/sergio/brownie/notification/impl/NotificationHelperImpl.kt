package sanchez.sanchez.sergio.brownie.notification.impl

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.os.Build
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper

class NotificationHelperImpl(private val context: Context): INotificationHelper {

    enum class NotificationChannels {
        ALERTS_CHANNEL, COMMON_CHANNEL
    }

    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (notificationChannels in NotificationChannels.values())
                createNotificationChannel(
                    notificationChannels
                )
        }
    }


    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationChannels: NotificationChannels) {
        val channelName: CharSequence = notificationChannels.name
        val importance = NotificationManager.IMPORTANCE_HIGH
        val notificationChannel = NotificationChannel(
            notificationChannels.name,
            channelName,
            importance
        )
        with(NotificationManagerCompat.from(context)) {
            createNotificationChannel(notificationChannel)
        }

    }


    /**
     * Show Notification
     * @param id
     * @param notification
     */
    private fun showNotification(id: Int, notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            notify(id, notification)
        }
    }

    override fun showImportantNotification(smallIcon: Int, id: Int, title: String, body: String, intent: Intent?) {
        showNotification(id, createImportantNotification(smallIcon, title, body, intent))
    }

    override fun createImportantNotification(
        smallIcon: Int,
        title: String,
        body: String,
        intent: Intent?
    ): Notification {

        val builder = NotificationCompat.Builder(context, NotificationChannels.ALERTS_CHANNEL.name)
            .setSmallIcon(smallIcon)
            .setContentTitle(title)
            .setColorized(true)
            .setColor(ContextCompat.getColor(context, R.color.colorPrimary))
            .setStyle(NotificationCompat.BigTextStyle().bigText( body ))
            .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setLights(Color.BLUE, 3000, 3000)
            .setCategory(NotificationCompat.CATEGORY_REMINDER)

        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            builder.setContentIntent(pendingIntent)
        }

        return builder.build()
    }


    companion object {
        private const val DEFAULT_NOTIFICATION_ID = 1000
    }
}