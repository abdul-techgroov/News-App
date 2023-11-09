package com.news.app.repository

import android.content.Context
import com.news.app.snippet.hasNetworkConnection
import com.news.app.webservice.State
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.withContext
import retrofit2.Response

abstract class NetworkBoundRepository<RESULT>(
    private val context: Context
) {
    fun asFlow() = flow<State<RESULT>> {
        if (context.hasNetworkConnection()) {
            // Fetch latest posts from remote
            val apiResponse: Response<RESULT>
            // Parse body
            val remotePosts: RESULT?
            withContext(Dispatchers.IO) {
                apiResponse = fetchData()
                remotePosts = apiResponse.body()
            }
            // Check for response validation
            if (apiResponse.isSuccessful && remotePosts != null) {
                // Save posts into the persistence storage
                if (apiResponse.code() == 200)
                    emit(State.success(remotePosts))
                else {
                    emit(State.error(apiResponse.message()))
                }
            } else {
                emit(State.error("Api Error"))
            }
        } else {
            // Fetch from database
        }
    }.catch { e ->
        // Exception occurred! Emit error
        withContext(Dispatchers.Main) {
            emit(State.error("Server Error"))
            e.printStackTrace()
        }
    }

    protected abstract suspend fun fetchData(): Response<RESULT>

}
