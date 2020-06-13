package com.chekh.paysage.mapper

interface OneParameterMapper<X, Y> {
    fun map(source: X): Y
}