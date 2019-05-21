package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import java.lang.ClassCastException

/**
 * Support Fragment
 */
abstract class SupportFragment<T>: Fragment() {

    /**
     * Listener
     */
    private var listener: T? = null

    /**
     * On Create View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View =
        inflater.inflate(layoutId(), container, false)


    /**
     * On Attach
     */
    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = context as T
        } catch (ex : ClassCastException) {}
    }

    /**
     * On Detach
     */
    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    /**
     * Layout Id
     */
    abstract fun layoutId(): Int
}