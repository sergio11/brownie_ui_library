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

open class ProgressDialogFragment: SupportDialogFragment() {

    override fun getLayoutRes(): Int = R.layout.progress_dialog_layout

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        // Set Dialog Title Text View
        dialogTitle.text = title
    }

    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) {
            super.show(manager, tag)
        }
    }

    override fun initializeInjector() {}

    companion object {

        val TAG = "PROGRESS_DIALOG_FRAGMENT"

        val TITLE_ARG = "DIALOG_TITLE"

        private var currentProgressDialog: WeakReference<ProgressDialogFragment>? = null

        @JvmStatic
        fun showDialog(activity: AppCompatActivity, title: String): ProgressDialogFragment {
            val progressDialog = ProgressDialogFragment()
            progressDialog.setStyle(STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
            val args = Bundle().apply {
                putString(TITLE_ARG, title)
            }
            progressDialog.arguments = args
            progressDialog.show(activity.supportFragmentManager, TAG)
            currentProgressDialog = WeakReference(progressDialog)
            progressDialog.isCancelable = false
            return progressDialog
        }


        @JvmStatic
        fun cancelCurrent() {
            currentProgressDialog?.get()?.dismiss()
            currentProgressDialog?.clear()
            currentProgressDialog = null
        }

    }

}