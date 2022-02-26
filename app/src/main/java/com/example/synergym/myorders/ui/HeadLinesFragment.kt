package com.example.synergym.myorders.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.synergym.databinding.FragmentHeadlinesBinding
import com.example.synergym.databinding.IncludeHeadlerBinding
import com.example.synergym.myorders.adapter.HeadLinesListAdapter
import com.example.synergym.myorders.data.NewsData
import com.example.synergym.myorders.repository.HeadLinesRepository
import com.example.synergym.myorders.viewmodel.HeadLinesViewModel
import com.example.synergym.myorders.viewmodelfactory.HeadLinesViewModelFactory
import com.example.synergym.network.Api
import com.example.synergym.network.NetworkConnectionIntercepter
import com.example.synergym.utils.*
import kotlinx.android.synthetic.main.fragment_headlines.*


class HeadLinesFragment : Fragment(), HeadLineListner {
    lateinit var myNewsListBinding: FragmentHeadlinesBinding
    lateinit var headLinesViewModel: HeadLinesViewModel
    private val res: MutableList<NewsData> = ArrayList()
    private var mLayoutManager: LinearLayoutManager? = null
    lateinit var headlerBinding: IncludeHeadlerBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val api =
            Api(networkConnectionIntercepter = NetworkConnectionIntercepter(requireActivity()))
        val repository = HeadLinesRepository(api)
        val factory = HeadLinesViewModelFactory(repository)
        myNewsListBinding = FragmentHeadlinesBinding.inflate(inflater, container, false)
        headLinesViewModel =
            ViewModelProviders.of(requireActivity(), factory).get(HeadLinesViewModel::class.java)
        myNewsListBinding.viewmodel = headLinesViewModel
        headLinesViewModel.headLineListner = this
        headlerBinding = myNewsListBinding.header
        headlerBinding.headerTitleTxt.labelsetText("HEADLINES")
        headlerBinding.headerTitleTxt.setTypeFaceRobotoBold(requireContext())
        headlerBinding.leftIconTxt.inVisible()
        headLinesViewModel.myNewsList(requireContext())
        headLinesViewModel.newsData.observe(viewLifecycleOwner, Observer {
            res.clear()
            myNewsListBinding.progressBar.hide()
            for (i in it.articles) {
                it.let { it1 -> res.addAll(listOf(it1)) }
            }
            initRecycleView()
        })
        return myNewsListBinding.root
    }

    override fun onSuccess(loginResponse: NewsData) {
//        res.clear()
//        for (i in loginResponse.articles) {
//            loginResponse.let { it1 -> res.addAll(listOf(it1)) }
//        }
    }

    private fun initRecycleView() {
        mLayoutManager = LinearLayoutManager(activity)
        order_rv?.let {
            it.layoutManager = mLayoutManager
            it.adapter =
                HeadLinesListAdapter(
                    requireContext(),
                    res,this
                )
        }
    }

    override fun onFailure(message: String) {
        myNewsListBinding.progressBar.hide()
        myNewsListBinding.myorderRootLay.snackbar(message)
    }

    override fun onStarted() {
        myNewsListBinding.progressBar.show()
    }

    override fun onCLick(pos:Int) {
        val bundle = Bundle()
        bundle.putString("title", res[pos].articles[pos].title)
        bundle.putString("imageUrl", res[pos].articles[pos].urlToImage)
        bundle.putString("author", res[pos].articles[pos].author)
        bundle.putString("publishedAt", res[pos].articles[pos].publishedAt)
        bundle.putString("description", res[pos].articles[pos].description)
        requireView().findNavController().navigate(com.example.synergym.R.id.detail_btn,bundle)

    }

    override fun onResume() {
        super.onResume()
        if (res.isNullOrEmpty()){
            headLinesViewModel.myNewsList(requireContext())
        }

    }
}