package com.pfariasmunoz.weatherapp.domain.commands

public interface Command<out T> {
    fun execute(): T
}