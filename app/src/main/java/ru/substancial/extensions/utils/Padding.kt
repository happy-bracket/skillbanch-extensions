package ru.substancial.extensions.utils

import android.view.View

fun View.setPaddingOptionally(
    left: Int = paddingLeft,
    right: Int = paddingRight,
    top: Int = paddingTop,
    bottom: Int = paddingBottom
) {
    setPadding(left, top, right, bottom)
}

fun sss() {

    val view: View = view()

    view.setPadding(16, 16, view.paddingRight, view.paddingBottom)
    view.setPaddingOptionally(left = 16, top = 16)

}

fun view(): View = TODO()