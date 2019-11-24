package sanchez.sanchez.sergio.brownie.ui.core.adapter.decorator

import android.graphics.Canvas
import android.util.Log
import android.view.View
import android.view.View.MeasureSpec.makeMeasureSpec
import android.view.ViewGroup
import android.view.ViewTreeObserver
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import sanchez.sanchez.sergio.brownie.models.Section
import sanchez.sanchez.sergio.brownie.ui.core.adapter.SupportStickyAdapter

class StickyHeaderItemDecorator<T>(private val adapter: SupportStickyAdapter<T>) : RecyclerView.ItemDecoration() {

    private var currentStickyPosition = RecyclerView.NO_POSITION
    private var recyclerView: RecyclerView? = null
    private var currentStickyHolder: SupportStickyAdapter<T>.SupportHeaderViewHolder<Section<T>>? = null
    private var lastViewOverlappedByHeader: View? = null

    fun attachToRecyclerView(recyclerView: RecyclerView?) {
        if (this.recyclerView === recyclerView) {
            return  // nothing to do
        }
        if (this.recyclerView != null) {
            destroyCallbacks(this.recyclerView!!)
        }
        this.recyclerView = recyclerView
        if (recyclerView != null) {
            currentStickyHolder = adapter.onCreateHeaderViewHolder(recyclerView)
            fixLayoutSize()
            setupCallbacks()
        }
    }

    private fun setupCallbacks() {
        recyclerView!!.addItemDecoration(this)
    }

    private fun destroyCallbacks(recyclerView: RecyclerView) {
        recyclerView.removeItemDecoration(this)
    }

    override fun onDrawOver(c: Canvas, parent: RecyclerView, state: RecyclerView.State) {
        Log.d("STICKY", "onDrawOver")
        super.onDrawOver(c, parent, state)

        val layoutManager = parent.layoutManager ?: return

        var topChildPosition = RecyclerView.NO_POSITION
        if (layoutManager is LinearLayoutManager) {
            topChildPosition = layoutManager.findFirstVisibleItemPosition()
        } else {
            val topChild = parent.getChildAt(0)
            if (topChild != null) {
                topChildPosition = parent.getChildAdapterPosition(topChild)
            }
        }

        if (topChildPosition == RecyclerView.NO_POSITION) {
            return
        }

        var viewOverlappedByHeader =
            getChildInContact(parent, currentStickyHolder!!.itemView.bottom)
        if (viewOverlappedByHeader == null) {
            if (lastViewOverlappedByHeader != null) {
                viewOverlappedByHeader = lastViewOverlappedByHeader
            } else {
                viewOverlappedByHeader = parent.getChildAt(topChildPosition)
            }
        }
        lastViewOverlappedByHeader = viewOverlappedByHeader

        val overlappedByHeaderPosition = parent.getChildAdapterPosition(viewOverlappedByHeader!!)
        val overlappedHeaderPosition: Int
        val preOverlappedPosition: Int
        if (overlappedByHeaderPosition > 0) {
            preOverlappedPosition = adapter.getHeaderPositionForItem(overlappedByHeaderPosition - 1)
            overlappedHeaderPosition = adapter.getHeaderPositionForItem(overlappedByHeaderPosition)
        } else {
            preOverlappedPosition = adapter.getHeaderPositionForItem(topChildPosition)
            overlappedHeaderPosition = preOverlappedPosition
        }

        if (preOverlappedPosition == RecyclerView.NO_POSITION) {
            return
        }

        if (preOverlappedPosition != overlappedHeaderPosition && shouldMoveHeader(
                viewOverlappedByHeader
            )
        ) {
            updateStickyHeader(topChildPosition, overlappedByHeaderPosition)
            moveHeader(c, viewOverlappedByHeader)
        } else {
            updateStickyHeader(topChildPosition, RecyclerView.NO_POSITION)
            drawHeader(c)
        }
    }

    // shouldMoveHeader returns the sticky header should move or not.
    // This method is for avoiding sinking/departing the sticky header into/from top of screen
    private fun shouldMoveHeader(viewOverlappedByHeader: View): Boolean {
        Log.d("STICKY", "shouldMoveHeader")
        val dy = viewOverlappedByHeader.top - viewOverlappedByHeader.height
        return viewOverlappedByHeader.top >= 0 && dy <= 0
    }

    /**
     * Update Sticky Header
     * @param topChildPosition
     * @param contactChildPosition
     */
    private fun updateStickyHeader(topChildPosition: Int, contactChildPosition: Int) {
        Log.d("STICKY", "updateStickyHeader")
        val headerPositionForItem = adapter.getHeaderPositionForItem(topChildPosition)
        if (headerPositionForItem != currentStickyPosition && headerPositionForItem != RecyclerView.NO_POSITION) {
            currentStickyHolder?.let {
                adapter.onBindHeaderViewHolder(it, headerPositionForItem)
            }
            currentStickyPosition = headerPositionForItem
        } else if (headerPositionForItem != RecyclerView.NO_POSITION) {
            currentStickyHolder?.let {
                adapter.onBindHeaderViewHolder(it, headerPositionForItem)
            }

        }
    }

    /**
     * Draw Header
     * @param canvas
     */
    private fun drawHeader(c: Canvas) {
        Log.d("STICKY", "drawHeader")
        c.save()
        c.translate(0.0f, 0.0f)
        currentStickyHolder!!.itemView.draw(c)
        c.restore()
    }

    /**
     * Move Header
     * @param canvas
     * @param nextHeader
     */
    private fun moveHeader(c: Canvas, nextHeader: View) {
        Log.d("STICKY", "moveHeader")
        c.save()
        c.translate(0.0f, (nextHeader.top - nextHeader.height).toFloat())
        currentStickyHolder?.itemView?.draw(c)
        c.restore()
    }

    /**
     * Get Child in Contact
     * @param parent
     * @param contactPoint
     */
    private fun getChildInContact(parent: RecyclerView, contactPoint: Int): View? {
        Log.d("STICKY", "getChildInContact")
        var childInContact: View? = null
        for (i in 0 until parent.childCount) {
            val child = parent.getChildAt(i)
            if (child.bottom > contactPoint) {
                if (child.top <= contactPoint) {
                    // This child overlaps the contactPoint
                    childInContact = child
                    break
                }
            }
        }
        return childInContact
    }

    private fun fixLayoutSize() {

        recyclerView?.let { rv ->

            Log.d("STICKY", "fixLayoutSize")
            rv.viewTreeObserver.addOnGlobalLayoutListener(object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    rv.viewTreeObserver.removeOnGlobalLayoutListener(this)
                    // Specs for parent (RecyclerView)
                    val widthSpec =
                        makeMeasureSpec(recyclerView!!.width, View.MeasureSpec.EXACTLY)

                    val heightSpec = makeMeasureSpec(
                        rv.height,
                        View.MeasureSpec.UNSPECIFIED
                    )

                    // Specs for children (headers)
                    val childWidthSpec = ViewGroup.getChildMeasureSpec(
                        widthSpec,
                        rv.paddingLeft + rv.paddingRight,
                        currentStickyHolder!!.itemView.layoutParams.width
                    )
                    val childHeightSpec = ViewGroup.getChildMeasureSpec(
                        heightSpec,
                        rv.paddingTop + rv.paddingBottom,
                        currentStickyHolder!!.itemView.layoutParams.height
                    )

                    currentStickyHolder!!.itemView.measure(childWidthSpec, childHeightSpec)

                    currentStickyHolder!!.itemView.layout(
                        0, 0,
                        currentStickyHolder!!.itemView.measuredWidth,
                        currentStickyHolder!!.itemView.measuredHeight
                    )
                }
            })


        }
    }
}