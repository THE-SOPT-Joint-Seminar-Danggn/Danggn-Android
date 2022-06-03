package org.sopt.seminar.presentation.write.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Picture
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.result.registerForActivityResult
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import com.bumptech.glide.Glide
import org.sopt.seminar.R
import org.sopt.seminar.data.api.ServiceCreator
import org.sopt.seminar.data.model.response.RequestCreate
import org.sopt.seminar.data.model.response.ResponseCreate
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.read.screens.ReadActivity
import org.sopt.seminar.presentation.write.viewmodels.WriteViewModel
import org.sopt.seminar.util.enqueueUtil
import retrofit2.Call
import java.util.jar.Manifest

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private val viewModel by viewModels<WriteViewModel>()
    private lateinit var pictureAdapter: PictureAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.writeViewModel = viewModel
        binding.lifecycleOwner = this

        checkComplete()
        checkButtonComplete()
        goReadActivity()
        changePriceColor()
        backClickEvent()
        cameraClickEvent()
        goReadActivity()
    }

    private fun cameraClickEvent() {
        pictureAdapter = PictureAdapter()
        binding.rvPicture.adapter = pictureAdapter
        val galleryLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK && it.data?.data != null) {
                    val clipData = it?.data?.clipData
                    val clipDataSize = clipData?.itemCount
                    val clipDatalist = mutableListOf<PictureData>()
                    if (clipData == null) {
                        val selectedImageUri = it?.data?.data!!
                        clipDatalist.add(PictureData(selectedImageUri))
                    } else {
                        clipData.let { clipData ->
                            for (i in 0 until clipDataSize!!) {
                                val selectedImageUri = clipData.getItemAt(i).uri
                                clipDatalist.add(PictureData(selectedImageUri))
                            }
                        }
                    }
                    pictureAdapter.submitList(clipDatalist)
                    binding.tvPic.text = clipDataSize.toString()
                }
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()) { isGranted: Boolean ->
                if (isGranted) {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    galleryLauncher.launch(intent)
                } else {
                    Toast.makeText(this, "갤러리 접근 권한이 없습니다.", Toast.LENGTH_SHORT).show()
                }
            }

        binding.layoutCamera.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
                    intent.action = Intent.ACTION_GET_CONTENT
                    galleryLauncher.launch(intent)
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }
    }

    private fun checkComplete() {
        viewModel.title.observe(this) {
            viewModel.completeCheck()
        }
        viewModel.category.observe(this) {
            viewModel.completeCheck()
        }
        viewModel.price.observe(this) {
            viewModel.completeCheck()
        }
        viewModel.content.observe(this) {
            viewModel.completeCheck()
        }

        viewModel.isSuccess.observe(this) {
            if (it) {
                binding.tvComplete.isClickable = true
                binding.tvComplete.setTextColor(ContextCompat.getColor(this, R.color.orange))
            } else {
                binding.tvComplete.isClickable = false
                binding.tvComplete.setTextColor(ContextCompat.getColor(this, R.color.squaregray))
            }
        }
    }

    private fun checkButtonComplete() {
        viewModel.price.observe(this) {
            viewModel.completePriceCheck()
        }
        viewModel.isChecked.observe(this) {
            if (it) {
                var isCheckBoxFilled = false
                binding.btnCheck.setOnClickListener {
                    isCheckBoxFilled = !isCheckBoxFilled
                    if (isCheckBoxFilled) binding.btnCheck.setImageResource(R.drawable.ic_check)
                    else binding.btnCheck.setImageResource(R.drawable.ic_no_check)
                }
            } else {
                binding.btnCheck.setImageResource(R.drawable.ic_no_check)
                binding.btnCheck.isClickable = false
            }
        }
    }

    private fun changePriceColor() {
        binding.etPrice.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (!s.isNullOrEmpty()) {
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
                }
            }

            override fun afterTextChanged(s: Editable?) {
            }
        })
    }

    private fun goReadActivity() {

        binding.tvComplete.setOnClickListener {
            val requestCreate = RequestCreate(
                imageCount = pictureAdapter.currentList.size,
                title = binding.etTitle.text.toString(),
                category = binding.etCategory.text.toString(),
                price = binding.etPrice.text.toString().toInt(),
                contents = binding.etWrite.text.toString(),
                isPriceSuggestion = binding.btnCheck.isSelected
            )

            val call: Call<ResponseCreate> =
                ServiceCreator.createService.registerProduct(requestCreate)
            call.enqueueUtil(
                onSuccess = {
                    val intent = Intent(this, ReadActivity::class.java).apply{
                        putExtra("id",it.data.id)
                    }
                    startActivity(intent)
                    finish()
                },
                onError = {
                    when (it) {
                        400 -> Toast.makeText(this, "요청값을 처리할 수 없습니다.", Toast.LENGTH_SHORT).show()
                        500 -> Toast.makeText(this, "internal server error", Toast.LENGTH_SHORT)
                            .show()
                    }
                }
            )
        }

    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}

