package sanchez.sanchez.sergio.brownie.notification.impl

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import android.os.Vibrator
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.fernandocejas.arrow.checks.Preconditions
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.notification.INotificationHelper


private const val DEFAULT_NOTIFICATION_ID = 1000

/**
    Notification Helper Impl
 **/
class NotificationHelperImpl constructor(private val context: Context): INotificationHelper {

    /**
     * Notification Channels
     */
    enum class NotificationChannels {
        SILENT_CHANNEL, COMMON_CHANNEL, IMPORTANT_CHANNEL
    }


    /**
     * Default ringtone
     */
    private val alertSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)

    /**
     * Create notification channel if needed
     */
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            for (notificationChannels in NotificationChannels.values())
                createNotificationChannel(notificationChannels)
        }
    }
    /**
     * Create Low importance Notification Channel
     */
    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNotificationChannel(notificationChannels: NotificationChannels) {
        Preconditions.checkNotNull(notificationChannels, "Notification channel can not be null")

        val channelName = notificationChannels.name
        val importance = NotificationManager.IMPORTANCE_LOW

        val notificationChannel = NotificationChannel(
            notificationChannels.name,
            channelName, importance
        )

        val audioAttributes = AudioAttributes.Builder()
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .setUsage(AudioAttributes.USAGE_NOTIFICATION_RINGTONE)
            .build()

        when (notificationChannels) {
            NotificationChannels.COMMON_CHANNEL -> {
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.BLUE
                notificationChannel.enableVibration(true)
                notificationChannel.setShowBadge(true)
                notificationChannel.setSound(alertSound, audioAttributes)
                notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            NotificationChannels.IMPORTANT_CHANNEL -> {
                notificationChannel.enableLights(true)
                notificationChannel.lightColor = Color.RED
                notificationChannel.setShowBadge(true)
                notificationChannel.enableVibration(true)
                notificationChannel.setSound(alertSound, audioAttributes)
                notificationChannel.vibrationPattern = longArrayOf(100, 200, 300, 400, 500, 400, 300, 200, 400)
            }
            else -> notificationChannel.enableLights(false)
        }

        val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager?.createNotificationChannel(notificationChannel)
    }

    /**
     * Vibrate
     * @param milliseconds
     */
    private fun vibrate(milliseconds: Long) {
        val vibratorService = context.getSystemService(Context.VIBRATOR_SERVICE) as Vibrator
        vibratorService.vibrate(milliseconds)
    }

    /**
     * Show Notification
     * @param notification
     */
    private fun showNotification(notification: Notification) {
        Preconditions.checkNotNull(notification, "Notification can not be null")

        vibrate(1000)

        val mNotificationManager = context
            .getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(DEFAULT_NOTIFICATION_ID, notification)
    }

    /**
     * Show Important Notification
     * @param title
     * @param body
     */
    override fun showImportantNotification(title: String, body: String, intent: Intent?) {
        showNotification(createImportantNotification(title, body, intent))
    }

    /**
     * Show Notice Notification
     * @param title
     * @param body
     */
    override fun showNoticeNotification(title: String, body: String, intent: Intent?) {
        showNotification(createNoticeNotification(title, body, intent))

    }


    /**
     * Show Silent Notification
     * @param title
     * @param body
     */
    override fun showSilentNotification(title: String, body: String, intent: Intent?) {
        showNotification(createSilentNotification(title, body, intent))
    }

    /**
     * Create Important Notification
     * @param title
     * @param body
     * @param intent
     * @return
     */
    override fun createImportantNotification(title: String, body: String, intent: Intent?): Notification {

        val notificationBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(body)
            .setOngoing(true)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setAutoCancel(true)
            .setSound(alertSound)

        if (Build.VERSION.SDK_INT >= 21) notificationBuilder.setVibrate(LongArray(0))

        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(
                context, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT
            )
            notificationBuilder.setContentIntent(pendingIntent)
        }


        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(NotificationChannels.IMPORTANT_CHANNEL.name)
        }

        return notificationBuilder.build()
    }

    /**
     * Create Notice Notification
     * @param title
     * @param body
     * @param intent
     */
    override fun createNoticeNotification(title: String, body: String, intent: Intent?): Notification {
        val notificationBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(body)
            .setOngoing(false)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setSound(alertSound)

        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.setContentIntent(pendingIntent)
        }

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(NotificationChannels.COMMON_CHANNEL.name)
        }

        return notificationBuilder.build()
    }


    /**
     * Crate Sile Notification
     * @param title
     * @param body
     * @param intent
     */
    override fun createSilentNotification(title: String, body: String, intent: Intent?): Notification {
        val notificationBuilder = NotificationCompat.Builder(context)
            .setSmallIcon(R.drawable.ic_stat_name)
            .setContentTitle(title)
            .setContentText(body)
            .setOngoing(false)
            .setAutoCancel(true)
            .setPriority(NotificationCompat.PRIORITY_LOW)
            .setSound(alertSound)

        if (intent != null) {
            val pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            notificationBuilder.setContentIntent(pendingIntent)
        }

        // Set the Channel ID for Android O.
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            notificationBuilder.setChannelId(NotificationChannels.SILENT_CHANNEL.name)
        }

        return notificationBuilder.build()
    }

}