package com.mitocode.marketapp.usescases

import arrow.core.Either
import com.mitocode.marketapp.data.Error
import com.mitocode.marketapp.data.repository.UserRepository
import com.mitocode.marketapp.domain.User
import javax.inject.Inject

class RequestAuth @Inject
constructor(private val userRepository: UserRepository) {

    // not necessary to use requestAuth.invoke() - use requestAuth() instead
    suspend operator fun invoke(email: String, password: String, firebaseToken: String): Either<Error, User> {
        return userRepository.requestAuth(email, password, firebaseToken)
    }
}