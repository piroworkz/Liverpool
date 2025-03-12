package com.davidluna.liverpool.ui.view.search

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.paging.PagingData
import androidx.paging.compose.collectAsLazyPagingItems
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent
import com.davidluna.liverpool.ui.presentation.ProductSearchViewModel
import com.davidluna.liverpool.ui.theme.LiverpoolTheme
import com.davidluna.liverpool.ui.view.composables.AppBarView
import com.davidluna.liverpool.ui.view.composables.ProductView
import com.davidluna.liverpool.ui.view.composables.fakeProduct
import kotlinx.coroutines.flow.flowOf

@Composable
fun SearchScreen(
    state: ProductSearchViewModel.State,
    onEvent: (ProductSearchEvent) -> Unit,
) {
    val products = state.products.collectAsLazyPagingItems()

    Scaffold(
        modifier = Modifier
            .windowInsetsPadding(WindowInsets.statusBars)
            .fillMaxSize(),
        topBar = {
            AppBarView(
                query = state.searchQuery ?: "",
                showMoreOptions = state.showMoreOptions,
                onEvent = { onEvent(it) }
            )
        }
    ) { paddingValues: PaddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            LazyColumn {
                items(products.itemCount,
                    key = { products[it]?.productId ?: it }) { index: Int ->
                    val product = products[index]
                    product?.let { ProductView(product = it) }
                }
            }
            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun SearchScreenPreview() {
    LiverpoolTheme {
        SearchScreen(
            state = ProductSearchViewModel.State(
                products = flowOf(PagingData.from(
                    (0..30).map { fakeProduct.copy(productId = it.toString()) }
                ))
            ),
            onEvent = {}
        )
    }
}