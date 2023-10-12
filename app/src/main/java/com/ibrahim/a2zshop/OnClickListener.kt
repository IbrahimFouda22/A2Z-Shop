package com.ibrahim.a2zshop

import com.example.domain.models.ProductModel

class OnClickListener(
    private val onClickListener: (productModel: ProductModel) -> Unit?,
    private val onClickButtonFavoriteListener: (id: Int) -> Unit,
    private val onClickButtonRemoveCartListener: ((id: Int) -> Unit)?,
    private val onClickButtonEditQtyCartListener: ((id: Int, quantity: Int) -> Unit)?
) {
    fun onClick(productModel: ProductModel) = onClickListener(productModel)
    fun onClickButtonFavorite(id: Int) = onClickButtonFavoriteListener(id)
    fun onClickButtonRemoveCart(id: Int) = onClickButtonRemoveCartListener?.let { it(id) }
    fun onClickButtonEditQtyCartListener(id: Int, quantity: Int) =
        onClickButtonEditQtyCartListener?.let { it(id, quantity) }
}