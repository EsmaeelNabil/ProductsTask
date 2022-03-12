package com.esmaeel.check24_challenge.data.remote

import com.esmaeel.check24_challenge.common.base.AppException
import com.esmaeel.check24_challenge.data.remote.models.products.ProductsListScreenResponse
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRemoteDataSource @Inject constructor(private val productsService: ProductsService) {
    suspend fun getProducts(): ProductsListScreenResponse {
        if ((0..3).random() == 3)
            throw AppException("Random Error For Testing")

        return productsService.getProducts()
    }


}