package ru.skillbranch.devintensive.ui.adapters

import android.animation.ArgbEvaluator
import android.graphics.*
import android.graphics.drawable.Drawable
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem

/**
 * Created by BashkatovSM on 26.08.2019
 */
open class ChatItemTouchHelperCallback(
    private val adapter: ChatAdapter,
    private val swipeListener : (ChatItem) -> Unit
): ItemTouchHelper.Callback() {
    private val bgRect = RectF()
    private val iconBounds = Rect()
    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    override fun getMovementFlags(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder): Int {
        return if(viewHolder is ItemTouchViewHolder) {
            makeFlag(ItemTouchHelper.ACTION_STATE_SWIPE, ItemTouchHelper.START)
        } else {
            makeFlag(ItemTouchHelper.ACTION_STATE_IDLE, ItemTouchHelper.START)
        }
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        return false
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        swipeListener.invoke(adapter.items[viewHolder.adapterPosition])
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        if(actionState != ItemTouchHelper.ACTION_STATE_IDLE && viewHolder is ItemTouchViewHolder) {
            viewHolder.onItemSelected()
        }
        super.onSelectedChanged(viewHolder, actionState)
    }

    override fun clearView(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder) {
        if(viewHolder is ItemTouchViewHolder) viewHolder.onItemCleared()
        super.clearView(recyclerView, viewHolder)
    }

    override fun onChildDraw(
        canvas: Canvas,
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        dX: Float,
        dY: Float,
        actionState: Int,
        isCurrentlyActive: Boolean
    ) {
        if(actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {
            val itemView = viewHolder.itemView
            drawBackground(canvas, itemView, dX)
            drawIcon(canvas, itemView, dX)
        }
        super.onChildDraw(canvas, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
    }

    private fun drawIcon(canvas: Canvas, itemView: View, dX: Float) {

        val icon: Drawable = getIcon(itemView)

        val iconSize = itemView.resources.getDimensionPixelSize(R.dimen.icon_size)
        val space = itemView.resources.getDimensionPixelSize(R.dimen.spacing_normal_16)

        val margin = (itemView.bottom - itemView.top - iconSize) / 2
        with(iconBounds) {
            left = itemView.right + dX.toInt() + space
            top = itemView.top + margin
            right = itemView.right + dX.toInt() + iconSize + space
            bottom = itemView.bottom - margin
        }

        icon.bounds = iconBounds
        icon.draw(canvas)
    }

    open fun getIcon(itemView: View): Drawable {
        val icon: Drawable
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            icon = itemView.resources.getDrawable(R.drawable.ic_archive_black_24dp, itemView.context.theme)
        } else {
            icon = itemView.resources.getDrawable(R.drawable.ic_archive_black_24dp)
        }
        return icon
    }

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        val evaluator = ArgbEvaluator()
        with(bgRect) {
            left = itemView.right.toFloat() + dX
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }
        with(bgPaint) {


            val startColor = TypedValue()
            val endColor = TypedValue()
            itemView.context.theme.resolveAttribute(R.attr.colorEvaluationStart, startColor, true)
            itemView.context.theme.resolveAttribute(R.attr.colorEvaluationEnd, endColor, true)
            color = evaluator.evaluate((dX / itemView.right) * -1, startColor.data, endColor.data) as Int

            canvas.drawRect(bgRect, bgPaint)
        }
    }
}

interface ItemTouchViewHolder{
    fun onItemSelected()
    fun onItemCleared()
}

class ArchiveItemTouchHelperCallback constructor(
    adapter: ChatAdapter,
    swipeListener: (ChatItem) -> Unit
): ChatItemTouchHelperCallback(adapter, swipeListener) {
    override fun getIcon(itemView: View): Drawable {
        val icon: Drawable
        if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
            icon = itemView.resources.getDrawable(R.drawable.ic_unarchive_black_24dp, itemView.context.theme)
        } else {
            icon = itemView.resources.getDrawable(R.drawable.ic_unarchive_black_24dp)
        }
        return icon
    }
}