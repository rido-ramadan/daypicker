package com.edgardrake.libs.test.daypicker

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.edgardrake.libs.views.daypicker.WeeklyDayPicker.Mode
import com.edgardrake.libs.views.daypicker.data.Day
import kotlinx.android.synthetic.main.activity_main.*
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val locale = Locale("id")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        
        weeklyDayPicker.apply {
            onDayToggled = { day, checked ->
                toast("$day ${if (checked) "enabled" else "disabled"}")
            }
        }

        changeDaysButton.setOnClickListener {
            weeklyDayPicker.selectedDays = listOf(Day.MONDAY, Day.THURSDAY)
        }

        selectedDaysButton.setOnClickListener {
            toast(weeklyDayPicker.selectedDays.joinToString(", ") {
                it.getName(locale, Day.Format.FULL)
            })
        }

        modeButton.setOnClickListener {
            weeklyDayPicker.mode = if (weeklyDayPicker.mode == Mode.SHOW_ALL)
                Mode.FILTERED else Mode.SHOW_ALL
        }

        addSundayButton.setOnClickListener {
            weeklyDayPicker += Day.SUNDAY
        }
    }

    private fun toast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}