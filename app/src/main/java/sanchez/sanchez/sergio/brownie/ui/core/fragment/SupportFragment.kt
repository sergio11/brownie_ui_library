package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.FragmentNavigator
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import sanchez.sanchez.sergio.brownie.extension.setupSnackbar
import sanchez.sanchez.sergio.brownie.ui.core.navigation.NavigationCommand
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportViewModel
import java.lang.ClassCastException
import javax.inject.Inject

/**
 * Support Fragment
 */
abstract class SupportFragment<VM : SupportViewModel, T>(private val mViewModelClass: Class<VM>): Fragment() {


    /**
     * Dependencies
     * ==============
     */

    /**
     * View Model Factory
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * Parent Activity
     */
    @Inject
    lateinit var parentActivity: Activity

    /**
     * View Model
     */
     lateinit var viewModel: VM

    /**
     * Listener
     */
    private var listener: T? = null


    /**
     * on Create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        onInject()
        viewModel = initViewModel()
    }

    /**
     * On Create View
     */
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
       return inflater.inflate(layoutId(), container, false)
    }

    /**
     * On Activity Created
     * @param savedInstanceState
     */
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        observeNavigation(viewModel)
        setupSnackbar(this, viewModel.snackBarError, Snackbar.LENGTH_LONG)
    }


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
    @LayoutRes
    abstract fun layoutId(): Int

    /**
     * On Inject
     */
    open fun onInject() {}

    /**
     * Get View Model
     */
     private fun initViewModel(): VM = ViewModelProviders.of(this, viewModelFactory)
        .get(mViewModelClass)


    /**
     * Utils Methods
     * =====================
     */

    /**
     * Observe a [NavigationCommand] [Event] [LiveData].
     * When this [LiveData] is updated, [Fragment] will navigate to its destination
     */
    private fun observeNavigation(viewModel: SupportViewModel) {
        viewModel.navigation.observe(viewLifecycleOwner, Observer {
            it?.getContentIfNotHandled()?.let { command ->
               when (command) {
                    is NavigationCommand.To -> findNavController().navigate(command.directions, getExtras())
                    is NavigationCommand.Back -> findNavController().navigateUp()
                }
            }
        })
    }

    /**
     * [FragmentNavigatorExtras] mainly used to enable Shared Element transition
     */
    open fun getExtras(): FragmentNavigator.Extras = FragmentNavigatorExtras()
}