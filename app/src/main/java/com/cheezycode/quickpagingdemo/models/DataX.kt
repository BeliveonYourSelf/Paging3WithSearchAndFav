package com.cheezycode.quickpagingdemo.models

import com.google.gson.annotations.SerializedName

data class DataX(
    val cnt_views: Int,
    val date: String,
    val excerpt: String,
    val href: String,
    val id: Int,
    val image: String,
    val isFeatured: Boolean,
    val isGD: String,
    val isLive: Boolean,
    val shareText: String,
    val slug: String,
    @SerializedName("title")
    val title: String,
    val type: String
)