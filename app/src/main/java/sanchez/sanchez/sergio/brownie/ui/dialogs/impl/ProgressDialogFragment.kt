package sanchez.sanchez.sergio.brownie.ui.dialogs.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.progress_dialog_layout.*
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.ui.dialogs.SupportDialogFragment
import java.lang.ref.WeakReference

/**
 Progress Dialog Fragment
 **/
open class ProgressDialogFragment: SupportDialogFragment() {

    /**
     * Get Layout Res
     * @return
     */
    override fun getLayoutRes(): Int = R.layout.progress_dialog_layout

    /**
     * On View Created
     * @param view
     * @param savedInstanceState
     */
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set Dialog Title Text View
        dialogTitle.text = title
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
     * Initialize Injector
     */
    override fun initializeInjector() {}

    /** COMPANION OBJETC, CONST ENUM, INNER CLASS **/

    companion object {

        /**
         * Tag
         */
        val TAG = "PROGRESS_DIALOG_FRAGMENT"

        /**
         * Title Arg
         */
        val TITLE_ARG = "DIALOG_TITLE"

        /**
         * Current Progress Dialog
         */
        private var currentProgressDialog: WeakReference<ProgressDialogFragment>? = null


        /**
         * Show Dialog
         * @param activity
         * @return
         */
        @JvmStatic
        fun showDialog(activity: AppCompatActivity, title: String): ProgressDialogFragment {
            val progressDialog = ProgressDialogFragment()
            progressDialog.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
            // Config Arguments
            val args = Bundle().apply {
                putString(TITLE_ARG, title)
            }
            progressDialog.arguments = args
            progressDialog.show(activity.supportFragmentManager, TAG)
            currentProgressDialog = WeakReference(progressDialog)
            progressDialog.isCancelable = false
            return progressDialog
        }

        /**
         * Cancel Current Progress Dialog
         */
        @JvmStatic
        fun cancelCurrent() {
            currentProgressDialog?.get()?.dismiss()
            currentProgressDialog?.clear()
            currentProgressDialog = null
        }

    }

}