package com.toomanybytes.spectrum.activity

import android.app.Activity
import android.app.Dialog
import android.content.ClipData
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.DisplayMetrics
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.PhotoAdapter
import com.toomanybytes.spectrum.databinding.ActivityMainBinding
import com.toomanybytes.spectrum.fragment.CurationFragment
import com.toomanybytes.spectrum.fragment.FeedFragment
import com.toomanybytes.spectrum.fragment.MypageFragment
import com.toomanybytes.spectrum.fragment.SearchFragment
import com.toomanybytes.spectrum.viewmodel.WriteViewModel
import org.w3c.dom.Text

class MainActivity : AppCompatActivity() {

    lateinit var binding : ActivityMainBinding
    private lateinit var adapter: PhotoAdapter
    private lateinit var viewModel: WriteViewModel
    private val pickImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == RESULT_OK) {
            val clipData = result.data?.clipData
            val selectedImageCount = clipData?.itemCount ?: 0

            viewModel.updateSelectedImageCount(selectedImageCount)

            clipData?.let {
                handleMultipleImages(it)
            } ?: result.data?.data?.let {
                // Handle single image selection
                viewModel.addPhoto(it.toString())
            }
        }
    }

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

        viewModel = ViewModelProvider(this)[WriteViewModel::class.java]

        adapter = PhotoAdapter(viewModel)
        bottomSheetView.findViewById<RecyclerView>(R.id.recyclerview).layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        bottomSheetView.findViewById<RecyclerView>(R.id.recyclerview).adapter = adapter

        // 게시글 카테고리 반복문을 통해 버튼에 onclick 설정
        for (i in 1..3) {
            val buttonId = resources.getIdentifier("post_category_$i", "id", this.packageName)
            bottomSheetView.findViewById<ToggleButton>(buttonId)?.setOnClickListener {
                val categoryName = when (i) {
                    1 -> "잡담"
                    2 -> "Q&A"
                    3 -> "A/B"
                    else -> ""
                }
                categoryBtnClick(it, categoryName)
            }
        }

        // title, contents, image, postCheck 변경 감지
        viewModel.photoList.observe(this) { photoList ->
            adapter.setPhotoList(photoList)
        }

        bottomSheetView.findViewById<EditText>(R.id.post_title).addTextChangedListener { text ->
            viewModel.updateTitleCount(text)
        }

        bottomSheetView.findViewById<EditText>(R.id.post_contents).addTextChangedListener { text ->
            viewModel.updateContentsCount(text)
        }

        viewModel.postCheck.observe(this){
            bottomSheetView.findViewById<Button>(R.id.post_posting_btn).isEnabled = it
        }


        // ViewModel의 titleCount, contentsCount, image 를 관찰하여 UI 업데이트
        viewModel.titleCount.observe(this) { titleCnt ->
            bottomSheetView.findViewById<TextView>(R.id.post_title_count).text = "$titleCnt/19"
        }

        viewModel.contentsCount.observe(this) { contentsCnt ->
            bottomSheetView.findViewById<TextView>(R.id.post_contents_count).text = "$contentsCnt/300"
        }

        viewModel.selectedImageCount.observe(this) { contentsCnt ->
            bottomSheetView.findViewById<TextView>(R.id.post_image_count).text = "${adapter.itemCount}/8"
        }


        // 이미지 추가 버튼 클릭 이벤트
        bottomSheetView.findViewById<TextView>(R.id.post_image_add).setOnClickListener {
            openGallery()
        }

        // 취소 버튼 클릭
        bottomSheetView.findViewById<Button>(R.id.post_cancel_btn).setOnClickListener {
            bottomSheetDialog.dismiss()
        }

        // bottomSheetDialog 뷰 생성
        bottomSheetDialog.setContentView(bottomSheetView)
        setupRatio(bottomSheetDialog)

        // bottomSheetDialog 호출
        bottomSheetDialog.show()

        bottomSheetDialog.setOnDismissListener {
            // BottomSheetDialog가 숨겨질 때의 동작을 수행
            handleBottomSheetDismissed()
        }

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

    private fun openGallery() {
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickImages.launch(intent)
    }

    private fun handleMultipleImages(clipData: ClipData) {
        for (i in 0 until clipData.itemCount.coerceAtMost(8)) {
            val imageUri = clipData.getItemAt(i).uri
            viewModel.addPhoto(imageUri.toString())
        }
    }

    private fun handleBottomSheetDismissed() {
        // BottomSheetDialog가 숨겨질 때의 동작을 구현
        // 예를 들어, 리사이클러뷰 초기화 등을 수행할 수 있습니다.
        viewModel.clearRecyclerViewItems()
        Log.d("handleBottomSheetDismissed","handleBottomSheetDismissed")
    }

    private fun categoryBtnClick(view: View, category: String) {
        if (view is ToggleButton) {
            val toggle = viewModel.addCategory(category)
            if (!toggle) {
                view.isChecked = false
            }
        }
    }

}