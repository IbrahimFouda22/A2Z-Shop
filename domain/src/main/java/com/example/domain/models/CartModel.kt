package com.example.domain.models

data class CartModel(val status: Boolean, val message: String?, val data: CartData)

data class CartData(val cart_items: List<CartItems>, val sub_total: Double, val total: Double)

data class CartItems(val id: Int, var quantity: Int, val product: ProductModel) {
    var difference = false
}

data class CartModelAddToCart(val status: Boolean, val message: String?)

data class EditQtyModel(val status: Boolean, val message: String?, val cart: CartItems)