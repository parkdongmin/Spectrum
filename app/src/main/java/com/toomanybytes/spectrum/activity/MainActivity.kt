package com.toomanybytes.spectrum.activity

import android.app.Activity
import android.app.Dialog
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.databinding.ActivityMainBinding
import com.toomanybytes.spectrum.fragment.CurationFragment
import com.toomanybytes.spectrum.fragment.FeedFragment
import com.toomanybytes.spectrum.fragment.MypageFragment
import com.toomanybytes.spectrum.fragment.SearchFragment

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
//                    val writetFragment = WriteFragment()
//                    supportFragmentManager.beginTransaction().replace(R.id.mainFrameLayout, writetFragment).commit()
                    writeBottomSheet()
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

    private fun writeBottomSheet(){
        // 포스팅, 스펙트럼을 선택하는 바텀시트를 생성한다
        val bottomSheetDialog = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )

        // layout_bottom_sheet를 뷰 객체로 생성
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.layout_select_bottom_sheet,
            findViewById<ConstraintLayout>(com.google.android.material.R.id.design_bottom_sheet)
        )

        // bottomSheetDialog의 dismiss 버튼 선택시 dialog disappear
        bottomSheetView.findViewById<View>(R.id.post_con).setOnClickListener {
            bottomSheetDialog.dismiss()
            postBottomSheet()
        }
        bottomSheetView.findViewById<View>(R.id.spectrum_con).setOnClickListener {
            Log.d("spectrum_con","spectrum_con")
        }

        // bottomSheetDialog 뷰 생성
        bottomSheetDialog.setContentView(bottomSheetView)
        // bottomSheetDialog 호출
        bottomSheetDialog.show()
    }

    private fun postBottomSheet(){
        // 포스팅 디테일 바텀시트를 생성한다
        val bottomSheetDialog = BottomSheetDialog(
            this@MainActivity, R.style.BottomSheetDialogTheme
        )

        // layout_bottom_sheet를 뷰 객체로 생성
        val bottomSheetView = LayoutInflater.from(applicationContext).inflate(
            R.layout.layout_post_bottom_sheet,
            findViewById<ConstraintLayout>(com.google.android.material.R.id.design_bottom_sheet)
        )

        // bottomSheetDialog 뷰 생성
        bottomSheetDialog.setContentView(bottomSheetView)
        setupRatio(bottomSheetDialog)

        // bottomSheetDialog 호출
        bottomSheetDialog.show()
    }

    private fun setupRatio(bottomSheetDialog: BottomSheetDialog){
        // 기기 높이에 맞게 바텀시트를 설정하고 드래그 기능 또한 막는다.
        val bottomSheet = bottomSheetDialog.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet) as View
        val behavior = BottomSheetBehavior.from(bottomSheet)
        val layoutParams = bottomSheet!!.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        // 기기 높이 대비 비율 설정 부분!!
        // 위 수치는 기기 높이 대비 95%로 다이얼로그 높이를 설정
        return getWindowHeight() * 95 / 100
    }

    private fun getWindowHeight(): Int {
        // 기기 높이를 가져온다.
        val displayMetrics = DisplayMetrics()
        this.windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

}