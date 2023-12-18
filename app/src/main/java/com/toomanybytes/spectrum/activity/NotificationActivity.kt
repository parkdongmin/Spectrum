package com.toomanybytes.spectrum.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityNotificationBinding
import com.toomanybytes.spectrum.fragment.ActionFragment
import com.toomanybytes.spectrum.fragment.FeedFragment
import com.toomanybytes.spectrum.fragment.NoticeFragment

class NotificationActivity : AppCompatActivity() {

    lateinit var binding : ActivityNotificationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityNotificationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val fragment = ActionFragment()
        supportFragmentManager.beginTransaction().replace(R.id.notFrameLayout, fragment).commit()
        binding.notBtn1.isChecked = true

        binding.notBtn1.setOnClickListener {
            // 활동 버튼 클릭시 프래그먼트 교체
            val actionFragment = ActionFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.notFrameLayout, actionFragment).commit()

            // 활동 버튼 활성화 및 공지사항 버튼 비활성화
            binding.notBtn1.isChecked = true
            binding.notBtn2.isChecked = false
        }

        binding.notBtn2.setOnClickListener {
            // 공지사항 버튼 클릭시 프래그먼트 교체
            val noticeFragment = NoticeFragment()
            supportFragmentManager.beginTransaction()
                .replace(R.id.notFrameLayout, noticeFragment).commit()

            // 공지사항 버튼 활성화 및 활동 버튼 비활성화
            binding.notBtn1.isChecked = false
            binding.notBtn2.isChecked = true
        }

        binding.notificationBackBtn.setOnClickListener {
            val intent= Intent( this,MainActivity::class.java)
            startActivity(intent)
            finish()
        }

    }
}