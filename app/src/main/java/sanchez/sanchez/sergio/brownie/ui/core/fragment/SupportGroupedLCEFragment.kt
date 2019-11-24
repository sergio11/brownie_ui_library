package sanchez.sanchez.sergio.brownie.ui.core.fragment

import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.brownie.models.Section
import sanchez.sanchez.sergio.brownie.models.SectionHeader
import sanchez.sanchez.sergio.brownie.models.SectionItem
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportStickyAdapter
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportLCEViewModel
import sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator.StickyHeaderItemDecorator
import sanchez.sanchez.sergio.brownie.ui.core.viewmodel.SupportGroupedLCEViewModel

abstract class SupportGroupedLCEFragment<T, E, P, VM: SupportGroupedLCEViewModel<E, P>>(mViewModelClass: Class<VM>):
    SupportLCEFragment<T, Section<E>, P, VM>(mViewModelClass){

    /**
     * on Create Adapter
     */
    abstract override fun onCreateAdapter(): SupportStickyAdapter<E>

    /**
     * On Configure Recycler View
     * @param recyclerView
     */
    override fun onConfigureRecyclerView(recyclerView: RecyclerView) {
        super.onConfigureRecyclerView(recyclerView)

        StickyHeaderItemDecorator(adapter as SupportStickyAdapter<E>).also {
            it.attachToRecyclerView(recyclerView)
        }

    }




}