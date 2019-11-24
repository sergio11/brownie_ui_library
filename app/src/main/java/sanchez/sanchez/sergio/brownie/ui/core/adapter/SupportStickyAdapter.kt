package sanchez.sanchez.sergio.brownie.ui.core.adapter

import android.content.Context
import android.view.View
import android.view.ViewGroup
import sanchez.sanchez.sergio.brownie.models.Section


abstract class SupportStickyAdapter<T>(context: Context):
    SupportRecyclerViewAdapter<Section<T>>(context, ArrayList<Section<T>>()) {

    /**
     * Get Item View Type
     */
    override fun getItemViewType(position: Int): Int {
        return getData()[position].type()
    }

    /**
     * This method gets called by {@link StickyHeaderItemDecorator} to fetch
     * the position of the header item in the adapter that is used for
     * (represents) item at specified position.
     *
     * @param itemPosition int. Adapter's position of the item for which to do
     *                     the search of the position of the header item.
     * @return int. Position of the header for an item in the adapter or
     * {@link RecyclerView.NO_POSITION} (-1) if an item has no header.
     */
    fun getHeaderPositionForItem(itemPosition: Int): Int {
        return getData()[itemPosition].sectionPosition()
    }

    /**
     * This method gets called by {@link StickyHeaderItemDecorator} to setup the header View.
     *
     * @param holder         RecyclerView.ViewHolder. Holder to bind the data on.
     * @param headerPosition int. Position of the header item in the adapter.
     */
    abstract fun onBindHeaderViewHolder(holder: SupportHeaderViewHolder<Section<T>>, headerPosition: Int)

    /**
     * On Create Header Item View Holder
     * @param viewGroup
     */
    abstract fun onCreateHeaderItemViewHolder(viewGroup: ViewGroup): SupportHeaderViewHolder<Section<T>>

    /**
     * Called only twice when {@link StickyHeaderItemDecorator} needs
     * a new {@link RecyclerView.ViewHolder} to represent a sticky header item.
     * Those two instances will be cached and used to represent a current top sticky header
     * and the moving one.
     * <p>
     * You can either create a new View manually or inflate it from an XML layout file.
     * <p>
     * The new ViewHolder will be used to display items of the adapter using
     * {@link #onBindHeaderViewHolder(RecyclerView.ViewHolder, int)}. Since it will be re-used to display
     * different items in the data set, it is a good idea to cache references to sub views of
     * the View to avoid unnecessary {@link View#findViewById(int)} calls.
     *
     * @param parent The ViewGroup to resolve a layout params.
     * @return A new ViewHolder that holds a View of the given view type.
     * @see #onBindHeaderViewHolder(RecyclerView.ViewHolder, int)
     */
    fun onCreateHeaderViewHolder(parent: ViewGroup): SupportHeaderViewHolder<Section<T>> =
        onCreateHeaderItemViewHolder(parent)



    abstract inner class SupportHeaderViewHolder<T>(itemView: View) : SupportItemViewHolder<T>(itemView) {
        override fun bind(element: T) {
            super.bind(element)
        }
    }

}