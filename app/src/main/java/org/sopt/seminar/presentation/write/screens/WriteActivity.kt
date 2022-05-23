package org.sopt.seminar.presentation.write.screens

import android.app.AlertDialog
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import org.sopt.seminar.R
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.read.screens.ReadActivity
import org.sopt.seminar.presentation.write.viewmodels.WriteViewModel
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

        initPictureAdapter()
        val galleryLauncher: ActivityResultLauncher<Intent> =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                if(it.resultCode == RESULT_OK && it.data != null) {
                    viewModel.imageList.observe(this, Observer {
                        pictureAdapter.submitList(it.toMutableList())
                        Log.e("viewmodel observe1","오류...")
                    })
                }
            }

        val requestPermissionLauncher =
            registerForActivityResult(ActivityResultContracts.RequestPermission()){
            isGranted : Boolean ->
            if(isGranted){
                val intent = Intent(Intent.ACTION_PICK)
                intent.type = MediaStore.Images.Media.CONTENT_TYPE
                galleryLauncher.launch(intent)
            }
        }

        binding.layoutCamera.setOnClickListener {
            when {
                ContextCompat.checkSelfPermission(
                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
                ) == PackageManager.PERMISSION_GRANTED -> {
                    val intent = Intent(Intent.ACTION_PICK)
                    intent.type = MediaStore.Images.Media.CONTENT_TYPE
                    galleryLauncher.launch(intent)
                }
                shouldShowRequestPermissionRationale(android.Manifest.permission.READ_EXTERNAL_STORAGE) -> {
                    AlertDialog.Builder(this)
                        .setTitle("권한 동의 필요")
                        .setMessage("프로필 사진 수정을 위해 갤러리 접근 권한이 필요합니다.")
                        .setPositiveButton("동의") { _, _ ->
                            requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                        }
                        .setNegativeButton("거부") { _, _ ->
                        }
                        .create()
                        .show()
                }
                else -> {
                    requestPermissionLauncher.launch(android.Manifest.permission.READ_EXTERNAL_STORAGE)
                }
            }
        }


        checkComplete()
        checkButtonComplete()
        goReadActivity()
        viewModel.imageList.observe(this, Observer {
            pictureAdapter.submitList(it.toMutableList())
            Log.e("viewmodel observe2","오류...")
        })
        changePriceColor()
        backClickEvent()
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
        if (binding.tvComplete.isClickable) {
            binding.tvComplete.setOnClickListener {
                val intent = Intent(this, ReadActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initPictureAdapter() {
        pictureAdapter = PictureAdapter()

    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}

