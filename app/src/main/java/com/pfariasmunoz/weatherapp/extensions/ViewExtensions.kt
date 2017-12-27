package com.pfariasmunoz.weatherapp.extensions

import android.content.Context
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

operator fun ViewGroup.get(position: Int): View = getChildAt(position)

val View.ctx: Context
    get() = context

var TextView.textColor: Int
    get() = currentTextColor
    set(v) = setTextColor(v)

/**
 * This coulple of extension functions which animate the views in and out of the screen.
 * They check if the animation hasn't been previously performed. That way it prevents
 * the view from being animated every time the scroll varies.
 */
fun View.slideExit() {
    if (translationX == 0f) animate().translationY(-height.toFloat())
}

fun View.slideEnter() {
    if (translationY < 0f) animate().translationX(0f)
}