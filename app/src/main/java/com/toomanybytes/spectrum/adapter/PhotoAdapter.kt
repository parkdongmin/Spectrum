package com.toomanybytes.spectrum.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.activity.MainActivity
import com.toomanybytes.spectrum.model.PhotoModel
import com.toomanybytes.spectrum.viewmodel.WriteViewModel

class PhotoAdapter(private val viewModel: WriteViewModel) : RecyclerView.Adapter<PhotoAdapter.ViewHolder>() {

    private var photoList = listOf<PhotoModel>()

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val photoImageView: ImageView = itemView.findViewById(R.id.photoImageView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.photo_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView.context)
            .load(photoList[position].photoUrl)
            .into(holder.photoImageView)

        holder.photoImageView.clipToOutline = true

        // photoImageView를 클릭했을 때의 동작
        holder.photoImageView.setOnClickListener {
            // 해당 아이템을 삭제
            removeItem(position)
            // WriteViewModel의 메서드를 호출하여 데이터 업데이트
            viewModel.updateSelectedImageCount(photoList.size)
            viewModel.removePhoto(position)
        }

    }

    override fun getItemCount(): Int {
        return photoList.size
    }

    fun setPhotoList(photoList: List<PhotoModel>) {
        this.photoList = photoList
        notifyDataSetChanged()
    }

    // 아이템을 삭제하는 메서드
    private fun removeItem(position: Int) {
        if (position in 0 until photoList.size) {
            photoList = photoList.toMutableList().apply {
                removeAt(position)
            }

            notifyDataSetChanged() // 모든 아이템을 새로 고침

            // 삭제 후 아이템이 없다면 전체 갱신
            if (photoList.isEmpty()) {
                notifyDataSetChanged()
            }
        }
    }

}