package com.app.movilbox.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.app.movilbox.data.database.dao.DaoProduct
import com.app.movilbox.data.database.entities.ProductDataRoom

@Database(entities = [ProductDataRoom::class], version = 1)
abstract class ProductDatabase : RoomDatabase() {
    abstract fun daoProduct(): DaoProduct
}