package ru.skillbranch.devintensive.ui.adapters

import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.TypedValue
import android.view.View
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.models.data.ChatItem
import ru.skillbranch.devintensive.ui.adapters.ChatAdapter

/**
 * Created by BashkatovSM on 26.08.2019
 */
class ChatItemTouchHelperCallback(
    val activity: String,
    val adapter: ChatAdapter,
    val swipeListener : (ChatItem) -> Unit
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
        val icon: Drawable

        when(activity) {
            "archive activity" -> if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                icon = itemView.resources.getDrawable(R.drawable.ic_unarchive_black_24dp, itemView.context.theme)
            } else {
                icon = itemView.resources.getDrawable(R.drawable.ic_unarchive_black_24dp)
            }
            else -> if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                icon = itemView.resources.getDrawable(R.drawable.ic_archive_black_24dp, itemView.context.theme)
            } else {
                icon = itemView.resources.getDrawable(R.drawable.ic_archive_black_24dp)
            }

        }


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

    private fun drawBackground(canvas: Canvas, itemView: View, dX: Float) {
        with(bgRect) {
            left = itemView.left.toFloat()
            top = itemView.top.toFloat()
            right = itemView.right.toFloat()
            bottom = itemView.bottom.toFloat()
        }
        with(bgPaint) {
            color = ContextCompat.getColor(App.applicationContext(), if(AppCompatDelegate.getDefaultNightMode() == AppCompatDelegate.MODE_NIGHT_YES)R.color.color_accent_night else R.color.color_primary_dark)

            /*if(android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {

                //color = itemView.resources.getColor(R.color.color_primary_dark, itemView.context.theme)
            } else {
                color = itemView.resources.getColor(R.color.color_primary_dark)
            }*/
            //ДЗ при свайпе прямоугольник должен менять цвет последовательно через промежуточные значения

            canvas.drawRect(bgRect, bgPaint)
        }
    }
}

enum class TypeOfActivity {
    ARCHIVE_ACTIVITY,
    MAIN_ACTIVITY
}

interface ItemTouchViewHolder{
    fun onItemSelected()
    fun onItemCleared()
}