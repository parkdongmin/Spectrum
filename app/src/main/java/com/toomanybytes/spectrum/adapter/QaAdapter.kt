package com.toomanybytes.spectrum.adapter

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.ContextWrapper
import android.content.Intent
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.model.FeedModel
import kotlin.math.roundToInt


class QaAdapter(
    var models: ArrayList<FeedModel>,
    var context: Context,
) : RecyclerView.Adapter<QaAdapter.AdapterViewHolder>() {

    private lateinit var v : View
    private lateinit var userProfile : View
    private lateinit var userName : TextView
    private lateinit var title : TextView
    private lateinit var image : ImageView

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.qa_item, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = models[position]

        if(models[position].image == null){
            holder.image.visibility = View.GONE
            val layoutParams = holder.itemView.findViewById<TextView>(R.id.feed_category).layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.leftMargin = 16.dpToPx(holder.itemView.context) // 여백 크기를 dpToPx 함수로 변환하여 설정
        }else{
            holder.image.visibility = View.VISIBLE
            models[position].image?.let { holder.image.setImageResource(it) }
        }

        holder.category.text = models[position].category
        holder.title.text = models[position].title
        holder.contents.text = models[position].contents
        holder.tags.text = models[position].tags
        holder.profile.setImageResource(models[position].profile)
        holder.name.text = models[position].name
        holder.handleName.text = models[position].handleName
        holder.comment.text = "${models[position].comment}"

        holder.profile.clipToOutline = true
        holder.image.clipToOutline = true

        // 마지막 아이템 여백 설정
        if (position == itemCount - 1) {
            val layoutParams = holder.itemView.findViewById<ConstraintLayout>(R.id.qa_con).layoutParams as ViewGroup.MarginLayoutParams
            layoutParams.bottomMargin = 16.dpToPx(holder.itemView.context) // 여백 크기를 dpToPx 함수로 변환하여 설정
        }

    }

    private fun getActivity(context: Context?): Activity {
        return if (context is Activity) {
            context
        } else {
            getActivity((context as ContextWrapper).baseContext)
        }
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.qa_image)
        val category: TextView = view.findViewById(R.id.feed_category)
        val title: TextView = view.findViewById(R.id.qa_title)
        val contents: TextView = view.findViewById(R.id.qa_contents)
        val tags: TextView = view.findViewById(R.id.qa_tags)
        val profile: ImageView = view.findViewById(R.id.qa_profile)
        val name: TextView = view.findViewById(R.id.qa_name)
        val handleName: TextView = view.findViewById(R.id.qa_handle_name)
        val comment: TextView = view.findViewById(R.id.qa_comment_count)
    }

    private fun Int.dpToPx(context: Context): Int {
        val density = context.resources.displayMetrics.density
        return (this * density).roundToInt()
    }

}