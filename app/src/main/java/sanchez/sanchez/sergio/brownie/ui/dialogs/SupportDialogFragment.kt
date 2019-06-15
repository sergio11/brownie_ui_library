package sanchez.sanchez.sergio.brownie.ui.dialogs

import android.graphics.Point
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import sanchez.sanchez.sergio.brownie.R

abstract class SupportDialogFragment: DialogFragment() {

    val TITLE_ARG = "DIALOG_TITLE"

    lateinit var title: String

    override fun onCreate(savedInstanceState: Bundle?) {
        this.initializeInjector()
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            onPrepareArgs(arguments)
        }
    }

    private fun onPrepareArgs(args: Bundle?) {
        title = args!!.getString(TITLE_ARG,
            getString(R.string.default_notice_dialog_title))
    }


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater.inflate(getLayoutRes(), container)

    override fun onResume() {

        dialog?.window?.let {
            val size = Point()
            val display = it.windowManager.defaultDisplay
            display.getSize(size)
            it.setLayout((size.x * 0.90).toInt(), ViewGroup.LayoutParams.WRAP_CONTENT)
            it.setGravity(Gravity.CENTER)

        }
        super.onResume()
    }

    protected abstract fun getLayoutRes(): Int

    protected abstract fun initializeInjector()

}