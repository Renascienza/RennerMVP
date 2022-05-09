package com.renascienza.rennermvp

import kotlinx.coroutines.flow.MutableStateFlow

internal fun <E> MutableSet<E>.addOrRemove(item: E) {
    if (! add(item)) {
        remove(item)
    }
}

inline fun <T> MutableStateFlow<T>.update(function: (T) -> T) {
    while (true) {
        val prevValue = value
        val nextValue = function(prevValue)
        if (compareAndSet(prevValue, nextValue)) {
            return
        }
    }
}