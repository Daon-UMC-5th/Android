package com.example.daon.diary

import android.animation.ObjectAnimator
import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import com.example.daon.MainActivity
import com.example.daon.R
import com.example.daon.databinding.ActivityDiaryEditBinding

class DiaryEditActivity : AppCompatActivity() {
    private lateinit var binding : ActivityDiaryEditBinding
    private var btnAble: Boolean = false
    private var selectedImageUri: Uri? = null
    private var imageBitmap: Bitmap? = null
    private var sharedPosition: String? = null
    private var saveSharedPosition: String? = null
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiaryEditBinding.inflate(layoutInflater)
        initTitle()
        clickListener()
        changeListener()
        setContentView(binding.root)
    }
    private fun initTitle(){
        val title: String =
            (intent.getIntExtra("year",0)%100).toString()+ "년 "+
                    (intent.getIntExtra("month",0)).toString()+ "월 " +
                    intent.getIntExtra("day",0).toString() + "일"
        binding.title.text = title
    }
    private fun clickListener(){
        binding.backBtn.setOnClickListener{
            if(btnAble){
                showCustomDialog()
            }else if(!btnAble){
                onBackPressed()
            }
        }
        binding.dialogNo.setOnClickListener{closeSnackBar()}
        binding.dialogYes.setOnClickListener{onBackPressed()}
        binding.saveBtn.setOnClickListener{ saveBtnClick()}
        binding.lock.setOnClickListener{ lockSnackBar() }
        binding.diaryImage.setOnClickListener{ openSnackBar() }
        binding.background.setOnClickListener{closeSnackBar()}
        binding.takePicture.setOnClickListener{
            openCamera()
            closeSnackBar()
        }
        binding.selectAlbum.setOnClickListener{
            openGallery()
            closeSnackBar()
        }
        binding.privateLock.setOnClickListener{lockSelect("private")}
        binding.shareLock.setOnClickListener{lockSelect("share")}
        binding.editBtn.setOnClickListener{
            saveSharedPosition = if(sharedPosition=="private"){
                "private"
            }else if(sharedPosition=="share"){
                "share"
            }else{
                null
            }
        }
    }
    private fun showCustomDialog(){
        binding.background.visibility=View.VISIBLE
        binding.cancelDialog.visibility=View.VISIBLE
    }
    private fun lockSelect(select:String){
        if(select=="private"){
            binding.shareRadioBtn.setImageResource(R.drawable.radio_false)
            binding.privateRadioBtn.setImageResource(R.drawable.radio_true)
            binding.editBtn.setImageResource(R.drawable.edit_btn_true)
            sharedPosition = "private"
        }else if(select=="share"){
            binding.shareRadioBtn.setImageResource(R.drawable.radio_true)
            binding.privateRadioBtn.setImageResource(R.drawable.radio_false)
            binding.editBtn.setImageResource(R.drawable.edit_btn_true)
            sharedPosition = "share"
        }
    }
    private fun lockSnackBar(){
        binding.background.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(binding.selectLockCardView, "translationY", -580f).apply { start() }
        ObjectAnimator.ofFloat(binding.editBtn, "translationY", -240f).apply { start() }
    }
    private fun openSnackBar(){
        binding.background.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", -360f).apply { start() }
    }
    private fun closeSnackBar(){
        ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", 0f).apply { start() }
        binding.background.visibility = View.GONE
        binding.cancelDialog.visibility = View.GONE
        ObjectAnimator.ofFloat(binding.selectLockCardView, "translationY", 0f).apply { start() }
        ObjectAnimator.ofFloat(binding.editBtn, "translationY", 0f).apply { start() }
    }
    private fun openCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            imageBitmap = data?.extras?.get("data") as Bitmap
            binding.diaryImage.setImageBitmap(imageBitmap)
        }
    }
    private fun changeListener(){
        with(binding) {
            val watcher = MyEditWatcher()

            diaryTitle.addTextChangedListener(watcher)

            diaryContent.addTextChangedListener(watcher)
        }
    }
    private val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            handleGalleryResult(data)
        }
    }
    private fun openGallery() {
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        getContent.launch(intent)
    }

    private fun handleGalleryResult(data: Intent?) {
        if (data != null) {
            selectedImageUri = data.data
            binding.diaryImage.setImageURI(selectedImageUri)

            // 여기에 선택한 이미지에 대한 추가 작업을 수행할 수 있습니다.
            // 예를 들어, 선택한 이미지의 URI를 변수에 저장하거나 다른 처리를 수행할 수 있습니다.
            // 변수에 저장하는 코드를 추가하려면 여기에 작성하세요.
        }
    }
    private fun saveBtnClick(){
        if(btnAble){
            //저장로직
        }else if(!btnAble){
            Toast.makeText(this,"사진을 첨부해주세요.",Toast.LENGTH_SHORT).show()
        }
    }
    inner class MyEditWatcher: TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int){
        }
        // 값 변경 시 실행되는 함수
        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            // 버튼 활성화 여부

        }
        override fun afterTextChanged(s: Editable?) {
            activateRegisterBtn()
        }
    }
    private fun activateRegisterBtn(){
        btnAble = if(title()||content()){
            binding.saveBtn.setImageResource(R.drawable.check_true)
            true
        }else{
            binding.saveBtn.setImageResource(R.drawable.check_false)
            false
        }
        binding.editTotal.text = binding.diaryContent.text.length.toString() + "/500"
    }
    private fun title():Boolean{
        return binding.diaryTitle.text.toString().trim().isNotEmpty()
    }
    private fun content():Boolean{
        return binding.diaryContent.text.toString().trim().isNotEmpty()
    }
    override fun onBackPressed() {
        super.onBackPressed()
        val intent = Intent(this, MainActivity::class.java)
        intent.putExtra("title","diary")
        intent.putExtra("type","private")
        startActivity(intent)
        finish()
    }
}