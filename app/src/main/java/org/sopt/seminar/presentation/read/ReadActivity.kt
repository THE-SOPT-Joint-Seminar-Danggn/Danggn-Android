package org.sopt.seminar.presentation.read

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.tabs.TabLayoutMediator
import org.sopt.seminar.R
import org.sopt.seminar.databinding.ActivityReadBinding
import org.sopt.seminar.util.BaseActivity

class ReadActivity : BaseActivity<ActivityReadBinding>(R.layout.activity_read) {

    private val arrTextViewId =
        arrayListOf(R.id.tv_title, R.id.tv_1, R.id.tv_2, R.id.tv_3, R.id.tv_4, R.id.tv_5, R.id.tv_6)

    private lateinit var imageViewPagerAdapter: ReadImageViewPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        val imageUrlList = listOf(
            "https://cdn.newspenguin.com/news/photo/202006/1837_5156_215.jpg",
            "https://cdnweb01.wikitree.co.kr/webdata/editor/202107/09/img_20210709133526_b7af1a66.webp"
        )
        imageViewPagerAdapter = ReadImageViewPagerAdapter(imageUrlList)
        setUpViewPager()

        TabLayoutMediator(binding.tlImages, binding.vpImages) { tab, position ->
        }.attach()

        val stateBottomSheetView = layoutInflater.inflate(R.layout.read_bottom_sheet, null)
        val stateBottomSheetDialog =
            BottomSheetDialog(this, R.style.DialogCustomTheme) // dialog에 sytle 추가
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

        var heart = false
        binding.ivHeart.setOnClickListener {
            if (!heart) {
                heart = true
                binding.ivHeart.isSelected = true
            } else {
                heart = false
                binding.ivHeart.isSelected = false
            }

        }
    }

    private fun setUpViewPager() {

        binding.vpImages.adapter = imageViewPagerAdapter

        binding.vpImages.orientation = ViewPager2.ORIENTATION_HORIZONTAL

        val currentPageIndex = 1
        binding.vpImages.currentItem = currentPageIndex
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
                dialog.dismiss()
            }
        }
    }

}