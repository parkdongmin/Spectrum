package com.toomanybytes.spectrum.dialog

import android.content.ClipData
import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.ToggleButton
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.widget.addTextChangedListener
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.PhotoAdapter
import com.toomanybytes.spectrum.viewmodel.WriteViewModel

class PostBottomSheetDialog(private val viewModel: WriteViewModel) : BottomSheetDialogFragment() {

    private lateinit var adapter: PhotoAdapter

    // 이미지를 단일 선택인지 여러개 선택인지 체크하며, 등록한 이미지의 개수를 업데이트 합니다
    private val pickImages = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == AppCompatActivity.RESULT_OK) {
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
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // 레이아웃을 인플레이트하고 리사이클러뷰 어댑터 등을 초기화합니다.
        val bottomSheetView = inflater.inflate(
            R.layout.layout_post_bottom_sheet,
            container,
            false
        )

        adapter = PhotoAdapter(viewModel)

        // 포스트 바텀 시트의 초기화 코드를 추가합니다
        bottomSheetView.findViewById<RecyclerView>(R.id.recyclerview).layoutManager =
            LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        bottomSheetView.findViewById<RecyclerView>(R.id.recyclerview).adapter = adapter

        return bottomSheetView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = PhotoAdapter(viewModel)
        view.findViewById<RecyclerView>(R.id.recyclerview).layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        view.findViewById<RecyclerView>(R.id.recyclerview).adapter = adapter

        // setupRatio()를 호출하여 바텀 시트의 크기를 설정합니다.
        setupRatio()

        // 게시글 카테고리 반복문을 통해 버튼에 onclick 설정
        for (i in 1..3) {
            val buttonId = resources.getIdentifier("post_category_$i", "id", requireContext().packageName)
            view.findViewById<ToggleButton>(buttonId)?.setOnClickListener {
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
        viewModel.photoList.observe(viewLifecycleOwner) { photoList ->
            adapter.setPhotoList(photoList)
        }

        view.findViewById<EditText>(R.id.post_title).addTextChangedListener { text ->
            viewModel.updateTitleCount(text)
        }

        view.findViewById<EditText>(R.id.post_contents).addTextChangedListener { text ->
            viewModel.updateContentsCount(text)
        }

        viewModel.postCheck.observe(viewLifecycleOwner){
            view.findViewById<Button>(R.id.post_posting_btn).isEnabled = it
        }

        // ViewModel의 titleCount, contentsCount, image 를 관찰하여 UI 업데이트
        viewModel.titleCount.observe(viewLifecycleOwner) { titleCnt ->
            view.findViewById<TextView>(R.id.post_title_count).text = "$titleCnt/19"
        }

        viewModel.contentsCount.observe(viewLifecycleOwner) { contentsCnt ->
            view.findViewById<TextView>(R.id.post_contents_count).text = "$contentsCnt/300"
        }

        viewModel.selectedImageCount.observe(viewLifecycleOwner) { contentsCnt ->
            view.findViewById<TextView>(R.id.post_image_count).text = "${adapter.itemCount}/8"
        }

        // 이미지 추가 버튼 클릭 이벤트
        view.findViewById<TextView>(R.id.post_image_add).setOnClickListener {
            openGallery()
        }

        // 취소 버튼 클릭
        view.findViewById<Button>(R.id.post_cancel_btn).setOnClickListener {
            dismiss()
        }
    } // onViewCreated

    override fun onDismiss(dialog: DialogInterface) {
        super.onDismiss(dialog)
        // BottomSheetDialog가 숨겨질 때의 동작을 수행
        handleBottomSheetDismissed()
    }

    private fun openGallery() {
        // 갤러리 열기 기능을 구현합니다
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT)
        intent.addCategory(Intent.CATEGORY_OPENABLE)
        intent.type = "image/*"
        intent.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true)
        pickImages.launch(intent)
    }

    private fun setupRatio() {
        // 바텀 시트의 높이 비율을 설정합니다
        val bottomSheet = dialog?.findViewById<View>(com.google.android.material.R.id.design_bottom_sheet)
        val behavior = BottomSheetBehavior.from(bottomSheet!!)
        val layoutParams = bottomSheet.layoutParams
        layoutParams.height = getBottomSheetDialogDefaultHeight()
        bottomSheet.layoutParams = layoutParams
        behavior.state = BottomSheetBehavior.STATE_EXPANDED
        behavior.isDraggable = false
    }

    private fun getBottomSheetDialogDefaultHeight(): Int {
        // 바텀 시트의 기본 높이를 반환합니다
        return getWindowHeight() * 95 / 100
    }

    private fun getWindowHeight(): Int {
        // 디바이스의 높이를 가져옵니다
        val displayMetrics = DisplayMetrics()
        requireActivity().windowManager.defaultDisplay.getMetrics(displayMetrics)
        return displayMetrics.heightPixels
    }

    private fun categoryBtnClick(view: View, category: String) {
        // 카테고리의 클릭이벤트로 인한 UI 변환을 체크합니다
        if (view is ToggleButton) {
            val toggle = viewModel.addCategory(category)
            if (!toggle) {
                view.isChecked = false
            }
        }
    }

    private fun handleMultipleImages(clipData: ClipData) {
        // 여러개의 이미지 클릭시 8개로 제한합니다
        for (i in 0 until clipData.itemCount.coerceAtMost(8)) {
            val imageUri = clipData.getItemAt(i).uri
            viewModel.addPhoto(imageUri.toString())
        }
    }

    private fun handleBottomSheetDismissed() {
        // BottomSheetDialog가 숨겨질 때의 동작을 구현
        // 추가한 사진 리사이클러뷰 초기화 수행
        viewModel.clearRecyclerViewItems()
    }

}