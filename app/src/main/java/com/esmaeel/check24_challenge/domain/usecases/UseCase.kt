package com.esmaeel.check24_challenge.domain.usecases

interface UseCase<ReturnType, InputType> {
    suspend fun invoke(inputType: InputType): ReturnType
}