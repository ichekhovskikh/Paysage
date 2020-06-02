package com.chekh.paysage.extension

fun <T> MutableSet<T>.toggle(item: T): Boolean {
    if (add(item)) {
        return true
    }
    remove(item)
    return false
}

fun <T> MutableList<T>.updateAll(item: List<T>) {
    clear()
    addAll(item)
}
