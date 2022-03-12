package com.esmaeel.check24_challenge.domain.usecases.products


import com.esmaeel.check24_challenge.common.Constants
import com.esmaeel.check24_challenge.data.mapToUiItems
import com.esmaeel.check24_challenge.data.remote.models.products.FilterTypes
import com.esmaeel.check24_challenge.data.remote.models.products.ProductItem
import com.esmaeel.check24_challenge.data.remote.models.products.ProductListScreenData
import com.esmaeel.check24_challenge.data.remote.models.products.STATIC_FILTERS
import com.esmaeel.check24_challenge.domain.repositories.ProductsRepository
import com.esmaeel.check24_challenge.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton


@Singleton
class GetProductsUseCase @Inject constructor(
    private val repo: ProductsRepository
) : UseCase<Flow<ProductListScreenData>, FilterTypes> {

    private fun isInFavorites(productId: Int) = repo.isInFavorites(productId)

    override suspend fun invoke(inputType: FilterTypes): Flow<ProductListScreenData> {
        val response =
            repo.getProductsListResponse().firstOrNull() ?: return flowOf(ProductListScreenData())

        val filteredProducts = filterProducts(inputType, response.products)

        return flowOf(
            ProductListScreenData(
                products = filteredProducts.mapToUiItems(response.header),
                // i choose to make it static for now as it is in the response
                // and the implementation is depending entirely on it.
                filters = STATIC_FILTERS
            )
        )
    }

    private fun filterProducts(
        filterType: FilterTypes,
        products: List<ProductItem>
    ): List<ProductItem> = when (filterType.type) {
        Constants.ALL -> products
        Constants.AVAILABLE -> products.filter { it.available }
        Constants.FAVORITES -> products.filter { isInFavorites(it.id) }
        else -> products
    }


}


