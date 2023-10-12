package com.ibrahim.a2zshop.adapters

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.CartItems
import com.ibrahim.a2zshop.OnClickListener
import com.ibrahim.a2zshop.databinding.ItemCartBinding

class CartAdapter(private val onClickListener: OnClickListener) :
    RecyclerView.Adapter<CartAdapter.ViewHolder>() {
    private lateinit var items: List<CartItems>

    class ViewHolder(val itemCartBinding: ItemCartBinding) :
        RecyclerView.ViewHolder(itemCartBinding.root) {
        private lateinit var qty: String
        fun bind(cartItems: CartItems) {
            itemCartBinding.cartModel = cartItems
            itemCartBinding.executePendingBindings()
        }

        fun minus(cartItems: CartItems) {
//            itemCartBinding.cartModel!!.quantity--
            cartItems.quantity = --cartItems.quantity
            bind(cartItems)
        }

        fun plus(cartItems: CartItems) {
//            itemCartBinding.cartModel!!.quantity++
            cartItems.quantity = ++cartItems.quantity
            bind(cartItems)
        }

        fun iconFavoriteToggle(cartItems: CartItems) {
            cartItems.product.in_favorites = !cartItems.product.in_favorites
            bind(cartItems)

        }

        fun checkEditButton(cartItems: CartItems) {
            cartItems.difference = qty != cartItems.quantity.toString()
            Log.d(
                "checkEditButton",
                cartItems.difference.toString() + "Q1 : " + qty + " " + "Q2 : " + cartItems.quantity.toString()
            )
            bind(cartItems)
        }

        fun setQty(qty: String) {
            this.qty = qty
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCartBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.setQty(items[position].quantity.toString())
        holder.itemCartBinding.imgButtonMinusItemCart.setOnClickListener {
            holder.minus(items[position])
            holder.checkEditButton(items[position])
        }
        holder.itemCartBinding.imgButtonPlusItemCart.setOnClickListener {
            holder.plus(items[position])
            holder.checkEditButton(items[position])
        }
        holder.itemCartBinding.imgButtonItemCart.setOnClickListener {
            onClickListener.onClickButtonFavorite(items[position].product.id)
            holder.iconFavoriteToggle(items[position])
        }
        holder.itemCartBinding.btnRemoveCart.setOnClickListener {
            onClickListener.onClickButtonRemoveCart(items[position].product.id)
        }
        holder.itemCartBinding.imgButtonEditQty.setOnClickListener {
            onClickListener.onClickButtonEditQtyCartListener(
                items[position].id,
                items[position].quantity
            )
        }
        holder.itemView.setOnClickListener {
            onClickListener.onClick(items[position].product)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items: List<CartItems>) {
        this.items = items
    }
//    class CartDiffUtil : DiffUtil.ItemCallback<CartItems>() {
//        override fun areItemsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
//            return oldItem.id == newItem.id
//        }
//
//        override fun areContentsTheSame(oldItem: CartItems, newItem: CartItems): Boolean {
//            return oldItem == newItem
//        }
//
//    }

}
