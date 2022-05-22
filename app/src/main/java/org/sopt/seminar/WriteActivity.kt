package org.sopt.seminar

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import org.sopt.seminar.presentation.home.PictureAdapter
import androidx.activity.viewModels
import org.sopt.seminar.databinding.ActivityWriteBinding
import org.sopt.seminar.presentation.read.ReadActivity

class WriteActivity : AppCompatActivity() {
    private lateinit var binding: ActivityWriteBinding
    private val viewModel by viewModels<WriteViewModel>()
    private lateinit var pictureAdapter: PictureAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //viewModel
        binding.writViewModel = viewModel
        binding.lifecycleOwner = this

        checkComplete()
        goReadActivity()
        initPictureAdapter()
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
        binding.rvPicture.adapter = pictureAdapter
    }

    private fun backClickEvent() {
        binding.btnBack.setOnClickListener() {
            finish()
        }
    }

}

