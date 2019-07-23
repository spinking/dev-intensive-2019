package studio.eyesthetics.devintensive.ui.custom

import android.content.Context
import android.graphics.Bitmap
import android.graphics.Color
import android.graphics.RectF
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.widget.ImageView
import androidx.annotation.ColorInt
import ru.skillbranch.devintensive.R

/**
 * Created by BashkatovSM on 23.07.2019
 */
class CircleImageView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : ImageView(context, attrs, defStyleAttr) {
    companion object {
        private const val DEFAULT_BORDER_COLOR = Color.WHITE
        private const val DEFAULT_BORDER_WIDTH = 2
    }

    private var mBorderColor = DEFAULT_BORDER_COLOR
    private var mBorderWidth = DEFAULT_BORDER_WIDTH

    private val mDrawableRect = RectF()
    private val mBorderRect = RectF()

    private var mBitmap: Bitmap? = null
    private var mBitmapWidth: Int = 0
    private var mBitmapHeight: Int = 0

    private var mDrawableRadius: Float = 0.toFloat()
    private var mBorderRadius: Float = 0.toFloat()

    var borderColor: Int
        get() = mBorderColor
        set(@ColorInt borderColor) {
            if(borderColor == mBorderColor) return
            mBorderColor = borderColor

            invalidate()
        }

    var borderWidth: Int
        get() = mBorderWidth
        set(borderWidth) {
            if(borderWidth == mBorderWidth) return
            mBorderWidth = borderWidth
            setup()
        }



    init {
        if(attrs != null) {
            val a = context.obtainStyledAttributes(attrs, R.styleable.CircleImageView)
            borderColor = a.getColor(R.styleable.CircleImageView_borderColor, DEFAULT_BORDER_COLOR)
            borderWidth = a.getInt(R.styleable.CircleImageView_borderWidth, DEFAULT_BORDER_WIDTH)
            a.recycle()
        }
    }


}