package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.tabs.TabLayoutMediator
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.CategoryAdapter
import com.toomanybytes.spectrum.adapter.CurationAdapter
import com.toomanybytes.spectrum.adapter.ViewPager2Adapter
import com.toomanybytes.spectrum.databinding.FragmentCurationBinding
import com.toomanybytes.spectrum.databinding.FragmentMypageBinding
import com.toomanybytes.spectrum.databinding.FragmentNoticeBinding
import com.toomanybytes.spectrum.model.CategoryModel
import com.toomanybytes.spectrum.model.CurationModel
import com.toomanybytes.spectrum.viewmodel.FeedViewModel

class MypageFragment : Fragment() {

    private lateinit var binding: FragmentMypageBinding
    private val viewModel: FeedViewModel by viewModels()

    private lateinit var categoryAdapter: CategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


        Log.d("asd","asdfffsssdddsssff")
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMypageBinding.inflate(layoutInflater)
        binding.userProfile.clipToOutline = true

        // 모델 더미 값 넣기
        var models: List<CategoryModel>
        models = ArrayList()

        models.add(
            CategoryModel(
                "브랜딩"
            )
        )

        models.add(
            CategoryModel(
                "타이포그래피"
            )
        )

        models.add(
            CategoryModel(
                "웹"
            )
        )

        models.add(
            CategoryModel(
                "UX/UI"
            )
        )

        models.add(
            CategoryModel(
                "무대예술"
            )
        )

        // 어댑터 초기화 및 연결
        categoryAdapter = CategoryAdapter(models,requireContext()) // 초기 데이터는 빈 리스트로 시작
        binding.recyclerview.adapter = categoryAdapter

        //가로 한줄로 쭉 뽑기
        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false)
        binding.recyclerview.layoutManager = layoutManager

        val adapter = ViewPager2Adapter(requireActivity())
        binding.vpViewpagerMain.adapter = adapter

        TabLayoutMediator(binding.tlNavigationView, binding.vpViewpagerMain) { tab, position ->
            when (position) {
                0 -> tab.text = "포스트 3"
                1 -> tab.text = "스크랩 2"
                2 -> tab.text = "스펙트럼 12"
            }
        }.attach()


        return binding.root

    }

}