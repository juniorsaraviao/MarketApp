package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.CategoryRepository
import javax.inject.Inject

class RequestCategories @Inject constructor(private val categoryRepository: CategoryRepository){

    suspend operator fun invoke() = categoryRepository.getCategories()
}