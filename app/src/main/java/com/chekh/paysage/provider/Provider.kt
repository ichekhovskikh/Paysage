package com.chekh.paysage.provider

interface Provider<T> {
    fun provide(): T
}