package com.davidluna.liverpool.ui.view.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidluna.liverpool.domain.entities.SortOrder
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.DefaultSearch
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.SortedSearch
import com.davidluna.liverpool.ui.presentation.ProductSearchViewModel
import com.davidluna.liverpool.ui.theme.LiverpoolTheme

@Composable
fun MoreVertMenu(
    showMoreOptions: Boolean,
    searchQuery: String,
    onEvent: (ProductSearchEvent) -> Unit,
) {
    DropdownMenu(
        expanded = showMoreOptions,
        onDismissRequest = { onEvent(ProductSearchEvent.ShowMoreOptions(false)) },
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = "Ordenar por:", modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth()
        )

        DropdownMenuItem(
            text = { Text(text = "Relevancia") },
            onClick = {
                onEvent(DefaultSearch(searchQuery))
            }
        )

        DropdownMenuItem(
            text = { Text(text = "Menor precio") },
            onClick = {
                onEvent(
                    SortedSearch(
                        SortOrder.ASCENDING,
                        searchQuery
                    )
                )
            }
        )

        DropdownMenuItem(
            text = { Text(text = "Mayor precio") },
            onClick = {
                onEvent(
                    SortedSearch(
                        SortOrder.DESCENDING,
                        searchQuery
                    )
                )
            }
        )
    }
}

@Preview
@Composable
private fun MoreVertMenuPreview() {
    LiverpoolTheme {
        MoreVertMenu(
            showMoreOptions = true,
            searchQuery = "searchQuery",
            onEvent = {}
        )
    }
}