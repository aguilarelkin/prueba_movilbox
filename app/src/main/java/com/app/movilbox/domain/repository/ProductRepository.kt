package com.app.movilbox.domain.repository

import com.app.movilbox.domain.models.ProductModel

interface ProductRepository {
    suspend fun getProducts(): List<ProductModel>
    suspend fun getProduct(id: String): ProductModel
    suspend fun getCategories(): List<String>
    suspend fun searchProduct(data: String): List<ProductModel>
    suspend fun addProduct(productModel: ProductModel): List<String>
    suspend fun updateProduct(productModel: ProductModel, id: String): List<String>
    suspend fun deleteProduct(id: String): ProductModel
}