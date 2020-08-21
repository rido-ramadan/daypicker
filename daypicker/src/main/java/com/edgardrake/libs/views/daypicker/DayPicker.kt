package com.edgardrake.libs.views.daypicker

import android.content.Context
import android.content.res.ColorStateList
import android.graphics.Typeface
import android.util.AttributeSet
import android.view.LayoutInflater
import android.widget.Checkable
import android.widget.LinearLayout
import androidx.annotation.ColorInt
import androidx.annotation.DrawableRes
import com.edgardrake.flameseeker.core.utils.dp
import com.edgardrake.libs.views.daypicker.data.Day
import com.edgardrake.libs.views.daypicker.data.Day.Format
import kotlinx.android.synthetic.main.day_picker_view.view.*
import java.util.Locale

/**
 * Created by Rido Ramadan : @EdgarDrake (email: rido.ramadan@gmail.com) on 19-Aug-20
 */
open class DayPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs), Checkable {

    private var check: Boolean = false
    var locale: Locale = Locale.getDefault()
    var format: Format = Format.SHORTHAND

    init {
        LayoutInflater.from(context).inflate(R.layout.day_picker_view, this)
        context.theme.obtainStyledAttributes(attrs, R.styleable.DayPicker, 0, 0).apply {
            try {
                isChecked = getBoolean(R.styleable.DayPicker_checked, false)
                getString(R.styleable.DayPicker_locale)?.let { resLocale ->
                    locale = Locale(resLocale)
                }
                format = if (getInt(R.styleable.DayPicker_dayFormat, 0) == 0)
                    Format.SHORTHAND else Format.FULL

                iconBackground = getResourceId(R.styleable.DayPicker_iconBackground,
                    R.drawable.selector_circle_blue)

                getColorStateList(R.styleable.DayPicker_captionColor)?.let { color ->
                    dayPickerCaption.setTextColor(color)
                }

                radius = getDimensionPixelSize(R.styleable.DayPicker_radius, 32.dp)

                // Day must be last to handle, since it is affected by properties above
                day = Day.values()[getInt(R.styleable.DayPicker_day, 0)]
            } finally {
                recycle()
            }
        }

        dayPickerButton.setOnClickListener { toggle() }
    }

    /**
     * Handle the stored for this day picker. Contains null if not properly initialized.
     * When [Day] is set properly, it will show the icon and the name of the [Day].
     */
    var day: Day? = null
        set(value) {
            field = value
            value?.getName(locale, format)?.let {
                dayPickerButton.text = it[0].toString()
                dayPickerCaption.text = it
            }
        }

    /**
     * Localized & formatted [Day.name]
     */
    val dayName: String? get() = day?.getName(locale, format)

    /**
     * Return currently active day, otherwise null, since it is not selected.
     */
    val activeDay: Day? get() = day?.takeIf { isChecked }

    /**
     * Localized & formatted [Day.name]
     */
    val activeDayName: String? get() = activeDay?.getName(locale, format)

    /**
     * Localized full [Day.name]
     */
    val activeDayFullName: String? get() = activeDay?.getName(locale, Format.FULL)

    var onToggleListener: ((Boolean) -> Unit)? = null

    override fun setChecked(checked: Boolean) {
        check = checked
        dayPickerButton.apply {
            isChecked = checked
            setTypeface(null, if (isChecked) Typeface.BOLD else Typeface.NORMAL)
        }
        dayPickerCaption.apply {
            isChecked = checked
            setTypeface(null, if (isChecked) Typeface.BOLD else Typeface.NORMAL)
        }
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

    override fun setClickable(clickable: Boolean) {
        super.setClickable(clickable)
        dayPickerButton.isClickable = clickable
    }

    @DrawableRes
    var iconBackground: Int = R.drawable.selector_circle_blue
        set(value) = dayPickerButton.setBackgroundResource(value)

    fun setCaptionColor(color: ColorStateList) {
        dayPickerCaption.setTextColor(color)
    }

    fun setCaptionColor(@ColorInt color: Int) {
        dayPickerCaption.setTextColor(color)
    }

    var radius: Int
        set(value) {
            field = value
            dayPickerButton.apply {
                width = value
                height = value
            }
        }
}
