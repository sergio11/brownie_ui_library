package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.CallSuper
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sanchez.sanchez.sergio.brownie.R
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

    private val TAG = "SUPP_LCE"

    protected lateinit var adapter: SupportRecyclerViewAdapter<E>

    private val itemOffsetDecoration by lazy {
        ItemOffsetDecoration(requireContext(), R.dimen.item_offset)
    }

    /**
     * Views
     */
    private var swipeRefreshLayout: SwipeRefreshLayout? = null
    protected var recyclerView: RecyclerView? = null
    private var noResultsFound: View? = null
    private var errorOcurred: View? = null
    private var loadingData: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel.status.observe(this, Observer<SupportLCEOperationResultEnum> {
            Log.d(TAG, "status change -> $it")
            when(it) {
                SupportLCEOperationResultEnum.LOADING -> onLoading()
                SupportLCEOperationResultEnum.DATA_FOUND -> onDataFound()
                SupportLCEOperationResultEnum.NO_DATA_FOUND -> onNoDataFound()
                else -> onError()
            }
        })

    }

    override fun onRefresh() {
        swipeRefreshLayout?.isRefreshing = false
        Log.d(TAG, "onRefresh loadData")
        loadData()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        initViews(view)

        swipeRefreshLayout?.let {
            Log.d(TAG, "configure swipeRefreshLayout")
            it.setColorSchemeResources(android.R.color.white)
            it.setProgressBackgroundColorSchemeResource(R.color.colorPrimary)
            it.setOnRefreshListener(this)
        }

        configureDataList()

        noResultsFound?.setOnClickListener {
            Log.d(TAG, "noResultsFound CLICK")
            loadData()
        }

        errorOcurred?.setOnClickListener {
            Log.d(TAG, "errorOcurred CLICK")
            loadData()
        }

        Log.d(TAG, "onViewCreated loadData")
        loadData()
    }

    /**
     * on Create Adapter
     */
    abstract fun onCreateAdapter(): SupportRecyclerViewAdapter<E>

    /**
     * On Create Layout Manager
     */
    open fun onCreateLayoutManager(): RecyclerView.LayoutManager = LinearLayoutManager(context)

    open fun onGetLoadParams(): P? = null

    open fun onLoading() {
        Log.d(TAG, "onLoading")
        loadingData?.visible()
        errorOcurred?.invisible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.invisible()
    }

    open fun onDataFound() {
        Log.d(TAG, "onDataFound")
        loadingData?.invisible()
        errorOcurred?.invisible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.visible()
        swipeRefreshLayout?.isRefreshing = false
        setDataList()
    }

    open fun onNoDataFound() {
        Log.d(TAG, "onNoDataFound")
        loadingData?.invisible()
        errorOcurred?.invisible()
        noResultsFound?.visible()
        swipeRefreshLayout?.invisible()
    }

    open fun onError() {
        Log.d(TAG, "onError")
        loadingData?.invisible()
        errorOcurred?.visible()
        noResultsFound?.invisible()
        swipeRefreshLayout?.invisible()
    }

    @CallSuper
    open fun onConfigureRecyclerView(recyclerView: RecyclerView) {

        Log.d(TAG, "configure recyclerView")

        val adapterCreated = onCreateAdapter().also {
            adapter = it
            adapter.setOnSupportRecyclerViewListener(this)
        }

        recyclerView.apply {
            layoutManager =  onCreateLayoutManager()
            removeItemDecoration(itemOffsetDecoration)
            addItemDecoration(itemOffsetDecoration)
            itemAnimator = DefaultItemAnimator()
            adapter = adapterCreated
        }

    }
    /**
     * Load Data
     */
    protected fun loadData() = viewModel.loadData(onGetLoadParams())

    /**
     * Request Update data List
     */
    protected fun requestUpdateDataList() {
        adapter.getData().also {currentData ->
            configureDataList().also {
                adapter.replaceData(currentData as MutableList<E>)
            }
        }
    }
    /**
     * Private Methods
     */

    private fun initViews(container: View) {
        swipeRefreshLayout = container.findViewById(R.id.swipeRefreshLayout)
        recyclerView = container.findViewById(R.id.recyclerView)
        noResultsFound = container.findViewById(R.id.noResultsFound)
        errorOcurred = container.findViewById(R.id.errorOcurred)
        loadingData = container.findViewById(R.id.loadingData)
    }

    private fun setDataList(){
        viewModel.dataList.value?.let { dataList ->
            Log.d(TAG, "setDataList")
            adapter.replaceData(dataList as MutableList<E>)
        }
    }

    /**
     * Configure Data List
     */
    private fun configureDataList() {
        recyclerView?.let {onConfigureRecyclerView(it) }
    }

}