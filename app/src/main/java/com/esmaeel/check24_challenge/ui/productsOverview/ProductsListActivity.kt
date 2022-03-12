package com.esmaeel.check24_challenge.ui.productsOverview

import androidx.activity.viewModels
import com.esmaeel.check24_challenge.R
import com.esmaeel.check24_challenge.common.base.BaseActivity
import com.esmaeel.check24_challenge.common.base.ViewState
import com.esmaeel.check24_challenge.data.remote.models.products.FilterTypes
import com.esmaeel.check24_challenge.data.remote.models.products.UiProductItem
import com.esmaeel.check24_challenge.databinding.ProductsListActivityBinding
import com.esmaeel.check24_challenge.ui.productDetails.ProductDetailsActivity
import com.esmaeel.check24_challenge.utils.ktx.gone
import com.esmaeel.check24_challenge.utils.ktx.openUrl
import com.esmaeel.check24_challenge.utils.ktx.show
import com.skydoves.bundler.intentOf
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ProductsListActivity :
    BaseActivity<ProductsListActivityBinding, ProductsViewModel>(R.layout.products_list_activity) {

    override val viewModel: ProductsViewModel by viewModels()
    private lateinit var productsListAdapter: ProductsListAdapter
    private lateinit var productsFilterAdapter: ProductsFilterAdapter
    private var latestSelectedFilter: FilterTypes = FilterTypes.ALL

    override fun setup() {
        productsListAdapter = ProductsListAdapter { item: UiProductItem, _: Int ->
            when (item.itemType.type) {

                ProductsListItemTypes.FOOTER.type -> openUrl(item.footer.ulr)
                ProductsListItemTypes.AVAILABLE_PRODUCT.type,
                ProductsListItemTypes.NOT_AVAILABLE_PRODUCT.type -> openDetails(item)

            }
        }

        productsFilterAdapter =
            ProductsFilterAdapter(onFilterClicked = { item: FilterTypes, _: Int ->
                getData(item)
            })

        binder.productsFilterRecycler.adapter = productsFilterAdapter
        binder.productsListRecycler.adapter = productsListAdapter

        getData()

        binder.swipeToRefresh.setOnRefreshListener {
            binder.swipeToRefresh.isRefreshing = false
            getData()
        }

    }

    private fun openDetails(item: UiProductItem) {
        intentOf<ProductDetailsActivity> {
            putExtra(ProductDetailsActivity.ProductKey, item)
            startActivity(this@ProductsListActivity)
        }
    }

    /**
     * cache the latest filter for (refreshing or pull refresh)
     */
    private fun getData(filterType: FilterTypes = latestSelectedFilter) {
        latestSelectedFilter = filterType
        viewModel.getProductsList(filterType)
    }


    override fun render(state: ViewState) {
        when (state) {
            is ProductsListViewState.OnProductsListResponse -> {
                // only bind the new data if it exists.
                // and keep the old data in case or refresh errors.
                if (state.data.products.isNullOrEmpty().not()) {
                    bindProductsList(state.data.products)
                    bindFilters(state.data.filters)
                    binder.errorLayout.root.gone()
                    binder.productsListRecycler.show()
                }
            }

            is ViewState.Error -> handleError(state.error)
        }
    }

    private fun handleError(error: String?) = with(binder) {
        errorLayout.root.show()
        errorLayout.retryButton.setOnClickListener { getData() }
        error?.let { errorLayout.detailsTv.text = it }
        binder.productsListRecycler.gone()
    }

    private fun bindProductsList(products: List<UiProductItem>) {
        productsListAdapter.submitList(products)
    }

    private fun bindFilters(filters: List<FilterTypes>) {
        productsFilterAdapter.submitList(filters)
    }


}