package com.esmaeel.check24_challenge.ui.productDetails

import com.esmaeel.check24_challenge.common.base.BaseViewModel
import com.esmaeel.check24_challenge.di.ContextProviders
import com.esmaeel.check24_challenge.domain.usecases.products.AddProductToFavoriteUseCase
import com.esmaeel.check24_challenge.domain.usecases.products.IsProductInFavoritesUseCase
import com.esmaeel.check24_challenge.domain.usecases.products.RemoveProductToFavoriteUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProductDetailsViewModel @Inject constructor(
    private val addToFavUseCase: AddProductToFavoriteUseCase,
    private val removeToFavUseCase: RemoveProductToFavoriteUseCase,
    private val isProductInFavoritesUseCase: IsProductInFavoritesUseCase,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {

    fun addToFavorite(productId: Int) = launchBlock {
        addToFavUseCase.invoke(productId).collect {
            setState(ProductsDetailsViewState.OnAddedToFavorite(productId))
        }
    }

    fun removeFromFavorite(productId: Int) = launchBlock {
        removeToFavUseCase.invoke(productId).collect {
            setState(ProductsDetailsViewState.OnRemovedFromFavorite(productId))
        }
    }

    fun syncFavoriteState(productId: Int?) = launchBlock {
        productId?.let {
            isProductInFavoritesUseCase.invoke(productId).collect {
                setState(ProductsDetailsViewState.OnSyncFavoriteState(it))
            }
        }
    }
}