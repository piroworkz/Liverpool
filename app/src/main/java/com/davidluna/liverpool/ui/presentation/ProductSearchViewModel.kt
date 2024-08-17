package com.davidluna.liverpool.ui.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import arrow.core.Either
import com.davidluna.liverpool.data.ProductSearchRepository
import com.davidluna.liverpool.domain.entities.AppError
import com.davidluna.liverpool.domain.entities.PlpResults
import com.davidluna.liverpool.domain.entities.Product
import com.davidluna.liverpool.domain.entities.SortOrder
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.DefaultSearch
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.ResetError
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.ShowMoreOptions
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.SortedSearch
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.UpdateQuery
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProductSearchViewModel @Inject constructor(
    private val searchRepository: ProductSearchRepository,
) : ViewModel() {

    private val _state: MutableStateFlow<State> = MutableStateFlow(State())
    val state: StateFlow<State> = _state.asStateFlow()

    init {
        fetchDefault(String())
    }

    data class State(
        val totalPages: Int = 0,
        val isLoading: Boolean = false,
        val appError: AppError? = null,
        val products: Flow<PagingData<Product>> = emptyFlow(),
        val searchQuery: String? = null,
        val sortByPrice: SortOrder = SortOrder.DEFAULT,
        val showMoreOptions: Boolean = false,
    )

    fun sendEvent(event: ProductSearchEvent) {
        when (event) {
            is DefaultSearch -> fetchDefault(event.searchQuery ?: String())
            is SortedSearch -> fetchSortedByPrice(event.sortOrder, event.searchQuery ?: String())
            ResetError -> resetError()
            is UpdateQuery -> updateQuery(event.searchQuery)
            is ShowMoreOptions -> setShowMoreOptions(event.show)

        }
    }

    private fun setShowMoreOptions(show: Boolean) {
        _state.update { it.copy(showMoreOptions = show) }
    }

    private fun updateQuery(query: String) {
        _state.update { it.copy(searchQuery = query) }
    }

    private fun resetError() = _state.update { it.copy(appError = null) }

    private fun fetchDefault(searchQuery: String) {
        setShowMoreOptions(false)
        viewModelScope.launch(Dispatchers.IO) {
            _state.update {
                it.copy(
                    isLoading = true,
                    products = asPagingDataFlow {
                        searchRepository.fetchDefault(
                            searchQuery,
                            it
                        )
                    })
            }
            hideLoader()
        }
    }

    private fun fetchSortedByPrice(
        sortOrder: SortOrder,
        searchQuery: String,
    ) {
        viewModelScope.launch(Dispatchers.IO) {
            setShowMoreOptions(false)
            _state.update {
                it.copy(
                    isLoading = true,
                    products = asPagingDataFlow {
                        searchRepository.fetchSorted(
                            searchQuery,
                            sortOrder.order,
                            it
                        )
                    })
            }
            hideLoader()
        }
    }

    private fun hideLoader() = _state.update { it.copy(isLoading = false) }

    private fun CoroutineScope.asPagingDataFlow(
        invoke: suspend (currentPage: Int) -> Either<AppError, PlpResults>,
    ): Flow<PagingData<Product>> =
        Pager(
            config = PagingConfig(pageSize = 56, prefetchDistance = 12),
            pagingSourceFactory = {
                ProductsPagingSource { invoke(it) }
            }).flow.cachedIn(this)

}


