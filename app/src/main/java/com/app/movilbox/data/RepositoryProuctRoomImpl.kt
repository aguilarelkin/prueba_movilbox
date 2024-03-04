package com.app.movilbox.data

import android.util.Log
import com.app.movilbox.data.database.dao.DaoProduct
import com.app.movilbox.domain.models.ProductModel
import com.app.movilbox.domain.repository.ProductRepositoryRoom
import javax.inject.Inject

/**
 * Function for DDBB
 */
class RepositoryProuctRoomImpl @Inject constructor(private val daoProduct: DaoProduct) :
    ProductRepositoryRoom {
    override suspend fun getProducts(): List<ProductModel> {
        runCatching { daoProduct.getAllProducts() }.onSuccess {
            return it.map { data -> data.toDomain() }
        }.onFailure { Log.i("DDBB", "Error: ${it.message}") }
        return emptyList()
    }

}