package com.example.synergym.myorders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.example.synergym.R
import com.example.synergym.utils.getDate

class NewsDetailFragment : Fragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view =  inflater.inflate(R.layout.fragment_news_detail, container, false)
        val imageView = view.findViewById(R.id.news_image) as ImageView
        val leftIconTxt = view.findViewById(R.id.leftIconTxt) as TextView
        val titleTextView = view.findViewById(R.id.tilte_news) as TextView
        val authorTxt = view.findViewById(R.id.channel_name) as TextView
        val publishedText = view.findViewById(R.id.date) as TextView
        val descriptionTxt = view.findViewById(R.id.news_dec) as TextView
        leftIconTxt.visibility =View.VISIBLE
        leftIconTxt.setOnClickListener {
            activity?.onBackPressed()
        }
        imageView.apply {
            Glide.with(requireContext())
                .load(arguments?.getString("imageUrl"))
                .diskCacheStrategy(DiskCacheStrategy.ALL)
                .into(this)
        }
        var title  = arguments?.getString("title")
        title = title?.replace("\r\n".toRegex(), "")
        titleTextView.text = title
        authorTxt.text = arguments?.getString("author")
        publishedText.text = getDate(arguments?.getString("publishedAt"))

        var description  = arguments?.getString("description")
        description = description?.replace("\r\n".toRegex(), "")
        descriptionTxt.text = description
        return view
    }

}