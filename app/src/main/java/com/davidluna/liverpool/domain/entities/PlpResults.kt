package com.davidluna.liverpool.domain.entities

data class PlpResults(
    val plpState: PlpState,
    val products: List<Product>,
)