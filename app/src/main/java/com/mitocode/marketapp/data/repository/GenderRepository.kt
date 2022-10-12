package com.mitocode.marketapp.data.repository

import com.mitocode.marketapp.data.datasource.GenderRemoteDataSource
import javax.inject.Inject

class GenderRepository @Inject constructor(private val remoteGenderRemoteDataSource: GenderRemoteDataSource) {

    suspend fun populateGenders() = remoteGenderRemoteDataSource.populateGenders()

}