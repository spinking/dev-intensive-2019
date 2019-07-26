package ru.skillbranch.devintensive.repositories

import android.content.SharedPreferences
import android.graphics.*
import android.graphics.drawable.Drawable
import android.graphics.drawable.ShapeDrawable
import android.graphics.drawable.shapes.OvalShape
import android.graphics.drawable.shapes.RectShape
import android.graphics.drawable.shapes.RoundRectShape
import android.preference.PreferenceManager
import androidx.appcompat.app.AppCompatDelegate
import ru.skillbranch.devintensive.App
import ru.skillbranch.devintensive.R
import ru.skillbranch.devintensive.extensions.spToPixels
import ru.skillbranch.devintensive.models.Profile
import ru.skillbranch.devintensive.utils.Utils

/**
 * Created by BashkatovSM on 23.07.2019
 */
object PreferencesRepository {

    private const val FIRST_NAME = "FIRST_NAME"
    private const val LAST_NAME = "LAST_NAME"
    private const val ABOUT = "ABOUT"
    private const val REPOSITORY = "REPOSITORY"
    private const val RATING = "RATING"
    private const val RESPECT = "RESPECT"
    private const val APP_THEME = "APP_THEME"

    private val prefs: SharedPreferences by lazy {
        val ctx = App.applicationContext()
        PreferenceManager.getDefaultSharedPreferences(ctx)
    }

    fun saveAppTheme(theme: Int) {
        putValue(APP_THEME to theme)
    }

    fun getAppTheme() : Int = prefs.getInt(APP_THEME, AppCompatDelegate.MODE_NIGHT_NO)

    fun getProfile(): Profile = Profile(
        prefs.getString(FIRST_NAME, "")!!,
        prefs.getString(LAST_NAME, "")!!,
        prefs.getString(ABOUT, "")!!,
        prefs.getString(REPOSITORY, "")!!,
        prefs.getInt(RATING, 0),
        prefs.getInt(RESPECT, 0)
    )

    fun saveProfile(profile: Profile) {
        with(profile) {
            putValue(FIRST_NAME to firstName)
            putValue(LAST_NAME to lastName)
            putValue(ABOUT to about)
            putValue(REPOSITORY to repository)
            putValue(RATING to rating)
            putValue(RESPECT to respect)
        }
    }

    private fun putValue(pair: Pair<String, Any>) = with(prefs.edit()) {
        val key = pair.first
        val value = pair.second

        when(value) {
            is String -> putString(key, value)
            is Int -> putInt(key, value)
            is Boolean -> putBoolean(key, value)
            is Long -> putLong(key, value)
            is Float -> putFloat(key, value)
            else -> error("Only primitives types can be stored in Shared Preferences")
        }
        apply()
    }

    fun getInitials(): Pair<String, String> {
        return prefs.getString(FIRST_NAME, "") to prefs.getString(LAST_NAME, "")
    }

    fun getTextInitials(initials: Pair<String, String>): Drawable {
        return textDrawable(Utils.toInitials(initials.first, initials.second)!!)
    }

    private fun textDrawable(initials: String): Drawable {
        return TextDrawable
            .builder()
            .beginConfig()
            .width(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
            .height(App.applicationContext().resources.getDimension(R.dimen.avatar_round_size).toInt())
            .fontSize(48.spToPixels)
            .endConfig()
            .buildRound(initials, R.attr.colorAccent)
    }

    class TextDrawable constructor(builder: Builder) : ShapeDrawable(builder.shape) {

        private val textPaint: Paint
        private val borderPaint: Paint
        private val text: String?
        private val color: Int
        private val shape: RectShape?
        private val height: Int
        private val width: Int
        private val fontSize: Int
        private val radius: Float
        private val borderThickness: Int

        init {

            // shape properties
            shape = builder.shape
            height = builder.height
            width = builder.width
            radius = builder.radius

            // text and color
            text = if (builder.toUpperCase) builder.text!!.toUpperCase() else builder.text
            color = builder.color

            // text paint settings
            fontSize = builder.fontSize
            textPaint = Paint()
            textPaint.color = builder.textColor
            textPaint.isAntiAlias = true
            textPaint.isFakeBoldText = builder.isBold
            textPaint.style = Paint.Style.FILL
            textPaint.typeface = builder.font
            textPaint.textAlign = Paint.Align.CENTER
            textPaint.strokeWidth = builder.borderThickness.toFloat()

            // border paint settings
            borderThickness = builder.borderThickness
            borderPaint = Paint()
            borderPaint.color = getDarkerShade(color)
            borderPaint.style = Paint.Style.STROKE
            borderPaint.strokeWidth = borderThickness.toFloat()

            // drawable paint color
            val paint = paint
            paint.color = color

        }

        private fun getDarkerShade(color: Int): Int {
            return Color.rgb(
                (SHADE_FACTOR * Color.red(color)).toInt(),
                (SHADE_FACTOR * Color.green(color)).toInt(),
                (SHADE_FACTOR * Color.blue(color)).toInt()
            )
        }

        override fun draw(canvas: Canvas) {
            super.draw(canvas)
            val r = bounds


            // draw border
            if (borderThickness > 0) {
                drawBorder(canvas)
            }

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

        private fun drawBorder(canvas: Canvas) {
            val rect = RectF(bounds)
            rect.inset((borderThickness / 2).toFloat(), (borderThickness / 2).toFloat())

            if (shape is OvalShape) {
                canvas.drawOval(rect, borderPaint)
            } else if (shape is RoundRectShape) {
                canvas.drawRoundRect(rect, radius, radius, borderPaint)
            } else {
                canvas.drawRect(rect, borderPaint)
            }
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

        class Builder constructor() : IConfigBuilder, IShapeBuilder, IBuilder {

            var text: String? = null

            var color: Int = 0

            var borderThickness: Int = 0

            var width: Int = 0

            var height: Int = 0

            var font: Typeface? = null

            var shape: RectShape? = null

            var textColor: Int = 0

            var fontSize: Int = 0

            var isBold: Boolean = false

            var toUpperCase: Boolean = false

            var radius: Float = 0.toFloat()

            init {
                text = ""
                color = Color.GRAY
                textColor = Color.WHITE
                borderThickness = 0
                width = -1
                height = -1
                shape = RectShape()
                font = Typeface.create("sans-serif-light", Typeface.NORMAL)
                fontSize = -1
                isBold = false
                toUpperCase = false
            }

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

            override fun withBorder(thickness: Int): IConfigBuilder {
                this.borderThickness = thickness
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

            fun withBorder(thickness: Int): IConfigBuilder

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
            private val SHADE_FACTOR = 0.9f

            fun builder(): IShapeBuilder {
                return Builder()
            }
        }
    }
}