package ru.substancial.extensions.utils

import android.content.Context
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import android.text.style.URLSpan
import androidx.annotation.ColorRes
import androidx.annotation.StringRes
import androidx.core.content.ContextCompat
import java.util.*

abstract class SpannableBuilder(private val ctx: Context) {

    protected val builder = SpannableStringBuilder()

    fun string(@StringRes string: Int, style: NestedSpannableBuilder.() -> Unit) {
        string(ctx.getString(string), style)
    }

    fun string(rawString: String?, style: NestedSpannableBuilder.() -> Unit) {
        val string = rawString ?: ""
        val ex = NestedSpanExtractor()
        ex.style()
        val start = builder.length
        builder.append(string)
        val end = builder.length
        val ap = { span: Any -> builder.setSpan(span, start, end, SpannableString.SPAN_INCLUSIVE_EXCLUSIVE) }
        ex.spans().forEach(ap)
    }

    fun plain(@StringRes string: Int) {
        builder.append(ctx.getString(string))
    }

    fun plain(string: String?) {
        builder.append(string ?: "")
    }

    fun space() {
        builder.append(" ")
    }

    fun nextLine() {
        builder.append("\n")
    }

    fun color(@StringRes string: Int, @ColorRes color: Int) {
        color(ctx.getString(string), color)
    }

    fun color(string: String?, @ColorRes color: Int) {
        builder.append(
            string ?: "",
            ForegroundColorSpan(ContextCompat.getColor(ctx, color)),
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    fun bold(@StringRes string: Int) {
        bold(ctx.getString(string))
    }

    fun bold(string: String?) {
        builder.append(
            string ?: "",
            StyleSpan(Typeface.BOLD),
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    fun italic(string: String?) {
        builder.append(
            string ?: "",
            StyleSpan(Typeface.ITALIC),
            SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
        )
    }

    open inner class NestedSpannableBuilder {

        protected val spans: LinkedList<Any> = LinkedList()

        fun color(@ColorRes color: Int) {
            spans += ForegroundColorSpan(ContextCompat.getColor(ctx, color))
        }

        fun link(@StringRes link: Int) {
            spans += URLSpan(ctx.getString(link))
        }

        fun bold() {
            spans += StyleSpan(Typeface.BOLD)
        }
    }

    inner class NestedSpanExtractor : NestedSpannableBuilder() {
        fun spans() = spans
    }

}

private class SpannableEmitter(ctx: Context) : SpannableBuilder(ctx) {

    fun emit(): Spannable = builder

}

fun Context.span(builderF: SpannableBuilder.() -> Unit): Spannable {
    val builder = SpannableEmitter(this)
    builder.builderF()
    return builder.emit()
}