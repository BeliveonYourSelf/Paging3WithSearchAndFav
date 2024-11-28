package com.cheezycode.quickpagingdemo.models

import com.google.gson.annotations.SerializedName

data class NewRespo(
    @SerializedName("data")
    val data: Data,
    val message: Any,
    val status: String
)