package com.chekh.paysage.core.provider

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main
import javax.inject.Singleton

interface DispatcherProvider {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
}

@Singleton
class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val ui = Main
    override val io = IO
}
