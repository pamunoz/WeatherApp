package com.pfariasmunoz.weatherapp.ui.activities


import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.Toolbar
import com.pfariasmunoz.weatherapp.ui.adapters.ForecastListAdapter
import com.pfariasmunoz.weatherapp.R
import com.pfariasmunoz.weatherapp.domain.commands.RequestForecastCommand
import com.pfariasmunoz.weatherapp.domain.model.ForecastList
import com.pfariasmunoz.weatherapp.extensions.DelegatesExt
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.*
import org.jetbrains.anko.coroutines.experimental.bg


/**
 * This Activity will render a list of daily forecast for the next seven days
 */
class MainActivity : AppCompatActivity(), ToolbarManager {
    /**
     * the lazy find is implemented so that the toolbar will be already
     * inflated by the time it is used.
     */
    override val toolbar by lazy { find<Toolbar>(R.id.toolbar) }

    val zipCode: Long by DelegatesExt.preference(this, SettingsActivity.ZIP_CODE,
            SettingsActivity.DEFAULT_ZIP)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        /**
         * This activity will initialize the toolbar, attach to the {@link RecyclerView}
         * scroll and modify the tollbar title
         */
        initToolbar()

        //val forecastList = find<RecyclerView>(R.id.forecast_list)
        forecastList.layoutManager = LinearLayoutManager(this)
        attachToScroll(forecastList)


    }

    override fun onResume() {
        super.onResume()
        /* Every time the activity resumed, it refreshed the data, just in case the zip code
         * changes */
        loadForecast()
    }

    private fun loadForecast() = async(UI) {
        val result = bg { RequestForecastCommand(zipCode).execute() }
        updateUI(result.await())
    }

    private fun updateUI(weekForecast: ForecastList) {
        val adapter = ForecastListAdapter(weekForecast) {
            startActivity<DetailActivity>(
                    DetailActivity.ID to it.id,
                    DetailActivity.CITY_NAME to weekForecast.city)
        }
        forecastList.adapter = adapter
        toolbarTitle = "${weekForecast.city} (${weekForecast.country})"
    }

}
