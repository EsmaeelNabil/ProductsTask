package com.esmaeel.check24_challenge.common.base


import com.esmaeel.check24_challenge.data.remote.models.ErrorModel
import com.esmaeel.check24_challenge.di.ContextProviders
import com.esmaeel.check24_challenge.di.ResourcesHandler
import com.google.gson.Gson
import kotlinx.coroutines.TimeoutCancellationException
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException

/**
 *  copied
 */
abstract class BaseRepository(
    private val contextProviders: ContextProviders,
    private var resourcesHandler: ResourcesHandler
) {

    fun <T> networkHandler(fetch: suspend () -> T) = flow {

        // trying to invoke the passed function
        // and emit it's value
        try {
            emit(fetch.invoke())
        }

        // there has been an exception
        // so we might need to respond to it differently
        catch (throwable: Throwable) {

            when (throwable) {

                // network timeout exception due to OkHttpClient timeout configurations
                is TimeoutCancellationException -> throw AppException(resourcesHandler.NETWORK_ERROR_TIMEOUT)

                // might be due to no wifi enabled or network.
                is IOException -> throw AppException(resourcesHandler.NETWORK_ERROR)

                // Server has responded and now we need to check for the status Code (404,401, .... )
                is HttpException -> handleNetworkException(throwable)

                // some other exception
                else -> throw AppException(throwable.message)
            }

        }

    }.flowOn(contextProviders.IO)

    private fun handleNetworkException(throwable: HttpException) {
        when (throwable.code()) {
            401 -> throw AuthException(getErrorFrom(throwable))
            else -> throw AppException(getErrorFrom(throwable))
        }
    }

    private fun getErrorFrom(throwable: HttpException): String {
        return try {
            Gson().fromJson(
                throwable.response()?.errorBody()?.string(),
                ErrorModel::class.java
            ).message
        } catch (exception: Exception) {
            Timber.e(exception)
            resourcesHandler.UNKNOWN_ERROR
        }
    }
}
