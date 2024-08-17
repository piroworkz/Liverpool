package com.davidluna.liverpool

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.runtime.getValue
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.davidluna.liverpool.ui.presentation.ProductSearchViewModel
import com.davidluna.liverpool.ui.theme.LiverpoolTheme
import com.davidluna.liverpool.ui.view.search.SearchScreen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private val viewModel: ProductSearchViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val state by viewModel.state.collectAsStateWithLifecycle()
            LiverpoolTheme {
                SearchScreen(
                    state = state,
                    onEvent = { viewModel.sendEvent(it) }
                )
            }
        }
    }
}
