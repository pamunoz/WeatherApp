package com.pfariasmunoz.weatherapp

import java.text.DateFormat
import com.pfariasmunoz.weatherapp.extensions.toDateString
import junit.framework.Assert.assertEquals
import org.junit.Test

class ExtensionsTest {

    @Test fun testLongToDateString() {
        assertEquals("19-10-2015", 1445275635000L.toDateString())
    }

    @Test fun testDateStringFullFormat() {
        assertEquals("lunes 19 de octubre de 2015", 1445275635000L.toDateString(DateFormat.FULL))
    }
}

