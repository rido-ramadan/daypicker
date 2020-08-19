package com.edgardrake.libs.views.daypicker.data

import org.junit.After
import org.junit.Before

import org.junit.Assert.*
import org.junit.Test
import java.util.Locale

class DayTest {

    @Before
    fun setUp() {
    }

    @After
    fun tearDown() {
    }

    @Test
    fun `Day_getName should return name in specified locale and formatting`() {
        val localeID = Locale("id")
        assertEquals(Day.SUNDAY.getName(localeID, "EEEE"), "Minggu")
        assertEquals(Day.FRIDAY.getName(format = "EEEE"), "Friday")
    }

    @Test
    fun `locale ID and long format should return Indonesian name of the day`() {
        val localeID = Locale("id")
        val longName = "EEEE"
        assertEquals(Day.SUNDAY.getName(localeID, longName), "Minggu")
        assertEquals(Day.MONDAY.getName(localeID, longName), "Senin")
        assertEquals(Day.TUESDAY.getName(localeID, longName), "Selasa")
        assertEquals(Day.WEDNESDAY.getName(localeID, longName), "Rabu")
        assertEquals(Day.THURSDAY.getName(localeID, longName), "Kamis")
        assertEquals(Day.FRIDAY.getName(localeID, longName), "Jumat")
        assertEquals(Day.SATURDAY.getName(localeID, longName), "Sabtu")
    }

    @Test
    fun `locale ID and short format should return abbreviated Indonesian name of the day`() {
        val localeID = Locale("id")
        assertEquals(Day.SUNDAY.getName(localeID), "Min")
        assertEquals(Day.MONDAY.getName(localeID), "Sen")
        assertEquals(Day.TUESDAY.getName(localeID), "Sel")
        assertEquals(Day.WEDNESDAY.getName(localeID), "Rab")
        assertEquals(Day.THURSDAY.getName(localeID), "Kam")
        assertEquals(Day.FRIDAY.getName(localeID), "Jum")
        assertEquals(Day.SATURDAY.getName(localeID), "Sab")
    }
}