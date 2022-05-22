package org.sopt.seminar.presentation.write

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.databinding.ItemPictureListBinding

class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    val pictureList = mutableListOf<PictureData>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding =
            ItemPictureListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.onBind(pictureList[position])
    }

    override fun getItemCount(): Int = pictureList.size

    class PictureViewHolder(
        private val binding: ItemPictureListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PictureData) {
            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivPicture)
        }
    }
}