package io.github.perick.canaryweather.repository.remote

/**
 * sealed class for all response from repository to the viewModel
 *  <out T> means that you can a specific class and downcast it to more generic class:
 *  https://discuss.kotlinlang.org/t/generic-variance-cant-use-var-with-out-t-and-cant-use-val-with-in-t/18868/8
 */
sealed class ResultWrapper<out T> {
    data class Success<T>(val value: T) : ResultWrapper<T>()

    data class GenericError(val message: String?) : ResultWrapper<Nothing>()
}