package com.davidluna.liverpool.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteRecord(
    @SerialName("lgImage")
    val lgImage: String,
    @SerialName("listPrice")
    val listPrice: Double,
    @SerialName("productDisplayName")
    val productDisplayName: String,
    @SerialName("productId")
    val productId: String,
    val promoPrice: Double,
    @SerialName("smImage")
    val smImage: String,
    @SerialName("variantsColor")
    val variantsColor: List<RemoteVariantsColor> = emptyList(),
)