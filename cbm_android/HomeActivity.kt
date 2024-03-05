package ac.duksung.cbm_android

import android.annotation.SuppressLint
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.util.Log
import android.view.View.VISIBLE
import android.widget.*
import androidx.appcompat.app.AlertDialog
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_login.*
import org.w3c.dom.Text
import java.lang.System.console
import kotlinx.android.synthetic.main.activity_home.setting as setting1


class HomeActivity : AppCompatActivity(){

    var button = arrayOfNulls<ImageButton>(6) // 각 회차별 해당 버튼 ActivityButtons
    var count = 0 // 차수를 받아오는 변수
    var currentProgress = 0
    var dayTextView:TextView? = null // 며칠인지 나타내는 변수
    var roundTextView:TextView? = null // 몇 회차인지 나타내는 변수
    var roundText = "" //몇 회차인지 Text로 저장
    var groupType = 0
    var lastItemIndex = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        val pkey = MySharedPreferences.getUserPKey(this)
        val day = findViewById<TextView>(R.id.dayTextView) // 며칠인지 나타내는 변수
        val round = findViewById<TextView>(R.id.roundTextView) // 몇 회차인지 나타내는 변수

        groupType = MySharedPreferences.getGroupType(this).toInt()

        //testVolley(this, pkey)//TestPKey 로컬에 저장하는 함수
        day.setText(count.toString() + "일차")
        round.setText(count.toString() + "번째 만남")

        //variable
        dayTextView = findViewById<TextView>(R.id.dayTextView)
        roundTextView = findViewById<TextView>(R.id.roundTextView)
        val greeting = findViewById<TextView>(R.id.greeting) //인사말

        //img
        val moon = findViewById<ImageView>(R.id.moon)
        val moon_half = findViewById<ImageView>(R.id.moon_half)
        val moon_full = findViewById<ImageView>(R.id.moon_full)

        //Button
        val setting = findViewById<ImageButton>(R.id.setting)

        //groupA Button
        val approachButton = findViewById<ImageButton>(R.id.approachButton)
        val orientationButton = findViewById<ImageButton>(R.id.orientationButton)
        val logButton1 = findViewById<ImageButton>(R.id.logButton1)
        val logButton2 = findViewById<ImageButton>(R.id.logButton2)
        val logButton3 = findViewById<ImageButton>(R.id.logButton3)
        val logButton4 = findViewById<ImageButton>(R.id.logButton4)
        val logButton5 = findViewById<ImageButton>(R.id.logButton5)
        val goalButton = findViewById<ImageButton>(R.id.goalButton)
        val relaxVideoButton = findViewById<ImageButton>(R.id.relaxVideoButton)
        val eduCBMButton = findViewById<ImageButton>(R.id.eduCBMButton)
        val relaxOTButton = findViewById<ImageButton>(R.id.relaxOTButton)
        val trainingMindButton = findViewById<ImageButton>(R.id.trainingMindButton)
        val trainingCBMButton1 = findViewById<ImageButton>(R.id.trainingCBMButton1)
        val trainingCBMButton2 = findViewById<ImageButton>(R.id.trainingCBMButton2)
        val trainingRelaxButton = findViewById<ImageButton>(R.id.trainingRelaxButton)
        val selfInjuryButton = findViewById<ImageButton>(R.id.selfInjuryButton)
        val checkConditionButton1 = findViewById<ImageButton>(R.id.checkConditionButton1)
        val checkConditionButton2 = findViewById<ImageButton>(R.id.checkConditionButton2)
        val checkConditionButton3 = findViewById<ImageButton>(R.id.checkConditionButton3)
        val checkConditionButton4 = findViewById<ImageButton>(R.id.checkConditionButton4)
        val checkConditionButton5 = findViewById<ImageButton>(R.id.checkConditionButton5)
        val endingButton1 = findViewById<ImageButton>(R.id.endingButton1)
        val endingButton2 = findViewById<ImageButton>(R.id.endingButton2)
        val endingButton3 = findViewById<ImageButton>(R.id.endingButton3)
        val endingButton4 = findViewById<ImageButton>(R.id.endingButton4)
        val endingButton5 = findViewById<ImageButton>(R.id.endingButton5)

        //groupB Button
        val approachBButton = findViewById<ImageButton>(R.id.approachBButton)
        val goalBButton = findViewById<ImageButton>(R.id.goalBButton)
        val selfInjuryBButton = findViewById<ImageButton>(R.id.selfInjuryBButton)
        val eduCBMBButton = findViewById<ImageButton>(R.id.eduCBMBButton)
        val trainingMindBButton = findViewById<ImageButton>(R.id.trainingMindBButton)
        val trainingCBMBButton = findViewById<ImageButton>(R.id.trainingCBMBButton)
        val checkConditionBButton1 = findViewById<ImageButton>(R.id.checkConditionBButton1)
        val checkConditionBButton2 = findViewById<ImageButton>(R.id.checkConditionBButton2)
        val checkConditionBButton3 = findViewById<ImageButton>(R.id.checkConditionBButton3)
        val checkConditionBButton4 = findViewById<ImageButton>(R.id.checkConditionBButton4)
        val endingBButton1 = findViewById<ImageButton>(R.id.endingBButton1)
        val endingBButton2 = findViewById<ImageButton>(R.id.endingBButton2)
        val endingBButton3 = findViewById<ImageButton>(R.id.endingBButton3)
        val endingBButton4 = findViewById<ImageButton>(R.id.endingBButton4)

        approachBButton.setOnClickListener() {
            val intent = Intent(this, ApproachActivity::class.java)
            startActivity(intent)
        }
        goalBButton.setOnClickListener() {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        }
        selfInjuryBButton.setOnClickListener() {
            val intent = Intent(this, SelfInjuryActivity::class.java)
            startActivity(intent)
        }
        eduCBMBButton.setOnClickListener() {
            val intent = Intent(this, EduCBMActivity::class.java)
            startActivity(intent)
        }
        trainingMindBButton.setOnClickListener() {
            val intent = Intent(this, TrainingMindActivity::class.java)
            startActivity(intent)
        }
        trainingCBMBButton.setOnClickListener() {
            val intent = Intent(this, TrainingCBMActivity::class.java)
            startActivity(intent)
        }

        //scroll 뷰
        val day1 = findViewById<ScrollView>(R.id.day1)
        val day2 = findViewById<ScrollView>(R.id.day2)
        val day3 = findViewById<ScrollView>(R.id.day3)
        val day4 = findViewById<ScrollView>(R.id.day4)
        val day5 = findViewById<ScrollView>(R.id.day5)
        val groupB1 = findViewById<ScrollView>(R.id.groupB1)
        val groupB2 = findViewById<ScrollView>(R.id.groupB2)
        val groupB3 = findViewById<ScrollView>(R.id.groupB3)
        val groupB4 = findViewById<ScrollView>(R.id.groupB4)
        
        day1.visibility = View.INVISIBLE
        day2.visibility = View.INVISIBLE
        day3.visibility = View.INVISIBLE
        day4.visibility = View.INVISIBLE
        day5.visibility = View.INVISIBLE
        groupB1.visibility = View.INVISIBLE
        groupB2.visibility = View.INVISIBLE
        groupB3.visibility = View.INVISIBLE
        groupB4.visibility = View.INVISIBLE

        setting.setOnClickListener {
            val intent = Intent(this, SettingActivity::class.java)
            startActivity(intent)
        }

        approachButton.setOnClickListener() {
            val intent = Intent(this, ApproachActivity::class.java)
            startActivity(intent)
        }

        orientationButton.setOnClickListener() {
            val intent = Intent(this, OrientationActivity::class.java)
            startActivity(intent)
        }

        goalButton.setOnClickListener() {
            val intent = Intent(this, GoalActivity::class.java)
            startActivity(intent)
        }

        relaxVideoButton.setOnClickListener() {
            val intent = Intent(this, RelaxVideoActivity::class.java)
            startActivity(intent)
        }

        eduCBMButton.setOnClickListener() {
            val intent = Intent(this, EduCBMActivity::class.java)
            startActivity(intent)
        }

        relaxOTButton.setOnClickListener() {
            val intent = Intent(this, RelaxOTActivity::class.java)
            startActivity(intent)
        }

        trainingMindButton.setOnClickListener() {
            val intent = Intent(this, TrainingMindActivity::class.java)
            startActivity(intent)
        }

        trainingRelaxButton.setOnClickListener() {
            val intent = Intent(this, TrainingRelaxActivity::class.java)
            startActivity(intent)
        }
        selfInjuryButton.setOnClickListener() {
            val intent = Intent(this, SelfInjuryActivity::class.java)
            startActivity(intent)
        }

    }

    //중복 버튼 onclick 처리
    public fun goLog(v : View){ //기분일기로 이동 button 중복
        val intent = Intent(this, LogActivity::class.java)
        startActivity(intent)
    }

    public fun gotrainingCBM(v : View){ //CBM훈련 이동
        val intent = Intent(this, TrainingCBMActivity::class.java)
        startActivity(intent)
    }
    public fun goCheckCondition(v : View){ //컨디션 체크 이동
        val intent = Intent(this, CheckConditionActivity::class.java)
        startActivity(intent)
    }

    public fun goEnding(v : View){ //쉬어가기
        //timer 3초정도 맞춰서 toast n일 n차 종료 출력하고 finish
        Toast.makeText(this, dayTextView?.text.toString() +" "+ roundTextView?.text.toString() + "이 종료 되었습니다.", Toast.LENGTH_LONG).show()
        Handler().postDelayed({
            //현재액티비티종료 finish()말고 모든 액티비티를 종료시켜야됨
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        },3000L)
    }

    override fun onResume() { //일단 처음에 실행되고 다시 돌아왔을때도 실행
        super.onResume()
        //setting버튼 enable true하기
        val setting = findViewById<ImageButton>(R.id.setting)
        setting.isEnabled = true
        val pkey = MySharedPreferences.getUserPKey(this)
        testVolley(this, pkey)

        //val testpkey = MySharedPreferences.getTestPKey(this)
        //testLogVolley(this,testpkey)
        //requestCount(this,testpkey) //전체차수 받아오는 함수
    }

    //다른 activity로 넘어갈 때 호출되는 함수
    override fun onPause() {
        super.onPause()
        disabled_button()
        //setting버튼 enabled false하기
        val setting = findViewById<ImageButton>(R.id.setting)
        setting.isEnabled = false
    }

    override fun onBackPressed() {
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("정말 종료하시겠습니까?")
        alert.setPositiveButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setNegativeButton("확인",
            DialogInterface.OnClickListener { dialog, which -> super.onBackPressed() })
        (alert.create()).show()
    }


    private fun testVolley(context: Context, upkey: String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_test_pkey.php"
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                MySharedPreferences.setTestPKey(this, response.toString())
                val testpkey = MySharedPreferences.getTestPKey(this)

                testLogVolley(this, testpkey) //testlogpkey 얻어오는 함수
                //Toast.makeText(context, "DB${MySharedPreferences.getTestPKey(this)}", Toast.LENGTH_SHORT ).show()
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                networkError()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["pkey"] = upkey
                return params
            }
        }
        queue.add(request)
    }

    private fun testLogVolley(context: Context, testpkey: String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_create_log.php"
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                MySharedPreferences.setTestLogPKey(this, response.toString())
                //Toast.makeText(context, "TestLogPKey:${MySharedPreferences.getTestLogPKey(this)}", Toast.LENGTH_SHORT).show()
                //requestCurrentProgress(this, testpkey)
                requestCount(this, testpkey) // 차수 확인 함수 호출
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                networkError()
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["tpkey"] = testpkey
                return params
            }
        }
        queue.add(request)
    }
    //status=10 완료 처리 함수
    fun doneTestLog(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_test_log.php"
        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if(roundText.toString()=="? 번째 만남")
                    greeting.setText("오늘도 수고하셨어요\uD83D\uDE09 내일 봐요!")
                else{
                    if(groupType == 0) {
                        if (count >= 41) {
                            if (currentProgress == 4) {
                                greeting.setText("모든 회차가 완료되었습니다")
                            }
                        } else {
                            greeting.setText(roundText + "에서 만나요\uD83D\uDE09")
                        }
                    }else if(groupType == 1){
                        if (count>=41){
                            if (currentProgress == 2){
                                greeting.setText("모든 회차가 완료되었습니다")

                            }else{
                                greeting.setText(roundText+"에서 만나요\uD83D\uDE09")
                            }
                        }
                    }

                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                networkError()
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
    // 전체 차수를 알려주는 api 연결 함수
    fun requestCount(context: Context, testPKey: String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_count_test.php"
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = @SuppressLint("SetTextI18n")
        object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                count = response.toInt()
                //Toast.makeText(context, "전체 차수: "+response.toString(), Toast.LENGTH_LONG).show()
                var day = count / 3 + 1 // 일자 값 저장하는 변수
                var round = count % 3 // 회차 값 저장하는 변수
                if (round == 0) { // 3의 배수 처리
                    round += 3
                    day -= 1
                }
                moon.visibility = View.INVISIBLE
                moon_half.visibility = View.INVISIBLE
                moon_full.visibility = View.INVISIBLE

                when(round){
                    1-> moon.visibility = View.VISIBLE
                    2-> moon_half.visibility = View.VISIBLE
                    3-> moon_full.visibility = View.VISIBLE
                }

                fun roundKor(x: Int): String { // 회차를 한글로 바꿔주는 함수
                    when (x) {
                        1 -> return "첫"
                        2 -> return "두"
                        3 -> return "세"
                    }
                    return "?"
                }
                dayTextView?.setText(day.toString() + "일차")
                roundTextView?.setText(roundKor(round) +" 번째 만남")
                roundText = roundKor(round+1)+" 번째 만남" //인사말에 쓰이는 text
                requestCurrentProgress(this, testPKey) //current 받아오기
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                networkError()
                //finish()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["testPKey"] = testPKey
                return params
            }
        }
        queue.add(request)
    }
    // 현재 상태 알려주는 api 연결 함수
    fun requestCurrentProgress(context: Context, testPKey: String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_current_progress.php"
        val pKey = MySharedPreferences.getTestLogPKey(this)
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->

                if(currentProgress != null)
                    currentProgress = response.toInt()
                //Toast.makeText(context,"currentProgress: "+response.toString(), Toast.LENGTH_LONG).show()

                //switch case -> 버튼 활성화 비활성화
                delete_button()
                if(currentProgress==0) //1번째 만남
                    if(count>1)
                        greeting.setText("또 만나서 반가워요!\n물 한잔 마시고 시작해보는 건 어때요?")
                    else if(count==1)
                        greeting.setText("만나서 반가워요\uD83D\uDE09 \n오늘 하루도 힘차게 보내봐요!")

                //그룹 타입
                val group_type = MySharedPreferences.getGroupType(this)

                if(group_type.toInt() == 0){
                when(count){
                    1 -> {  day1.visibility = View.VISIBLE
                            button[0]= approachButton
                            button[1]= orientationButton
                            button[2]= logButton1 //1
                            button[3] = checkConditionButton1 //1
                            button[4] = endingButton1 //1
                            visible_button()
                            disabled_button()
                            enable_button()
                            if(currentProgress>=4) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                    }
                    2-> {   day2.visibility = View.VISIBLE
                            button[0]= logButton2 //2
                            button[1]= goalButton
                            button[2]= relaxVideoButton
                            button[3]= checkConditionButton2 //2
                            button[4] = endingButton2//2
                            visible_button()
                            disabled_button()
                            enable_button()
                            if(currentProgress>=4) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                    }
                    3->{    day3.visibility = View.VISIBLE
                            button[0]=logButton3 //3
                            button[1]=selfInjuryButton
                            button[2]=eduCBMButton
                            button[3]=trainingMindButton
                            button[4]=checkConditionButton3 //3 finishActivity 출력
                            button[5]=endingButton3//3
                            visible_button()
                            disabled_button()
                            enable_button()
                        // 마무리에서 curP 하나 증가시켜서 5가 아니라 6
                            if(currentProgress>=5)
                                doneTestLog(this, pKey, testPKey)
                    }
                    4->{
                        day4.visibility = View.VISIBLE
                        button[0]=logButton4 //4
                        button[1]=relaxOTButton
                        button[2]=trainingCBMButton1 //1
                        button[3]=checkConditionButton4//4
                        button[4]=endingButton4 //4
                        visible_button()
                        disabled_button()
                        enable_button()
                        if(currentProgress>=4)
                            doneTestLog(this, pKey, testPKey)
                    }
                }
                if(count>4) {
                    if (count > 41) {
                        moon.visibility = View.INVISIBLE
                        moon_half.visibility = View.INVISIBLE
                        moon_full.visibility = View.VISIBLE
                        dayTextView?.setText("")
                        roundTextView?.setText("모든 회차가")
                        greeting.setText("완료되었습니다.")
                        delete_button()
                    } else {
                        day5.visibility = View.VISIBLE
                        button[0] = logButton5 //5
                        button[1] = trainingRelaxButton
                        button[2] = trainingCBMButton2 //2
                        button[3] = checkConditionButton5 //5
                        button[4] = endingButton5 //5
                        visible_button()
                        disabled_button()
                        enable_button()
                        if (currentProgress >= 4)
                            doneTestLog(this, pKey, testPKey)
                    }
                }
                }
                else if(group_type.toInt() == 1){
                    when(count){
                        1->{
                            groupB1.visibility = View.VISIBLE
                            button[0]= approachBButton
                            button[1] = checkConditionBButton1
                            button[2] = endingBButton1
                            visible_button()
                            disabled_button()
                            enable_button()
                            if(currentProgress>=2) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                            }
                        2->{
                            groupB2.visibility = View.VISIBLE
                            button[0]= goalBButton
                            button[1] = checkConditionBButton2
                            button[2] = endingBButton2
                            visible_button()
                            disabled_button()
                            enable_button()
                            if(currentProgress>=2) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                            }
                        3->{
                            groupB3.visibility = View.VISIBLE
                            button[0]= selfInjuryBButton
                            button[1]= eduCBMBButton
                            button[2]= trainingMindBButton
                            button[3] = checkConditionBButton3
                            button[4] = endingBButton3
                            visible_button()
                            disabled_button()
                            enable_button()
                            if(currentProgress>=4) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                            }
                    }
                    if(count > 3) {
                        if (count > 41) {
                            moon.visibility = View.INVISIBLE
                            moon_half.visibility = View.INVISIBLE
                            moon_full.visibility = View.VISIBLE
                            dayTextView?.setText("")
                            roundTextView?.setText("모든 회차가")
                            greeting.setText("완료되었습니다.")
                            delete_button()
                        } else {
                            groupB4.visibility = View.VISIBLE
                            button[0] = trainingCBMBButton
                            button[1] = checkConditionBButton4
                            button[2] = endingBButton4
                            visible_button()
                            disabled_button()
                            enable_button()
                            if (currentProgress >= 2) //쉬어가기
                                doneTestLog(this, pKey, testPKey) // status = 10 완료
                        }
                    }
                }

            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                networkError()
                //finish()
            }

        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["pKey"] = pKey
                params["testPKey"] = testPKey
                return params
            }
        }
        queue.add(request)
    }

    //버튼 비활성화 함수
    fun disabled_button(){
        for(i:Int in 0 .. 5){
            if(button[i] != null)
                button[i]!!.setEnabled(false)
        }
    }
    //버튼 활성화 함수
    fun enable_button(){
        for(i:Int in 0 .. 5){
            if(button[i]!=null)
                lastItemIndex = i
        }
        if(currentProgress <= lastItemIndex)
            button[currentProgress]!!.setEnabled(true)
        else
            button[lastItemIndex]!!.setEnabled(true)

    }
    //버튼 시각화 함수
    fun visible_button(){
        for(i:Int in 0 .. 5){
            if(button[i] != null)
                button[i]!!.visibility = VISIBLE
        }
    }
    //버튼&배열 초기화 함수
    fun delete_button() {
        day1.visibility = View.INVISIBLE //버튼 스크롤뷰 비활성화
        day2.visibility = View.INVISIBLE
        day3.visibility = View.INVISIBLE
        day4.visibility = View.INVISIBLE
        day5.visibility = View.INVISIBLE
        groupB1.visibility = View.INVISIBLE
        groupB2.visibility = View.INVISIBLE
        groupB3.visibility = View.INVISIBLE
        groupB4.visibility = View.INVISIBLE

        for (i: Int in 0..5) {
            if (button[i] != null) {
                button[i]?.visibility = View.INVISIBLE
                button[i] = null
            }
        }
    }

    //네트워크 에러 시 호출되는 함수
    fun networkError(){
        //setting 버튼 enabled = false 하기
        val setting = findViewById<ImageButton>(R.id.setting)
        setting.isEnabled = false;
        //버튼 상태 disable하기
        disabled_button()
        val alert : android.app.AlertDialog.Builder = android.app.AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("네트워크 연결에 실패했습니다.")
        alert.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.show()
        //3초후 종료
        Handler().postDelayed({
            //현재액티비티종료 finish()말고 모든 액티비티를 종료시켜야됨
            finishAffinity()
            System.runFinalization()
            System.exit(0)
        },3000L)
    }

}
