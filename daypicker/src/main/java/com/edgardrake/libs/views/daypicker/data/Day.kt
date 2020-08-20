package com.edgardrake.libs.views.daypicker.data

import com.google.gson.annotations.SerializedName
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Locale

/**
 * Created by Rido Ramadan : @EdgarDrake (email: rido.ramadan@gmail.com) on 19-Aug-20
 */
enum class Day constructor(val index: Int) {
    @SerializedName("0") SUNDAY(0),
    @SerializedName("1") MONDAY(1),
    @SerializedName("2") TUESDAY(2),
    @SerializedName("3") WEDNESDAY(3),
    @SerializedName("4") THURSDAY(4),
    @SerializedName("5") FRIDAY(5),
    @SerializedName("6") SATURDAY(6);

    private val calendar: Calendar by lazy {
        Calendar.getInstance(Locale.getDefault())
    }

    @JvmOverloads
    fun getName(
        locale: Locale = Locale.getDefault(),
        format: Format = Format.SHORTHAND
    ): String {
        val date = calendar.apply { set(YEAR_2018, JANUARY, 7 + index) }.time
        return SimpleDateFormat(format.toString(), locale).format(date)
    }

    companion object {
        private const val YEAR_2018 = 2018
        private const val JANUARY = 0
    }

    enum class Format {
        SHORTHAND { override fun toString() = "EEE" },
        FULL { override fun toString() = "EEEE" }
    }
}