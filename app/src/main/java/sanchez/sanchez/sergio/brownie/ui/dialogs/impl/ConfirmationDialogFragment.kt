package sanchez.sanchez.sergio.brownie.ui.dialogs.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.confirmation_dialog_layout.*
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.ui.dialogs.SupportDialogFragment

/**
 Confirmation Dialog Fragment
 **/
open class ConfirmationDialogFragment: SupportDialogFragment() {


    private var confirmationDialogListener: ConfirmationDialogListener? = null

    /**
     * Set Confirmation Dialog Listener
     */
    fun setConfirmationDialogListener(confirmationDialogListener: ConfirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener
    }

    /**
     * On View Created
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set Dialog Title Text View
        dialogTitle?.text = title

        // Accept Click Listener
        accept.setOnClickListener {
            confirmationDialogListener?.onAccepted(this)
            dismiss()
        }
        // Cancel Click Listener
        cancel.setOnClickListener {
            confirmationDialogListener?.onRejected(this)
            dismiss()
        }
    }

    /**
     * Get Layout Resource
     */
    override fun getLayoutRes(): Int = R.layout.confirmation_dialog_layout

    /**
     * Initialize Injector
     */
    override fun initializeInjector() {}

    /**
     * Show
     */
    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) {
            super.show(manager, tag)
        }
    }



    /**
     * Confirmation Dialog Listener
     */
    interface ConfirmationDialogListener {

        /**
         * On Accepted
         * @param dialog
         */
        fun onAccepted(dialog: DialogFragment)


        /**
         * On Rejected
         * @param dialog
         */
        fun onRejected(dialog: DialogFragment)
    }


    /** COMPANION OBJETC, CONST ENUM, INNER CLASS **/

    companion object {

        /**
         * Tag
         */
        val TAG = "CONFIRMATION_DIALOG_FRAGMENT"

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
                       title: String, confirmationDialogListener: ConfirmationDialogListener?): ConfirmationDialogFragment {

            val confirmationDialog = ConfirmationDialogFragment()
            confirmationDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
            // Config Arguments
            val args = Bundle()
            args.putString(TITLE_ARG, title)
            confirmationDialog.arguments = args

            // Config Listener
            if (confirmationDialogListener != null)
                confirmationDialog.setConfirmationDialogListener(confirmationDialogListener)

            confirmationDialog.isCancelable = false
            confirmationDialog.show(activity.supportFragmentManager, TAG)
            return confirmationDialog
        }

        /**
         * Show Dialog
         * @param activity
         * @param title
         * @return
         */
        @JvmStatic
        fun showDialog(activity: AppCompatActivity, title: String): ConfirmationDialogFragment {
            return showDialog(activity, title, null)
        }

    }

}