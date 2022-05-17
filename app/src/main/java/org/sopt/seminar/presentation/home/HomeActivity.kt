package org.sopt.seminar.presentation.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import org.sopt.seminar.R
import org.sopt.seminar.databinding.ActivityHomeBinding

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initView()
    }

    private fun initView() {
        val homeFragment = HomeFragment()
        supportFragmentManager.beginTransaction().add(R.id.fragment_home, homeFragment)
            .commit()
    }

    private fun fabClickEvent() {
        binding.fabWrite.setOnClickListener {
            //TODO writeActivity로 intent 이동

        }
    }
}
