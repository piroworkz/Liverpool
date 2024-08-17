package com.davidluna.liverpool.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteVariantsColor(
    @SerialName("colorHex")
    val colorHex: String,
    @SerialName("colorImageURL")
    val colorImageURL: String,
    @SerialName("colorName")
    val colorName: String
)