package com.chekh.paysage.core.mapper

interface TwoParametersMapper<X, Y, Z> {
    fun map(firstSource: X, secondSource: Y): Z
}
