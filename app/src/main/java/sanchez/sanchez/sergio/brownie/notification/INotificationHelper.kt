package sanchez.sanchez.sergio.brownie.notification

import android.app.Notification
import android.content.Intent


interface INotificationHelper {

    fun showImportantNotification(title: String, body: String, intent: Intent?)

    fun showNoticeNotification(title: String, body: String, intent: Intent?)

    fun showSilentNotification(title: String, body: String, intent: Intent?)

    fun createImportantNotification(title: String, body: String, intent: Intent?): Notification

    fun createNoticeNotification(title: String, body: String, intent: Intent?): Notification

    fun createSilentNotification(title: String, body: String, intent: Intent?): Notification

}