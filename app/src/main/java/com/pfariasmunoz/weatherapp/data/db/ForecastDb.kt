package com.pfariasmunoz.weatherapp.data.db

import android.text.method.TextKeyListener.clear
import com.pfariasmunoz.weatherapp.domain.datasource.ForecastDataSource
import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.domain.model.ForecastList
import com.pfariasmunoz.weatherapp.extensions.*
import org.jetbrains.anko.db.insert
import org.jetbrains.anko.db.select

class ForecastDb(private val forecastDbHelper: ForecastDbHelper = ForecastDbHelper.instance,
                 private val dataMapper: DbDataMapper = DbDataMapper()): ForecastDataSource {
    override fun requestDayForecast(id: Long): Forecast? = forecastDbHelper.use {
        val forecast = select(DayForecastTable.NAME).byId(id)
                .parseOpt { DayForecast(HashMap(it)) }
        if (forecast != null) dataMapper.convertDayToDomain(forecast) else null
    }

    override fun requestForecastByZipCode(zipCode: Long, date: Long) = forecastDbHelper.use {

        val dailyRequest = "${DayForecastTable.CITY_ID} = ? AND ${DayForecastTable.DATE} >= ?"
        /**
         * * The request return a {@link Cursor}. {@link #parseList} iterates over it and gets the rows
         * from the {@link Cursor} until it reaches the last one. For each row it creates a map
         * with the columns as keys and assign the value to the corresponding key. The map is then returned
         * to the parser.
         *  If there is no result for the request {@link #parseList} return an empty list.
         */
        val dailyForecast = select(DayForecastTable.NAME)
                .whereSimple(dailyRequest, zipCode.toString(), date.toString())
                .parseList { DayForecast(HashMap(it)) }

        /**
         * This function return a nullable object, depending on whatever the request find something
         * in the database or not. There is another function called {@link #parsesingle}, which does
         * essentially the same, but return a non-nullable object. So if it doesn't find a row in the
         * database, it throws an exception. In our case, first we query a city it won't be there, so
         * using {@link #parseOpt} is safer.
         */
        val city = select(CityForecastTable.NAME)
                .whereSimple("${CityForecastTable.ID} = ?", zipCode.toString())
                .parseOpt { CityForecast(HashMap(it), dailyForecast) }

        /**
         * if the returned city is not null, we convert it to a domain object and return it, using
         * the {@link dataMapper}. Otherwise, we return a {@link null}. As you may remember, last
         * line inside a lambda represents what the it returns. So it will return an object from
         * the type {@link CityForecast?:}
         */
        if (city != null) dataMapper.convertToDomain(city) else null
    }

    /**
     * This function just clear the data from the database so that we save fresh data, convert the
     * domain forecast model to database model, and insert each daily forecast and the city forecast.
     * The structure is similar to the previous one: it return the value from the {@link #use}
     * function from the database helper. In this case we don't need a result, so we return
     * {@link Unit}.
     */
    fun saveForecast(forecast: ForecastList) = forecastDbHelper.use {

        /**
         * first, we clear both tables, using another extension function.
         */
        clear(CityForecastTable.NAME)
        clear(DayForecastTable.NAME)

        /**
         * Now it's time to convert the data, an use the result to execute the {@link #insert}
         * queries.
         */
        with(dataMapper.convertFromDomain(forecast)) {
            /**
             * This function will convert the #varag to the {@link ContentValues} object the Android SDK
             * needs.
             *  It insert a row in the {@link CityForecast} table. The '*' used before the result
             *  {@link #toVaragArray} indicates that the array will be decomposed to a #vararg
             *  parameter. This is done automatically in Java, but we need to make it explicit in
             *  Kotlin.
             *
             */
            insert(CityForecastTable.NAME, *map.toVarargArray())
            /**
             * And the same for each daily forecast
             */
            dailyForecast.forEach { insert(DayForecastTable.NAME, *it.map.toVarargArray()) }
        }
    }
}