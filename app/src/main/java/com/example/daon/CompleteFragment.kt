package com.example.daon

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daon.R

class CompleteFragment : Fragment() {

    private lateinit var completeNameTextView: TextView
    private lateinit var sharedViewModel: SharedViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_complete, container, false)

        completeNameTextView = view.findViewById(R.id.complete_name_tv)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)

        completeNameTextView.text = sharedViewModel.profileName

        return view
    }
}
