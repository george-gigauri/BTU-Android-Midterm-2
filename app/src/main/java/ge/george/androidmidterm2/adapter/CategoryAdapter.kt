package ge.george.androidmidterm2.adapter

import android.graphics.Color
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagingDataAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import ge.george.androidmidterm2.R
import ge.george.androidmidterm2.databinding.ItemCategoryBinding
import ge.george.androidmidterm2.model.Category

class CategoryAdapter(
    private val listener: OnCategoryClickListener
) :
    PagingDataAdapter<Category, CategoryAdapter.VH>(diffCallback) {


    inner class VH(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Category?) {
            item?.let {
                binding.tvCategory.text = it.name
                binding.root.setOnClickListener {
                    binding.root.isSelected = !binding.root.isSelected
                    if (binding.root.isSelected) {
                        binding.root.setTextColor(Color.WHITE)
                    } else {
                        binding.root.setTextColor(binding.root.context.resources.getColor(R.color.teal_200))
                    }

                    listener.onCategoryClicked(item)
                }
            }
        }
    }

    override fun onBindViewHolder(holder: VH, position: Int) = holder.bind(getItem(position))

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VH =
        VH(ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false))

    companion object {
        private val diffCallback = object : DiffUtil.ItemCallback<Category>() {
            override fun areItemsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem

            override fun areContentsTheSame(oldItem: Category, newItem: Category): Boolean =
                oldItem == newItem
        }
    }

    interface OnCategoryClickListener {
        fun onCategoryClicked(item: Category)
    }
}