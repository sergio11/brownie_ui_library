package sanchez.sanchez.sergio.brownie.ui.core.fragment

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DefaultItemAnimator
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout
import sanchez.sanchez.sergio.brownie.R
import sanchez.sanchez.sergio.brownie.extension.bindView
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportItemTouchHelper
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportRecyclerViewAdapter
import sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator.ItemOffsetDecoration

/**
 * Support LCE Fragment
 */
abstract class SupportLCEFragment<T, E>: SupportFragment<T>(),
    SwipeRefreshLayout.OnRefreshListener, SupportRecyclerViewAdapter.OnSupportRecyclerViewListener<E>,
    SupportItemTouchHelper.ItemTouchHelperListener{

    /**
     * Adapter
     */
    private val adapter: SupportRecyclerViewAdapter<E> by lazy {
        onCreateAdapter()
    }

    /**
     * Bind Views
     */
    private val swipeRefreshLayout: SwipeRefreshLayout? by bindView(R.id.swipeRefreshLayout)
    private val recyclerView: RecyclerView? by bindView(R.id.recyclerView)

    /**
     * On Refresh
     */
    override fun onRefresh() {
        swipeRefreshLayout?.isRefreshing = false
    }

    /**
     * on View Created
     */
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

    }


    /**
     * Get Adapter
     */
    abstract fun onCreateAdapter(): SupportRecyclerViewAdapter<E>

}