package sanchez.sanchez.sergio.brownie.ui.core.fragment

import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.brownie.models.Section
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportGroupedRecyclerViewAdapter
import sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator.HeaderItemDecoration
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportGroupedLCEViewModel

abstract class SupportGroupedLCEFragment<T, E, P, VM: SupportGroupedLCEViewModel<E, P>>(mViewModelClass: Class<VM>):
    SupportLCEFragment<T, Section<E>, P, VM>(mViewModelClass){

    /**
     * on Create Adapter
     */
    abstract override fun onCreateAdapter(): SupportGroupedRecyclerViewAdapter<E>

    /**
     * On Configure Recycler View
     * @param recyclerView
     */
    override fun onConfigureRecyclerView(recyclerView: RecyclerView) {
        super.onConfigureRecyclerView(recyclerView)

        recyclerView.addItemDecoration(
            HeaderItemDecoration(recyclerView,
                adapter as SupportGroupedRecyclerViewAdapter<E>)
        )

    }




}