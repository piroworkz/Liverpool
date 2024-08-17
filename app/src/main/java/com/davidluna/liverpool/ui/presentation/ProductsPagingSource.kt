package com.davidluna.liverpool.ui.presentation

import androidx.paging.PagingSource
import androidx.paging.PagingState
import arrow.core.Either
import com.davidluna.liverpool.domain.entities.AppError
import com.davidluna.liverpool.domain.entities.PlpResults
import com.davidluna.liverpool.domain.entities.Product

class ProductsPagingSource(
    private val invoke: suspend (currentPage: Int) -> Either<AppError, PlpResults>,
) : PagingSource<Int, Product>() {
    override fun getRefreshKey(state: PagingState<Int, Product>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Product> {
        return try {
            val currentPage: Int = params.key ?: 1

            val response: PlpResults? = invoke(currentPage)
                .fold(
                    ifLeft = { null },
                    ifRight = { it }
                )

            val data = response?.products ?: emptyList()

            val next = if (isLastPage(response, currentPage)) {
                null
            } else {
                currentPage.plus(1)
            }
            LoadResult.Page(
                data = data,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = next
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }

    private fun isLastPage(response: PlpResults?, currentPage: Int): Boolean {
        val totalNumPages = response?.plpState?.totalNumPages ?: 0
        return totalNumPages == 0 || currentPage == totalNumPages
    }
}