package com.example.mypage2

import android.os.Bundle
import android.view.KeyEvent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.mypage2.databinding.FragmentEditProfileBinding

class EditProfileFragment : Fragment() {
    private val sharedViewModel: SharedViewModel by activityViewModels()
    private val sharedViewModel2: SharedViewModel2 by activityViewModels()
    private var _binding: FragmentEditProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentEditProfileBinding.inflate(inflater, container, false)

        binding.editNickname.hint = "홍길동"
        binding.editIntro.hint = "안녕하세요. 의료인으로서 열심히 답해드릴게요!"

        binding.backBtn.setOnClickListener {
            parentFragmentManager.popBackStack()
        }

        binding.editNickname.setText(sharedViewModel.getUserInput())
        binding.editIntro.setText(sharedViewModel2.getUserInput2())

        binding.editNickname.addTextChangedListener { editable ->
            val userInputText = editable?.toString() ?: ""
            sharedViewModel.setUserInput(userInputText)
            binding.editNickname.hint = userInputText
        }
        binding.editIntro.addTextChangedListener { editable ->
            val userInputText = editable?.toString() ?: ""
            sharedViewModel2.setUserInput2(userInputText)
            binding.editIntro.hint = userInputText
        }

        binding.editIntro.setOnEditorActionListener { _, _, event ->
            if ((event != null && event.action == KeyEvent.ACTION_DOWN &&
                        event.keyCode == KeyEvent.KEYCODE_ENTER)) {
                navigateToNextFragment()
                return@setOnEditorActionListener true
            }
            false
        }
        return binding.root
    }

    private fun navigateToNextFragment() {
        val nextFragment = MypageFragment()
        val transaction = parentFragmentManager.beginTransaction()
        transaction.replace(R.id.main_frm, nextFragment)
        transaction.addToBackStack(null)
        transaction.commit()
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}