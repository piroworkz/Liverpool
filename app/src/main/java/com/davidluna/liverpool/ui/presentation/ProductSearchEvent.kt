package com.davidluna.liverpool.ui.presentation

import com.davidluna.liverpool.domain.entities.SortOrder

sealed interface ProductSearchEvent {
    data class UpdateQuery(val searchQuery: String) : ProductSearchEvent
    data class DefaultSearch(val searchQuery: String?) : ProductSearchEvent
    data class SortedSearch(val sortOrder: SortOrder, val searchQuery: String?) : ProductSearchEvent
    data object ResetError : ProductSearchEvent
    data class ShowMoreOptions(val show: Boolean) : ProductSearchEvent
}