package com.pfariasmunoz.weatherapp.data.server

import com.pfariasmunoz.weatherapp.data.db.ForecastDb
import com.pfariasmunoz.weatherapp.domain.datasource.ForecastDataSource
import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.domain.model.ForecastList


class ForecastServer(private val dataMapper: ServerDataMapper = ServerDataMapper(),
                     private val forecastDb: ForecastDb = ForecastDb()): ForecastDataSource {
    /**
     * The server will never be used, because the info will be always cached in the database. We
     * could implement it to defend our code from uncommon edge cases, but we're not doing it
     * in this case, so it will just throw an exception if it's called.
     */
    override fun requestDayForecast(id: Long): Forecast? = throw UnsupportedOperationException()

    /**
     * This function makes the request to the server, converts the result to domain objects and
     * saves them into the database. It finally returns the values from the database, because we
     * need the row ids that were self-generated by the insert query.
     */
    override fun requestForecastByZipCode(zipCode: Long, date: Long): ForecastList? {
        val result = ForecastByZipCodeRequest(zipCode).execute()
        val converted = dataMapper.convertToDomain(zipCode, result)
        forecastDb.saveForecast(converted)
        return forecastDb.requestForecastByZipCode(zipCode, date)
    }
}