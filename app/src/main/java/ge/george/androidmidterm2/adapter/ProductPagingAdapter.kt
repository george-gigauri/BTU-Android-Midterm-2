package ge.george.androidmidterm2.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.signature.ObjectKey
import ge.george.androidmidterm2.databinding.ItemProductBinding
import ge.george.androidmidterm2.model.Product

class ProductPagingAdapter(
    private val listener: OnProductClickListener
) : PagingDataAdapter<Product, ProductPagingAdapter.VH>(diffCallback) {

    inner class VH(private val binding: ItemProductBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: Product?) {
            item?.let {
                binding.tvProductTitle.text = it.name
                binding.tvProductDescription.text = it.description
                binding.tvPrice.text = "${it.price}₾"

                Glide.with(binding.root.context.applicationContext)
                    .load(item.image)
                    .diskCacheStrategy(DiskCacheStrategy.ALL)
                    .signature(ObjectKey(item.id))
                    .into(binding.ivImage)

                binding.cl.setOnClickListener { listener.onProductClicked(item) }
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemProductBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Product>() {
            override fun areItemsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Product, newItem: Product): Boolean =
                oldItem == newItem
        }
    }

    interface OnProductClickListener {
        fun onProductClicked(item: Product)
    }
}