package com.esmaeel.check24_challenge.ui.productsOverview

import com.esmaeel.check24_challenge.common.base.BaseViewModel
import com.esmaeel.check24_challenge.data.remote.models.products.FilterTypes
import com.esmaeel.check24_challenge.domain.usecases.products.GetProductsUseCase
import com.esmaeel.check24_challenge.di.ContextProviders
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class ProductsViewModel @Inject constructor(
    private val getProductsUseCase: GetProductsUseCase,
    contextProviders: ContextProviders
) : BaseViewModel(contextProviders) {

    fun getProductsList(filterType: FilterTypes) = launchBlock {
        getProductsUseCase.invoke(filterType).collect {
            setState(ProductsListViewState.OnProductsListResponse(it))
        }
    }
}