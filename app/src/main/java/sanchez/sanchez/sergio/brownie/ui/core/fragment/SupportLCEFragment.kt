package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.os.Bundle
import android.view.View
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.extension.bindView
import sanchez.sanchez.sergio.brownie.extension.invisible
import sanchez.sanchez.sergio.brownie.extension.visible
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportItemTouchHelper
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportRecyclerViewAdapter
import sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator.ItemOffsetDecoration
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportLCEOperationResultEnum
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportLCEViewModel

abstract class SupportLCEFragment<T, E, P, VM: SupportLCEViewModel<E, P>>(mViewModelClass: Class<VM>):
    SupportFragment<VM, T>(mViewModelClass),
        SwipeRefreshLayout.OnRefreshListener, SupportRecyclerViewAdapter.OnSupportRecyclerViewListener<E>,
        SupportItemTouchHelper.ItemTouchHelperListener{

    private val adapter: SupportRecyclerViewAdapter<E> by lazy {
        onCreateAdapter()
    }

    private val swipeRefreshLayout: SwipeRefreshLayout? by bindView(R.id.swipeRefreshLayout)
    private val recyclerView: RecyclerView? by bindView(R.id.recyclerView)
    private val noResultsFound: View? by bindView(R.id.noResultsFound)
    private val errorOcurred: View? by bindView(R.id.errorOcurred)
    private val loadingData: View? by  bindView(R.id.loadingData)


    override fun onRefresh() {
        swipeRefreshLayout?.isRefreshing = false
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        swipeRefreshLayout?.let {
            it.setColorSchemeResources(android.R.color.white)
            it.setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
            it.setOnRefreshListener(this)
        }

        recyclerView?.let {
            it.layoutManager =  LinearLayoutManager(context)
            val itemOffsetDecoration = ItemOffsetDecoration(context!!, R.dimen.item_offset)
            it.addItemDecoration(itemOffsetDecoration)
            it.itemAnimator = DefaultItemAnimator()
            it.adapter = adapter
            adapter.setOnSupportRecyclerViewListener(this)
        }

        noResultsFound?.setOnClickListener {
            loadData()
        }

        errorOcurred?.setOnClickListener {
            loadData()
        }

        viewModel.status.observe(this, Observer<SupportLCEOperationResultEnum> {
            when(it) {
                SupportLCEOperationResultEnum.LOADING -> onLoading()
                SupportLCEOperationResultEnum.DATA_FOUND -> onDataFound()
                SupportLCEOperationResultEnum.NO_DATA_FOUND -> onNoDataFound()
                else -> onError()
            }
        })

        viewModel.dataList.observe(this, Observer<List<E>> {
            adapter.setData(it as MutableList<E>)
            adapter.notifyDataSetChanged()
        })

        loadData()
    }


    abstract fun onCreateAdapter(): SupportRecyclerViewAdapter<E>

    open fun onGetLoadParams(): P? = null


    open fun onLoading() {
        loadingData?.visible()
        errorOcurred?.invisible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.invisible()
    }

    open fun onDataFound() {
        loadingData?.invisible()
        errorOcurred?.invisible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.visible()
        swipeRefreshLayout?.isRefreshing = false
    }

    open fun onNoDataFound() {
        loadingData?.invisible()
        errorOcurred?.invisible()
        noResultsFound?.visible()
        swipeRefreshLayout?.invisible()
    }

    open fun onError() {
        loadingData?.invisible()
        errorOcurred?.visible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.invisible()
    }


    /**
     * Private Methods
     */

    private fun loadData() = viewModel.loadData(onGetLoadParams())
}