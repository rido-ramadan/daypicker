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
        assertEquals(Day.SUNDAY.getName(localeID, Day.Format.FULL), "Minggu")
        assertEquals(Day.FRIDAY.getName(format = Day.Format.FULL), "Friday")
    }

    @Test
    fun `locale ID and long format should return Indonesian name of the day`() {
        val localeID = Locale("id")
        val full = Day.Format.FULL
        assertEquals(Day.SUNDAY.getName(localeID, full), "Minggu")
        assertEquals(Day.MONDAY.getName(localeID, full), "Senin")
        assertEquals(Day.TUESDAY.getName(localeID, full), "Selasa")
        assertEquals(Day.WEDNESDAY.getName(localeID, full), "Rabu")
        assertEquals(Day.THURSDAY.getName(localeID, full), "Kamis")
        assertEquals(Day.FRIDAY.getName(localeID, full), "Jumat")
        assertEquals(Day.SATURDAY.getName(localeID, full), "Sabtu")
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