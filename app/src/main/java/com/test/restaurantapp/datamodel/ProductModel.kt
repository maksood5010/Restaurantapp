package com.test.restaurantapp.datamodel


/*
data class HomeResponse(val response: MutableList<ProductModel>)
*/

data class ProductModel(
    val id: String,

    val name: String,
    val offer: String,
    val description: String,
    val image_url: String
)