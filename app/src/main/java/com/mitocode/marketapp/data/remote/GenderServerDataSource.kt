package com.mitocode.marketapp.data.remote

import arrow.core.Either
import com.mitocode.marketapp.Error
import com.mitocode.marketapp.data.Api
import com.mitocode.marketapp.data.GenderRemote
import com.mitocode.marketapp.data.RemoteService
import com.mitocode.marketapp.domain.Gender
import com.mitocode.marketapp.tryCall
import javax.inject.Inject

class GenderServerDataSource @Inject constructor(private val remoteService: RemoteService)
    : GenderRemoteDataSource {

    override suspend fun populateGenders(): Either<Error, List<Gender>> = tryCall {
        remoteService.getGenders().data!!.toDomainModel()
    }

    private fun List<GenderRemote>.toDomainModel(): List<Gender> = map { it.toDomainModel() }

    private fun GenderRemote.toDomainModel(): Gender = Gender(gender, description)

}