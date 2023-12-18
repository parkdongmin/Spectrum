package com.toomanybytes.spectrum.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ToggleButton
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.viewModels
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.activity.NotificationActivity
import com.toomanybytes.spectrum.adapter.FeedAdapter
import com.toomanybytes.spectrum.databinding.FragmentChatFeedBinding
import com.toomanybytes.spectrum.databinding.FragmentFeedBinding
import com.toomanybytes.spectrum.fragment.feed.AbFeedFragment
import com.toomanybytes.spectrum.fragment.feed.ChatFeedFragment
import com.toomanybytes.spectrum.fragment.feed.QaFeedFragment
import com.toomanybytes.spectrum.model.FeedModel
import com.toomanybytes.spectrum.viewmodel.FeedViewModel


class FeedFragment : Fragment() {

    private lateinit var binding: FragmentFeedBinding
    private val viewModel: FeedViewModel by viewModels()

    // 선택한 카테고리 필터 버튼
    private var selectedCategoryId: Int = 1


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        binding = FragmentFeedBinding.inflate(layoutInflater)

        // 기본적으로 feed_category_1이 선택되도록 초기화
        selectedCategoryId = resources.getIdentifier("feed_category_1", "id", requireContext().packageName)
        categoryBtnClick(selectedCategoryId)

        // 필터 카테고리 버튼 초기화
        setupCategoryButtons()
        // 피드 정렬 스피너 초기화
        setupSpinner()

        binding.feedNotificationBtn.setOnClickListener {
            val intent= Intent( activity,NotificationActivity::class.java)
            startActivity(intent)
        }

        return binding.root
    }

    private fun setupSpinner() {
        // Spinner 설정
        // 정렬하는 스피너 초기화 함수
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_title, viewModel.dropDownItem.value!!)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        binding.filterSpinner.adapter = adapter

        binding.filterSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                val selectedItem = parent?.getItemAtPosition(position) as? String
                selectedItem?.let {
                    viewModel.onItemSelected(it)
                }
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {
                // 아무것도 선택되지 않았을 때의 동작
            }
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    private fun setupCategoryButtons() {
        // 필터 버튼 클릭 이벤트 만들기
        for (i in 1..3) {
            val buttonId = resources.getIdentifier("feed_category_$i", "id", requireContext().packageName)
            binding.root.findViewById<ToggleButton>(buttonId)?.setOnClickListener {
                categoryBtnClick(buttonId)
            }
        }
    }

    private fun categoryBtnClick(categoryId: Int) {
        // 선택된 필터 버튼 클릭 이외에 버튼 해제하기
        if (selectedCategoryId != 0) {
            binding.root.findViewById<ToggleButton>(selectedCategoryId)?.isChecked = false
        }

        when (categoryId) {
            R.id.feed_category_1 -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.feedFrameLayout, ChatFeedFragment())
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
            }
            R.id.feed_category_2 -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.feedFrameLayout, QaFeedFragment())
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
            }
            R.id.feed_category_3 -> {
                childFragmentManager.beginTransaction()
                    .replace(R.id.feedFrameLayout, AbFeedFragment())
                    .setTransition(FragmentTransaction.TRANSIT_NONE)
                    .commit()
            }
        }

        binding.root.findViewById<ToggleButton>(categoryId)?.isChecked = true
        selectedCategoryId = categoryId
    }
}
