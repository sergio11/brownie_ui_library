package sanchez.sanchez.sergio.brownie.ui.core.adapter

import android.graphics.Canvas
import android.util.Log
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

/**
 * Support Item Touch Helper
 */
class SupportItemTouchHelper <E, T : SupportRecyclerViewAdapter<E>.SupportItemSwipedViewHolder<E>>
/**
 * @param dragDirs
 * @param swipeDirs
 * @param listener
 */
    (dragDirs: Int, swipeDirs: Int,
     private val listener: ItemTouchHelperListener) : ItemTouchHelper.SimpleCallback(dragDirs, swipeDirs) {

    /**
     * On Move
     * @param recyclerView
     * @param viewHolder
     * @param target
     * @return_icon
     */
    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder,
                        target: RecyclerView.ViewHolder): Boolean {
        Log.d("ITEM_TOUCH_HELPER", "on Move called")
        return true
    }

    /**
     * On Selected Changed
     * @param viewHolder
     * @param actionState
     */
    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if (viewHolder != null) {
            val foregroundView = (viewHolder as T).viewForeground
            ItemTouchHelper.Callback.getDefaultUIUtil().onSelected(foregroundView)
        }
    }

    /**
     * On Child Draw over
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    override fun onChildDrawOver(c: Canvas, recyclerView: RecyclerView,
                                 viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                                 actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = (viewHolder as T).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive)
    }

    /**
     * Clear View
     * @param recyclerView
     * @param viewHolder
     */
    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        val foregroundView = (viewHolder as T).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil().clearView(foregroundView)
    }

    /**
     * On Child Draw
     * @param c
     * @param recyclerView
     * @param viewHolder
     * @param dX
     * @param dY
     * @param actionState
     * @param isCurrentlyActive
     */
    override fun onChildDraw(c: Canvas, recyclerView: RecyclerView,
                             viewHolder: RecyclerView.ViewHolder, dX: Float, dY: Float,
                             actionState: Int, isCurrentlyActive: Boolean) {
        val foregroundView = (viewHolder as T).viewForeground
        ItemTouchHelper.Callback.getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY,
            actionState, isCurrentlyActive)
    }

    /**
     * On Swiped
     * @param viewHolder
     * @param direction
     */
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        listener.onSwiped(viewHolder, direction, viewHolder.adapterPosition)
    }

    /**
     * Convert To Absolute Direction
     * @param flags
     * @param layoutDirection
     * @return_icon
     */
    override fun convertToAbsoluteDirection(flags: Int, layoutDirection: Int): Int {
        return super.convertToAbsoluteDirection(flags, layoutDirection)
    }


    /**
     * Last Alerts Item Touch Helper Listener
     */
    interface ItemTouchHelperListener {
        fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int, position: Int)
    }
}