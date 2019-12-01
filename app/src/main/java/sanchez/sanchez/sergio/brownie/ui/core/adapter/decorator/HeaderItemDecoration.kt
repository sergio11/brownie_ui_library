package sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator

import android.graphics.Canvas
import android.graphics.Rect
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportGroupedRecyclerViewAdapter

class HeaderItemDecoration<E>(
    private val recyclerView: RecyclerView,
    private val adapter: SupportGroupedRecyclerViewAdapter<E>
) : RecyclerView.ItemDecoration() {


    private var currentHeader: Pair<Int, RecyclerView.ViewHolder>? = null

    init {

        adapter.registerAdapterDataObserver(object : RecyclerView.AdapterDataObserver() {
            override fun onChanged() {
                // clear saved header as it can be outdated now
                currentHeader = null
            }
        })

        recyclerView.apply {
            doOnEachNextLayout {
                // clear saved layout as it may need layout update
                currentHeader = null
            }
            // handle click on sticky header
            addOnItemTouchListener(object : RecyclerView.SimpleOnItemTouchListener() {
                override fun onInterceptTouchEvent(recyclerView: RecyclerView, motionEvent: MotionEvent): Boolean {
                    return if (motionEvent.action == MotionEvent.ACTION_DOWN) {
                        motionEvent.y <= currentHeader?.second?.itemView?.bottom ?: 0
                    } else false
                }
            })
        }
    }

    /**
     * On Draw Over
     * @param canvas
     * @param parent
     * @param state
     */
    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        super.onDrawOver(c, parent, state)

        val topChild = parent.getChildAt(0) ?: return
        val topChildPosition = parent.getChildAdapterPosition(topChild)
        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        val headerView = getHeaderViewForItem(topChildPosition, parent) ?: return

        val contactPoint = headerView.bottom
        val childInContact = getChildInContact(parent, contactPoint) ?: return

        if (adapter.isHeaderAtPosition(parent.getChildAdapterPosition(childInContact))) {
            moveHeader(c, headerView, childInContact)
            return
        }

        drawHeader(c, headerView)
    }

    /**
     * Private Methods
     */


    /**
     * Get Header View For Item
     * @param itemPosition
     * @param parent
     *
     */
    private fun getHeaderViewForItem(itemPosition: Int, parent: RecyclerView): View? {

        val headerPosition = getHeaderPositionForItem(itemPosition)
        if(headerPosition == RecyclerView.NO_POSITION) return null
        val headerType = adapter.getItemViewType(headerPosition)
        // if match reuse viewHolder
        if (currentHeader?.first == headerPosition && currentHeader?.second?.itemViewType == headerType) {
            return currentHeader?.second?.itemView
        }

        val headerHolder =
            adapter.onCreateHeaderViewHolder(parent)

        adapter.onBindHeaderViewHolder(headerHolder, headerPosition)
        fixLayoutSize(parent, headerHolder.itemView)
        // save for next draw
        currentHeader = headerPosition to headerHolder

        return headerHolder.itemView
    }

    /**
     * Draw Header
     * @param canvas
     * @param header
     */
    private fun drawHeader(c: Canvas, header: View) {
        c.save()
        c.translate(0f, 0f)
        header.draw(c)
        c.restore()
    }

    /**
     * Move Header
     * @param canvas
     * @param currentHeader
     * @param nextHeader
     */
    private fun moveHeader(c: Canvas, currentHeader: View, nextHeader: View) {
        c.save()
        c.translate(0f, (nextHeader.top - currentHeader.height).toFloat())
        currentHeader.draw(c)
        c.restore()
    }

    /**
     * Get Child In Contact
     * @param parent
     * @param contactPoint
     */
    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            val mBounds = Rect()
            parent.getDecoratedBoundsWithMargins(child, mBounds)
            if (mBounds.bottom > contactPoint) {
                if (mBounds.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    /**
     * Properly measures and layouts the top sticky header.
     *
     * @param parent ViewGroup: RecyclerView in this case.
     */
    private fun fixLayoutSize(parent: ViewGroup, view: View) {

        // Specs for parent (RecyclerView)
        val widthSpec = View.MeasureSpec.makeMeasureSpec(parent.width, View.MeasureSpec.EXACTLY)
        val heightSpec = View.MeasureSpec.makeMeasureSpec(parent.height, View.MeasureSpec.UNSPECIFIED)

        // Specs for children (headers)
        val childWidthSpec = ViewGroup.getChildMeasureSpec(widthSpec, parent.paddingLeft + parent.paddingRight, view.layoutParams.width)
        val childHeightSpec = ViewGroup.getChildMeasureSpec(heightSpec, parent.paddingTop + parent.paddingBottom, view.layoutParams.height)

        view.measure(childWidthSpec, childHeightSpec)
        view.layout(0, 0, view.measuredWidth, view.measuredHeight)
    }

    private fun getHeaderPositionForItem(itemPosition: Int): Int {
        var headerPosition = RecyclerView.NO_POSITION
        var currentPosition = itemPosition
        do {
            if (adapter.isHeaderAtPosition(currentPosition)) {
                headerPosition = currentPosition
                break
            }
            currentPosition -= 1
        } while (currentPosition >= 0)
        return headerPosition
    }
}

inline fun View.doOnEachNextLayout(crossinline action: (view: View) -> Unit) {
    addOnLayoutChangeListener { view, left, top, right, bottom, oldLeft, oldTop, oldRight, oldBottom -> action(view) }
}