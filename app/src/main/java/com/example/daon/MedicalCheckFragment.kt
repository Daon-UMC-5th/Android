package com.example.daon

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.FrameLayout
import android.widget.ImageView
import com.example.daon.R


class MedicalCheckFragment : Fragment() {

    private lateinit var medicalSubmitButton: ImageView
    private lateinit var medicalBack: View
    private lateinit var medicalDialog: FrameLayout
    private lateinit var closeBtn: ImageView
    private lateinit var backBtn: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_medical_check, container, false)

        medicalSubmitButton = view.findViewById(R.id.medical_submit_btn)
        medicalBack = view.findViewById(R.id.medical_back)
        medicalDialog = view.findViewById(R.id.medical_dialog)
        closeBtn = view.findViewById(R.id.medical_close_btn)
        backBtn = view.findViewById(R.id.medical_back_btn)

        closeBtn.setOnClickListener {
            val intent = Intent(context, StartActivity::class.java)
            startActivity(intent)

            requireActivity().finish()
        }

        backBtn.setOnClickListener {
            val selfCertifyFragment = SelfCertifyFragment()
            val transaction = requireActivity().supportFragmentManager.beginTransaction()
            transaction.replace(R.id.fragment_medical_check, selfCertifyFragment)
            transaction.addToBackStack(null)
            transaction.commit()
        }

        medicalSubmitButton.setOnClickListener {
            medicalBack.visibility = View.VISIBLE
            medicalDialog.visibility = View.VISIBLE

            medicalBack.setOnClickListener {
                medicalBack.visibility = View.GONE
                medicalDialog.visibility = View.GONE
            }
        }

        return view
    }

}