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
import com.toomanybytes.spectrum.model.CurationModel


class CurationAdapter(
    var models: ArrayList<CurationModel>,
    var context: Context,
) : RecyclerView.Adapter<CurationAdapter.AdapterViewHolder>() {

    private lateinit var v : View
    private lateinit var image : ImageView

    override fun getItemCount(): Int {
        return models?.size ?: 0
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterViewHolder {
        v = LayoutInflater.from(parent.context).inflate(R.layout.curation_item, parent, false)
        return AdapterViewHolder(v)
    }

    override fun onBindViewHolder(holder: AdapterViewHolder, position: Int) {
        val item = models[position]
        holder.image.setImageResource(models.get(position).image)


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
        val image: ImageView = view.findViewById(R.id.curation_img)
    }

}