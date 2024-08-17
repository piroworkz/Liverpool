package com.davidluna.liverpool.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePlpState(
    @SerialName("categoryId")
    val categoryId: String,
    @SerialName("currentFilters")
    val currentFilters: String,
    @SerialName("currentSortOption")
    val currentSortOption: String,
    @SerialName("firstRecNum")
    val firstRecNum: Int,
    @SerialName("lastRecNum")
    val lastRecNum: Int,
    @SerialName("plpSellerName")
    val plpSellerName: String,
    @SerialName("recsPerPage")
    val recsPerPage: Int,
    @SerialName("totalNumRecs")
    val totalNumRecs: Int
)