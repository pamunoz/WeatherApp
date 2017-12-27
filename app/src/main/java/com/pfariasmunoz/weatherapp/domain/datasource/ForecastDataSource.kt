package com.pfariasmunoz.weatherapp.domain.datasource

import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.domain.model.ForecastList

/**
 * This interface that abstracts the source of the data.
 */
interface ForecastDataSource {
    fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList?
    fun requestDayForecast(id: Long): Forecast?
}
