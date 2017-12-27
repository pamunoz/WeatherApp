package com.pfariasmunoz.weatherapp.domain.commands

import com.pfariasmunoz.weatherapp.domain.datasource.ForecastProvider
import com.pfariasmunoz.weatherapp.domain.model.ForecastList

class RequestForecastCommand(
        private val zipCode: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()):
        Command<ForecastList> {
    companion object {
        val DAYS = 7
    }

    /**
     * This class uses the provider to get the data from the database
     */
    override fun execute(): ForecastList = forecastProvider.requestByZipCode(zipCode, DAYS)

}