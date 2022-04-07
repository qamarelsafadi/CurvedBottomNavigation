package com.qamar.curvedbottomnaviagtion

import android.content.Context
import android.content.res.ColorStateList
import android.content.res.Resources
import android.content.res.TypedArray
import android.graphics.PorterDuff
import android.graphics.Typeface
import android.graphics.drawable.Drawable
import android.os.Build
import android.os.Handler
import android.os.Looper
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat

val Int.toPx: Int get() = (this * Resources.getSystem().displayMetrics.density).toInt()

fun Int.dp(context: Context): Int = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
).toInt()

fun Float.dp(context: Context): Float = TypedValue.applyDimension(
    TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
)

@RequiresApi(Build.VERSION_CODES.O)
fun Context.findFont(font: Int): Typeface {
    return this.resources.getFont(font)
}

fun View.visible() {
    visibility = View.VISIBLE
}

fun View.gone() {
    visibility = View.GONE
}

internal fun tintColor(
    @ColorInt color: Int
) = ColorStateList.valueOf(color)

fun <T> View?.updateLayoutParams(onLayoutChange: (params: T) -> Unit) {
    if (this == null)
        return
    try {
        @Suppress("UNCHECKED_CAST")
        onLayoutChange(layoutParams as T)
        layoutParams = layoutParams
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun <T> (() -> T).withDelay(delay: Long = 250L) {
    Handler(Looper.getMainLooper()).postDelayed({ this.invoke() }, delay)
}

inline fun <reified T : Enum<T>> TypedArray.getEnum(index: Int, default: T) =
    getInt(index, -1).let {
        if (it >= 0) enumValues<T>()[it] else default
    }