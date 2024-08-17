package com.davidluna.liverpool.domain.entities

data class Product(
    val lgImage: String,
    val listPrice: Double,
    val productDisplayName: String,
    val productId: String,
    val promoPrice: Double,
    val smImage: String,
    val variantsColor: List<VariantsColor>,
)