package org.sopt.seminar.presentation.write.screens

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import org.sopt.seminar.databinding.ItemPictureListBinding

class PictureAdapter : RecyclerView.Adapter<PictureAdapter.PictureViewHolder>() {
    private var _pictureList = mutableListOf<PictureData>()
    var pictureList: MutableList<PictureData> = _pictureList

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PictureViewHolder {
        val binding =
            ItemPictureListBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PictureViewHolder(binding)
    }

    private var pictureListCountListener: ((Int) -> Unit)? = null
    fun setPictureListCountListener(listener: ((Int) -> Unit)) {
        pictureListCountListener = listener
    }

    override fun onBindViewHolder(holder: PictureViewHolder, position: Int) {
        holder.onBind(_pictureList[position], position)
    }

    override fun getItemCount(): Int = pictureList.size


    inner class PictureViewHolder(
        private val binding: ItemPictureListBinding
    ) : RecyclerView.ViewHolder(binding.root) {
        fun onBind(data: PictureData, position: Int) {

            Glide.with(binding.root)
                .load(data.image)
                .into(binding.ivPicture)

            binding.ivClose.setOnClickListener {
                _pictureList.removeAt(position)
                pictureListCountListener?.invoke(itemCount)
                notifyDataSetChanged()
            }

        }


    }
}