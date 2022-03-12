package com.esmaeel.check24_challenge.di

import kotlinx.coroutines.Dispatchers
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.CoroutineContext

/**
 *  copied
 */
@Singleton
open class ContextProviders @Inject constructor() {
    open val Main: CoroutineContext = Dispatchers.Main
    open val IO: CoroutineContext = Dispatchers.IO
}
