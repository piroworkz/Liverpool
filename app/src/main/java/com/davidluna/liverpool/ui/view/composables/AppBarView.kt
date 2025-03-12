package com.davidluna.liverpool.ui.view.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Devices
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.davidluna.liverpool.R
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.DefaultSearch
import com.davidluna.liverpool.ui.presentation.ProductSearchEvent.UpdateQuery
import com.davidluna.liverpool.ui.theme.LiverpoolTheme

@Composable
fun AppBarView(
    query: String,
    showMoreOptions: Boolean,
    onEvent: (ProductSearchEvent) -> Unit,
) {
    val controller = LocalSoftwareKeyboardController.current

    Column {
        Row(
            modifier = Modifier
                .wrapContentHeight()
                .fillMaxWidth()
                .background(MaterialTheme.colorScheme.primary),
            verticalAlignment = Alignment.CenterVertically
        ) {

            IconButton(
                onClick = { /*TODO*/ },
                enabled = false
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.icon),
                    contentDescription = "Liverpool logo icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }

            TextField(
                value = query,
                onValueChange = { onEvent(UpdateQuery(it)) },
                modifier = Modifier
                    .weight(1f)
                    .padding(8.dp),
                textStyle = LocalTextStyle.current.copy(
                    textDecoration = TextDecoration.None
                ),
                placeholder = {
                    Text(
                        text = "Buscar por producto, marca y más...",
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                    )
                },
                trailingIcon = {
                    SearchTrailingIcon(
                        query = query,
                        onEvent = {
                            controller?.hide()
                            onEvent(it)
                        }
                    )
                },
                keyboardOptions = KeyboardOptions.Default.copy(
                    autoCorrectEnabled = true,
                    imeAction = ImeAction.Search,
                    keyboardType = KeyboardType.Text
                ),
                keyboardActions = KeyboardActions(
                    onSearch = {
                        onEvent(DefaultSearch(query))
                        defaultKeyboardAction(ImeAction.Search)
                        controller?.hide()
                    }
                ),
                singleLine = true,
                shape = MaterialTheme.shapes.small,
                colors = searchFieldColors()
            )
            IconButton(
                onClick = { onEvent(ProductSearchEvent.ShowMoreOptions(true)) },
            ) {
                Icon(
                    imageVector = Icons.Default.MoreVert,
                    contentDescription = "Liverpool logo icon",
                    tint = MaterialTheme.colorScheme.onPrimary
                )
            }
        }
        MoreVertMenu(
            showMoreOptions = showMoreOptions,
            searchQuery = query,
            onEvent = { onEvent(it) }
        )
    }
}

@Composable
private fun searchFieldColors() = TextFieldDefaults.colors(
    focusedIndicatorColor = MaterialTheme.colorScheme.primary,
    unfocusedIndicatorColor = MaterialTheme.colorScheme.primary,
    focusedContainerColor = MaterialTheme.colorScheme.onPrimary,
    unfocusedContainerColor = MaterialTheme.colorScheme.onPrimary,
)

@Composable
private fun SearchTrailingIcon(
    query: String,
    onEvent: (ProductSearchEvent) -> Unit,
) {

    IconButton(onClick = {
        onEvent(DefaultSearch(query))
    }) {
        Icon(Icons.Default.Search, contentDescription = null)
    }
}

@Preview(
    showBackground = true,
    showSystemUi = true,
    device = Devices.PIXEL_7_PRO
)
@Composable
private fun AppBarPreView() {

    var query: String by remember { mutableStateOf("") }

    LiverpoolTheme {
        Scaffold(
            topBar = {
                AppBarView(
                    showMoreOptions = false,
                    query = query,
                    onEvent = {
                        query = when (it) {
                            is UpdateQuery -> it.searchQuery
                            is DefaultSearch -> it.searchQuery ?: ""
                            else -> query
                        }
                    }
                )
            }
        ) {
            Box(
                modifier = Modifier
                    .padding(it)
                    .fillMaxSize()
            )
        }
    }
}