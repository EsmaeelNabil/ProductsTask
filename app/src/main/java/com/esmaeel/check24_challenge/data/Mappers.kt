package com.esmaeel.check24_challenge.data

import com.esmaeel.check24_challenge.data.remote.models.products.*
import com.esmaeel.check24_challenge.ui.productsOverview.ProductsListItemTypes

/**
 * adds Header/footer item in the beginning/end of the list
 * wraps Response item into Ui item
 */
fun List<ProductItem>.mapToUiItems(productListScreenHeader: ProductListScreenHeader): List<UiProductItem> {
    val list = mutableListOf<UiProductItem>()
    // add header
    list.add(
        UiProductItem(
            itemType = ProductsListItemTypes.HEADER,
            header = ProductListScreenHeader(
                headerDescription = productListScreenHeader.headerDescription,
                headerTitle = productListScreenHeader.headerTitle
            ),
        )
    )

    // map all products to the new form for ui, and add products.
    list.addAll(this.map { it.mapToUiProductItem() })

    // add footer
    list.add(
        UiProductItem(itemType = ProductsListItemTypes.FOOTER, footer = FooterItem())
    )
    return list
}

/**
 *  maps the response item into adapter item
 */
fun ProductItem.mapToUiProductItem(): UiProductItem {
    val type =
        if (this.available)
            ProductsListItemTypes.AVAILABLE_PRODUCT
        else ProductsListItemTypes.NOT_AVAILABLE_PRODUCT
    return UiProductItem(itemType = type, product = this)
}