package com.esmaeel.check24_challenge.domain.usecases.products

import com.esmaeel.check24_challenge.domain.repositories.ProductsRepository
import com.esmaeel.check24_challenge.domain.usecases.UseCase
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RemoveProductToFavoriteUseCase @Inject constructor(
    private val repo: ProductsRepository
) : UseCase<Flow<Boolean>, Int> {
    override suspend fun invoke(inputType: Int): Flow<Boolean> {
        repo.removeFromFavorites(inputType)
        return flowOf(true)
    }
}