package com.example.daon

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.daon.R

class CompleteFragment : Fragment() {

    private lateinit var completeNameTextView: TextView
    private lateinit var sharedViewModel: SharedViewModel
    private lateinit var complete: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_complete, container, false)

        completeNameTextView = view.findViewById(R.id.complete_name_tv)
        sharedViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        complete = view.findViewById(R.id.complete_start_btn)

        completeNameTextView.text = sharedViewModel.profileName

        complete.setOnClickListener {
            val intent = Intent(context, MainActivity::class.java)
            startActivity(intent)
        }

        return view
    }
}
