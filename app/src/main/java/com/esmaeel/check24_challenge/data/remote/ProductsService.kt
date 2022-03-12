package com.esmaeel.check24_challenge.data.remote

import com.esmaeel.check24_challenge.data.remote.models.products.ProductsListScreenResponse
import retrofit2.http.GET

interface ProductsService {

    @GET("products-test.json")
    suspend fun getProducts(): ProductsListScreenResponse

}



