package com.chekh.paysage.core.mapper

interface OneParameterMapper<X, Y> {
    fun map(source: X): Y
}
