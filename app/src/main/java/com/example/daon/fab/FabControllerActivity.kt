package com.example.daon.fab


import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.daon.MainActivity
import com.example.daon.OnEventListener
import com.example.daon.R
import com.example.daon.databinding.ActivityFabControllerBinding



class FabControllerActivity : AppCompatActivity(), OnEventListener {
    private lateinit var binding: ActivityFabControllerBinding
    private var flag = false

    interface OnSaveEventListener {
        fun onSaveEventOccurred(data: String)
    }
    private var eventListener: OnSaveEventListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityFabControllerBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initFragment()
        clickListener()
    }

    private fun clickListener() {
        binding.backBtn.setOnClickListener{onBackPressed()}
        binding.saveBtn.setOnClickListener{save()}
    }
    private fun save(){
        if (!flag) return
        else if(flag){
            when(intent.getStringExtra("fragment")){
                "진료" -> {
                    event("clinic")
                }
                "복용" -> {
                    event("dose")
                }
                "신체" -> {
                    event("body")
                }
            }
        }
    }
    private fun event(event:String){
        eventListener?.onSaveEventOccurred(event)
//        val fragment = ClinicFragment()
//        val intent = Intent(this,MainActivity::class.java)
//        intent.putExtra("title","clinic")
//        intent.putExtra("hospital",fragment.getHospitalText())
//        intent.putExtra("content",fragment.getContentText())
//        Log.i("hospital",fragment.getHospitalText())
//        startActivity(intent)
//        finish()
    }

    private fun initFragment(){
        when (intent.getStringExtra("fragment")) {
            "진료" -> {
                binding.title.text = "진료"
                selectFragment(ClinicFragment())
            }
            "복용" -> {
                binding.title.text = "복용"
                selectFragment(DoseFragment())
            }
            "신체" -> {
                binding.title.text = "신체"
                selectFragment(BodyFragment())
            }
        }
    }
    private fun selectFragment(fragment:Fragment){
        supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, fragment)
            .commit()
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
        finish()
    }

    override fun onEventOccurred(data: String) {
        when(data){
            "false" -> {
                binding.saveBtn.setImageResource(R.drawable.check_false)
                flag = false
            }
            "true" -> {
                binding.saveBtn.setImageResource(R.drawable.check_true)
                flag = true
            }
        }
    }
    fun setOnSomeEventListener(listener: OnSaveEventListener) {
        this.eventListener = listener
    }
}