package org.sopt.seminar.presentation.read.screens

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.bumptech.glide.Glide
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import org.sopt.seminar.R
import org.sopt.seminar.data.api.ServiceCreator
import org.sopt.seminar.data.model.request.RequestDetailOnSail
import org.sopt.seminar.data.model.response.ResponseDetailOnSale
import org.sopt.seminar.databinding.ActivityReadBinding
import org.sopt.seminar.util.BaseActivity
import org.sopt.seminar.util.enqueueUtil
import retrofit2.Call

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {

    private val arrTextViewId =
        arrayListOf(R.id.tv_title, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6)

    private lateinit var imageUrlList: List<String>
    private lateinit var imageViewPagerAdapter: ReadImageViewPagerAdapter
    private lateinit var readId: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initItemId()
        addReadList()
        setUpBottomSheet()
        setHeart()

    }

    private fun initItemId() {
        readId = intent.getStringExtra("id").toString()
    }

    private fun setUpBottomSheet() {
        val stateBottomSheetView = layoutInflater.inflate(R.layout.read_bottom_sheet, null)
        val stateBottomSheetDialog =
            BottomSheetDialog(this, R.style.DialogCustomTheme)
        val stateArray = resources.getStringArray(R.array.read_state_bottom_sheet_array)

        stateBottomSheetDialog.setContentView(stateBottomSheetView)
        setBottomSheetView(
            stateBottomSheetView,
            stateArray,
            stateBottomSheetDialog,
            binding.tvState
        )
        binding.clState.setOnClickListener {
            stateBottomSheetDialog.show()
        }
    }

    private fun setUpTabLayout() {
        TabLayoutMediator(binding.tlImages, binding.vpImages) { tab, position ->
        }.attach()
    }

    private fun setUpViewPager() {

        binding.vpImages.adapter = imageViewPagerAdapter

        binding.vpImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val currentPageIndex = 1
        binding.vpImages.currentItem = currentPageIndex

        setUpTabLayout()
    }

    private fun setBottomSheetView(
        bottomSheetView: View,
        arr: Array<String>,
        dialog: BottomSheetDialog,
        spinner: TextView
    ) {
        for (i in arr.indices) {
            val textView = bottomSheetView.findViewById<TextView>(arrTextViewId[i])
            textView.text = arr[i]
            textView.visibility = View.VISIBLE
            textView.setOnClickListener {
                spinner.text = arr[i]
                val requestDetailOnSail = RequestDetailOnSail(
                    id = readId,
                    onSale = i.toString()
                )
                val call: Call<ResponseDetailOnSale> =
                    ServiceCreator.readService.putReadOnSale(requestDetailOnSail)
                call.enqueueUtil(
                    onSuccess = {
                        dialog.dismiss()
                    },
                    onError = {
                        Log.e("putReadOnSale error", "상태 정보 변경 서버 오류")
                    }
                )
            }
        }
    }

    private fun setHeart() {
        var heart = false

        val call = ServiceCreator.readService

        binding.ivHeart.setOnClickListener {
            if (!heart) {
                heart = true
                binding.ivHeart.isSelected = true
                call.putReadLike(readId).enqueueUtil(
                    onSuccess = {
                        Log.e("like success", "서버성공이다")
                    }
                )
            } else {
                heart = false
                binding.ivHeart.isSelected = false
                call.putReadLike(readId).enqueueUtil(
                    onSuccess = {
                        Log.e("like success", "서버성공이다")
                    }
                )
            }
        }
    }

    private fun addReadList() {
        Log.e("read list ", "함수 들어옴")
        val call = ServiceCreator.readService

        call.getReadInfo(readId).enqueueUtil(
            onSuccess = {
                Log.e("read success", "서버성공이다")
                Log.e("id", readId)
                binding.detailData = it.data
                imageUrlList = it.data.image
                imageViewPagerAdapter = ReadImageViewPagerAdapter(imageUrlList)
                binding.ivHeart.isSelected = it.data.isLiked

                Glide.with(this)
                    .load(it.data.user.profile)
                    .error(resources.getDrawable(R.drawable.profile))
                    .into(binding.ivProfile)

                setUpViewPager()
            }
        )
    }
}