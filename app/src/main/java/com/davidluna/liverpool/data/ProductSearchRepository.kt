package com.davidluna.liverpool.data

import arrow.core.Either
import com.davidluna.liverpool.domain.entities.AppError
import com.davidluna.liverpool.domain.entities.PlpResults

interface ProductSearchRepository {

    suspend fun fetchDefault(searchString: String, page: Int): Either<AppError, PlpResults>

    suspend fun fetchSorted(
        searchString: String,
        minSortPrice: Int,
        page: Int,
    ): Either<AppError, PlpResults>

}