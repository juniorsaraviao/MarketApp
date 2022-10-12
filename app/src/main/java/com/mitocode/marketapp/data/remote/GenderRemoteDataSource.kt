package com.mitocode.marketapp.data.remote

import arrow.core.Either
import com.mitocode.marketapp.Error
import com.mitocode.marketapp.domain.Gender

interface GenderRemoteDataSource {

    suspend fun populateGenders(): Either<Error, List<Gender>>

}