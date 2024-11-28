package com.cheezycode.quickpagingdemo.retrofit

import com.cheezycode.quickpagingdemo.models.NewRespo
import com.cheezycode.quickpagingdemo.models.QuoteList
import retrofit2.http.GET
import retrofit2.http.Query

interface QuoteAPI {

    @GET("news")
    suspend fun getQuotes(@Query("page_no") page: Int): NewRespo
}