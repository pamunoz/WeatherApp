package com.pfariasmunoz.weatherapp.extensions

/**
 *  this function consist of transforming the {@link #map} into a #varag array.
 */
fun <K, V : Any> Map<K, V?>.toVarargArray(): Array<out Pair<K, V>> =
        map({ Pair(it.key, it.value!!) }).toTypedArray()

/**
 * This function will return the first result that is not null. This is based on the #fist function.
 * It receives a predicate which gets an object of type T and returns a value of type R?
 * It will iterate and execute the predicate over the elements in the Iterable collection. When
 * the result of the predicate is not null, this result will be returned.
 */
inline fun <T, R: Any> Iterable<T>.firstResult(predicate: (T) -> R?): R {
    for (element in this) {
        val result = predicate(element)
        if (result != null) return result
    }
    throw NoSuchElementException("No element matching predicate was found")
}