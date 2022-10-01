package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.UserRepository
import javax.inject.Inject

class RequestAuth @Inject
constructor(private val userRepository: UserRepository) {

    // not necessary to use requestAuth.invoke() - use requestAuth() instead
    suspend operator fun invoke(email: String, password: String, firebaseToken: String){
        return userRepository.requestAuth(email, password, firebaseToken)
    }
}