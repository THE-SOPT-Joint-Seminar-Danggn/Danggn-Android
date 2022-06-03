package org.sopt.seminar.presentation.home.screens

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.seminar.util.BaseFragment
import org.sopt.seminar.R
import org.sopt.seminar.data.api.ServiceCreator
import org.sopt.seminar.data.model.response.ResponseDetail
import org.sopt.seminar.databinding.FragmentHomeBinding
import org.sopt.seminar.presentation.read.screens.ReadActivity
import org.sopt.seminar.util.enqueueUtil

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private lateinit var productAdapter: ProductAdapter

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.lifecycleOwner = this
        initAdapter()
        addRepoList()
    }
    private fun initAdapter() {
        val dividerItemDecoration =
            DividerItemDecoration(
                binding.rvProductList.context,
                LinearLayoutManager(requireContext()).orientation
            )
        binding.rvProductList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            addItemDecoration(dividerItemDecoration)
        }

        productAdapter = ProductAdapter{
            val intent = Intent(activity, ReadActivity::class.java)
            intent.apply {
                intent.putExtra("id",it.id)
            }
            startActivity(intent)
        }
        binding.rvProductList.adapter = productAdapter
    }


    private fun addRepoList() {
        val call = ServiceCreator.feedService.getFeedInfo()
        call.enqueueUtil(
            onSuccess = {
                Log.e("success","서버성공이다")
                productAdapter.submitList(it.data)
            }
        )

    }

}
