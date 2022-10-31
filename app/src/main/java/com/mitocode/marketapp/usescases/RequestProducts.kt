package com.mitocode.marketapp.usescases

import com.mitocode.marketapp.data.repository.ProductRepository
import javax.inject.Inject

class RequestProducts @Inject constructor(private val productRepository: ProductRepository) {

    suspend operator fun invoke(categoryId: String) = productRepository.getProduct(categoryId)
}