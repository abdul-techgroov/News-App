package com.news.app.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import java.io.IOException

abstract class BasePagingSource<T : Any>(private var limit: Int) : PagingSource<Int, T>() {

    protected var itemCount: Int = 0
    protected var loadMore = true

    abstract suspend fun performApiCall(params: LoadParams<Int>): Pair<Int, MutableList<T>?>

    override fun getRefreshKey(state: PagingState<Int, T>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, T> {
        return try {
            if (itemCount % limit == 0 && loadMore) {
                val data = withContext(Dispatchers.IO) { performApiCall(params) }
                loadMore = data.second.isNullOrEmpty().not() || (data.second?.size ?: 0) >= limit
                itemCount += data.second?.size ?: 0
                LoadResult.Page(
                        data = data.second ?: listOf(),
                        prevKey = params.key,
                        nextKey = data.first
                )
            }else{
                LoadResult.Error(IOException("No more data"))
            }
        } catch (e: IOException) {
            LoadResult.Error(e)
        } catch (e: HttpException) {
            LoadResult.Error(e)
        }
    }
}