package com.app.movilbox.domain.models

data class ProductModel(
    val id: Int = 0,
    val title: String = "",
    val description: String = "",
    val price: Long = 0,
    val discountPercentage: Double = 0.0,
    val rating: Double = 0.0,
    val stock: Int = 0,
    val brand: String = "",
    val category: String = "",
    val thumbnail: String = "",
    val images: List<String> = emptyList()
)
