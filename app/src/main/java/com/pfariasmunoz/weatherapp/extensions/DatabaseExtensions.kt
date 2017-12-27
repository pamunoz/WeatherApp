package com.pfariasmunoz.weatherapp.extensions

import android.database.sqlite.SQLiteDatabase
import org.jetbrains.anko.db.MapRowParser
import org.jetbrains.anko.db.SelectQueryBuilder

/**
 * The immutable map that the parser receives is converted into a mutable map (we need it to
 * be mutable in our database model) by using the corresponding constructor from the {@link HashMap}
 * This {@link HashMap is used by the constructor of {@link DayForecast}
 * The request return a {@link Cursor}. {@link #parseList} iterates over it and gets the rows
 * from the {@link Cursor} until it reaches the last one. For each row it creates a map
 * with the columns as keys and assign the value to the corresponding key. The map is then returned
 * to the parser.
 *  If there is no result for the request {@link #parseList} return an empty list.
 */
fun <T : Any> SelectQueryBuilder.parseList(parser: (Map<String, Any?>) -> T): List<T> =
        parseList(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })

/**
 * This function return a nullable object, depending on whatever the request find something
 * in the database or not. There is another function called {@link #parsesingle}, which does
 * essentially the same, but return a non-nullable object. So if it doesn't find a row in the
 * databe, it throws an exception. In our case, first we query a city it won't be there, so
 * using {@link #parseOpt} is safer.
 */
fun <T : Any> SelectQueryBuilder.parseOpt(parser: (Map<String, Any?>) -> T): T? =
        parseOpt(object : MapRowParser<T> {
            override fun parseRow(columns: Map<String, Any?>): T = parser(columns)
        })

/**
 * This will clear the database
 */
fun SQLiteDatabase.clear(tableName: String) {
    execSQL("delete from $tableName")
}

/**
 * Query by Id is so common that is convenient to create an extension function for this.
 */
fun SelectQueryBuilder.byId(id: Long) = whereSimple("_id = ?", id.toString())