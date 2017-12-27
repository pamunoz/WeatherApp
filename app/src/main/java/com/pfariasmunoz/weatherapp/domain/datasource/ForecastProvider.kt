package com.pfariasmunoz.weatherapp.domain.datasource

import com.pfariasmunoz.weatherapp.data.db.ForecastDb
import com.pfariasmunoz.weatherapp.data.server.ForecastServer
import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.domain.model.ForecastList
import com.pfariasmunoz.weatherapp.extensions.firstResult

/**
 * The provider will require a function that receives a zip code and a date, and it should return
 * a weekly forecast from that day.
 *
 * It receives a list of sources, that can be specified through the constructor (for test purposes
 * for instance). It will use a database source and a server source. The order is important, because
 * it will iterate over the sources, and the search will stop when any of the sources return a valid
 * result. The logical order is to search first locally (in the database) and then through the API.
 */
class ForecastProvider(private val sources: List<ForecastDataSource> = ForecastProvider.SOURCES) {
    companion object {
        val DAY_IN_MILLIS = 1000 * 60 * 60 * 24
        val SOURCES by lazy { listOf(ForecastDb(), ForecastServer()) }
    }

    /**
     * The main method of the provider. We use an custom extension function base on the
     * #first function, and it will return the first result that is no null.
     */
    fun requestByZipCode(zipCode: Long, days: Int): ForecastList =  requestToSources {
        val res = it.requestForecastByZipCode(zipCode, todayTimeSpan())
        if (res != null && res.size >= days) res else null
    }
    /**
     * The request is executed and only returns a value if the result is not null and the number
     * of days matches the parameter. Otherwise, the source doesn't have enough up-to-date data to
     * return a successful result.
     */
    fun requestForecast(id: Long): Forecast = requestToSources { it.requestDayForecast(id) }

    /**
     * This function calculate the time in milliseconds for the current day, eliminating the "time"
     * offset, and keeping only the day. Some of the sources (in our case the database) may need it.
     * The server default to today if we don't send more information, so it won't be used there.
     */
    private fun todayTimeSpan() = System.currentTimeMillis() / DAY_IN_MILLIS * DAY_IN_MILLIS

    /**
     * This function returns the requested {@link Forecast} by id.
     */
    private fun <T: Any> requestToSources(f: (ForecastDataSource) -> T?): T
            = sources.firstResult { f(it) }
}