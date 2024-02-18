package com.example.daon

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction


class NoResultFragment : Fragment() {

    private lateinit var reButton: Button

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_yes_result, container, false)

        reButton = view.findViewById(R.id.retry_btn)

        reButton.setOnClickListener {
            val noResultFragment = NoResultFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.find_id_page, noResultFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        return view
    }


}