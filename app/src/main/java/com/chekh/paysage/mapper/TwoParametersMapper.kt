package com.chekh.paysage.mapper

interface TwoParametersMapper<X, Y, Z> {
    fun map(firstSource: X, secondSource: Y): Z
}