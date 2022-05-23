package org.sopt.seminar.presentation.home

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.databinding.ItemPictureListBinding

class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    //public val pictureList = mutableListOf<PictureData>()
    val pictureList = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding = ItemPictureListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.onBind(pictureList[position])
    }

    override fun getItemCount(): Int = pictureList.size

    class PictureViewHolder(
        private val binding: ItemPictureListBinding
    ) : RecyclerView.ViewHolder(binding.root){
        fun onBind(data: String){
            Glide.with(binding.root)
                .load(data)
                .into(binding.ivPicture)
        }
    }

}