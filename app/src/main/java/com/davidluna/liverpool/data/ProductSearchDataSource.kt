package com.davidluna.liverpool.data

import arrow.core.Either
import com.davidluna.liverpool.domain.entities.AppError
import com.davidluna.liverpool.domain.entities.PlpResults
import com.davidluna.liverpool.framework.model.toDomain
import com.davidluna.liverpool.framework.services.ProductSearchService
import com.davidluna.liverpool.framework.tryCatch
import javax.inject.Inject

class ProductSearchDataSource @Inject constructor(private val service: ProductSearchService) :
    ProductSearchRepository {

    override suspend fun fetchDefault(
        searchString: String,
        page: Int,
    ): Either<AppError, PlpResults> =
        tryCatch(service.fetchDefault(searchString, page).plpResults::toDomain)

    override suspend fun fetchSorted(
        searchString: String,
        minSortPrice: Int,
        page: Int,
    ): Either<AppError, PlpResults> =
        tryCatch(service.fetchSorted(searchString, page, minSortPrice).plpResults::toDomain)

}
