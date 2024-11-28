package com.cheezycode.quickpagingdemo.models

import com.google.gson.annotations.SerializedName

data class Data(
    @SerializedName("data")
    val data: List<DataX>,
    val page: Int,
    val per_page: Int,
    val total: Int,
    val total_pages: Int
)