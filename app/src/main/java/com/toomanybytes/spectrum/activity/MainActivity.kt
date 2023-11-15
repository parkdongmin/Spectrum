package com.toomanybytes.spectrum.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityMainBinding
import com.toomanybytes.spectrum.fragment.CurationFragment
import com.toomanybytes.spectrum.fragment.FeedFragment
import com.toomanybytes.spectrum.fragment.MypageFragment
import com.toomanybytes.spectrum.fragment.SearchFragment
import com.toomanybytes.spectrum.fragment.WriteFragment

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        binding.navigationView.itemIconTintList = null
        binding.navigationView.run { setOnNavigationItemSelectedListener {
            when(it.itemId) {
                R.id.homeFragment -> {
                    // 다른 프래그먼트 화면으로 이동하는 기능
                    val homeFragment = FeedFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, homeFragment).commit()
                }
                R.id.searchFragment -> {
                    val searchFragment = SearchFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, searchFragment).commit()
                }
                R.id.writetFragment -> {
                    val writetFragment = WriteFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, writetFragment).commit()
                }
                R.id.curationFragment -> {
                    val curationFragment = CurationFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, curationFragment).commit()
                }
                R.id.mypageFragment -> {
                    val mypageFragment = MypageFragment()
                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, mypageFragment).commit()
                }
            }
            true
        }
            selectedItemId = R.id.homeFragment
        }

    }
}