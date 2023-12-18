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
import androidx.recyclerview.widget.RecyclerView
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.model.FeedModel
import com.toomanybytes.spectrum.model.NoticeModel

class NoticeAdapter(
    var models: ArrayList<NoticeModel>,
    var context: Context,
) : RecyclerView.Adapter<NoticeAdapter.AdapterViewHolder>() {

    private lateinit var v : View
    private lateinit var userProfile : View
    private lateinit var userName : TextView
    private lateinit var title : TextView
    private lateinit var image : ImageView

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.notice_item, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = models[position]
        holder.image.setImageResource(models[position].image)
        holder.category.text = models[position].category
        holder.title.text = models[position].title
        holder.contents.text = models[position].contents
        holder.profile.setImageResource(models[position].profile)
        holder.name.text = models[position].name
        holder.handleName.text = models[position].handleName

        holder.profile.clipToOutline = true

//        holder.itemView.setOnClickListener {
//            var actividad : Activity = getActivity(context)
//            Log.d("position","$position")
//            Intent(context, FeedDetailActivity::class.java).apply {
//                putExtra("image", "${models[position].image}")
//                putExtra("title", models[position].title)
//                putExtra("profile", "${models[position].profile}")
//                putExtra("name", models[position].name)
//                addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
//            }.run {
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//                    val Pair1 = Pair(holder.imageView,"image")
//                    val Pair2 = Pair(holder.titleView,"title")
//                    val Pair3 = Pair(holder.nameView,"user_name")
//                    val Pair4 = Pair(holder.profileView,"user_profile")
//                    val options : ActivityOptions = ActivityOptions.makeSceneTransitionAnimation(actividad,Pair1,Pair2,Pair3,Pair4)
//                    context.startActivity(this,options.toBundle())
//                } else {
//                    context.startActivity(this)
//                }
//            }
//        }
    }

    private fun getActivity(context: Context?): Activity {
        return if (context is Activity) {
            context
        } else {
            getActivity((context as ContextWrapper).baseContext)
        }
    }

    inner class AdapterViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val image: ImageView = view.findViewById(R.id.notice_image)
        val category: TextView = view.findViewById(R.id.notice_category)
        val title: TextView = view.findViewById(R.id.notice_title)
        val contents: TextView = view.findViewById(R.id.notice_contents)
        val profile: ImageView = view.findViewById(R.id.notice_profile)
        val name: TextView = view.findViewById(R.id.notice_name)
        val handleName: TextView = view.findViewById(R.id.notice_handle_name)
    }

}