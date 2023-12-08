package com.toomanybytes.spectrum.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.ViewModelProvider
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.dialog.PostBottomSheetDialog
import com.toomanybytes.spectrum.databinding.ActivityMainBinding
import com.toomanybytes.spectrum.dialog.SptBottomSheetDialog
import com.toomanybytes.spectrum.fragment.CurationFragment
import com.toomanybytes.spectrum.fragment.FeedFragment
import com.toomanybytes.spectrum.fragment.MypageFragment
import com.toomanybytes.spectrum.fragment.SearchFragment
import com.toomanybytes.spectrum.viewmodel.WriteViewModel

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private lateinit var viewModel: WriteViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)


        // 하단 탭이 눌렸을 때 화면을 전환하기 위해선 이벤트 처리하기 위해 BottomNavigationView 객체 생성
        binding.navigationView.itemIconTintList = null
        binding.navigationView.run {
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.homeFragment -> {
                        // 다른 프래그먼트 화면으로 이동하는 기능
                        val homeFragment = FeedFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, homeFragment).commit()
                    }

                    R.id.searchFragment -> {
                        val searchFragment = SearchFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, searchFragment).commit()
                    }

                    R.id.writetFragment -> {
                        writeBottomSheet()
                    }

                    R.id.curationFragment -> {
                        val curationFragment = CurationFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, curationFragment).commit()
                    }

                    R.id.mypageFragment -> {
                        val mypageFragment = MypageFragment()
                        supportFragmentManager.beginTransaction()
                            .replace(R.id.mainFrameLayout, mypageFragment).commit()
                    }
                }
                true
            }
            selectedItemId = R.id.homeFragment
        }


    }

    private fun writeBottomSheet() {
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
            showPostBottomSheetDialog()
        }
        bottomSheetView.findViewById<View>(R.id.spectrum_con).setOnClickListener {
            bottomSheetDialog.dismiss()
            showSptBottomSheetDialog()
        }

        // bottomSheetDialog 뷰 생성
        bottomSheetDialog.setContentView(bottomSheetView)
        // bottomSheetDialog 호출
        bottomSheetDialog.show()
    }

    private fun showPostBottomSheetDialog() {
        // PostBotttomSheet Class 를 불러옵니다
        viewModel = ViewModelProvider(this)[WriteViewModel::class.java]
        val postBottomSheetDialog = PostBottomSheetDialog(viewModel)
        postBottomSheetDialog.show(supportFragmentManager, "PostBottomSheetDialog")
    }

    private fun showSptBottomSheetDialog() {
        // SptBotttomSheet Class 를 불러옵니다
        viewModel = ViewModelProvider(this)[WriteViewModel::class.java]
        val sptBottomSheetDialog = SptBottomSheetDialog(viewModel)
        sptBottomSheetDialog.show(supportFragmentManager, "SptBottomSheetDialog")
    }

}
