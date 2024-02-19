package com.example.daon.main

import android.animation.ObjectAnimator
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.daon.EventDecorator
import com.example.daon.MyApplication
import com.example.daon.R
import com.example.daon.bottomsheet.BodyAdapter
import com.example.daon.bottomsheet.ClinicAdapter
import com.example.daon.bottomsheet.DoseAdapter
import com.example.daon.conect.ApiClient
import com.example.daon.conect.calendar.BodyListCallResponseDto
import com.example.daon.conect.calendar.ClinicListCallResponseDto
import com.example.daon.conect.calendar.DoseAllListCallResponseDto
import com.example.daon.conect.calendar.DoseListCallResponseDto
import com.example.daon.conect.calendar.DoseMonthListResponseDto
import com.example.daon.conect.calendar.data.BodyListCall
import com.example.daon.conect.calendar.data.ClinicListCall
import com.example.daon.conect.calendar.data.DoseListCall
import com.example.daon.conect.calendar.data.Month
import com.example.daon.databinding.FragmentCalendarBinding
import com.example.daon.fab.BodyActivity
import com.example.daon.fab.ClinicActivity
import com.example.daon.fab.DoseActivity
import com.prolificinteractive.materialcalendarview.CalendarDay
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.text.SimpleDateFormat
import java.util.Collections
import java.util.Date
import java.util.Locale
import java.util.Objects

class CalendarFragment : Fragment(){
    private var _binding: FragmentCalendarBinding? = null
    private var jwt: String = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VyX2lkIjoxMywiaWF0IjoxNzA3NzIwMDY2LCJleHAiOjE3MDgzMjQ4NjYsInN1YiI6InVzZXJJbmZvIn0.8oDPW4Z_Mifj7NwEbO517W9xprRGKbNSU5TUl6sjnc4"
    private val binding get() = _binding!!

    private lateinit var doseAdapter: DoseAdapter
    private lateinit var clinicAdapter: ClinicAdapter
    private lateinit var bodyAdapter: BodyAdapter

    private lateinit var doseList : MutableList<DoseListCall>

    private var selectedDate : String = ""

    private var isFabOpen = false
    private var year: Int = 0
    private var month: Int = 0
    private var day: Int = 0

    private var bottomSheetState = false

    private var noticeCnt: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCalendarBinding.inflate(inflater, container, false)
        doseAdapter = DoseAdapter(requireContext())
        bodyAdapter = BodyAdapter(requireContext())
        clinicAdapter = ClinicAdapter(requireContext())
        doseList = arrayListOf()
        initNotice()
        initCalendar()
        clickListener()

        return binding.root
    }
    //캘린더 UI 초기 설정 함수
    private fun initCalendar(){
        year = MyApplication.preferences.getDate(CALENDAR_YEAR,CalendarDay.today().year)
        month = MyApplication.preferences.getDate(CALENDAR_MONTH,CalendarDay.today().month+1)
        day = MyApplication.preferences.getDate(CALENDAR_DAY,CalendarDay.today().day)
        var dateString: String = "$year-$month-$day"//MyApplication.preferences.getDate(CALENDAR_DATE,CalendarDay.today().toString())
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date: Date = dateFormat.parse(dateString)
        binding.calendarView.selectedDate = CalendarDay.from(date)
        binding.calendarView.setCurrentDate(CalendarDay.from(date), true)
        Log.i("date",date.toString())
        binding.bottomSheetDate.text = "$year 년 $month 월 $day 일"
        if (month<10){
            selectedDate =
                year.toString()+ "-0"+
                        (month).toString()+ "-" +
                        day.toString()
        }else{
            selectedDate =
                year.toString()+ "-"+
                        month.toString()+ "-" +
                        day.toString()
        }
        Log.i("selectedDate",selectedDate)
        val dateMonthFormat = "yyyy-MM"
        val dateMonth = Date(System.currentTimeMillis())
        val simpleDateFormat = SimpleDateFormat(dateMonthFormat)

        val simpleDate: String = simpleDateFormat.format(dateMonth)
        callMonthList(simpleDate)
        callList(year,month,day)

//            binding.calendarView.selectedDate = CalendarDay.today()
//            binding.calendarView.addDecorators(SaturdayDecorator(), SundayDecorator())
    }
    //알림 UI 초기 설정 함수
    private fun initNotice(){
        if (noticeCnt==0) binding.notice.setImageResource(R.drawable.notice)
        else binding.notice.setImageResource(R.drawable.notice_true)
    }
    private fun calendarStatementSave(year:Int,month: Int,day: Int){
        MyApplication.preferences.setDate(CALENDAR_YEAR,year)
        MyApplication.preferences.setDate(CALENDAR_MONTH,month)
        MyApplication.preferences.setDate(CALENDAR_DAY,day)
    }
    //클릭 이벤트 종합 함수
    private fun clickListener(){
        //임시로 내부저장소 비우기
        binding.notice.setOnClickListener{
            MyApplication.preferences.removeDate(CALENDAR_YEAR)
            MyApplication.preferences.removeDate(CALENDAR_MONTH)
            MyApplication.preferences.removeDate(CALENDAR_DAY)
        }
        binding.calendarView.setOnDateChangedListener { _, date, _ ->
            year = date.year
            month = date.month + 1
            day = date.day
            val today: String =
                CalendarDay.today().year.toString()+ "-"+
                        (CalendarDay.today().month+1).toString()+ "-" +
                        CalendarDay.today().day.toString()
            if (month<10){
                selectedDate =
                    year.toString()+ "-0"+
                            (month).toString()+ "-" +
                            day.toString()
            }else{
                selectedDate =
                    year.toString()+ "-"+
                            month.toString()+ "-" +
                            day.toString()
            }
            Log.i("selectedDate",selectedDate)
            binding.bottomSheetDate.text = "$year 년 $month 월 $day 일"
            if(selectedDate == today){
                // 오늘 색깔로 바꾸는 코드
            }else{
                // 다른 색깔로 바꾸는 코드
            }
            calendarStatementSave(year,month,day)
            callList(year,month,day)
        }
        binding.fabPlus.setOnClickListener {
            toggleFab()
        }
        // 플로팅 버튼 클릭 이벤트 - 신체
        binding.fabBody.setOnClickListener {
            Toast.makeText(this.context, "신체 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("신체")
        }
        // 플로팅 버튼 클릭 이벤트 - 진료
        binding.fabClinic.setOnClickListener {
            Toast.makeText(this.context, "진료 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("진료")
        }
        // 플로팅 버튼 클릭 이벤트 - 복용
        binding.fabDose.setOnClickListener{
            Toast.makeText(this.context, "복용 버튼 클릭!", Toast.LENGTH_SHORT).show()
            changeIntent("복용")
        }
        // 회색 배경 클릭 이벤트
        binding.background.setOnClickListener{
            toggleFab()
        }
        binding.bottomSheetLayout.setOnClickListener{bottomSheetController()}
    }
    private fun bottomSheetController(){
        if(bottomSheetState){
            ObjectAnimator.ofFloat(binding.bottomSheetLayout, "translationY", 0f).apply { start() }
            binding.fabController.visibility = View.VISIBLE
            binding.bottomSheetDate.visibility = View.GONE
            bottomSheetState=false
        }else{
            ObjectAnimator.ofFloat(binding.bottomSheetLayout, "translationY", -(binding.mainFrm.height).toFloat()
            ).apply { start() }
            binding.fabController.visibility = View.GONE
            binding.bottomSheetDate.visibility = View.VISIBLE
            bottomSheetState=true
        }
    }
    private fun changeIntent(selectFragment: String){
        when(selectFragment){
            "신체" -> {
                val intent = Intent(activity, BodyActivity::class.java)
                intent.putExtra("selectedDate",selectedDate)
                startActivity(intent)
                activity?.finish()
            }
            "복용" -> {
                val intent = Intent(activity, DoseActivity::class.java)
                intent.putExtra("selectedDate",selectedDate)
                startActivity(intent)
                activity?.finish()
            }
            "진료" -> {
                val intent = Intent(activity, ClinicActivity::class.java)
                intent.putExtra("selectedDate",selectedDate)
                startActivity(intent)
                activity?.finish()
            }
        }

    }
    private fun toggleFab() {
        Toast.makeText(this.context, "메인 버튼 클릭!", Toast.LENGTH_SHORT).show()
        // 플로팅 액션 버튼 닫기 - 열려있는 플로팅 버튼 집어넣는 애니메이션
        if (isFabOpen) {
            binding.background.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabClinic, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.clinicText, "translationY", 0f).apply { start() }
            binding.clinicText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabDose, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.doseText, "translationY", 0f).apply { start() }
            binding.doseText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabBody, "translationY", 0f).apply { start() }
            ObjectAnimator.ofFloat(binding.bodyText, "translationY", 0f).apply { start() }
            binding.bodyText.visibility = View.GONE
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION, 45f, 0f).apply { start() }
        } else { // 플로팅 액션 버튼 열기 - 닫혀있는 플로팅 버튼 꺼내는 애니메이션
            binding.background.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabClinic, "translationY", -540f).apply { start() }
            ObjectAnimator.ofFloat(binding.clinicText, "translationY", -540f).apply { start() }
            binding.clinicText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabDose, "translationY", -360f).apply { start() }
            ObjectAnimator.ofFloat(binding.doseText, "translationY", -360f).apply { start() }
            binding.doseText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabBody, "translationY", -180f).apply { start() }
            ObjectAnimator.ofFloat(binding.bodyText, "translationY", -180f).apply { start() }
            binding.bodyText.visibility = View.VISIBLE
            ObjectAnimator.ofFloat(binding.fabPlus, View.ROTATION, 0f, 45f).apply { start() }
        }
        isFabOpen = !isFabOpen

    }
    private fun setClinicBottomSheet(result: ClinicListCall){
        clinicAdapter.setData(listOf(result))
        binding.clinicRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = clinicAdapter
            clinicAdapter.notifyDataSetChanged()
        }
    }
    private fun setDoseBottomSheet(data:MutableList<DoseListCall>){//result: List<DoseListCall>){
        doseAdapter.setData(data)
        binding.doseRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = doseAdapter
            doseAdapter.notifyDataSetChanged()
        }
    }
    private fun setBodyBottomSheet(result: BodyListCall){
        bodyAdapter.setData(listOf(result))
        binding.bodyRv.apply {
            layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)
            adapter = bodyAdapter
            bodyAdapter.notifyDataSetChanged()
        }
    }
    //현재 fail
    private fun callMonthList(date : String){
        Log.i("monthString",date)
        val call = ApiClient.calendarService.doseMonthList(jwt,date)
        call.enqueue(object : Callback<DoseMonthListResponseDto> {
            override fun onResponse(call: Call<DoseMonthListResponseDto>, response: Response<DoseMonthListResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("monthSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                initMonth(body.result)
                            }
                            404 -> {
//                                showToast(body.message)
                            }
                            500 -> {
//                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    showToast("Failed to communicate with the server.")
                    Log.i("monthNot",response.body().toString())
                }
            }
            override fun onFailure(call: Call<DoseMonthListResponseDto>, t: Throwable) {
            }
        })
    }
    private fun initMonth(month: List<Month>){
        for (i in month){
            val eventDate = i.date.split("-")
            val year = Integer.parseInt(eventDate[0])
            val month = Integer.parseInt(eventDate[1])
            val day = Integer.parseInt(eventDate[2])
            forInMonth(year,month,day,i)
        }
    }
    private fun forInMonth(year: Int, month:Int,day:Int, i:Month){
        if(i.physical_record>0){
            binding.calendarView
                .addDecorator(
                    EventDecorator(
                        Color.parseColor("#FF7878"),
                        Collections.singleton(CalendarDay.from(year, month-1, day)))
                )
        }
        if(i.consultation>0){
            binding.calendarView
                .addDecorator(
                    EventDecorator(
                        Color.parseColor("#366AD1"),
                        Collections.singleton(CalendarDay.from(year, month-1, day)))
                )
        }
        if(i.medication>0){
            binding.calendarView
                .addDecorator(
                    EventDecorator(
                        Color.parseColor("#FFCC00"),
                        Collections.singleton(CalendarDay.from(year, month-1, day)))
                )
        }

    }

    private fun callList(year:Int,month:Int,day:Int){
        var dateString: String = "$year-$month-$day"//MyApplication.preferences.getDate(CALENDAR_DATE,CalendarDay.today().toString())
        if (month<10){
            dateString =
                year.toString()+ "-0"+
                        (month).toString()+ "-" +
                        day.toString()
        }else{
            dateString =
                year.toString()+ "-"+
                        month.toString()+ "-" +
                        day.toString()
        }
        initBody(dateString)
        initClinic(dateString)
        initAllDose(dateString)
    }
    private fun initBody(dateString: String){
        Log.i("bodyDataString",dateString)
        val call = ApiClient.calendarService.bodyListCall(jwt,dateString)
        call.enqueue(object : Callback<BodyListCallResponseDto> {
            override fun onResponse(call: Call<BodyListCallResponseDto>, response: Response<BodyListCallResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("initBodySuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                setBodyBottomSheet(body.result)
                            }
                            404 -> {
//                                showToast(body.message)
                            }
                            500 -> {
//                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    showToast("Failed to communicate with the server.")
                    Log.i("initBodyNot",response.toString())
                }
            }
            override fun onFailure(call: Call<BodyListCallResponseDto>, t: Throwable) {
            }
        })
    }
    private fun initClinic(dateString: String){
        Log.i("initClinic",dateString)
        val call = ApiClient.calendarService.clinicListCall(jwt,dateString)
        call.enqueue(object : Callback<ClinicListCallResponseDto> {
            override fun onResponse(call: Call<ClinicListCallResponseDto>, response: Response<ClinicListCallResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("initClinicSuccess", body.toString())
                        when (body.code) {
                            200 -> {
                                setClinicBottomSheet(body.result)
                            }
                            404 -> {
//                                showToast(body.message)
                            }
                            500 -> {
//                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    showToast("Failed to communicate with the server.")
                    Log.i("initClinicSuccess", response.toString())
                }
            }
            override fun onFailure(call: Call<ClinicListCallResponseDto>, t: Throwable) {
            }
        })
    }
    private fun initAllDose(dateString: String){
        val call = ApiClient.calendarService.doseAllListCall(jwt,dateString)
        call.enqueue(object : Callback<DoseAllListCallResponseDto> {
            override fun onResponse(call: Call<DoseAllListCallResponseDto>, response: Response<DoseAllListCallResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("getDoseAllSuccess",body.toString())
                        when (body.code) {
                            200 -> {
                                initDose(body.result,dateString)
                            }
                            404 -> {
//                                showToast(body.message)
                            }
                            500 -> {
//                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    Log.i("getDoseAllNot",response.body().toString())
                    showToast("Failed to communicate with the server.")
                }
            }
            override fun onFailure(call: Call<DoseAllListCallResponseDto>, t: Throwable) {
            }
        })
    }
    private fun initDose(data: List<DoseListCall>,dateString:String){
//        val call = ApiClient.calendarService.doseAllListCall(jwt,dateString)
//        val call = ApiClient.calendarService.doseListCall(jwt,"morning",dateString)
//        call.enqueue(object : Callback<DoseAllListCallResponseDto> {
//            override fun onResponse(call: Call<DoseAllListCallResponseDto>, response: Response<DoseAllListCallResponseDto>) {
//                if (response.isSuccessful) {
//                    val body = response.body()
//                    // 서버로부터 받은 응답을 처리합니다.
//                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
//                    if (body != null) {
//                        Log.i("doseSuccess",body.toString())
//                        when (body.code) {
//                            200 -> {
//                                setDoseBottomSheet(body.result)
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
//                    Log.i("doseNot",response.body().toString())
//                    showToast("Failed to communicate with the server.")
//                }
//            }
//            override fun onFailure(call: Call<DoseAllListCallResponseDto>, t: Throwable) {
//                showToast("Network request failed. Error: ${t.message}")
//                Log.i("doseFail",t.message.toString())
//            }
//        })
        var doseList:MutableList<DoseListCall> = mutableListOf()
        for(i in data){
            if(i.timeOfDay=="아침, 점심, 저녁"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("morning", dateString,doseList,i.medicationId)
                    doseList = getDose("noon",dateString,doseList,i.medicationId)
                    doseList = getDose("evening",dateString,doseList,i.medicationId)
                }
            }
            else if(i.timeOfDay == "아침, 점심"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("morning", dateString,doseList,i.medicationId)
                    doseList = getDose("noon",dateString,doseList,i.medicationId)
                }
            }
            else if(i.timeOfDay== "아침, 저녁"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("morning", dateString,doseList,i.medicationId)
                    doseList = getDose("evening",dateString,doseList,i.medicationId)
                }
            }
            else if(i.timeOfDay == "점심, 저녁"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("noon",dateString,doseList,i.medicationId)
                    doseList = getDose("evening",dateString,doseList,i.medicationId)
                }
            }
            else if(i.timeOfDay == "아침"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("morning", dateString,doseList,i.medicationId)
                }
            }
            else if (i.timeOfDay == "점심"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("noon",dateString,doseList,i.medicationId)
                }
            }
            else if (i.timeOfDay == "저녁"){
                if(i.alarmedDate == dateString){
                    doseList = getDose("evening",dateString,doseList,i.medicationId)
                }
            }
        }
        setDoseBottomSheet(doseList)
    }
    //현재 getDoseNot
    private fun getDose(date: String,dateString: String,doseList:MutableList<DoseListCall>,id : Int):MutableList<DoseListCall>{
        val call = ApiClient.calendarService.doseListCall(jwt,id)
        call.enqueue(object : Callback<DoseListCallResponseDto> {
            override fun onResponse(call: Call<DoseListCallResponseDto>, response: Response<DoseListCallResponseDto>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    // 서버로부터 받은 응답을 처리합니다.
                    // 예를 들어, 로그인 성공 여부에 따라 처리할 수 있습니다.
                    if (body != null) {
                        Log.i("getDoseSuccess",body.toString())
                        when (body.code) {
                            200 -> {
                                doseList.addAll(listOf( body.result))
                            }
                            404 -> {
//                                showToast(body.message)
                            }
                            500 -> {
//                                showToast(body.message)
                            }
                        }
                    }
                } else {
                    Log.i("getDoseNot",response.body().toString())
                    showToast("Failed to communicate with the server.")
                }
            }
            override fun onFailure(call: Call<DoseListCallResponseDto>, t: Throwable) {
            }
        })
        return doseList
    }
    override fun onDestroyView() {
        _binding = null
        Log.i(TAG,"onDestroy")
        super.onDestroyView()
    }
    private fun showToast(message: String) {
        Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
    }
    companion object{
        const val TAG = "CalendarFragment"
        const val CALENDAR_YEAR = "calendarYear"
        const val CALENDAR_MONTH = "calendarMonth"
        const val CALENDAR_DAY = "calendarDay"
    }
}