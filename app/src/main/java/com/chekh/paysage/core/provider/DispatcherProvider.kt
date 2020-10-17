package com.chekh.paysage.core.provider

import javax.inject.Inject
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.Dispatchers.Main

interface DispatcherProvider {
    val ui: CoroutineDispatcher
    val io: CoroutineDispatcher
}

class DispatcherProviderImpl @Inject constructor() : DispatcherProvider {
    override val ui = Main
    override val io = IO
}
