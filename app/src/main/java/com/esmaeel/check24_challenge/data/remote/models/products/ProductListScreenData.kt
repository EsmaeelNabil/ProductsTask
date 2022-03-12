package com.esmaeel.check24_challenge.data.remote.models.products

import android.os.Parcel
import com.esmaeel.check24_challenge.common.Constants
import com.esmaeel.check24_challenge.ui.productsOverview.ProductsListItemTypes
import java.io.Serializable

sealed class FilterTypes(val type: String) {
    object ALL : FilterTypes(Constants.ALL)
    object AVAILABLE : FilterTypes(Constants.AVAILABLE)
    object FAVORITES : FilterTypes(Constants.FAVORITES)
}

val STATIC_FILTERS = listOf(
    FilterTypes.ALL,
    FilterTypes.AVAILABLE,
    FilterTypes.FAVORITES
)

data class ProductListScreenData(
    val products: List<UiProductItem> = listOf(),
    val filters: List<FilterTypes> = listOf()
) : Serializable

data class FooterItem(
    val footerText: String = "Footer Text",
    val ulr: String = "http://m.check24.de/rechtliche-hinweise?deviceoutput=app"
) : Serializable

data class UiProductItem(
    val itemType: ProductsListItemTypes,
    val product: ProductItem = ProductItem(),
    val header: ProductListScreenHeader = ProductListScreenHeader(),
    val footer: FooterItem = FooterItem()
) : Serializable