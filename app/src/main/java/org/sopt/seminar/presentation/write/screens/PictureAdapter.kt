package org.sopt.seminar.presentation.write.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.databinding.ItemPictureListBinding

class PictureAdapter : ListAdapter<PictureData, PictureAdapter.PictureViewHolder>(diffUtil) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding =
            ItemPictureListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class PictureViewHolder(
        private val binding: ItemPictureListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PictureData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivPicture)

            binding.ivClose.setOnClickListener {
                val newList = currentList.toMutableList()
                val position = currentList.indexOf(data)
                newList.removeAt(position)
                submitList(newList)

            }
        }
    }


    companion object {
        val diffUtil = object : DiffUtil.ItemCallback<PictureData>() {
            override fun areItemsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
                return oldItem.image == newItem.image
            }

            override fun areContentsTheSame(oldItem: PictureData, newItem: PictureData): Boolean {
                return oldItem == newItem
            }

        }
    }
}