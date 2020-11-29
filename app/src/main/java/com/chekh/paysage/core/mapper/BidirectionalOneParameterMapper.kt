package com.chekh.paysage.core.mapper

interface BidirectionalOneParameterMapper<X, Y> : OneParameterMapper<X, Y> {
    fun unmap(source: Y): X
}
