package com.chekh.paysage.provider

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.Dispatchers.IO
import javax.inject.Inject

interface DispatcherProvider {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val ui = Main
    override val io = IO
}