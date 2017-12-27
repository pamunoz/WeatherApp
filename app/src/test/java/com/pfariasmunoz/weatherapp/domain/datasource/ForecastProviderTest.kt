package com.pfariasmunoz.weatherapp.domain.datasource

import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.domain.model.ForecastList
import org.junit.Assert.*
import org.junit.Test
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`

class ForecastProviderTest {

    @Test
    fun testDataSourceReturnsValue() {
        val ds = mock(ForecastDataSource::class.java)
        `when`(ds.requestDayForecast(0))
                .then { Forecast(0, 0, "desc", 20, 0, "url") }

        val provider = ForecastProvider(listOf(ds))
        assertNotNull(provider.requestForecast(0))
    }

    @Test fun emptyDatabaseReturnsServerValue() {
        val db = mock(ForecastDataSource::class.java)

        val server = mock(ForecastDataSource::class.java)
        // When a data source return Null
        `when`(server.requestForecastByZipCode(
                any(Long::class.java), any(Long::class.java)))
                // it will iterate over the next source to get a result
                .then {
                    ForecastList(0, "city", "country", listOf())
                }

        val provider = ForecastProvider(listOf(db, server))
        assertNotNull(provider.requestByZipCode(0, 0))
    }

}