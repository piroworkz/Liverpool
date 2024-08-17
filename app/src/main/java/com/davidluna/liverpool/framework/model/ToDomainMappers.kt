package com.davidluna.liverpool.framework.model

import com.davidluna.liverpool.domain.entities.PlpResults
import com.davidluna.liverpool.domain.entities.PlpState
import com.davidluna.liverpool.domain.entities.Product
import com.davidluna.liverpool.domain.entities.VariantsColor


fun RemotePlpResults.toDomain(): PlpResults {
    return PlpResults(
        plpState = plpState.toDomain(),
        products = records.map { it.toDomain() }
    )
}

private fun RemoteRecord.toDomain(): Product {
    return Product(
        productId = productId,
        productDisplayName = productDisplayName,
        listPrice = listPrice,
        promoPrice = promoPrice,
        smImage = smImage,
        lgImage = lgImage,
        variantsColor = variantsColor.map { it.toDomain() }
    )
}

private fun RemotePlpState.toDomain(): PlpState {
    return PlpState(
        categoryId = categoryId,
        currentFilters = currentFilters,
        currentSortOption = currentSortOption,
        firstRecNum = firstRecNum,
        lastRecNum = lastRecNum,
        plpSellerName = plpSellerName,
        recsPerPage = recsPerPage,
        totalNumRecs = totalNumRecs,
        totalNumPages = if (totalNumRecs % recsPerPage > 0) {
            totalNumRecs / recsPerPage + 1
        } else {
            totalNumRecs / recsPerPage
        }
    )
}

private fun RemoteVariantsColor.toDomain(): VariantsColor {
    return VariantsColor(
        colorHex = colorHex,
        colorImageURL = colorImageURL,
        colorName = colorName
    )
}
