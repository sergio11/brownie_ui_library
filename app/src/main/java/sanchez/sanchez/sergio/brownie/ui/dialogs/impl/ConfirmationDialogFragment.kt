package sanchez.sanchez.sergio.brownie.ui.dialogs.impl

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentManager
import kotlinx.android.synthetic.main.confirmation_dialog_layout.*
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.sounds.DIALOG_CONFIRM_SOUND
import sanchez.sanchez.sergio.brownie.ui.dialogs.SupportDialogFragment
import sanchez.sanchez.sergio.brownie.sounds.ISoundManager
import javax.inject.Inject


open class ConfirmationDialogFragment: SupportDialogFragment() {


    private var confirmationDialogListener: ConfirmationDialogListener? = null


    @Inject
    protected lateinit var soundManager: ISoundManager


    fun setConfirmationDialogListener(confirmationDialogListener: ConfirmationDialogListener) {
        this.confirmationDialogListener = confirmationDialogListener
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dialogTitle?.text = title

        accept.setOnClickListener {
            confirmationDialogListener?.onAccepted(this)
            dismiss()
        }
        cancel.setOnClickListener {
            confirmationDialogListener?.onRejected(this)
            dismiss()
        }

        soundManager.playSound(DIALOG_CONFIRM_SOUND)
    }


    override fun getLayoutRes(): Int = R.layout.confirmation_dialog_layout

    override fun initializeInjector() {}

    override fun show(manager: FragmentManager, tag: String?) {
        if (!manager.isDestroyed && !manager.isStateSaved) {
            super.show(manager, tag)
        }
    }


    interface ConfirmationDialogListener {

        fun onAccepted(dialog: DialogFragment)

        fun onRejected(dialog: DialogFragment)
    }


    companion object {

        val TAG = "CONFIRMATION_DIALOG_FRAGMENT"

        val TITLE_ARG = "DIALOG_TITLE"


        @JvmStatic
        fun showDialog(activity: AppCompatActivity,
                       title: String, confirmationDialogListener: ConfirmationDialogListener?): ConfirmationDialogFragment {

            val confirmationDialog = ConfirmationDialogFragment()
            confirmationDialog.setStyle(STYLE_NO_TITLE, R.style.CommonDialogFragmentTheme)
            val args = Bundle()
            args.putString(TITLE_ARG, title)
            confirmationDialog.arguments = args

            if (confirmationDialogListener != null)
                confirmationDialog.setConfirmationDialogListener(confirmationDialogListener)

            confirmationDialog.isCancelable = false
            confirmationDialog.show(activity.supportFragmentManager, TAG)
            return confirmationDialog
        }

        @JvmStatic
        fun showDialog(activity: AppCompatActivity, title: String): ConfirmationDialogFragment {
            return showDialog(activity, title, null)
        }

    }

}