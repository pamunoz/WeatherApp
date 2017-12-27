package com.pfariasmunoz.weatherapp.ui.activities

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.Toolbar
import android.widget.TextView
import com.pfariasmunoz.weatherapp.R
import com.pfariasmunoz.weatherapp.R.id.*
import com.pfariasmunoz.weatherapp.domain.commands.RequestDayForecastCommand
import com.pfariasmunoz.weatherapp.domain.model.Forecast
import com.pfariasmunoz.weatherapp.extensions.color
import com.pfariasmunoz.weatherapp.extensions.textColor
import com.pfariasmunoz.weatherapp.extensions.toDateString
import com.squareup.picasso.Picasso
import kotlinx.android.synthetic.main.activity_detail.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.asReference
import org.jetbrains.anko.coroutines.experimental.bg
import org.jetbrains.anko.ctx
import org.jetbrains.anko.doAsync
import org.jetbrains.anko.find
import org.jetbrains.anko.uiThread
import java.text.DateFormat

class DetailActivity : AppCompatActivity(), ToolbarManager {

    /**
     * the lazy find is implemented so that the toolbar will be already
     * inflated by the time it is used.
     */
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    companion object {
        val ID = "DetailActivity:id"
        val CITY_NAME = "DetailActivity:cityName"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        initToolbar()
        toolbarTitle = intent.getStringExtra(CITY_NAME)
        enableHomeAsUp { onBackPressed() }

        // to avoid leaks, we use a weak reference for the activity
        val ref = asReference()
        val id = intent.getLongExtra(ID, -1)
        // Corouitine function
        async(UI) {
            /* The forecast is requested in a baground thread thanks to the bg function, that will
             * return a Referred result */
            val result = bg {
                RequestDayForecastCommand(id).execute() }
            /* The result is awaited in this function, until is ready to be returned on the UI */
            /* here we use the weak reference of the activity */
            ref().bindForecast(result.await())
        }
    }

    private fun bindForecast(forecast: Forecast) = with(forecast) {
        Picasso.with(ctx).load(iconUrl).into(icon)
        toolbar.subtitle = date.toDateString(DateFormat.FULL)
        weatherDescription.text = description
        bindWeather(high to maxTemperature, low to minTemperature)
    }

    /**
     * This method gets a vararg of pairs of Int and TextView, and assign a text and
     * a text color to the TextViews based on the temperature.
     * For each pair, it assigns the text that will show the temperature and a color based
     * on the value of the temperature: red for low temperatures, orange for mild ones and a
     * green for the rest. The values are taken quite randomly, but it's a good representation
     * of what we can do with a #when expression, how clean and short the code becomes.
     */
    private fun bindWeather(vararg views: Pair<Int, TextView>) = views.forEach {
        it.second.text = "${it.first}°"
        it.second.textColor = color(when (it.first) {
            in -50..0 -> android.R.color.holo_red_dark
            in 0..15 -> android.R.color.holo_orange_dark
            else -> android.R.color.holo_green_dark
        })
    }

}
