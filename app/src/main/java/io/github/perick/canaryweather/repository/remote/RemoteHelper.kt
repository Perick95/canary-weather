package io.github.perick.canaryweather.repository.remote

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

class RemoteHelper {
    companion object {
        suspend fun <T> safeApiCall(
            dispatcher: CoroutineDispatcher,
            apiCall: suspend () -> T
        ): ResultWrapper<T> {
            return withContext(dispatcher) {
                try {
                    ResultWrapper.Success(apiCall.invoke())
                } catch (throwable: Throwable) {
                    ResultWrapper.GenericError(throwable.message)
                }
            }
        }
    }
}