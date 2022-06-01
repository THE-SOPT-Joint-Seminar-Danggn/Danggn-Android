package org.sopt.seminar.presentation.write.screens

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.databinding.ItemPictureListBinding
import org.sopt.seminar.presentation.write.viewmodels.WriteViewModel

class PictureAdapter : ListAdapter<PictureData, PictureAdapter.PictureViewHolder>(diffUtil) {

    val writeViewModel = WriteViewModel()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding =
            ItemPictureListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        val writeactivity =
            ActivityWriteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding, writeactivity)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.onBind(currentList[position])
    }

    inner class PictureViewHolder(
        private val binding: ItemPictureListBinding,
        private val writeActivity: ActivityWriteBinding

    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PictureData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivPicture)
            val newList = currentList.toMutableList()
            binding.ivClose.setOnClickListener {
                Log.e("beforedelete", "${newList.size}")
                val position = currentList.indexOf(data)
                newList.removeAt(position)
                Log.e("beforesubmitList", "${newList.size}")
                submitList(newList)
                writeActivity.tvPic.setText(newList.size)
                Log.e("afterdelete", "${newList.size}")
                Log.e("write tvPic", "${writeActivity.tvPic.text}")
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