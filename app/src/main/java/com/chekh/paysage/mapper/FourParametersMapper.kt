package com.chekh.paysage.mapper

interface FourParametersMapper<X, Y, Z, W, R> {
    fun map(firstSource: X, secondSource: Y, thirdSource: Z, fourthSource: W): R
}