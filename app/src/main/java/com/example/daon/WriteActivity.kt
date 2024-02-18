package com.example.daon

import android.Manifest
import android.animation.ObjectAnimator
import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues.TAG
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.daon.Adapter.GanRVAdapter
import com.example.daon.Adapter.YeeData
import com.example.daon.Adapter.YeeRVAdapter
import com.example.daon.data.community.PostWriteRequestDto
import com.example.daon.data.community.PostWriteResponseDto
import com.example.daon.data.community.token.PreferenceUtil
import com.example.daon.data.community.token.UploadResponse
import com.example.daon.databinding.ActivityWriteBinding
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.toRequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.ByteArrayOutputStream
import java.io.IOException

class WriteActivity : AppCompatActivity() {
    private lateinit var preferenceUtil: PreferenceUtil
    private val checkonResourceId = R.drawable.checkon
    private val checknoResourceId = R.drawable.checkno
    private lateinit var liverFragment: GanFragment
    private lateinit var stomachFragment: YeeFragment
    private lateinit var intestineFragment: DaeFragment
    private lateinit var breastFragment: YuuFragment
    private lateinit var wombFragment: JaeGFragment
    private lateinit var etcFragment: GitarFragment
    private lateinit var imageUrl: String
    private lateinit var binding: ActivityWriteBinding
    private var yeeitem: ArrayList<YeeData> = ArrayList()

    private val cameraLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data: Intent? = result.data
            val imageBitmap = data?.extras?.get("data") as Bitmap?
            imageBitmap?.let {
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.setImageBitmap(imageBitmap)
                moveEditTextDown()
            }
        } else {
            // 사용자가 카메라를 취소한 경우 또는 오류가 발생한 경우 처리
        }
    }
    private val galleryLauncher = registerForActivityResult(ActivityResultContracts.GetContent()) { uri ->
        uri?.let {
            try {
                val inputStream = contentResolver.openInputStream(uri)
                val selectedBitmap = BitmapFactory.decodeStream(inputStream)
                binding.imageView.visibility = View.VISIBLE
                binding.imageView.setImageBitmap(selectedBitmap)
                moveEditTextDown()
                uploadImageToServer(selectedBitmap)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityWriteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        preferenceUtil = PreferenceUtil(this)

        liverFragment = GanFragment()
        stomachFragment = YeeFragment()
        intestineFragment = DaeFragment()
        breastFragment = YuuFragment()
        wombFragment = JaeGFragment()
        etcFragment = GitarFragment()

        val fragment = supportFragmentManager.findFragmentById(R.id.yee_Rv) as? YeeFragment
        val fragment2 = supportFragmentManager.findFragmentById(R.id.yee_Rv) as? GanFragment
        val adapter = fragment?.let { YeeRVAdapter(yeeitem, it) }
        val adapter2 = fragment?.let { GanRVAdapter(yeeitem,it) }
        if (adapter != null) {
            fragment.setAdapter(adapter)
        }
        if (adapter2 != null) {
            fragment2?.setAdapter2(adapter2)
        }

        val boardType = intent.getStringExtra("boardType")?:""
        stomachFragment = YeeFragment()
        binding.writeBack.setOnClickListener {
            val currentResourceId = binding.accessCheck.tag as? Int ?: checknoResourceId

            if (currentResourceId == checkonResourceId) {
                // accessCheck 이미지가 checkon인 경우의 동작
                showConfirmationDialog()
            } else {
                // accessCheck 이미지가 checkon이 아닌 경우의 동작
                onBackPressedDispatcher.onBackPressed()
            }
        }

        binding.camera.setOnClickListener {
            onsnackbar()

            binding.background.setOnClickListener {
                backsnackbar()
            }
        }
        binding.takePicture.setOnClickListener {
            openCamera()
            backsnackbar()
        }
        binding.selectAlbum.setOnClickListener {
            openGallery()
            backsnackbar()
        }

        binding.accessCheck.setOnClickListener {
            val title = binding.titleWr.text.toString()
            val detail = binding.detailWr.text.toString()
            if (::imageUrl.isInitialized) {
                writePost(boardType, title, detail, imageUrl)
                finish()
            }
            else {
                writePost(boardType, title, detail, "")
                finish()
            }
        }

        val maxCharacters = 500

        binding.titleWr.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun afterTextChanged(p0: Editable?) {
                if (!p0.isNullOrEmpty()) {
                    binding.accessCheck.setImageResource(R.drawable.checkon)
                    binding.accessCheck.tag = R.drawable.checkon
                } else {
                    // 텍스트가 없다면 다시 기본 이미지로 변경
                    binding.accessCheck.setImageResource(R.drawable.checkno)
                    binding.accessCheck.tag = R.drawable.checkno
                }
            }
        })
        binding.detailWr.addTextChangedListener (object: TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}
            @SuppressLint("SetTextI18n")
            override fun afterTextChanged(p0: Editable?) {
                val remainingCharacters = maxCharacters - p0.toString().length
                binding.maxtext.text = "$remainingCharacters/$maxCharacters"

                if (!p0.isNullOrEmpty()) {
                    binding.accessCheck.setImageResource(R.drawable.checkon)
                    binding.accessCheck.tag = R.drawable.checkon
                } else {
                    // 텍스트가 없다면 다시 기본 이미지로 변경
                    binding.accessCheck.setImageResource(R.drawable.checkno)
                    binding.accessCheck.tag = R.drawable.checkno
                }
            }
        })
    }

    private fun moveEditTextDown() {
        ObjectAnimator.ofFloat(binding.detailWr, "translationY", 56f)
            .apply { start() }
    }
    private fun openGallery() {
        galleryLauncher.launch("image/*")
    }
    private fun onsnackbar() {
        binding.background.visibility = View.VISIBLE
        ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", -145f)
            .apply { start() }
    }
    private fun backsnackbar() {
        binding.background.visibility = View.GONE
        ObjectAnimator.ofFloat(binding.selectPictureCardView, "translationY", 0f)
            .apply { start() }
    }
    @SuppressLint("QueryPermissionsNeeded")
    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA)
            != PackageManager.PERMISSION_GRANTED) {
            // 카메라 권한이 없을 경우 권한 요청 다이얼로그 표시
            ActivityCompat.requestPermissions(this,
                arrayOf(Manifest.permission.CAMERA),
                CAMERA_PERMISSION_REQUEST_CODE
            )
        } else {
            val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            if (takePictureIntent.resolveActivity(packageManager) != null) {
                cameraLauncher.launch(takePictureIntent)
            }
        }
    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        when (requestCode) {
            Companion.CAMERA_PERMISSION_REQUEST_CODE -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    // 사용자가 카메라 권한을 승인한 경우 카메라 앱 실행
                    val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                    startActivityForResult(takePictureIntent, Companion.REQUEST_IMAGE_CAPTURE)
                } else {
                    // 사용자가 권한을 거부한 경우 메시지 또는 다른 작업 수행
                    Toast.makeText(this, "카메라 권한이 필요합니다.", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }
    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val CAMERA_PERMISSION_REQUEST_CODE = 100
    }
    private fun uploadImageToServer(bitmap: Bitmap) {
        val stream = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, stream)
        val byteArray = stream.toByteArray()
        val requestFile = byteArray.toRequestBody("image/*".toMediaTypeOrNull())
        val imagePart = MultipartBody.Part.createFormData("file", "image.jpg", requestFile)

        com.example.daon.data.community.ApiClient.boardService.uploadBoardImage(imagePart).enqueue(object : Callback<UploadResponse> {
            override fun onResponse(call: Call<UploadResponse>, response: Response<UploadResponse>) {
                if (response.isSuccessful) {
                    val uploadResponse = response.body()
                    if (uploadResponse != null && uploadResponse.isSuccess) {
                        imageUrl = uploadResponse.result.imageUrl
                    } else {
                        Log.e(TAG, "Failed to upload image: ${uploadResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to upload image: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<UploadResponse>, t: Throwable) {
                Log.e(TAG, "Failed to upload image: ${t.message}")
            }
        })
    }
    private fun writePost(boardType: String, title: String, detail: String, imageUrl: String) {
        val requestDto = PostWriteRequestDto(title, detail, imageUrl)

        val call = com.example.daon.data.community.ApiClient.boardService.writePost(boardType, requestDto)
        call.enqueue(object : Callback<PostWriteResponseDto> {
            override fun onResponse(
                call: Call<PostWriteResponseDto>,
                response: Response<PostWriteResponseDto>
            ) {
                if (response.isSuccessful) {
                    val postWriteResponse = response.body()
                    if (postWriteResponse != null && postWriteResponse.isSuccess) {
                        // 게시글 작성 성공
                        val newPost = postWriteResponse.result
                        val newPostData  = YeeData(
                            boardId = newPost.board_id,
                            nickname = preferenceUtil.getUserNickname().toString(), // 닉네임 설정 필요
                            detail = detail,
                            title = title,
                            timeAgo = "10 분전",
                            profileImage = newPost.image_url, // 프로필 이미지 설정 필요
                            favorIcon = R.drawable.favor1,  // 좋아요 아이콘 설정 필요
                            favorCount = newPost.likecount.toString(),
                            commentIcon = R.drawable.comment, // 댓글 아이콘 설정 필요
                            commentCount = newPost.commentcount.toString(),
                            bookmarkIcon = R.drawable.bookmark, // 북마크 아이콘 설정 필요
                            bookmarkCount = newPost.scrapecount.toString()
                        )
                        addNewPostToBoard(boardType, newPostData)
                        preferenceUtil.savePostId(newPost.board_id)
                        preferenceUtil.saveBoardType(newPost.board_type)
                    } else {
                        // 게시글 작성 실패
                        Log.e(TAG, "Failed to write post: ${postWriteResponse?.message}")
                    }
                } else {
                    Log.e(TAG, "Failed to write post: ${response.code()}")
                }
            }

            override fun onFailure(call: Call<PostWriteResponseDto>, t: Throwable) {
                Log.e(TAG, "Failed to write post: ${t.message}")
            }
        })
    }

    private fun addNewPostToBoard(boardType: String, newPostData: YeeData) {
        when (boardType) {
            "liver" -> {
                // 간암 게시판에 글을 추가하는 작업을 수행합니다.
                liverFragment.addNewPost(newPostData)
            }
            "stomach" -> {
                // 위암 게시판에 글을 추가하는 작업을 수행합니다.
                stomachFragment.addNewPost(newPostData)
            }
            "intestine" -> {
                // 대장 게시판에 글을 추가하는 작업을 수행합니다.
                intestineFragment.addNewPost(newPostData)
            }
            "breast" -> {
                // 유방암 게시판에 글을 추가하는 작업을 수행합니다.
                breastFragment.addNewPost(newPostData)
            }
            "womb" -> {
                // 자궁경부암 게시판에 글을 추가하는 작업을 수행합니다.
                wombFragment.addNewPost(newPostData)
            }
            "etc" -> {
                // 기타암 게시판에 글을 추가하는 작업을 수행합니다.
                etcFragment.addNewPost(newPostData)
            }
        }
    }

    private fun showConfirmationDialog() {
        val builder = AlertDialog.Builder(this)
        builder.setTitle("")
            .setMessage("작성을 취소하시겠습니까?")
            .setPositiveButton("예") { _, _ ->
                onBackPressedDispatcher.onBackPressed()
            }
            .setNegativeButton("아니오") { _, _ ->
                // 아니오 버튼을 클릭했을 때의 동작
                // 예: 다이얼로그 닫기 또는 특정 동작 수행
            }
            .show()
    }
}