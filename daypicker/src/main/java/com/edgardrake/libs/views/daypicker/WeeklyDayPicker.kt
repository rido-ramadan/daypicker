package com.edgardrake.libs.views.daypicker

import android.content.Context
import android.content.res.ColorStateList
import android.util.AttributeSet
import android.widget.LinearLayout
import androidx.annotation.DrawableRes
import com.edgardrake.flameseeker.core.utils.dp
import com.edgardrake.flameseeker.core.utils.visible
import com.edgardrake.libs.views.daypicker.data.Day
import java.util.Locale

/**
 * Created by Rido Ramadan : rido-ramadan (email: rido.ramadan@gmail.com) on 20-Aug-20
 */
class WeeklyDayPicker @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null
) : LinearLayout(context, attrs) {

    private var dayPickers: List<DayPicker>
    private var locale: Locale = Locale.getDefault()
    private var format: Day.Format = Day.Format.SHORTHAND
    private var renderMode: Mode = Mode.SHOW_ALL
    private var activeDaysFlag: Int = 0
    private var radius: Int = 32.dp
    private var clickable: Boolean = true

    @DrawableRes
    private var iconBackground: Int
    private var captionTextColor: ColorStateList?

    init {
        orientation = HORIZONTAL
        setPadding(2.dp, 2.dp, 2.dp, 2.dp)

        // Read attributes
        context.theme.obtainStyledAttributes(attrs, R.styleable.WeeklyDayPicker, 0, 0).apply {
            try {
                // Locale
                getString(R.styleable.WeeklyDayPicker_locale)?.let { resLocale ->
                    locale = Locale(resLocale)
                }

                // Date Formatting
                format = if (getInt(R.styleable.WeeklyDayPicker_dayFormat, 0) == 0)
                    Day.Format.SHORTHAND else Day.Format.FULL

                // DayPicker related rendering
                radius = getDimensionPixelSize(R.styleable.WeeklyDayPicker_radius, 32.dp)
                iconBackground = getResourceId(R.styleable.WeeklyDayPicker_iconBackground,
                    R.drawable.selector_circle_blue)
                captionTextColor = getColorStateList(R.styleable.DayPicker_captionColor)

                // Active (selected) days
                activeDaysFlag = getInt(R.styleable.WeeklyDayPicker_days, NONE)

                // Render Mode
                renderMode = Mode.values()[getInt(R.styleable.WeeklyDayPicker_mode, 0)]

            } catch (e: Exception) {
                throw e
            } finally {
                recycle()
            }
        }

        dayPickers = Day.values().map { day ->
            DayPicker(context).also {
                it.isClickable = clickable
                it.isEnabled = isEnabled
                it.iconBackground = iconBackground
                captionTextColor?.let { color -> it.setCaptionColor(color) }
                it.format = format
                it.locale = locale
                it.radius = radius
                it.day = day
                it.onToggleListener = { checked ->
                    onDayToggled?.invoke(day.getName(locale, Day.Format.FULL), checked)
                }

                this@WeeklyDayPicker.addView(it)
            }
        }
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        setActiveDays(activeDaysFlag)
        mode = renderMode // Show all or filter, happens after XML inflation is done
    }

    /**
     * Lambda function to be invoked when 1 of the [DayPicker] is toggled
     */
    var onDayToggled: ((String, Boolean) -> Unit)? = null

    /**
     * Helper method to perform set active days using Layout XML inflation.
     */
    private fun setActiveDays(flag: Int) {
        if (flag == ALL) {
            for (dayPicker in dayPickers) {
                dayPicker.isChecked = true
            }
            return
        } else {
            if (flag and SUNDAY == SUNDAY) {
                dayPickers[0].isChecked = true
            }
            if (flag and MONDAY == MONDAY) {
                dayPickers[1].isChecked = true
            }
            if (flag and TUESDAY == TUESDAY) {
                dayPickers[2].isChecked = true
            }
            if (flag and WEDNESDAY == WEDNESDAY) {
                dayPickers[3].isChecked = true
            }
            if (flag and THURSDAY == THURSDAY) {
                dayPickers[4].isChecked = true
            }
            if (flag and FRIDAY == FRIDAY) {
                dayPickers[5].isChecked = true
            }
            if (flag and SATURDAY == SATURDAY) {
                dayPickers[6].isChecked = true
            }
        }

        // Show/hide day picker depends on render mode
        render(mode)
    }

    /**
     * Contains the selected day(s) of the WeeklyDayPicker, sorted by [Day.index],
     * with [Day.SUNDAY] as the lowest index.
     */
    var selectedDays: Collection<Day>
        get() = dayPickers.mapNotNull { it.activeDay }.sortedBy { it.index }
        set(value) {
            dayPickers.forEach { dayPicker ->
                dayPicker.isChecked = dayPicker.day in value
            }
            render(mode)
        }

    val selectedDaysName: Collection<String>
        get() = dayPickers.mapNotNull { it.activeDayName }

    val selectedDaysFullName: Collection<String>
        get() = dayPickers.mapNotNull { it.activeDayFullName }

    /**
     * Add [day] to [selectedDays]
     */
    fun addSelectedDay(day: Day) {
        if (day !in selectedDays) {
            dayPickers[day.index].apply {
                isChecked = true
                visible = true
            }
        }
    }

    /**
     * Remove [day] from [selectedDays]
     */
    fun removeSelectedDay(day: Day) {
        if (day in selectedDays) {
            dayPickers[day.index].apply {
                isChecked = false
                visible = mode != Mode.FILTERED
            }
        }
    }

    /**
     * @see [addSelectedDay]
     */
    operator fun plusAssign(day: Day) {
        addSelectedDay(day)
    }

    /**
     * @see [removeSelectedDay]
     */
    operator fun minusAssign(day: Day) {
        removeSelectedDay(day)
    }

    /**
     * Change the mode within the View.
     * - [Mode.SHOW_ALL] (Default) will show all days, even the non selected days.
     * - [Mode.FILTERED] will only show [selectedDays] only
     */
    var mode: Mode
        get() = renderMode
        set(value) {
            renderMode = value
            render(value)
        }

    /**
     * Helper function for [mode] with goal to help hide/show unselected [DayPicker]
     */
    private fun render(mode: Mode) {
        if (mode == Mode.FILTERED) {
            dayPickers.forEach {
                it.visible = it.activeDay != null
                it.isEnabled = isEnabled
                it.isClickable = isClickable
            }
        } else {
            dayPickers.forEach {
                it.visible = true
                it.isEnabled = isEnabled
                it.isClickable = isClickable
            }
        }
    }

    override fun setEnabled(enabled: Boolean) {
        super.setEnabled(enabled)
        dayPickers.forEach { it.isEnabled = enabled }
    }

    override fun isClickable() = clickable

    override fun setClickable(clickable: Boolean) {
        this.clickable = clickable
        dayPickers.forEach { it.isClickable = clickable }
    }

    companion object {
        private const val NONE = 0x0000000
        private const val SUNDAY = 0x1000000
        private const val MONDAY = 0x0100000
        private const val TUESDAY = 0x0010000
        private const val WEDNESDAY = 0x0001000
        private const val THURSDAY = 0x0000100
        private const val FRIDAY = 0x0000010
        private const val SATURDAY = 0x0000001
        private const val ALL = 0x1111111
    }

    enum class Mode { SHOW_ALL, FILTERED }
}
