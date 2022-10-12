package com.mitocode.marketapp.data.datasource

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.server.GenderRemote
import com.mitocode.marketapp.data.server.RemoteService
import com.mitocode.marketapp.data.tryCall
import com.mitocode.marketapp.domain.Gender
import javax.inject.Inject

class GenderServerDataSource @Inject constructor(private val remoteService: RemoteService)
    : GenderRemoteDataSource {

    override suspend fun populateGenders(): Either<Error, List<Gender>> = tryCall {
        remoteService.getGenders().data!!.toDomainModel()
    }

    private fun List<GenderRemote>.toDomainModel(): List<Gender> = map { it.toDomainModel() }

    private fun GenderRemote.toDomainModel(): Gender = Gender(gender, description)

}