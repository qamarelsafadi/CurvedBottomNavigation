package com.qamar.curvedbottomnavigation

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
import android.util.Log
import android.util.TypedValue
import android.view.View
import androidx.annotation.ColorInt
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.vectordrawable.graphics.drawable.VectorDrawableCompat


fun View.gone() {
    visibility = View.GONE
}
fun Any.log(tag: String = "") {
    if (tag.equals("")) {
        Log.d("TAG_QMR", this.toString())
    } else {
        Log.d("TAG_QMR $tag", this.toString())

    }
}