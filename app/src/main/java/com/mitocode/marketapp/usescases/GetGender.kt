package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.GenderRepository
import javax.inject.Inject

class GetGender @Inject constructor(private val genderRepository: GenderRepository) {

    suspend operator fun invoke() = genderRepository.populateGenders()
}