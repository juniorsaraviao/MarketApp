package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.domain.Gender

interface GenderRemoteDataSource {

    suspend fun populateGenders(): Either<Error, List<Gender>>

}