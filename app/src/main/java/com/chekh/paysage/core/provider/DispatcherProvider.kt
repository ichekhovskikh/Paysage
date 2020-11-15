package com.chekh.paysage.core.provider

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Singleton

interface DispatcherProvider {
    val main: CoroutineDispatcher
    val io: CoroutineDispatcher
}

@Singleton
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val main = Main
    override val io = IO
}
