package org.sopt.seminar.presentation.home

import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import org.sopt.seminar.util.BaseFragment
import org.sopt.seminar.R
import org.sopt.seminar.databinding.FragmentHomeBinding

class HomeFragment : BaseFragment<FragmentHomeBinding>(R.layout.fragment_home) {
    private var productAdapter = ProductAdapter()

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
        binding.rvProductList.adapter = productAdapter
    }

    private fun addRepoList() {
        productAdapter.submitList(
            listOf(
                ProductData(
                    R.drawable.bookimage, "강아지 샘플 사료 판매합니다.","끌올 9초 전","개봉동","3,000"
                ),
                ProductData(
                    R.drawable.bookimage2, "마스크 판매합니다.","10초 전","구로구 신도림동","10,000"
                ),
                ProductData(
                    R.drawable.bookimage, "크림 세트 판매합니다.","15초 전","개봉동","9,000"
                ),
                ProductData(
                    R.drawable.bookimage2, "맥북 프로 중고 판매합니다.","끌올 20초 전","고척동","2,500,000"
                ),
                ProductData(
                    R.drawable.bookimage2, "헤드셋 판매합니다.","1분 전","개봉동","200,000"
                ),
                ProductData(
                    R.drawable.bookimage, "문제집 새 책 판매합니다.","2분 전","개봉동","13,000"
                ),
                ProductData(
                    R.drawable.bookimage2, "에어팟 오른쪽 판매합니다.","끌올 5분 전","개봉동","83,000"
                ),
                ProductData(
                    R.drawable.bookimage, "휴대폰 케이스 판매합니다.","19분 전","개봉동","12,000"
                )


            )
        )

    }

}
