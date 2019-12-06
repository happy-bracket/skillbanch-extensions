package ru.substancial.extensions

import android.content.Context
import android.graphics.Typeface
import android.os.Bundle
import android.text.SpannableString
import android.text.SpannableStringBuilder
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModel
import kotlinx.android.synthetic.main.activity_main.*
import ru.substancial.extensions.utils.setPaddingOptionally
import ru.substancial.extensions.utils.span
import ru.substancial.extensions.utils.view

class MainActivity : AppCompatActivity() {
    
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        some_text_view.text =
            SpannableStringBuilder()
                .append(
                    "This string will be red",
                    ForegroundColorSpan(ContextCompat.getColor(this, R.color.red)),
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                ).append(
                    " "
                ).append(
                    "This string will be sad :^("
                ).append(
                    " "
                ).append(
                    "This string will be italic",
                    StyleSpan(Typeface.ITALIC),
                    SpannableString.SPAN_INCLUSIVE_EXCLUSIVE
                )

        some_text_view.text = span {
            color("This string will be red!", R.color.red)
            space()
            plain("This string will be sad :^(")
            space()
            italic("This string will be italic")
            string("This string will be blue and bold!") {
                color(R.color.blue)
                bold()
            }
        }

        some_text_view.setPaddingOptionally(
            top = resources.getDimensionPixelOffset(R.dimen.dp16),
            left = resources.getDimensionPixelOffset(R.dimen.dp16)
        )

        val sp = getSharedPreferences("sss", Context.MODE_PRIVATE)

        sp.edit()
            .putLong("some_pref", 3L)
            .apply()

        sp.edit {
            putLong("some_pref", 3L)
        }

        supportFragmentManager.beginTransaction()
            .add(Fragment(), "TAG")
            .commit()

        supportFragmentManager.beginTransaction {
            add(Fragment(), "TAG")
        }

        view().isVisible = true
        view().isVisible = false

    }
}

fun FragmentManager.beginTransaction(f: FragmentTransaction.() -> Unit) {
    val tr = beginTransaction()
    tr.f()
    tr.commit()
}