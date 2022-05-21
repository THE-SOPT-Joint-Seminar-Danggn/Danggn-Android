package org.sopt.seminar

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import com.google.android.material.internal.ContextUtils.getActivity
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.home.PictureAdapter
import org.sopt.seminar.presentation.home.PictureData
import org.sopt.seminar.util.BaseActivity
import java.util.jar.Manifest

class WriteActivity : BaseActivity<ActivityWriteBinding>(R.layout.activity_write) {
    private lateinit var pictureAdapter: PictureAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        initPictureAdapter()
        changePriceColor()
        backClickEvent()


        binding.layoutCamera.setOnClickListener {
            val intent = Intent()
            intent.type = "image/*"
            intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
            intent.action = Intent.ACTION_PICK
            requestActivity.launch(intent)
        }



    }

    private val requestActivity =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == Activity.RESULT_OK && it.data?.data != null) { //갤러리 캡쳐 결과값
                val clipData = it?.data?.clipData
                val clipDataSize = clipData?.itemCount
                if (clipData == null) { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
                    val selectedImageUri = it?.data?.data!!
                    //TODO 얻어온 이미지 uri로 작업 진행
                    Log.e("selectedImageUri", "$selectedImageUri")


                } else {
                    clipData.let { clipData ->
                        for (i in 0 until clipDataSize!!) { //선택 한 사진수만큼 반복
                            val selectedImageUri = clipData.getItemAt(i).uri
                            val bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                            //pictureAdapter.pictureList.add(i, clipData.getItemAt(i).toString())
                            pictureAdapter.pictureList.add(i, bitmap.toString())
                            Log.e("selectedImageUri", "$selectedImageUri")
                        }
                    }
                }
                pictureAdapter.notifyDataSetChanged()
                Log.e("adapter list", "${pictureAdapter.itemCount}")
                Log.e("clip data size", "clipDataSize: $clipDataSize")
            }
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
                } else {
                    binding.tvWon.setTextColor(
                        ContextCompat.getColor(
                            this@WriteActivity,
                            R.color.squaregray
                        )
                    )
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun initPictureAdapter() {
        pictureAdapter = PictureAdapter()
        binding.rvPicture.adapter = pictureAdapter
    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener() {
            finish()
        }
    }

}
