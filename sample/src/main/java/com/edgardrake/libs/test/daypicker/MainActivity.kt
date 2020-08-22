package com.edgardrake.libs.test.daypicker

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.edgardrake.flameseeker.core.utils.showDebug
import com.edgardrake.flameseeker.core.utils.toast
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
            showDebug(this, "Selected Days", 10, 90,
                weeklyDayPicker.selectedDaysFullName.map { "●" to it }
            )
        }

        modeButton.setOnClickListener {
            weeklyDayPicker.mode = if (weeklyDayPicker.mode == Mode.SHOW_ALL)
                Mode.FILTERED else Mode.SHOW_ALL
        }

        addSundayButton.setOnClickListener {
            weeklyDayPicker += Day.SUNDAY
        }

        disableButton.setOnClickListener {
            weeklyDayPicker.isEnabled = !weeklyDayPicker.isEnabled
        }

        toggleClickableButton.setOnClickListener {
            weeklyDayPicker.isClickable = !weeklyDayPicker.isClickable
        }
    }
}