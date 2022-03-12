package com.esmaeel.check24_challenge.domain.repositories

import com.esmaeel.check24_challenge.common.base.BaseRepository
import com.esmaeel.check24_challenge.data.local.ProductsLocalDataSource
import com.esmaeel.check24_challenge.data.remote.ProductsRemoteDataSource
import com.esmaeel.check24_challenge.di.ContextProviders
import com.esmaeel.check24_challenge.di.ResourcesHandler
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ProductsRepository @Inject constructor(
    private val remoteDataSource: ProductsRemoteDataSource,
    private val localDataSource: ProductsLocalDataSource,
    contextProviders: ContextProviders,
    resourcesHandler: ResourcesHandler
) : BaseRepository(contextProviders, resourcesHandler) {

    fun getProductsListResponse() = networkHandler {
        remoteDataSource.getProducts()
    }

    fun isInFavorites(productId: Int) = localDataSource.isInFavorites(productId)

    fun addToFavorites(productId: Int) = localDataSource.addToFavorites(productId)

    fun removeFromFavorites(productId: Int) = localDataSource.removeFromFavorites(productId)
}