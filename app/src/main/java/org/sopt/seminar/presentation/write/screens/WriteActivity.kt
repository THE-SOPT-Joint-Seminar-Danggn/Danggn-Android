package org.sopt.seminar.presentation.write.screens

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.sopt.seminar.R
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.read.screens.ReadActivity
import org.sopt.seminar.presentation.write.viewmodels.WriteViewModel

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
        binding.rvPicture.adapter = pictureAdapter

        checkComplete()
        checkButtonComplete()
        goReadActivity()
        initPictureAdapter()
        changePriceColor()
        backClickEvent()
        //cameraClickEvent()
        goReadActivity()
    }

//    private fun cameraClickEvent() {
//        pictureAdapter = PictureAdapter()
//        binding.rvPicture.adapter = pictureAdapter
//        val galleryLauncher: ActivityResultLauncher<Intent> =
//            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
//                if (it.resultCode == RESULT_OK) {
//                    if (it.data?.data != null) {
//                        Log.e("clip","clip2")
//                        val clipData = it?.data?.clipData
//                        val clipDataSize = clipData!!.itemCount
//
//                        for (i in 0 until clipDataSize) {
//                            val imageUrl = clipData!!.getItemAt(i).uri
//                            Log.e("clip","clip")
//                            pictureAdapter.currentList.add(PictureData(imageUrl.toString()))
//                        }
//                    } else { //이미지를 하나만 선택할 경우 clipData가 null이 올수 있음
//                        it?.data?.data?.let { uri ->
//                            val imageUrl: Uri? = it.data?.data
//                            if (imageUrl != null) {
//                                pictureAdapter.currentList.add(PictureData(imageUrl.toString()))
//                            }
//                        }
//
//                    }
//                }
//                pictureAdapter.submitList(pictureAdapter.currentList)
//            }
//        binding.layoutCamera.setOnClickListener {
//            when {
//                ContextCompat.checkSelfPermission(
//                    this, android.Manifest.permission.READ_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED -> {
//                    val intent = Intent(Intent.ACTION_PICK)
//                    intent.data = MediaStore.Images.Media.EXTERNAL_CONTENT_URI
//                    intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
//                    intent.action = Intent.ACTION_GET_CONTENT
//                    galleryLauncher.launch(intent)
//                }
//            }
//        }
//    }

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
        if (binding.tvComplete.isClickable) {
            binding.tvComplete.setOnClickListener {
                val intent = Intent(this, ReadActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun initPictureAdapter() {
        val img1 =
            "https://images.velog.io/images/jojo_devstory/post/dae32386-bffc-40c3-b866-5c1e64516902/Android%2010_0.jpg"
        val img2 =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRzvg63gbw4hewhMLLTqQIYQgm6H0MZ-ERTGg&usqp=CAU"
        val img3 =
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSDjsWqLzZTl4yB01g4JEhRIKbkrBPPrrBaWg&usqp=CAU"
        pictureAdapter = PictureAdapter()
        binding.rvPicture.adapter = pictureAdapter
        pictureAdapter.submitList(
            listOf(
                PictureData(img1),
                PictureData(img2),
                PictureData(img3)
            )
        )
    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener {
            finish()
        }
    }

}

