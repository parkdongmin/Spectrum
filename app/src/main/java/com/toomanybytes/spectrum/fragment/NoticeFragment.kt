package com.toomanybytes.spectrum.fragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.toomanybytes.spectrum.R
import com.toomanybytes.spectrum.adapter.FeedAdapter
import com.toomanybytes.spectrum.adapter.NoticeAdapter
import com.toomanybytes.spectrum.databinding.FragmentNoticeBinding
import com.toomanybytes.spectrum.databinding.FragmentSearchBinding
import com.toomanybytes.spectrum.model.FeedModel
import com.toomanybytes.spectrum.model.NoticeModel

class NoticeFragment : Fragment() {

    private lateinit var binding: FragmentNoticeBinding
    private lateinit var noticeAdapter: NoticeAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNoticeBinding.inflate(layoutInflater)

        // 모델 더미 값 넣기
        var models: List<NoticeModel>
        models = ArrayList()

        models.add(
            NoticeModel(
                R.drawable.ex_4,
                "공지사항",
                "스펙트럼 사용자 분들, 안녕하세요!",
                "공지사항 자리를 빌려 인사드립니다! 스펙트럼이 서비스를 정식으로 시작하게 되었어요. 다사다난 했던 출시까지의 여정을 뒤로 한 채 드디어 이렇게",
                R.drawable.sample,
                "DPark",
                "CDD_PDM",
                "1 시간전",
            )
        )

        models.add(
            NoticeModel(
                R.drawable.ex_4,
                "공지사항",
                "스펙트럼 드디어 출시!",
                "출시 폼 미쳤다",
                R.drawable.sample_2,
                "D_Kim",
                "Capybara_D",
                "2 시간전",
            )
        )

        // 어댑터 초기화 및 연결
        noticeAdapter = NoticeAdapter(models,requireContext()) // 초기 데이터는 빈 리스트로 시작
        binding.noticeRecyclerview.adapter = noticeAdapter

        return binding.root


    }

}