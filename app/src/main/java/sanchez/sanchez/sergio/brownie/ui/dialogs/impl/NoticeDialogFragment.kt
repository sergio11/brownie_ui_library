package sanchez.sanchez.sergio.brownie.ui.dialogs.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.notice_dialog_layout.*
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.ui.dialogs.SupportDialogFragment

/**
 Notice Dialog Fragment
 **/
open class NoticeDialogFragment: SupportDialogFragment() {


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
    override fun initializeInjector() {}

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
        val TITLE_ARG = "DIALOG_TITLE"

        /**
         * Show Dialog
         * @param activity
         * @return
         */
        @JvmStatic
        fun showDialog(activity: AppCompatActivity,
                       title: String, noticeDialogListener: NoticeDialogListener?): NoticeDialogFragment {

            val noticeDialog = NoticeDialogFragment()
            noticeDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
            // Config Arguments
            val args = Bundle()
            args.putString(TITLE_ARG, title)
            noticeDialog.arguments = args
            noticeDialog.isCancelable = false
            // Config Listener
            if (noticeDialogListener != null)
                noticeDialog.setNoticeDialogListener(noticeDialogListener)

            noticeDialog.show(activity.supportFragmentManager, TAG)

            return noticeDialog
        }

        /**
         * Show Dialog
         * @param activity
         * @param title
         * @return
         */
        @JvmStatic
        fun showDialog(activity: AppCompatActivity, title: String): NoticeDialogFragment {
            return showDialog(activity, title, null)
        }



    }

}