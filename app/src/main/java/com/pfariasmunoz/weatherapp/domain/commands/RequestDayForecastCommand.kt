package com.pfariasmunoz.weatherapp.domain.commands

import com.pfariasmunoz.weatherapp.domain.datasource.ForecastProvider
import com.pfariasmunoz.weatherapp.domain.model.Forecast

/**
 * The request of this command will return a {@link Forecast} result that will
 * be used by the activity to draw its UI.
 */
class RequestDayForecastCommand(
        val id: Long,
        private val forecastProvider: ForecastProvider = ForecastProvider()):
        Command<Forecast> {
    override fun execute() = forecastProvider.requestForecast(id)
}
