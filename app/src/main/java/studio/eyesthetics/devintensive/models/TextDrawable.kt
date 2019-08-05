package ru.skillbranch.devintensive.models

import android.graphics.*
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape

class TextDrawable constructor(builder: Builder) : ShapeDrawable(builder.shape) {

    private val textPaint: Paint
    private val text: String?
    private val color: Int
    private val shape: RectShape?
    private val height: Int
    private val width: Int
    private val fontSize: Int
    private val radius: Float

    init {
        // Shape Properties
        shape = builder.shape
        height = builder.height
        width = builder.width
        radius = builder.radius

        // text and color
        text = if (builder.toUpperCase) builder.text!!.toUpperCase() else builder.text
        color = builder.color

        // text paint settings
        fontSize = builder.fontSize
        textPaint = Paint().apply {
            color = builder.textColor
            isAntiAlias = true
            isFakeBoldText = builder.isBold
            style = Paint.Style.FILL
            typeface = builder.font
            textAlign = Paint.Align.CENTER
        }
        // drawable paint color
        val paint = paint
        paint.color = color

    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val r = bounds

        val count = canvas.save()
        canvas.translate(r.left.toFloat(), r.top.toFloat())

        // draw text
        val width = if (this.width < 0) r.width() else this.width
        val height = if (this.height < 0) r.height() else this.height
        val fontSize = if (this.fontSize < 0) Math.min(width, height) / 2 else this.fontSize
        textPaint.textSize = fontSize.toFloat()
        canvas.drawText(
            text!!,
            (width / 2).toFloat(),
            height / 2 - (textPaint.descent() + textPaint.ascent()) / 2,
            textPaint
        )
        canvas.restoreToCount(count)
    }

    override fun setAlpha(alpha: Int) {
        textPaint.alpha = alpha
    }

    override fun setColorFilter(cf: ColorFilter?) {
        textPaint.colorFilter = cf
    }

    override fun getOpacity(): Int {
        return PixelFormat.TRANSLUCENT
    }

    override fun getIntrinsicWidth(): Int {
        return width
    }

    override fun getIntrinsicHeight(): Int {
        return height
    }

    class Builder(
        var text: String? = "",
        var color: Int = Color.GRAY,
        var width: Int = -1,
        var height: Int = -1,
        var font: Typeface? = Typeface.create("sans-serif-light", Typeface.NORMAL),
        var shape: RectShape? = RectShape(),
        var textColor: Int = Color.WHITE,
        var fontSize: Int = -1,
        var isBold: Boolean = false,
        var toUpperCase: Boolean = false,
        var radius: Float = 0.toFloat()
    ) : IConfigBuilder, IShapeBuilder, IBuilder {

        override fun width(width: Int): IConfigBuilder {
            this.width = width
            return this
        }

        override fun height(height: Int): IConfigBuilder {
            this.height = height
            return this
        }

        override fun textColor(color: Int): IConfigBuilder {
            this.textColor = color
            return this
        }

        override fun useFont(font: Typeface): IConfigBuilder {
            this.font = font
            return this
        }

        override fun fontSize(size: Int): IConfigBuilder {
            this.fontSize = size
            return this
        }

        override fun bold(): IConfigBuilder {
            this.isBold = true
            return this
        }

        override fun toUpperCase(): IConfigBuilder {
            this.toUpperCase = true
            return this
        }

        override fun beginConfig(): IConfigBuilder {
            return this
        }

        override fun endConfig(): IShapeBuilder {
            return this
        }

        override fun rect(): IBuilder {
            this.shape = RectShape()
            return this
        }

        override fun round(): IBuilder {
            this.shape = OvalShape()
            return this
        }

        override fun roundRect(radius: Int): IBuilder {
            this.radius = radius.toFloat()
            val radii = floatArrayOf(
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat(),
                radius.toFloat()
            )
            this.shape = RoundRectShape(radii, null, null)
            return this
        }

        override fun buildRect(text: String, color: Int): TextDrawable {
            rect()
            return build(text, color)
        }

        override fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable {
            roundRect(radius)
            return build(text, color)
        }

        override fun buildRound(text: String, color: Int): TextDrawable {
            round()
            return build(text, color)
        }

        override fun build(text: String, color: Int): TextDrawable {
            this.color = color
            this.text = text
            return TextDrawable(this)
        }
    }

    interface IConfigBuilder {
        fun width(width: Int): IConfigBuilder

        fun height(height: Int): IConfigBuilder

        fun textColor(color: Int): IConfigBuilder

        fun useFont(font: Typeface): IConfigBuilder

        fun fontSize(size: Int): IConfigBuilder

        fun bold(): IConfigBuilder

        fun toUpperCase(): IConfigBuilder

        fun endConfig(): IShapeBuilder
    }

    interface IBuilder {

        fun build(text: String, color: Int): TextDrawable
    }

    interface IShapeBuilder {

        fun beginConfig(): IConfigBuilder

        fun rect(): IBuilder

        fun round(): IBuilder

        fun roundRect(radius: Int): IBuilder

        fun buildRect(text: String, color: Int): TextDrawable

        fun buildRoundRect(text: String, color: Int, radius: Int): TextDrawable

        fun buildRound(text: String, color: Int): TextDrawable
    }

    companion object {
        fun builder(): IShapeBuilder {
            return Builder()
        }
    }
}