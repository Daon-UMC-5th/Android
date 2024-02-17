package com.example.daon

import android.annotation.SuppressLint
import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.daon.databinding.FragmentMypageBinding
import com.example.daon.mypage_api.ApiClient
import com.example.daon.mypage_api.MypageService
import com.example.daon.mypage_api.UserListResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MypageFragment : Fragment() {
    private var _binding: FragmentMypageBinding? = null
    private val binding get() = _binding!!

    @SuppressLint("SetTextI18n")
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMypageBinding.inflate(inflater, container, false)

            binding.profileName.text = "홍길동"
            binding.profileIntro.text = "안녕하세요. 좋은 모든 일들이 다 오기를!"

        binding.mypageNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Edit_ProfileActivity::class.java)
                startActivity(intent)
                activity?.supportFragmentManager?.popBackStack()
            }
        }
        binding.alarmNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, PagealarmActivity::class.java)
                startActivity(intent)
            }
        }
        binding.accessNext.setOnClickListener {
            activity?.let{
                val intent = Intent(context, AccessActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rule.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RulesActivity::class.java)
                startActivity(intent)
            }
        }
        binding.rule2.setOnClickListener {
            activity?.let{
                val intent = Intent(context, RulesActivity2::class.java)
                startActivity(intent)
            }
        }
        binding.postCount.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Posts_ViewActivity::class.java)
                startActivity(intent)
            }
        }
        binding.commentCount.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Comment_ViewActivity2::class.java)
                startActivity(intent)
            }
        }
        binding.scrapCount.setOnClickListener {
            activity?.let{
                val intent = Intent(context, Scrap_ViewActivity::class.java)
                startActivity(intent)
            }
        }
        fetchUserInfoFromServer()

        return binding.root
    }

    private fun fetchUserInfoFromServer() {
        val apiService = ApiClient.retrofit.create(MypageService::class.java)
        val call = apiService.getUsers()
        call.enqueue(object : Callback<UserListResponse> {
            override fun onResponse(
                call: Call<UserListResponse>,
                response: Response<UserListResponse>
            ) {
                if (response.isSuccessful) {
                    val userListResponse = response.body()
                    val users = userListResponse?.result
                    val nicknameget = arguments?.getString("nick")
                    val introget = arguments?.getString("intr")
                    users?.userNickname = nicknameget.toString()
                    users?.introduction = introget.toString()
                    binding.profileName.text =  users?.userNickname
                    binding.profileIntro.text =  users?.introduction
                }
            }

            override fun onFailure(call: Call<UserListResponse>, t: Throwable) {
                Log.e(TAG, "Failed to fetch user info: ${t.message}")
            }
        })
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}