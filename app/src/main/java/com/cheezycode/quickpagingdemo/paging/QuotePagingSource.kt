package com.cheezycode.quickpagingdemo.paging

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cheezycode.quickpagingdemo.models.DataX
import com.cheezycode.quickpagingdemo.retrofit.QuoteAPI
import com.google.gson.GsonBuilder

class QuotePagingSource(private val quoteAPI: QuoteAPI) : PagingSource<Int, DataX>() {

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, DataX> {
        return try {
            val position = params.key ?: 1
            val response = quoteAPI.getQuotes(position)
            Log.e("TAG", "load: ------->"+quoteAPI.getQuotes(position) )
            return LoadResult.Page(
                data = response.data.data,
                prevKey = if (position == 1) null else position - 1,
                nextKey = if (position == response.data.total_pages) null else position + 1
            )
        } catch (e: Exception) {
            Log.e("TAG", "Exception: ------->"+e.message )
            LoadResult.Error(e)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, DataX>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }

}