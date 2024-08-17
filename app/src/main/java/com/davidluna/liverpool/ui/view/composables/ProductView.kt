package com.davidluna.liverpool.ui.view.composables

import android.graphics.Color.parseColor
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.davidluna.liverpool.domain.entities.Product
import com.davidluna.liverpool.domain.entities.VariantsColor
import com.davidluna.liverpool.ui.theme.LiverpoolTheme
import java.text.NumberFormat
import java.util.Locale

@Composable
fun ProductView(product: Product) {

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 8.dp),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.onPrimary,
        ),
        elevation = CardDefaults.elevatedCardElevation(
            defaultElevation = 8.dp
        )
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {

            AsyncImage(
                model = product.smImage,
                contentDescription = "Product Image",
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1f)
                    .aspectRatio(1f)
                    .clip(MaterialTheme.shapes.large),
                contentScale = ContentScale.Crop
            )

            Column(
                modifier = Modifier
                    .padding(16.dp)
                    .weight(1.25f)
            ) {

                Text(
                    text = product.productDisplayName,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Black,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )

                val alpha = if (product.listPrice > product.promoPrice) 1f else 0f

                Text(
                    text = product.listPrice.asCurrencyMXN(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray.copy(alpha = alpha),
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    textDecoration = TextDecoration.LineThrough
                )

                Text(
                    text = product.promoPrice.asCurrencyMXN(),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.error,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis
                )

                LazyRow {
                    items(product.variantsColor.size) { index ->
                        val variant = product.variantsColor[index].colorHex

                        if (variant.isNotEmpty()) {
                            Box(
                                modifier = Modifier
                                    .size(32.dp)
                                    .clip(CircleShape)
                                    .border(1.dp, MaterialTheme.colorScheme.onPrimary, CircleShape)
                                    .background(variant.toColor())
                            )
                        }

                        Spacer(modifier = Modifier.size(8.dp))
                    }
                }

            }

        }
    }

}

private fun Double.asCurrencyMXN(): String {
    val localeMX = Locale("es", "MX")
    val currencyFormatter = NumberFormat.getCurrencyInstance(localeMX)
    return currencyFormatter.format(this)
}

private fun String.toColor(): Color = Color(parseColor(this))

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
private fun ProductPreView() {
    LiverpoolTheme {
        ProductView(fakeProduct)
    }
}

private val variantsColorList = listOf(
    VariantsColor(
        colorName = "Negro",
        colorHex = "#000000",
        colorImageURL = ""
    ),
    VariantsColor(
        colorName = "Gris",
        colorHex = "#c6c7c9",
        colorImageURL = ""
    ),
    VariantsColor(
        colorName = "Rojo",
        colorHex = "#d60303",
        colorImageURL = ""
    ),
    VariantsColor(
        colorName = "Café",
        colorHex = "#43280c",
        colorImageURL = ""
    )
)

val fakeProduct = Product(
    lgImage = "https://ss111.liverpool.com.mx/lg/1105826407.jpg",
    listPrice = 9999.toDouble(),
    productDisplayName = "Product Name https: //ss111.liverpool. com.mx/lg/1105826407.jpg",
    productId = "651263",
    promoPrice = 8999.toDouble(),
    smImage = "https://ss111.liverpool.com.mx/sm/1105826407.jpg",
    variantsColor = variantsColorList
)
