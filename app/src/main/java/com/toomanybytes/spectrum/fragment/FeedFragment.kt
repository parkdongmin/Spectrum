package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.RecyclerView
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.FeedAdapter
import com.toomanybytes.spectrum.databinding.FragmentFeedBinding
import com.toomanybytes.spectrum.model.FeedModel
import com.toomanybytes.spectrum.viewmodel.FeedViewModel


class FeedFragment : Fragment() {
    private lateinit var binding: FragmentFeedBinding
    private val viewModel: FeedViewModel by viewModels()

    private lateinit var feedAdapter: FeedAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_feed, container, false)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        // Spinner 설정
        val adapter = ArrayAdapter(requireContext(), R.layout.spinner_title, viewModel.dropDownItem.value!!)
        adapter.setDropDownViewResource(R.layout.spinner_list)
        binding.filterSpinner.adapter = adapter

        // 모델 더미 값 넣기
        var models: List<FeedModel>
        models = ArrayList()

        models.add(
            FeedModel(
                R.drawable.ex_1,
                "잡담",
                "체포·구속·압수 또는 수색을 할 때에는",
                "포·구속·압수 또는 수색을 할 때에는 적법한 절차에 따라 검사의 신청에 의하여 법관이 발부한 영장을 제시하여야 한다. 다만, 현행범인인 경우와 ...",
                "#투메바 #멋쟁이 #방재현바보",
                R.drawable.sample,
                "DPark",
                "CDD_PDM",
                "1 시간전",
                19,
                20
            )
        )

        models.add(
            FeedModel(
                R.drawable.ex_2,
                "잡담",
                "새로운 회계연도가 개시될 때까지",
                "국가는 전통문화의 계승·발전과 민족문화의 창달에 노력하여야 한다. 대통령은 전시·사변 또는 이에 준하는 국가비상사태에 있어서 병력으로써 군사상의 ...",
                "#UI/UX #수염 #Animal",
                R.drawable.sample_2,
                "D_Kim",
                "Capybara_D",
                "1 시간전",
                40,
                8
            )
        )

        models.add(
            FeedModel(
                R.drawable.ex_3,
                "잡담",
                "건축물 외관의 각진 부분",
                "날렵하면서도 무게감이 느껴지고 너무 멋있지 않나요? 외장재가 어떻냐에 따라서도 그 느낌이 많이 달라져서 재밌는 것 같아요. 스펙트럼 유저분들은 ...",
                "#브랜딩 #열정 #용기",
                R.drawable.sample_3,
                "HBang",
                "Hyemin_Bang",
                "2 시간전",
                302,
                70
            )
        )

        // 어댑터 초기화 및 연결
        feedAdapter = FeedAdapter(models,requireContext()) // 초기 데이터는 빈 리스트로 시작
        binding.recyclerview.adapter = feedAdapter


        // 드롭다운 아이템 선택 리스너 설정
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

        return binding.root
    }
}
