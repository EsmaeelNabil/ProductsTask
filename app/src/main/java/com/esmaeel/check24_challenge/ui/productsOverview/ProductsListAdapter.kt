package com.esmaeel.check24_challenge.ui.productsOverview

import android.content.res.ColorStateList
import android.graphics.Color
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.esmaeel.check24_challenge.R
import com.esmaeel.check24_challenge.data.remote.models.products.UiProductItem
import com.esmaeel.check24_challenge.databinding.AvailableProductItemBinding
import com.esmaeel.check24_challenge.databinding.NotAvailableProductItemBinding
import com.esmaeel.check24_challenge.databinding.ProductListScreenFooterBinding
import com.esmaeel.check24_challenge.databinding.ProductListScreenHeaderBinding
import com.esmaeel.check24_challenge.utils.ktx.getDate
import com.esmaeel.check24_challenge.utils.ktx.layoutInflator

enum class ProductsListItemTypes(val type: Int) {
    HEADER(1),
    AVAILABLE_PRODUCT(2),
    NOT_AVAILABLE_PRODUCT(3),
    FOOTER(4)
}

class ProductsListAdapter(
    private val onItemClicked: (item: UiProductItem, position: Int) -> Unit,
) : ListAdapter<UiProductItem, RecyclerView.ViewHolder>(ItemDiffUtil) {

    private object ItemDiffUtil : DiffUtil.ItemCallback<UiProductItem>() {
        override fun areItemsTheSame(oldItem: UiProductItem, newItem: UiProductItem) =
            oldItem == newItem

        override fun areContentsTheSame(oldItem: UiProductItem, newItem: UiProductItem) =
            areItemsTheSame(oldItem, newItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            ProductsListItemTypes.HEADER.type -> HeaderViewHolder(
                ProductListScreenHeaderBinding.inflate(
                    parent.layoutInflator,
                    parent,
                    false
                )
            )

            ProductsListItemTypes.AVAILABLE_PRODUCT.type -> AvailableProductItemViewHolder(
                AvailableProductItemBinding.inflate(
                    parent.layoutInflator,
                    parent,
                    false
                )
            )

            ProductsListItemTypes.NOT_AVAILABLE_PRODUCT.type -> NotAvailableProductItemViewHolder(
                NotAvailableProductItemBinding.inflate(
                    parent.layoutInflator,
                    parent,
                    false
                )
            )

            ProductsListItemTypes.FOOTER.type -> FooterViewHolder(
                ProductListScreenFooterBinding.inflate(
                    parent.layoutInflator,
                    parent,
                    false
                )
            )

            // would be better to create an empty view for such cases.
            else -> NotAvailableProductItemViewHolder(
                NotAvailableProductItemBinding.inflate(
                    parent.layoutInflator,
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return getItem(position).itemType.type
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder) {
            is HeaderViewHolder -> holder.bind(getItem(position))
            is AvailableProductItemViewHolder -> holder.bind(getItem(position))
            is NotAvailableProductItemViewHolder -> holder.bind(getItem(position))
            is FooterViewHolder -> holder.bind(getItem(position))
        }
    }

    inner class AvailableProductItemViewHolder(private val binder: AvailableProductItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(item: UiProductItem) = with(binder) {
            root.setOnClickListener { onItemClicked(item, absoluteAdapterPosition) }
            binder.nameTv.text = item.product.name
            binder.image.load(item.product.imageURL)
            binder.image.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#${item.product.colorCode}"))
            binder.detailsTv.text = item.product.description
            binder.simpleRatingBar.rating = item.product.rating?.toFloat() ?: 0f
            binder.dateTv.text = item.product.releaseDate.getDate()
            binder.priceItSelfTv.text = item.product.price?.getPrice()

            binder.background.setBackgroundColor(
                if (item.product.available)
                    ContextCompat.getColor(binder.root.context, R.color.available_color)
                else Color.WHITE
            )

        }
    }

    inner class NotAvailableProductItemViewHolder(private val binder: NotAvailableProductItemBinding) :
        RecyclerView.ViewHolder(binder.root) {
        fun bind(item: UiProductItem) = with(binder) {
            root.setOnClickListener { onItemClicked(item, absoluteAdapterPosition) }
            binder.nameTv.text = item.product.name
            binder.image.load(item.product.imageURL)
            binder.image.imageTintList =
                ColorStateList.valueOf(Color.parseColor("#${item.product.colorCode}"))
            binder.detailsTv.text = item.product.description
            binder.simpleRatingBar.rating = item.product.rating?.toFloat() ?: 0f
        }
    }

    inner class HeaderViewHolder(private val binder: ProductListScreenHeaderBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: UiProductItem) = with(binder) {
            root.setOnClickListener { onItemClicked(item, absoluteAdapterPosition) }
            binder.titleTv.text = item.header.headerTitle
            binder.descriptionTv.text = item.header.headerDescription
        }
    }

    inner class FooterViewHolder(private val binder: ProductListScreenFooterBinding) :
        RecyclerView.ViewHolder(binder.root) {

        fun bind(item: UiProductItem) = with(binder) {
            root.setOnClickListener { onItemClicked(item, absoluteAdapterPosition) }
            binder.footerTv.text = item.footer.footerText
        }
    }


}