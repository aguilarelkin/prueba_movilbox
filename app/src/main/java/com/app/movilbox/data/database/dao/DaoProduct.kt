package com.app.movilbox.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.app.movilbox.data.database.entities.ProductDataRoom

@Dao
interface DaoProduct {

    @Query("SELECT * FROM products")
    suspend fun getAllProducts(): List<ProductDataRoom>
}