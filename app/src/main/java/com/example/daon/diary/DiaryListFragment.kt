package com.example.daon.diary

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daon.conect.ApiClient
import com.example.daon.conect.diary.DiaryGetOneResponseDto
import com.example.daon.conect.diary.DiaryGetResponseDto
import com.example.daon.conect.diary.data.DiaryGetPrivate
import com.example.daon.databinding.FragmentDiaryListBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DiaryListFragment : Fragment() {
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    private var _binding: FragmentDiaryListBinding? = null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentDiaryListBinding.inflate(inflater,container,false)
//        initPublicDiary()
        return binding.root
    }
//    private fun initPublicDiary(){
//        val call = ApiClient.diaryService.diaryGetPublic(jwt)
//        call.enqueue(object : Callback<DiaryGetResponseDto> {
//            override fun onResponse(call: Call<DiaryGetResponseDto>, response: Response<DiaryGetResponseDto>) {
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    // 서버로부터 받은 응답을 처리합니다.
//                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
//                    if (body != null) {
//                        Log.i("DiaryGetOneSuccess", body.toString())
//                        when (body.code) {
//                            200 -> {
//                                setDiaryList(body.result)
//                            }
//                            404 -> {
//                                showToast(body.message)
//                            }
//                            500 -> {
//                                showToast(body.message)
//                            }
//                        }
//                    }
//                } else {
//                    showToast("Failed to communicate with the server.")
//                    Log.i("DiaryGetOneNot",response.toString())
//                }
//            }
//            override fun onFailure(call: Call<DiaryGetResponseDto>, t: Throwable) {
//                showToast("Network request failed. Error: ${t.message}")
//                Log.i("DiaryGetOneFail",t.message.toString())
//            }
//        })
//    }
    private fun setDiaryList(data: List<DiaryGetPrivate>){
        var diaryListAdapter = DiaryListAdapter()
        diaryListAdapter.setList(data)
        binding.diaryListRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = diaryListAdapter
            diaryListAdapter.notifyDataSetChanged()
        }
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    override fun onDestroyView() {
        _binding = null
        super.onDestroyView()
    }
}