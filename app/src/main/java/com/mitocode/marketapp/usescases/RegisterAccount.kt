package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.RegisterAccountRequest
import com.mitocode.marketapp.data.UserRepository
import javax.inject.Inject

class RegisterAccount @Inject constructor(private val userRepository: UserRepository) {

    suspend operator fun invoke(request: RegisterAccountRequest) = userRepository.createAccount(request)
}