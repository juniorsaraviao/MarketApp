package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.CategoryRepository
import com.mitocode.marketapp.data.server.RegisterCategoryRequest
import javax.inject.Inject

class RegisterCategory @Inject constructor(private val categoryRepository: CategoryRepository) {

    suspend operator fun invoke(request: RegisterCategoryRequest) = categoryRepository.saveCategory(request)

}