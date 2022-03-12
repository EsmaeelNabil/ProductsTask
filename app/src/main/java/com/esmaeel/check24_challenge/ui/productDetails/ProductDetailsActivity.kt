package com.esmaeel.check24_challenge.ui.productDetails

import androidx.activity.viewModels
import com.esmaeel.check24_challenge.R
import com.esmaeel.check24_challenge.common.base.BaseActivity
import com.esmaeel.check24_challenge.common.base.ViewState
import com.esmaeel.check24_challenge.data.remote.models.products.ProductItem
import com.esmaeel.check24_challenge.data.remote.models.products.UiProductItem
import com.esmaeel.check24_challenge.databinding.ProductsDetailsActivityBinding
import com.esmaeel.check24_challenge.utils.ktx.showToast
import com.skydoves.bundler.bundle
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductDetailsActivity :
    BaseActivity<ProductsDetailsActivityBinding, ProductDetailsViewModel>(R.layout.products_details_activity) {

    override val viewModel: ProductDetailsViewModel by viewModels()
    private val data: UiProductItem? by bundle(ProductKey)
    private var isFavorite = false

    companion object {
        const val ProductKey = "Product"
    }

    override fun setup() {
        showToast(data?.product.toString())
        viewModel.syncFavoriteState(data?.product?.id)
        bindProductToView(data?.product)
    }

    private fun bindProductToView(product: ProductItem?) {
        // TODO: Bind Product to view
    }

    override fun render(state: ViewState) {
        when (state) {
            is ViewState.Error -> showToast(state.error)
            is ProductsDetailsViewState.OnSyncFavoriteState -> {
                handleFavoriteState(state.isFavorite)
            }
            is ProductsDetailsViewState.OnAddedToFavorite -> viewModel.syncFavoriteState(state.productId)
            is ProductsDetailsViewState.OnRemovedFromFavorite -> viewModel.syncFavoriteState(state.productId)

        }
    }

    private fun handleFavoriteState(favorite: Boolean) {
        isFavorite = favorite
        // TODO: update button text
    }
}