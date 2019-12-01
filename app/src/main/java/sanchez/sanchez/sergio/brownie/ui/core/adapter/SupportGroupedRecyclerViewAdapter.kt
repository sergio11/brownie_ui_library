package sanchez.sanchez.sergio.brownie.ui.core.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import sanchez.sanchez.sergio.brownie.models.SECTION_CUSTOM_HEADER
import sanchez.sanchez.sergio.brownie.models.SECTION_HEADER
import sanchez.sanchez.sergio.brownie.models.SECTION_ITEM
import sanchez.sanchez.sergio.brownie.models.Section


abstract class SupportGroupedRecyclerViewAdapter<T>(context: Context):
    SupportRecyclerViewAdapter<Section<T>>(context, ArrayList<Section<T>>()) {

    /**
     * Get Item View Type
     */
    override fun getItemViewType(position: Int): Int {
        return getData()[position].type()
    }

    /**
     * on Create View Holder
     */
    override fun onCreateViewHolder(
        viewGroup: ViewGroup,
        viewType: Int
    ): SupportItemViewHolder<Section<T>> {
        return if(viewType == SECTION_HEADER || viewType == SECTION_CUSTOM_HEADER)
            onCreateHeaderViewHolder(viewGroup)
        else
            super.onCreateViewHolder(viewGroup, viewType)
    }

    /**
     * Is Header At Position
     * @param itemPosition
     */
    fun isHeaderAtPosition(itemPosition: Int): Boolean =
        getData()[itemPosition].type() == SECTION_HEADER


    /**
     * Get Header Position For Item
     * @param itemPosition
     */
    fun getHeaderPositionForItem(itemPosition: Int): Int {
        return getData()[itemPosition].sectionPosition()
    }

    /**
     * On Bind Header View Holder
     * @param holder
     * @param headerPosition
     */
    abstract fun onBindHeaderViewHolder(
        holder: SupportHeaderViewHolder<Section<T>>,
        headerPosition: Int)

    /**
     * On Create Header Item View Holder
     * @param viewGroup
     */
    abstract fun onCreateHeaderItemViewHolder(viewGroup: ViewGroup): SupportHeaderViewHolder<Section<T>>

    /**
     * On Create Header View Holder
     * @param parent
     */
    fun onCreateHeaderViewHolder(parent: ViewGroup): SupportHeaderViewHolder<Section<T>> =
        onCreateHeaderItemViewHolder(parent)


    abstract inner class SupportHeaderViewHolder<T>(itemView: View) : SupportItemViewHolder<T>(itemView) {
        override fun bind(element: T) {
            super.bind(element)
        }
    }

}