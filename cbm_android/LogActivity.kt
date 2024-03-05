package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.util.Log
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.inputmethod.InputMethodManager
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_log.*
import org.apache.http.util.EncodingUtils

class LogActivity : AppCompatActivity() {

    var isDoingNetwork = 0 // 네트워크 전역변수 (0: 터치가능 1: 네트워크1개생성 2: 2개생성)
    var isDoingNetwork2 = 0 // 네트워크 전역변수2 (중간에 통신하는 부분이 있어서)
    var log9Str = "" //emotion 긍정 부정 그 각자 감정
    //감정관련 문자
    var emotionBadStr = ""
    var emotionStr = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log)

        val testPkey = MySharedPreferences.getTestPKey(this)
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val userPkey = MySharedPreferences.getUserPKey(this)
        val userId = MySharedPreferences.getUserId(this)
        val groupType = MySharedPreferences.getGroupType(this)

        var moodWhen = ""
        var moodWhere = ""
        var moodWho = ""
        var moodTotal = ""
        //텍스트뷰 및 이미지뷰 변수
        val logText1 = findViewById<TextView>(R.id.logText1)
        val logText1_1 = findViewById<TextView>(R.id.logText1_1)
        val logText4 = findViewById<TextView>(R.id.logText4)
        val logText5 = findViewById<TextView>(R.id.logText5)
        val logText17 = findViewById<TextView>(R.id.logText17)
        val logText17_2 = findViewById<TextView>(R.id.logText17_2)
        val logNextBtn = findViewById<ImageButton>(R.id.btnNext1)
        val logNextBtn2 = findViewById<ImageButton>(R.id.btnNext2)
        val logNextBtn3 = findViewById<ImageButton>(R.id.btnNext3)
        val logNextBtn3Bad = findViewById<ImageButton>(R.id.btnNext3Bad)
        val logNextBtn4 = findViewById<ImageButton>(R.id.btnNext4)
        val logNextBtn5 = findViewById<ImageButton>(R.id.btnNext5)
        val logNextBtn6 = findViewById<ImageButton>(R.id.btnNext6)
        val logNextBtn7 = findViewById<ImageButton>(R.id.btnNext7)
        val logNextBtn8_1 = findViewById<Button>(R.id.btn8_1)
        val logNextBtn8_2 = findViewById<Button>(R.id.btn8_2)
        val logNextBtn9 = findViewById<ImageButton>(R.id.btnNext9)
        val logNextBtn10 = findViewById<ImageButton>(R.id.btnNext10)
        val logNextBtn11 = findViewById<ImageButton>(R.id.btnNext11)
        val logNextBtn12 = findViewById<ImageButton>(R.id.btnNext12)
        val logNextBtn13 = findViewById<ImageButton>(R.id.btnNext13)
        val logNextBtn14 = findViewById<ImageButton>(R.id.btnNext14)
        val logNextBtn15 = findViewById<ImageButton>(R.id.btnNext15)
        val logNextBtn16 = findViewById<ImageButton>(R.id.btnNext16)
        val logNextBtn17 = findViewById<ImageButton>(R.id.btnNext17)
        val logLayout1 = findViewById<ConstraintLayout>(R.id.logLayout1)
        val logLayout2 = findViewById<ConstraintLayout>(R.id.logLayout2)
        val logLayout3Bad = findViewById<ConstraintLayout>(R.id.logLayout3bad)
        val logLayout3 = findViewById<ConstraintLayout>(R.id.logLayout3)
        val logLayout4 = findViewById<ConstraintLayout>(R.id.logLayout4)
        val logLayout5 = findViewById<ConstraintLayout>(R.id.logLayout5)
        val logLayout6 = findViewById<ConstraintLayout>(R.id.logLayout6)
        val logLayout13 = findViewById<ConstraintLayout>(R.id.logLayout13)
        val logLayout14 = findViewById<ConstraintLayout>(R.id.logLayout14)
        val logLayout15 = findViewById<ConstraintLayout>(R.id.logLayout15)

        logLayout1.visibility = View.VISIBLE

        var mood1 = 0
        var emotionLevel = 0

        //글씨체 style를 bold로 변경
        val logBoldText1 = logText1.text.toString()
        val builder_logText1 = SpannableStringBuilder(logBoldText1)

        builder_logText1.setSpan(StyleSpan(Typeface.BOLD) , 7,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        logText1.text = builder_logText1

        //동적간격생성
        autoLayoutSpace()
       //logWebView.setOnTouchListener(OnTouchListener { v, event -> event.action == MotionEvent.ACTION_MOVE })

        val negativeBar = findViewById<Slider>(R.id.negativeBar)
        negativeBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                mood1 = slider.value.toInt()
                //Toast.makeText(this@LogActivity, mood1.toString(), Toast.LENGTH_LONG).show()
                if (mood1 == 0) {
                    Toast.makeText(this@LogActivity, "음수 또는 양수의 숫자를 선택해주세요", Toast.LENGTH_LONG)
                        .show()
                    logNextBtn2.visibility = View.INVISIBLE
                    btnPinkBack2.visibility = View.INVISIBLE
                } else {
                    logNextBtn2.visibility = View.VISIBLE
                    btnPinkBack2.visibility = View.VISIBLE
                }

            }
        })



        visibleText(logText1_1)
        logNextBtn.setOnClickListener {
            invisibleLayout(logLayout1)
            logLayout2.visibility = View.VISIBLE

        }
        getFirstName()
        logNextBtn2.setOnClickListener {
            invisibleLayout(logLayout2)
            if (mood1 < 0) {
                logLayout3Bad.visibility = View.VISIBLE

            } else {
                logLayout3.visibility = View.VISIBLE

            }

        }


        //logLayout3
        /******************* RADIO GROUP(LOG) *******************/
        val firstGroup = findViewById<RadioGroup>(R.id.logRadioGroup1)
        val secondGroup = findViewById<RadioGroup>(R.id.logRadioGroup2)
        var isChecking = false // 체크된 라디오 버튼이 있는지 확인하는 변수
        var checkStr = "" // 체크된 라디오 버튼의 번호 저장하는 변수
        val moodArr = arrayOf("행복한", "편안한", "기쁜", "감사한", "만족스러운")
        // 각 그룹에 체크된 라디오 버튼이 있는 경우 다른 그룹 라디오 버튼 해제
        firstGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                secondGroup.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logRadio1 -> checkStr = "1"
                R.id.logRadio2 -> checkStr = "2"
                R.id.logRadio3 -> checkStr = "3"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3.visibility = View.VISIBLE
                btnPinkBack3.visibility = View.VISIBLE
            } else {
                logNextBtn3.visibility = View.INVISIBLE
                btnPinkBack3.visibility = View.INVISIBLE
            }
        }

        secondGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                firstGroup.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logRadio4 -> checkStr = "4"
                R.id.logRadio5 -> checkStr = "5"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3.visibility = View.VISIBLE
                btnPinkBack3.visibility = View.VISIBLE
            } else {
                logNextBtn3.visibility = View.INVISIBLE
                btnPinkBack3.visibility = View.INVISIBLE
            }
        }

        emotionStr = ""
        logNextBtn3.setOnClickListener {
            if (checkStr == "") {
                Toast.makeText(this, "기분을 선택해주세요", Toast.LENGTH_SHORT).show()
            } else {
                invisibleLayout(logLayout3)
                emotionStr = moodArr[checkStr.toInt() - 1]
                logText4.text = "지금 " + getFirstName() + "님이\n\n " + emotionStr + " 감정을 느끼고 계시군요."
                logLayout4.visibility = View.VISIBLE
                visibleBtn(logNextBtn4, btnPinkBack4)
            }
        }
        //logLayout3Bad
        /******************* RADIO GROUP(LOGBAD) *******************/
        val badGroup = findViewById<RadioGroup>(R.id.logBadGroup1)
        val badGroup2 = findViewById<RadioGroup>(R.id.logBadGroup2)
        val badGroup3 = findViewById<RadioGroup>(R.id.logBadGroup3)
        val badGroup4 = findViewById<RadioGroup>(R.id.logBadGroup4)

        //var isChecking = false // 체크된 라디오 버튼이 있는지 확인하는 변수
        //var checkStr = "" // 체크된 라디오 버튼의 번호 저장하는 변수
        val emotionBadArr =
            arrayOf("슬픈", "불안한", "절망적인", "화난", "답답한", "무서운", "짜증난", "우울한", "공허한", "걱정스러운", "수치스러운")
        // 각 그룹에 체크된 라디오 버튼이 있는 경우 다른 그룹 라디오 버튼 해제
        badGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                badGroup2.clearCheck()
                badGroup3.clearCheck()
                badGroup4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logBadRadio1 -> checkStr = "1"
                R.id.logBadRadio2 -> checkStr = "2"
                R.id.logBadRadio3 -> checkStr = "3"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3Bad.visibility = View.VISIBLE
                btnPinkBack3Bad.visibility = View.VISIBLE
            } else {
                logNextBtn3Bad.visibility = View.INVISIBLE
                btnPinkBack3Bad.visibility = View.INVISIBLE
            }
        }

        badGroup2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                badGroup.clearCheck()
                badGroup3.clearCheck()
                badGroup4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logBadRadio4 -> checkStr = "4"
                R.id.logBadRadio5 -> checkStr = "5"
                R.id.logBadRadio6 -> checkStr = "6"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3Bad.visibility = View.VISIBLE
                btnPinkBack3Bad.visibility = View.VISIBLE
            } else {
                logNextBtn3Bad.visibility = View.INVISIBLE
                btnPinkBack3Bad.visibility = View.INVISIBLE
            }
        }
        badGroup3.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                badGroup.clearCheck()
                badGroup2.clearCheck()
                badGroup4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logBadRadio7 -> checkStr = "7"
                R.id.logBadRadio8 -> checkStr = "8"
                R.id.logBadRadio9 -> checkStr = "9"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3Bad.visibility = View.VISIBLE
                btnPinkBack3Bad.visibility = View.VISIBLE
            } else {
                logNextBtn3Bad.visibility = View.INVISIBLE
                btnPinkBack3Bad.visibility = View.INVISIBLE
            }
        }
        badGroup4.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && isChecking) {
                isChecking = false
                badGroup.clearCheck()
                badGroup2.clearCheck()
                badGroup3.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.logBadRadio10 -> checkStr = "10"
                R.id.logBadRadio11 -> checkStr = "11"
            }
            isChecking = true
            if (isChecking) {
                logNextBtn3Bad.visibility = View.VISIBLE
                btnPinkBack3Bad.visibility = View.VISIBLE
            } else {
                logNextBtn3Bad.visibility = View.INVISIBLE
                btnPinkBack3Bad.visibility = View.INVISIBLE
            }
        }
        emotionBadStr = ""
        logNextBtn3Bad.setOnClickListener {
            if (checkStr == "") {
                Toast.makeText(this, "기분을 선택해주세요", Toast.LENGTH_SHORT).show()
            } else {
                invisibleLayout(logLayout3bad)
                emotionBadStr = emotionBadArr[checkStr.toInt() - 1]
                logText4.text = "지금 " + getFirstName() + "님이\n\n " + emotionBadStr + " 감정을 느끼고 계시군요."
                logLayout4.visibility = View.VISIBLE
                visibleBtn(logNextBtn4, btnPinkBack4)
            }
        }
        //////logLayout4
        logNextBtn4.setOnClickListener {
            invisibleLayout(logLayout4)
            if(emotionBadStr != ""){
                logText5.text = emotionBadStr + " 감정이 어느 정도 느껴지나요?"
            }
            else{
                logText5.text = emotionStr + " 감정이 어느 정도 느껴지나요?"
            }
            logLayout5.visibility = View.VISIBLE
        }

        //////logLayout5
        val emotionBar = findViewById<Slider>(R.id.emotionBar)
        emotionBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                emotionLevel = slider.value.toInt()
                //Toast.makeText(this@LogActivity, emotionLevel.toString(), Toast.LENGTH_LONG).show()
                if (emotionLevel == 0) {
                    Toast.makeText(this@LogActivity, "감정의 정도를 선택해주세요", Toast.LENGTH_LONG).show()
                    logNextBtn5.visibility = View.INVISIBLE
                    btnPinkBack5.visibility = View.INVISIBLE

                } else {
                    Log.d("emotionLevel",emotionLevel.toString())
                    logNextBtn5.visibility = View.VISIBLE
                    btnPinkBack5.visibility = View.VISIBLE
                }

            }
        })
        logNextBtn5.setOnClickListener {
            //기분,감정 관련 값 전송 -> user가 선택한 값이 webview에 반영
            val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_log.php"
            if(isDoingNetwork2 == 0){
                updateEmotionVolley(
                    this@LogActivity,
                    url,
                    mood1.toString(),
                    checkStr,
                    emotionLevel.toString(),
                    moodWhen,
                    moodWhere,
                    moodWho,
                    moodTotal
                )
            }
        }

        //////logLayout6
        logNextBtn6.setOnClickListener {

            invisibleLayout(logLayout6)
            logText7.text = log9Str + " 감정을 느꼈다고 했는데,\n\n그런 감정을 느끼게 된 어떤 사건이나\n\n상황에 대해 같이 생각해봐요."
            logLayout7.visibility = View.VISIBLE
            visibleBtn(logNextBtn7, btnPinkBack7)
            //////logLayout7
            logNextBtn7.setOnClickListener {
                invisibleLayout(logLayout7)
                logText8.text =
                    log9Str + " 감정을 느끼게 한 사건이나 상황이\n\n언제 어디서 누구와 같이 있었던 상황이었는지\n\n하나씩 같이 생각해 볼게요."
                logLayout8.visibility = View.VISIBLE
                visibleBtn(logNextBtn8_1)
                visibleBtn(logNextBtn8_2)
            }
            //////logLayout8
            logNextBtn8_1.setOnClickListener {
                invisibleLayout(logLayout8)
                logLayout13.visibility = View.VISIBLE
                //visibleBtn(logNextBtn13)
            }

            logNextBtn8_2.setOnClickListener {
                invisibleLayout(logLayout8)
                if (mood1 < 0) { //부정
                    logText10_ex.text = "어제 오후 학교에서 시험을 보고 난 후\n\n시험을 망친 것 같아서 기분이 나빴어"
                    logText10_1ex.text = "방금 집에서 엄마랑 싸웠는데 너무 화가 나"
                    logText10_2ex.text = "내일 학교에서 중요한 발표가 있는데\n\n너무 스트레스 받아"
                    logText11_ex.text = "2시간 전쯤 친구랑 통화했는데\n\n친구가 이유없이 짜증을 내서 기분이 안 좋아"
                    logText11_1ex.text = "어제 저녁에 친구랑 오해가 있어서 다퉜는데\n\n아직 풀지 못해서 너무 머리가 아파"
                    logText11_2ex.text =
                        "1시간 전쯤 기말고사 시험결과가 나왔는데\n\n너무 낮게 나왔어. 나만 뒤처지는 것 같아서\n\n나 자신이 너무 한심하고 바보같아"
                } else { //긍정
                    logText10_ex.text =
                        "오늘 점심에 친구들과 만나 좋아하는 식당에서\n\n밥도 먹고 커피를 마시면서 수다 떨었는데\n\n기분이 좋아졌어"
                    logText10_1ex.text = "방금 집에 들어와서 목욕하고 누워서\n\n드라마 보는데 너무 편하고 좋아"
                    logText10_2ex.text = "오늘 내 생일인데 아까 학교에서 점심시간에\n\n친구들이 생일파티를 해줘서 너무 고마웠어"
                    logText11_ex.text =
                        "꼭 갖고 싶던 신발을 인터넷으로 샀는데\n\n30분 전쯤 택배가 곧 도착한다는 메시지를 받아서\n\n너무 기대돼"
                    logText11_1ex.text = "2시간 전쯤 친구랑 같이 운동장 걸으면서\n\n수다 떨었는데 기분이 상쾌했어"
                    logText11_2ex.text = "방금 기말시험이 끝나서 기분이 너무 좋아"
                }
                logText9.text = log9Str + " 감정과 관련된 사건이\n\n떠오르지 않더라도 걱정마세요."
                logText9_1.text =
                    "지금부터 무슨 일이 있었는지\n\n차분히 생각하다 보면\n\n " + log9Str + " 감정을 느끼게 된 상황을\n\n떠올릴 수 있을 거예요."
                logLayout9.visibility = View.VISIBLE
                visibleText(logText9_1, logText9_2, logNextBtn9, btnPinkBack9)
            }
            //////logLayout9
            logNextBtn9.setOnClickListener {
                invisibleLayout(logLayout9)
                logLayout10.visibility = View.VISIBLE
            }
            //////logLayout10
            logNextBtn10.setOnClickListener {
                invisibleLayout(logLayout10)
                logLayout11.visibility = View.VISIBLE
            }
            //////logLayout11
            logNextBtn11.setOnClickListener {
                invisibleLayout(logLayout11)
                logText12.text = "예시를 모두 살펴봤어요.\n\n\n\n그럼 이제 " + log9Str +
                        " 감정을\n\n느끼게 한 사건이나 상황이\n\n언제 어디서 누구와 같이 있었던 상황이었는지\n\n하나씩 같이 생각해 볼게요."
                logLayout12.visibility = View.VISIBLE
                visibleBtn(logNextBtn12,btnPinkBack12)
            }
            //////logLayout12
            logNextBtn12.setOnClickListener {
                invisibleLayout(logLayout12)
                logLayout13.visibility = View.VISIBLE
            }

            //////logLayout13
            logNextBtn13.setOnClickListener {
                moodWhen = findViewById<EditText>(R.id.moodWhen).text.toString().trim()
                if (moodWhen.isNullOrBlank()) {
                    Toast.makeText(this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    invisibleLayout(logLayout13)
                    logLayout14.visibility = View.VISIBLE
                }
            }
            //////logLayout14
            logNextBtn14.setOnClickListener {
                moodWhere = findViewById<EditText>(R.id.moodWhere).text.toString().trim()
                if (moodWhere.isNullOrBlank()) {
                    Toast.makeText(this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    invisibleLayout(logLayout14)
                    logLayout15.visibility = View.VISIBLE
                }
            }
            //////logLayout15
            logNextBtn15.setOnClickListener {
                moodWho = findViewById<EditText>(R.id.moodWho).text.toString().trim()
                if (moodWho.isNullOrBlank()) {
                    Toast.makeText(this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    invisibleLayout(logLayout15)
                    logLayout16.visibility = View.VISIBLE
                }
            }
            //////logLayout16
            logNextBtn16.setOnClickListener {
                moodTotal = findViewById<EditText>(R.id.moodTotal).text.toString().trim()
                if (moodTotal.isNullOrBlank()) {
                    Toast.makeText(this, "빈칸을 입력하세요", Toast.LENGTH_SHORT).show()
                } else {
                    invisibleLayout(logLayout16)
                    if (mood1 < 0) { //부정메시지 출력
                        logText17.text =
                            getFirstName() + "님이 '" + moodTotal + "' 라고 하셨는데,\n\n그래서 " + log9Str + " 감정을 느끼게 된 거군요."
                        logText17_1.text =
                            log9Str + " 감정으로 많이 힘드셨을 것 같아요.\n\n다른 사람들도 " + log9Str + " 감정을 느끼면\n\n많이 힘들어하더라고요."
                        logText17_2.text =
                            "우리 같이 " + log9Str + " 감정을 극복할 수 있도록\n\n방법을 생각해봐요!\n\n\n힘내요 화이팅!"
                    } else { //긍정메시지 출력
                        logText17.text =
                            getFirstName() + "님이 '" + moodTotal + "' 라고 하셨는데,\n\n그래서 " + log9Str + " 감정을 느끼게 된 거군요."
                        logText17_1.text =
                            getFirstName() + "님에게 앞으로\n\n이런 " + log9Str + " 감정이 가득했으면 좋겠어요!"
                        logText17_2.text = "\uD83D\uDE0A 화이팅!!"
                    }
                    logScroll.visibility = View.VISIBLE
                    logLayout17.visibility = View.VISIBLE
                    visibleText(logText17_1, logText17_2, logNextBtn17, btnPinkBack17)
                }
            }
            //////logLayout17
            logNextBtn17.setOnClickListener {
                //최종 기분일기 전송
                val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_log.php"
                if(isDoingNetwork == 0){
                    updateLogVolley(
                        this,
                        url,
                        mood1.toString(),
                        checkStr,
                        emotionLevel.toString(),
                        moodWhen,
                        moodWhere,
                        moodWho,
                        moodTotal
                    )
                }
                //invisibleLayout(logLayout17)
                //updateDoneSection(this, testLogPkey, testPkey)

            }

        }
    }
    //동적으로 간격생성(글자랑 그림)
    private fun autoLayoutSpace(){
        //동적위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        /**Layout1**/
        (logText1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (82.0/480.0)).toInt()
        }
        (logImage1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        val params = logImage1.layoutParams
        params.width = (width * (242.3/320.0)).toInt()
        params.height = (height * (136.0/480.0)).toInt()
        logImage1.layoutParams = params
        (logText1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (btnNext1.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout2**/
        (btnNext2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout3**/
        (logText3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (122.0/480.0)).toInt()
        }
        (logText3bad.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (80.0/480.0)).toInt()
        }
        (btnNext3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        (btnNext3Bad.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout4**/
        (logText4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (148.0/480.0)).toInt()
        }
        (logText4_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (106.0/480.0)).toInt()
        }
        (btnNext4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout5**/
        (btnNext5.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout6**/
        (textView18.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (50.0/480.0)).toInt()
        }
        (btnNext6.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout7**/
        (logText7.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (78.0/480.0)).toInt()
        }
        (imageView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        val params2 = imageView.layoutParams
        params2.width = (width * (177.0/320.0)).toInt()
        params2.height = (height * (155.0/480.0)).toInt()
        (btnNext7.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout8**/
        (logText8.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (130.0/480.0)).toInt()
        }
        /**Layout9**/
        (logText9.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (93.0/480.0)).toInt()
        }
        (logText9_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (logText9_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (30.0/480.0)).toInt()
        }
        (btnNext9.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout10**/
        (logText10.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (62.0/480.0)).toInt()
        }
        (logText10_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (logText10_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (btnNext10.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout11**/
        (logText11.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (62.0/480.0)).toInt()
        }
        (logText11_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (logText11_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (btnNext11.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout12**/
        (logText12.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (127.0/480.0)).toInt()
        }
        (btnNext12.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout13**/
        (logText13.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (100.0/480.0)).toInt()
        }
        (btnNext13.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout14**/
        (logText14.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (100.0/480.0)).toInt()
        }
        (btnNext14.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout15**/
        (logText15.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (100.0/480.0)).toInt()
        }
        (btnNext15.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout16**/
        (logText16.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (75.0/480.0)).toInt()
        }
        (btnNext16.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout17**/
        (btnPinkBack17.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (50.0/480.0)).toInt()
        }

        // Button 높이를 화면 높이에 맞게 조정
        val logBtn8Layout = findViewById<ConstraintLayout>(R.id.btn8Layout)
        val logBtn8_1 = findViewById<Button>(R.id.btn8_1)
        val logBtn8_2 = findViewById<Button>(R.id.btn8_2)

        val params8 = logBtn8Layout.getLayoutParams()
        params8.height = (height * (50.0/480.0)).toInt()
        logBtn8Layout.layoutParams = params8

        val params8_1 = logBtn8_1.getLayoutParams()
        params8_1.height = (height * (40.0/ 480.0)).toInt()
        logBtn8_1.layoutParams = params8_1

        val params8_2 = logBtn8_2.getLayoutParams()
        params8_2.height = (height * (40.0/ 480.0)).toInt()
        logBtn8_2.layoutParams = params8_2


    }
    private fun visibleText(text: TextView){
        val logNextBtn = findViewById<ImageButton>(R.id.btnNext1)
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleBtn(logNextBtn,btnPinkBack1)
        }, 3000)
    }
    private fun visibleText(text: TextView, btn: ImageButton,imageView: ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleBtn(btn,imageView)
        }, 3000)
    }
    private fun visibleText(text: TextView, text2: TextView, btn: ImageButton, imageView: ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleText(text2,btn,imageView)
        }, 3000)
    }
    private fun visibleBtn(btn: ImageButton, imageView: ImageView){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
            imageView.visibility=View.VISIBLE
        }, 3000)
    }
    private fun visibleBtn(btn: ImageButton){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
        }, 3000)
    }
    private fun visibleBtn(btn: Button){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
        }, 3000)
    }
    fun invisibleLayout(layout: ConstraintLayout){
        layout.visibility = View.INVISIBLE
    }

    //통신후 성공하면 page6으로 넘어감
    private fun nextPage6(mood1: String){
        val logLayout5 = findViewById<ConstraintLayout>(R.id.logLayout5)
        val logLayout6 = findViewById<ConstraintLayout>(R.id.logLayout6)
        val logWebView = findViewById<WebView>(R.id.logWeb)
        val userPkey = MySharedPreferences.getUserPKey(this)
        val groupType = MySharedPreferences.getGroupType(this)
        val logNextBtn6 = findViewById<ImageButton>(R.id.btnNext6)
        val logNextBtn5 = findViewById<ImageButton>(R.id.btnNext5)
        logNextBtn5.isEnabled = false //네트워크 성공 후 이중터치방지
        invisibleLayout(logLayout5)
        //웹뷰 만들기
        makeLogWebView(logWebView,userPkey,groupType)
        logLayout6.visibility = View.VISIBLE
        if (mood1.toInt() < 0) { //부정
            log9Str = emotionBadStr
        } else {
            log9Str = emotionStr
        }
        visibleBtn(logNextBtn6, btnPinkBack6)
    }
    private fun updateLogVolley(context: Context, url: String, mood1: String, mood2:String, emotion:String, moodWhen:String,
                                moodWhere:String,who: String, total:String) {
        val queue = Volley.newRequestQueue(this)
        val pkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)

        isDoingNetwork++
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if (isDoingNetwork == 1){ //정상 전역 네트워크 1개
                    if(response.toInt() == 0) {//정상response = 0
                        updateDoneSection(this, testLogPkey, testPkey)
                    }else{
                        //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                        isDoingNetwork=0
                        netWorkError()
                    }
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                isDoingNetwork = 0
                netWorkError()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["pkey"] = pkey
                params["testPkey"] = testPkey
                params["mood1"] = mood1
                params["mood2"] = mood2
                params["emotion"] = emotion
                params["when"] = moodWhen
                params["where"] = moodWhere
                params["who"] = who
                params["total"] = total
                return params
            }
        }
        queue.add(request)
    }
    private fun updateEmotionVolley(context: Context, url: String, mood1: String, mood2:String, emotion:String, moodWhen:String,
                                moodWhere:String,who: String, total:String) {
        val queue = Volley.newRequestQueue(this)
        val pkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)

        isDoingNetwork2++
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if (isDoingNetwork2 == 1){ //정상 전역 네트워크 1개
                    if(response.toInt() == 0) {//정상response = 0
                        nextPage6(mood1)
                    }else{
                        //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                        isDoingNetwork2=0
                        netWorkError()
                    }
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                isDoingNetwork2 = 0
                netWorkError()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["pkey"] = pkey
                params["testPkey"] = testPkey
                params["mood1"] = mood1
                params["mood2"] = mood2
                params["emotion"] = emotion
                params["when"] = moodWhen
                params["where"] = moodWhere
                params["who"] = who
                params["total"] = total
                return params
            }
        }
        queue.add(request)
    }
    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)

        isDoingNetwork = 2

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->

                if (isDoingNetwork == 2){
                    if(response.toInt() == 0){
                        finish()
                    }else{
                        //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                        isDoingNetwork =0
                        netWorkError()
                    }
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                isDoingNetwork =0
                netWorkError()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]=pKey
                params["testPkey"]=testPKey
                return params
            }
        }
        queue.add(request)
    }
    //성떼고 이름만 가져오기
    fun getFirstName(): String {
        //local userName
        val userName = MySharedPreferences.getUserName(this)
        var nameToken = userName.chunked(1)
        var firstName = ""
        if (userName.length >=2){
            for (i in 1 until userName.length){
                firstName += nameToken[i]
            }
        }else{
            firstName = userName
        }
        return firstName
    }
    //키보드내리기
    fun closeKeyboard(v : View)
    {
        if(v != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
    }
    //웹뷰
    fun makeLogWebView(logWebView : WebView, userPkey:String,groupType:String) {
        //웹뷰
        logWebView.webViewClient=LogWebViewClient()
        val graphUrl = "http://healingmindcenter.com/cbm_app/cbm_api/cbm_graph.php"
        var postData = "user_id="+userPkey+"&"+"type="+groupType
        logWebView.postUrl(graphUrl,EncodingUtils.getBytes(postData,"base64"))
        logWebView.settings.javaScriptEnabled = true
        logWebView.isVerticalScrollBarEnabled = false
        logWebView.setOnTouchListener(object : OnTouchListener {
            var m_downY = 0f
            override fun onTouch(v: View, event: MotionEvent): Boolean {
                if (event.pointerCount > 1) {
                    return true
                }
                when (event.action) {
                    MotionEvent.ACTION_DOWN -> m_downY = event.y
                    MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP -> event.setLocation(event.x, m_downY)
                }
                return false
            }
        })
    }

    //알림창
    override fun onBackPressed() {
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("진행중에 나가시면 저장되지 않습니다.\n정말 종료하시겠습니까?")
        alert.setPositiveButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setNegativeButton("확인",
            DialogInterface.OnClickListener { dialog, which -> super.onBackPressed() })
        (alert.create()).show()
    }
    //네트워크 실패 시 호출
    private fun netWorkError(){
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("네트워크 연결에 실패했습니다.")
        alert.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.show()
    }
}

