package com.example.synergym.myorders.adapter

import android.content.Context
import android.graphics.Typeface
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.synergym.R
import com.example.synergym.myorders.data.NewsData
import com.example.synergym.myorders.ui.HeadLineListner
import com.example.synergym.utils.getDate
import com.example.synergym.utils.setTypeFaceRobotoBold
import com.example.synergym.utils.setTypeFaceRobotoRegular
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

class HeadLinesListAdapter(private val mContext: Context, private val data: List<NewsData>,private val onCLickListner:HeadLineListner) :
    RecyclerView.Adapter<HeadLinesListAdapter.CustomViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        val inflater = LayoutInflater.from(mContext)
        var view: View? = null
        view = inflater.inflate(R.layout.news_list, parent, false)
        return CustomViewHolder(view)
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        var item = data[position]
        Glide.with(mContext)
            .load(data[position].articles[position].urlToImage)
            .diskCacheStrategy(DiskCacheStrategy.ALL)
            .into(holder.newsImage)
        holder.newsTitle.setTypeFaceRobotoRegular(mContext)
        holder.author.setTypeFaceRobotoBold(mContext)
        if (item.articles[position].author!=null){
            holder.author.visibility = View.VISIBLE
            holder.author.text = item.articles[position].author
        }else holder.author.visibility = View.GONE
        holder.newsTitle.text = item.articles[position].title
        holder.publishedAt.text = getDate(item.articles[position].publishedAt)
        holder.cardList.setOnClickListener {
            onCLickListner.onCLick(position)
        }
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class CustomViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var newsTitle: TextView = itemView.findViewById(R.id.tilte_news)
        var author: TextView = itemView.findViewById(R.id.channel_name)
        var publishedAt: TextView = itemView.findViewById(R.id.date)
        var newsImage:ImageView = itemView.findViewById(R.id.news_image)
        var cardList:CardView = itemView.findViewById(R.id.card_news_list)
    }
}