package com.davidluna.liverpool.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemoteResponse(
    @SerialName("plpResults")
    val plpResults: RemotePlpResults,
)

