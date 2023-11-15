package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.GridLayoutManager
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.CurationAdapter
import com.toomanybytes.spectrum.databinding.FragmentCurationBinding
import com.toomanybytes.spectrum.model.CurationModel
import com.toomanybytes.spectrum.viewmodel.FeedViewModel

class CurationFragment : Fragment() {

    private lateinit var binding: FragmentCurationBinding
    private val viewModel: FeedViewModel by viewModels()

    private lateinit var curationAdapter: CurationAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_curation, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Spinner 설정
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_title, viewModel.dropDownItem.value!!)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        binding.filterSpinner.adapter = adapter

        // 모델 더미 값 넣기
        var models: List<CurationModel>
        models = ArrayList()

        models.add(
            CurationModel(
                R.drawable.ex_1
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_2
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_3
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_1
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_2
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_3
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_1
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_2
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_3
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_1
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_2
            )
        )

        models.add(
            CurationModel(
                R.drawable.ex_3
            )
        )

        // 어댑터 초기화 및 연결
        curationAdapter = CurationAdapter(models,requireContext()) // 초기 데이터는 빈 리스트로 시작
        binding.recyclerview.adapter = curationAdapter

        // 2열로 표시하기 위해 GridLayoutManager 사용
        val layoutManager = GridLayoutManager(requireContext(), 2)
        binding.recyclerview.layoutManager = layoutManager


        return binding.root
    }

}