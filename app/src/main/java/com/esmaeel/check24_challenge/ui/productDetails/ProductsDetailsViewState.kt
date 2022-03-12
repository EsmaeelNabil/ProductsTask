package com.esmaeel.check24_challenge.ui.productDetails

import com.esmaeel.check24_challenge.common.base.ViewState

sealed class ProductsDetailsViewState() : ViewState() {
    data class OnSyncFavoriteState(val isFavorite: Boolean) : Loaded<Boolean>(isFavorite)
    data class OnAddedToFavorite(val productId: Int) : Loaded<Int>(productId)
    data class OnRemovedFromFavorite(val productId: Int) : Loaded<Int>(productId)
}