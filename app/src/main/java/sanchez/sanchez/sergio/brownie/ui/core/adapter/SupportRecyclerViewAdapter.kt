package sanchez.sanchez.sergio.brownie.ui.core.adapter

import android.content.Context
import android.graphics.Color
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.brownie.R
import kotlin.math.max

/**
 * Support Recycler View Adapter
 */
abstract class SupportRecyclerViewAdapter<T>
constructor(protected var context: Context,
            private var data: MutableList<T>,
            private val minItemsCount: Int = if (data.size > 0) data.size - 1 else 0)
    : RecyclerView.Adapter<SupportRecyclerViewAdapter<T>.SupportItemViewHolder<T>>() {

    /**
     * Layout Inflater
     */
    protected var inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    /**
     * Listener
     */
    private var listener: OnSupportRecyclerViewListener<T>? = null

    /**
     * Highlight Text
     */
    private var highlightText: String? = null

    /**
     * Get Item
     * @param position
     * @return_icon
     */
    fun getItem(position: Int): T {
        return data[position]
    }

    /**
     * Set data
     * @param data
     */
    @CallSuper
    open fun setData(data: MutableList<T>) {
        this.data = data
    }

    @CallSuper
    open fun replaceData(newData: MutableList<T>) {
        this.data.apply {
            clear()
            addAll(newData)
        }
        notifyDataSetChanged()

    }

    /**
     * Get data
     * @return_icon
     */
    fun getData(): List<T> {
        return this.data
    }

    /**
     * Set Highlight Text
     * @param highlightText
     */
    fun setHighlightText(highlightText: String) {
        this.highlightText = highlightText.toLowerCase()
    }

    /**
     * Has Highlight Text
     * @return_icon
     */
    fun hasHighlightText(): Boolean {
        return !highlightText.isNullOrEmpty()
    }

    /**
     * Get Spannable String
     * @param text
     * @return_icon
     */
    protected fun getSpannableString(text: String): Spannable {
        val lowerText = text.toLowerCase()
        val spannable = SpannableString(lowerText)
        val startIndex = lowerText.indexOf(highlightText!!)
        if (startIndex >= 0) {
            val stopIndex = startIndex + highlightText!!.length
            spannable.setSpan(
                ForegroundColorSpan(Color.YELLOW), startIndex, stopIndex,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        }
        return spannable
    }


    /**
     * Get Item by adapter position
     * @return_icon
     */
    fun getItemByAdapterPosition(position: Int): T {
        return data[position]
    }


    /**
     * Get Item Count
     * @return_icon
     */
    override fun getItemCount(): Int = max(data.size, minItemsCount)

    /**
     * Get Item id
     * @param position
     * @return_icon
     */
    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    /**
     * On Create View Holder
     * @param viewGroup
     * @param viewType
     * @return_icon
     */
    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int):
            SupportItemViewHolder<T> {
        Log.d("SRVA", "onCreateItemViewHolder(viewGroup)")
        return onCreateItemViewHolder(viewGroup)
    }

    /**
     * On Bind View Holder
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(
        holder: SupportItemViewHolder<T>,
        position: Int) {

        Log.d("SRVA", "onBindViewHolder for position: $position ")
        holder.bind(data[position])
    }

    /**
     * On Create Item View Holder
     * @param viewGroup
     */
    protected abstract fun onCreateItemViewHolder(viewGroup: ViewGroup): SupportItemViewHolder<T>

    /**
     * Remove Item
     * @param position
     */
    fun removeItem(position: Int) {
        data.removeAt(position)
        notifyItemRemoved(position)
    }

    /**
     * Restore Item
     * @param item
     * @param position
     */
    fun restoreItem(item: T, position: Int) {
        data.add(position, item)
        // notify item added by position
        notifyItemInserted(position)
    }

    /**
     * Remove All
     */
    fun removeAll() {
        notifyItemRangeRemoved(0, data.size)
    }

    /**
     * Set On Support Recycler View Listener
     */
    fun setOnSupportRecyclerViewListener(listener: OnSupportRecyclerViewListener<T>) {
        this.listener = listener
    }

    /**
     * On Support Recycler View Listener
     */
    interface OnSupportRecyclerViewListener<T> {
        fun onItemClick(item: T)
    }


    /**
     * View Holders Types
     */

    /**
     * Support Item View Holder
     * @param <T>
    </T> */
    abstract inner class SupportItemViewHolder<T>(itemView: View) : RecyclerView.ViewHolder(itemView) {
        open fun bind(element: T) {
            if (listener != null) {
                itemView.setOnClickListener { listener!!.onItemClick(data[layoutPosition]) }
            }
        }
    }

    /**
     * Support Item Swiped View Holder
     * @param <T>
    </T> */
    abstract inner class SupportItemSwipedViewHolder<T>(itemView: View) : SupportItemViewHolder<T>(itemView) {
        val viewBackground: ViewGroup = itemView.findViewById(R.id.itemBackground)
        val viewForeground: ViewGroup = itemView.findViewById(R.id.itemForeground)
    }
}