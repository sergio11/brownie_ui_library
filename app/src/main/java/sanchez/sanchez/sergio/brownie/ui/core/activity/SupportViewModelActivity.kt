package sanchez.sanchez.sergio.brownie.ui.core.activity

import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportViewModel
import javax.inject.Inject

/**
 * Support View Model Activity
 */
abstract class SupportViewModelActivity<VM : SupportViewModel>
    (private val mViewModelClass: Class<VM>): SupportActivity() {


    /**
     * Dependencies
     * ====================
     */

    /**
     * View Model Factory
     */
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    /**
     * View Model
     */
    val viewModel by lazy {
        ViewModelProviders.of(this, viewModelFactory).get(mViewModelClass)
    }

    /**
     * on Create
     */
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModel(viewModel)
    }

    /**
     *
     *  You need override this method.
     *  And you need to set viewModel to binding: binding.viewModel = viewModel
     *
     */
    abstract fun initViewModel(viewModel: VM)

}