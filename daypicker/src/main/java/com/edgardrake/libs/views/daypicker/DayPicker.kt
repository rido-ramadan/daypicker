package com.edgardrake.libs.views.daypicker

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.LinearLayout
import com.edgardrake.libs.views.daypicker.data.Day
import kotlinx.android.synthetic.main.day_picker_view.view.*
import java.util.Locale

/**
 * Created by Rido Ramadan : @EdgarDrake (email: rido.ramadan@gmail.com) on 19-Aug-20
 */
class DayPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), Checkable {

    private var check: Boolean = false
    private var locale: Locale = Locale.getDefault()

    init {
        LayoutInflater.from(context).inflate(R.layout.day_picker_view, this)
        context.theme.obtainStyledAttributes(attrs, R.styleable.DayPicker, 0, 0).apply {
            try {
                isChecked = getBoolean(R.styleable.DayPicker_checked, false)
                getString(R.styleable.DayPicker_locale)?.let { resLocale ->
                    locale = Locale(resLocale)
                }
                day = Day.values()[getInt(R.styleable.DayPicker_day, 0)]
            } finally {
                recycle()
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        dayPickerButton.setOnClickListener { toggle() }
    }

    var day: Day? = null
        set(value) {
            field = value
            value?.getName(locale)?.let {
                dayPickerButton.text = it[0].toString()
                dayPickerCaption.text = it
            }
        }

    var onToggleListener: ((Boolean) -> Unit)? = null

    override fun setChecked(checked: Boolean) {
        check = checked
        dayPickerButton.isChecked = checked
        dayPickerCaption.isChecked = checked
    }

    override fun isChecked() = check

    override fun toggle() {
        isChecked = !isChecked
        onToggleListener?.invoke(isChecked)
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        dayPickerButton.isEnabled = enabled
        dayPickerCaption.isEnabled = enabled
    }
}
