package sanchez.sanchez.sergio.brownie.notification

import android.app.Notification
import android.content.Intent

/**
 Notification Helper
 **/
interface INotificationHelper {

    /**
     * Show Important Notification
     * @param title
     * @param body
     * @param intent
     */
    fun showImportantNotification(title: String, body: String, intent: Intent?)


    /**
     * Show Notice Notification
     * @param title
     * @param body
     * @param intent
     */
    fun showNoticeNotification(title: String, body: String, intent: Intent?)


    /**
     * Show Silent Notification
     * @param title
     * @param body
     * @param intent
     */
    fun showSilentNotification(title: String, body: String, intent: Intent?)

    /**
     * Create Important Notification
     * @param title
     * @param body
     * @param intent
     * @return
     */
    fun createImportantNotification(title: String, body: String, intent: Intent?): Notification

    /**
     * Create Notice Notification
     * @param title
     * @param body
     * @param intent
     */
     fun createNoticeNotification(title: String, body: String, intent: Intent?): Notification

    /**
     * Create Silent Notification
     * @param title
     * @param body
     * @param intent
     */
    fun createSilentNotification(title: String, body: String, intent: Intent?): Notification

}