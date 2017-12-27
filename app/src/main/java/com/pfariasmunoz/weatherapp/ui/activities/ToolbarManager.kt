package com.pfariasmunoz.weatherapp.ui.activities

import android.support.v7.graphics.drawable.DrawerArrowDrawable
import android.support.v7.widget.RecyclerView
import android.support.v7.widget.Toolbar
import com.pfariasmunoz.weatherapp.R
import com.pfariasmunoz.weatherapp.extensions.ctx
import com.pfariasmunoz.weatherapp.extensions.slideEnter
import com.pfariasmunoz.weatherapp.extensions.slideExit
import com.pfariasmunoz.weatherapp.ui.App
import org.jetbrains.anko.startActivity
import org.jetbrains.anko.toast

interface ToolbarManager {

    val toolbar: Toolbar

    var toolbarTitle: String
        get() = toolbar.title.toString()
        set(value) {
            toolbar.title = value
        }

    /**
     * This inflates the toolbar menu and set the listener.
     */
    fun initToolbar() {
        toolbar.inflateMenu(R.menu.menu_main)
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_settings -> toolbar.ctx.startActivity<SettingsActivity>()
                else -> App.instance.toast("Unknown option")
            }
            true
        }
    }

    /**
     * This enables the icon on the toolbar, sets an arrow icon and a listener that
     * will be fired when the icon is pressed. This receives the listener, creates up
     * the drawable by using the {@link DrawerArrowDrawable} on its final state (when the
     * arrow is already showing) and assigns the listener to the toolbar.
     */
    fun enableHomeAsUp(up: () -> Unit) {
        toolbar.navigationIcon = createUpDrawable()
        toolbar.setNavigationOnClickListener { up() }
    }

    private fun createUpDrawable() = DrawerArrowDrawable(toolbar.ctx).apply { progress = 1f }

    /**
     * This function allow the toolbar to be attached to a scroll, and animates the toolbar
     * depending on the direction of the scroll. The toolbar will be hidden while we are
     * scrolling down and displayed again when scrolling up.
     */
    fun attachToScroll(recyclerView: RecyclerView) {
            recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrolled(recyclerView: RecyclerView?,
                                        horizontalScroll: Int, verticalScroll: Int) {
                    if (verticalScroll > 0) toolbar.slideExit() else toolbar.slideEnter()
                }
            })
    }

}