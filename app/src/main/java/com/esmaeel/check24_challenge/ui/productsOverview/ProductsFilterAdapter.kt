package com.esmaeel.check24_challenge.ui.productsOverview

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.esmaeel.check24_challenge.data.remote.models.products.FilterTypes
import com.esmaeel.check24_challenge.databinding.FilterItemBinding
import com.esmaeel.check24_challenge.utils.ktx.layoutInflator

// use selection library and add filter selection colors later
class ProductsFilterAdapter(
    private val onFilterClicked: (item: FilterTypes, position: Int) -> Unit,
) : ListAdapter<FilterTypes, ProductsFilterAdapter.ProductFilterItemViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil : DiffUtil.ItemCallback<FilterTypes>() {
        override fun areItemsTheSame(oldItem: FilterTypes, newItem: FilterTypes) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: FilterTypes, newItem: FilterTypes) =
            oldItem.type == newItem.type
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductFilterItemViewHolder {
        return ProductFilterItemViewHolder(
            FilterItemBinding.inflate(
                parent.layoutInflator,
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ProductFilterItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    inner class ProductFilterItemViewHolder(private val binder: FilterItemBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: FilterTypes) = with(binder) {
            root.setOnClickListener { onFilterClicked(item, absoluteAdapterPosition) }
            binder.filterTv.text = item.type
        }
    }

}