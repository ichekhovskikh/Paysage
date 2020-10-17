package com.chekh.paysage.core.mapper

interface ThreeParametersMapper<X, Y, Z, R> {
    fun map(firstSource: X, secondSource: Y, thirdSource: Z): R
}
