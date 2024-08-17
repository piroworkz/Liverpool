package com.davidluna.liverpool.framework.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class RemotePlpResults(
    @SerialName("plpState")
    val plpState: RemotePlpState,
    @SerialName("records")
    val records: List<RemoteRecord>
)