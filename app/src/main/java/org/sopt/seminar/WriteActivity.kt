package org.sopt.seminar

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import androidx.core.content.ContextCompat
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.home.PictureAdapter
import org.sopt.seminar.presentation.home.PictureData
import org.sopt.seminar.util.BaseActivity

class WriteActivity : BaseActivity<ActivityWriteBinding>(R.layout.activity_write) {
    private lateinit var pictureAdapter: PictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding.btnCheck.isEnabled = false
        initPictureAdapter()
        changePriceColor()
        changeSuggestButton()
        backClickEvent()
    }


    private fun changePriceColor() {
        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s?.length!! > 0) {
                    binding.tvWon.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.carrot_black
                        )
                    )
                    binding.tvSuggest.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.carrot_black
                        )
                    )
                    binding.btnCheck.isEnabled= true
                } else {
                    binding.tvWon.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.squaregray
                        )
                    )
                    binding.tvSuggest.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.squaregray
                        )
                    )
                    binding.btnCheck.isEnabled = false
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun changeSuggestButton() {
        binding.btnCheck.setOnClickListener(object : View.OnClickListener {
            var isClicked = false
            override fun onClick(v: View?) {
                isClicked = !isClicked
                if (isClicked) binding.btnCheck.setImageResource(R.drawable.ic_check)
                else binding.btnCheck.setImageResource(R.drawable.ic_no_check)
            }

        })
    }

    private fun initPictureAdapter() {
       val img = "https://images.velog.io/images/jojo_devstory/post/dae32386-bffc-40c3-b866-5c1e64516902/Android%2010_0.jpg"
        pictureAdapter = PictureAdapter()
        binding.rvPicture.adapter = pictureAdapter
        pictureAdapter.pictureList.addAll(
            listOf(
                PictureData(img),
                PictureData(img),
                PictureData(img),
                PictureData(img)
            )
        )
        pictureAdapter.notifyDataSetChanged()
    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}