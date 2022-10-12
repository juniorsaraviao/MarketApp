package com.mitocode.marketapp.data

import com.mitocode.marketapp.data.remote.GenderRemoteDataSource
import javax.inject.Inject

class GenderRepository @Inject constructor(private val remoteGenderRemoteDataSource: GenderRemoteDataSource) {

    suspend fun populateGenders() = remoteGenderRemoteDataSource.populateGenders()

}