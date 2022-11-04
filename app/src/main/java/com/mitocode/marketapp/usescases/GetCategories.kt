package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.CategoryRepository
import javax.inject.Inject

class GetCategories @Inject constructor(private val categoryRepository: CategoryRepository) {

    operator fun invoke() = categoryRepository.categories

}