package com.app.movilbox.data.response

import com.app.movilbox.domain.models.Product
import com.google.gson.annotations.SerializedName

data class ProductResponse(
    @SerializedName("products") val products: List<ProductDataResponse>
) {
    fun toDomain() = Product(products.map { it.toDomain() })
}