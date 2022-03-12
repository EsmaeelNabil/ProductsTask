package com.esmaeel.check24_challenge.data.remote.models.products

import com.google.gson.annotations.SerializedName
import java.io.Serializable

data class ProductsListScreenResponse(
    @SerializedName("filters")
    val filters: List<String>? = listOf(),
    @SerializedName("header")
    val header: ProductListScreenHeader = ProductListScreenHeader(),
    @SerializedName("products")
    val products: List<ProductItem> = listOf()
)

data class ProductItem(
    @SerializedName("available")
    val available: Boolean = false,
    @SerializedName("color")
    val color: String? = "",
    @SerializedName("colorCode")
    val colorCode: String = "",
    @SerializedName("description")
    val description: String? = "",
    @SerializedName("id")
    val id: Int = 0,
    @SerializedName("imageURL")
    val imageURL: String? = "",
    @SerializedName("longDescription")
    val longDescription: String? = "",
    @SerializedName("name")
    val name: String? = "",
    @SerializedName("price")
    val price: ProductPrice? = ProductPrice(),
    @SerializedName("rating")
    val rating: Double? = 0.0,
    @SerializedName("releaseDate")
    val releaseDate: Long = 0,
    @SerializedName("type")
    val type: String? = ""
) : Serializable

data class ProductPrice(
    @SerializedName("currency")
    val currency: String? = "",
    @SerializedName("value")
    val value: Double? = 0.0
) : Serializable {
    fun getPrice() = "${(value ?: 0)} $currency"

}

data class ProductListScreenHeader(
    @SerializedName("headerDescription")
    val headerDescription: String? = "",
    @SerializedName("headerTitle")
    val headerTitle: String? = ""
) : Serializable