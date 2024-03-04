package com.app.movilbox.domain.repository

import com.app.movilbox.domain.models.ProductModel

interface ProductRepositoryRoom {
    suspend fun getProducts(): List<ProductModel>
}