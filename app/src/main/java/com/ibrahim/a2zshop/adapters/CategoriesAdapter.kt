package com.ibrahim.a2zshop.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.domain.models.EntireData
import com.ibrahim.a2zshop.databinding.ItemCategoryBinding

class CategoriesAdapter(private val onClickListener: OnClickListenerCategory) :
    RecyclerView.Adapter<CategoriesAdapter.ViewHolder>() {
    private lateinit var items: List<EntireData>

    class ViewHolder(private val itemCategoryBinding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(itemCategoryBinding.root) {
        fun bind(categoryItem: EntireData) {
            itemCategoryBinding.categoryItem = categoryItem
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener {
            onClickListener.onItemCategoryClickListener(items[position].id)
        }
    }

    override fun getItemCount(): Int {
        return items.size
    }

    fun setData(items: List<EntireData>) {
        this.items = items
    }
}

class OnClickListenerCategory(
    private val onItemClickListener: (categoryId: Int) -> Unit
) {
    fun onItemCategoryClickListener(categoryId: Int) = onItemClickListener(categoryId)
}