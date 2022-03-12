package com.esmaeel.check24_challenge.ui.productsOverview

import com.esmaeel.check24_challenge.common.base.ViewState
import com.esmaeel.check24_challenge.data.remote.models.products.ProductListScreenData

sealed class ProductsListViewState() : ViewState() {
    data class OnProductsListResponse(val data: ProductListScreenData) :
        Loaded<ProductListScreenData>(data)
}