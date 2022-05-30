package org.sopt.seminar.presentation.home.screens

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toUri
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.R
import org.sopt.seminar.ResponseFeed
import org.sopt.seminar.databinding.ItemProductListBinding

class ProductAdapter : ListAdapter<ResponseFeed.Data, ProductAdapter.ProductViewHolder>(DIFFUTIL) {

    private lateinit var itemClickListener: OnItemClickListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProductViewHolder {
        val binding =
            ItemProductListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ProductViewHolder(binding)

    }

    override fun onBindViewHolder(holder: ProductViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    class ProductViewHolder(
        private val binding: ItemProductListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(responseFeed: ResponseFeed.Data) {
            binding.product = responseFeed
            Glide.with(binding.root)
                .load(responseFeed.image)
                .circleCrop()
                .into(binding.ivProduct)
        }
    }

    companion object {
        val DIFFUTIL = object : DiffUtil.ItemCallback<ResponseFeed.Data>() {
            override fun areItemsTheSame(
                oldItem: ResponseFeed.Data,
                newItem: ResponseFeed.Data
            ): Boolean {
                return oldItem.id == newItem.id
            }
            override fun areContentsTheSame(
                oldItem: ResponseFeed.Data,
                newItem: ResponseFeed.Data
            ): Boolean {
                return oldItem == newItem
            }
        }
    }

    interface OnItemClickListener {
        fun onClick(data: View, position: Int)
    }

    fun setItemClickListener(itemClickListener: OnItemClickListener) {
        this.itemClickListener = itemClickListener
    }
}