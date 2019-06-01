package sanchez.sanchez.sergio.brownie.ui.dialogs.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.notice_dialog_layout.*
import sanchez.sanchez.sergio.brownie.BrownieApp
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.sounds.DIALOG_ERROR_SOUND
import sanchez.sanchez.sergio.brownie.sounds.DIALOG_SUCCESS_SOUND
import sanchez.sanchez.sergio.brownie.ui.dialogs.SupportDialogFragment
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager
import javax.inject.Inject


/**
 Notice Dialog Fragment
 **/
open class NoticeDialogFragment: SupportDialogFragment() {

    /**
     * Dependencies
     * ============
     */

    @Inject
    protected lateinit var soundManager: ISoundManager


    private var noticeDialogListener: NoticeDialogListener? = null

    /**
     * Set Notice Dialog Listener
     */
    fun setNoticeDialogListener(noticeDialogListener: NoticeDialogListener) {
        this.noticeDialogListener = noticeDialogListener
    }

    /**
     * Get Layout Resource
     */
    override fun getLayoutRes(): Int = R.layout.notice_dialog_layout

    /**
     * Initialize Injector
     */
    override fun initializeInjector() {
        BrownieApp.applicationComponent.inject(this)
    }

    /**
     * On View Created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set Dialog Title
        dialogTitle.text = title
        // Dismiss On Click Listener
        dismiss.setOnClickListener {
            noticeDialogListener?.onAccepted(this)
            dismiss()
        }

        if(arguments != null && arguments!!.getBoolean(IS_SUCCESS_ARG))
            soundManager.playSound(DIALOG_SUCCESS_SOUND)
        else
            soundManager.playSound(DIALOG_ERROR_SOUND)

    }

    /**
     * Show
     */
    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) {
            super.show(manager, tag)
        }
    }

    /**
     * Notice Dialog Listener
     */
    interface NoticeDialogListener {

        /**
         * On Accepted
         * @param dialog
         */
        fun onAccepted(dialog: DialogFragment)
    }


    companion object {

        /**
         * Tag
         */
        val TAG = "NOTICE_DIALOG_FRAGMENT"

        /**
         * Title Arg
         */
        const val TITLE_ARG = "DIALOG_TITLE"
        const val IS_SUCCESS_ARG = "IS_SUCCESS_ARG"


        /**
         * Show Dialog
         * @param activity
         * @return
         */
        @JvmStatic
        fun showDialog(
            activity: AppCompatActivity,
            title: String, isSuccess: Boolean, noticeDialogListener: NoticeDialogListener? = null
        ): NoticeDialogFragment {


            val noticeDialog = NoticeDialogFragment().apply {
                setStyle(STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
                arguments = Bundle().apply {
                    putString(TITLE_ARG, title)
                    putBoolean(IS_SUCCESS_ARG, isSuccess)
                }
                isCancelable = false
            }

            // Config Listener
            if (noticeDialogListener != null)
                noticeDialog.setNoticeDialogListener(noticeDialogListener)

            noticeDialog.show(activity.supportFragmentManager, TAG)

            return noticeDialog
        }

    }

}