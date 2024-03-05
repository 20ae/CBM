package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.RelativeSizeSpan
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_goal.*

class GoalActivity : AppCompatActivity() {

    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_goal)

        // 모든 레이아웃
        val goalLayout1 = findViewById<ConstraintLayout>(R.id.goalLayout1)
        val goalLayout2 = findViewById<ConstraintLayout>(R.id.goalLayout2)
        val goalLayout3 = findViewById<ConstraintLayout>(R.id.goalLayout3)
        val goalLayout4 = findViewById<ConstraintLayout>(R.id.goalLayout4)
        val goalLayout5 = findViewById<ConstraintLayout>(R.id.goalLayout5)
        val goalLayout6 = findViewById<ConstraintLayout>(R.id.goalLayout6)
        val goalLayout7 = findViewById<ConstraintLayout>(R.id.goalLayout7)

        // 텍스트뷰(딜레이 적용)
        val goalTextView1_1 = findViewById<TextView>(R.id.goalTextView1_1)
        val goalTextView1_2 = findViewById<TextView>(R.id.goalTextView1_2)
        val goalTextView1_3 = findViewById<TextView>(R.id.goalTextView1_3)
        val goalTextView2_2 = findViewById<TextView>(R.id.goalTextView2_2)
        val goalTextView2_3 = findViewById<TextView>(R.id.goalTextView2_3)
        val goalTextView6_2 = findViewById<TextView>(R.id.goalTextView6_2)

        // EditText
        val goalEditText = findViewById<EditText>(R.id.goalText)

        // linearLayout
        val linearLayout = findViewById<LinearLayout>(R.id.linearLayout)

        // 모든 다음 버튼, 버튼 배경
        val goalNextBtn1 = findViewById<ImageButton>(R.id.goalNextBtn1)
        val goalNextBtn2 = findViewById<ImageButton>(R.id.goalNextBtn2)
        val goalNextBtn3 = findViewById<ImageButton>(R.id.goalNextBtn3)
        val goalNextBtn5 = findViewById<ImageButton>(R.id.goalNextBtn5)
        val goalNextBtn6 = findViewById<ImageButton>(R.id.goalNextBtn6)
        val goalNextBtn7 = findViewById<ImageButton>(R.id.goalNextBtn7)

        val goalNextBtnBack1 = findViewById<ImageView>(R.id.goalNextBtnBack1)
        val goalNextBtnBack2 = findViewById<ImageView>(R.id.goalNextBtnBack2)
        val goalNextBtnBack3 = findViewById<ImageView>(R.id.goalNextBtnBack3)
        val goalNextBtnBack4 = findViewById<ImageView>(R.id.goalNextBtnBack4)
        val goalNextBtnBack5 = findViewById<ImageView>(R.id.goalNextBtnBack5)
        val goalNextBtnBack6 = findViewById<ImageView>(R.id.goalNextBtnBack6)
        val goalNextBtnBack7 = findViewById<ImageView>(R.id.goalNextBtnBack7)

        // 화면 높이 가져오기
        val metrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        // RadioGroup, EditText높이를 화면 높이에 맞게 조정
        val params1 = goalEditText.getLayoutParams()
        params1.height = (height * (230.0/ 480.0)).toInt()
        goalEditText.layoutParams = params1

        (goalEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (14.0/480.0)).toInt()
        }

        val params2 = linearLayout.getLayoutParams()
        params2.height = (height * (260.0/ 480.0)).toInt()
        linearLayout.layoutParams = params2

        (linearLayout.layoutParams as ConstraintLayout.LayoutParams).apply {
            leftMargin = (width * (30.0/480.0)).toInt()
        }

        (linearLayout.layoutParams as ConstraintLayout.LayoutParams).apply {
            rightMargin = (width * (30.0/480.0)).toInt()
        }

        (linearLayout.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (12.0/480.0)).toInt()
        }


        // API에 전달할 로컬에 저장된 변수들
        val Pkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)

        //동적간격생성(글,그림)
        autoLayoutSpace()
        /**** goalLayout1 ****/

        val goalTitle_Big = goalTextView1_1.text.toString()
        val builder = SpannableStringBuilder(goalTitle_Big)

        val goalTitle = "목표 세우기"
        val start : Int = goalTitle_Big.indexOf(goalTitle)
        val end : Int = start + goalTitle.length

        val boldSpan = StyleSpan(Typeface.BOLD)
        builder.setSpan(boldSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        val sizeBigSpan = RelativeSizeSpan(1.3f)
        builder.setSpan(sizeBigSpan, start, end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)

        goalTextView1_1.text = builder

        visibleText(goalTextView1_2, goalTextView1_3, goalNextBtn1, goalNextBtnBack1) // 텍스트를 3초마다 띄움
        goalNextBtn1.setOnClickListener{
            goalLayout1.visibility = View.INVISIBLE
            goalLayout2.visibility = View.VISIBLE
            goalTextView2_2.text = "그리고 보여주는 선택지에서 " + getFirstName() + "님의 \n14일 후 모습을 하나만 골라보세요😉"
            visibleText(goalTextView2_2, goalTextView2_3, goalNextBtn2, goalNextBtnBack2)
        }

        /**** goalLayout2 ****/

        goalNextBtn2.setOnClickListener{
            goalLayout2.visibility = View.INVISIBLE
            goalLayout3.visibility = View.VISIBLE
        }

        /**** goalLayout3 ****/

        val firstGroup = findViewById<RadioGroup>(R.id.radioGroup)
        val secondGroup = findViewById<RadioGroup>(R.id.radioGroup2)
        var isChecking = false // 체크된 라디오 버튼이 있는지 확인하는 변수
        var goal = "" // 체크된 라디오 버튼의 번호를 저장하는 변수

        firstGroup.setOnCheckedChangeListener { group, checkedId -> // 각 그룹에 체크된 라디오 버튼이 있는 경우 다른 그룹 라디오 버튼 해제
            if(checkedId != -1 && isChecking) {
                isChecking = false
                secondGroup.clearCheck()
            }
            when(checkedId){ // 체크된 라디오 버튼의 번호 저장
                R.id.goal1 -> goal = "1"
                R.id.goal3 -> goal = "3"
                R.id.goal5 -> goal = "5"
                R.id.goal7 -> goal = "7"
                R.id.goal9 -> goal = "9"
            }
            isChecking = true

            if (isChecking){
                goalNextBtn3.visibility=View.VISIBLE
                goalNextBtnBack3.visibility = View.VISIBLE
            }
        }

        secondGroup.setOnCheckedChangeListener { group, checkedId ->
            if(checkedId != -1 && isChecking){
                isChecking = false
                firstGroup.clearCheck()
            }
            when(checkedId){ // 체크된 라디오 버튼의 번호 저장
                R.id.goal2 -> goal = "2"
                R.id.goal4 -> goal = "4"
                R.id.goal6 -> goal = "6"
                R.id.goal8 -> goal = "8"
                R.id.goal10 -> goal = "10"
            }
            isChecking = true

            if (isChecking){
                goalNextBtn3.visibility=View.VISIBLE
                goalNextBtnBack3.visibility = View.VISIBLE
            }
        }

        goalNextBtn3.setOnClickListener{
            goalLayout3.visibility = View.INVISIBLE
            goalLayout4.visibility= View.VISIBLE
        }

        /**** goalLayout4 ****/

        val goalYesBtn = findViewById<Button>(R.id.goalYesBtn)
        val goalNoBtn = findViewById<Button>(R.id.goalNoBtn)

        goalYesBtn.setOnClickListener{
            goalLayout4.visibility = View.INVISIBLE
            goalLayout5.visibility= View.VISIBLE
        }

        goalNoBtn.setOnClickListener{
            goalLayout4.visibility = View.INVISIBLE
            goalLayout7.visibility= View.VISIBLE
            visibleBtn(goalNextBtn7, goalNextBtnBack7)
        }

        /**** goalLayout5 ****/

        var goalText = ""

        goalNextBtn5.setOnClickListener{
            goalText = goalEditText.text.toString()
            if (goalText.isNullOrBlank()) {
                Toast.makeText(this, "내용을 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                goalLayout5.visibility = View.INVISIBLE
                goalLayout6.visibility= View.VISIBLE
                visibleText(goalTextView6_2, goalNextBtn6, goalNextBtnBack6)
            }
        }

        /**** goalLayout6 ****/

        goalNextBtn6.setOnClickListener{
            goalLayout6.visibility = View.INVISIBLE
            updateGoalVolley(this, Pkey, testPkey, goal, goalText)
        }

        /**** goalLayout7 ****/
        goalNextBtn7.setOnClickListener{
            if(isDoingNetwork == 0) {
                updateGoalVolley(this, Pkey, testPkey, goal, goalText)
            }
        }
    }
    //동적으로 간격생성(글자랑 그림)
    private fun autoLayoutSpace(){
        //동적위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        /**goalLayout1**/
        (goalTextView1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (113.0/480.0)).toInt()
        }
        (goalTextView1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (46.0/480.0)).toInt()
        }
        (goalTextView1_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (46.0/480.0)).toInt()
        }
        (goalNextBtn1.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout2**/
        (goalTextView2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (89.0/480.0)).toInt()
        }
        (goalTextView2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }
        (goalTextView2_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (44.0/480.0)).toInt()
        }
        (goalNextBtn2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout3**/
        (goalTextView3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (59.0/480.0)).toInt()
        }
        (goalNextBtn3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout4**/
        (goalTextView4_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (121.0/480.0)).toInt()
        }
        (goalNextBtn4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout5**/
        (goalTextView5_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (59.0/480.0)).toInt()
        }
        (goalNextBtn5.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout6**/
        (goalTextView6_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (141.0/480.0)).toInt()
        }
        (goalTextView6_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (74.0/480.0)).toInt()
        }
        (goalNextBtn6.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**goalLayout7**/
        (goalTextView7_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (193.0/480.0)).toInt()
        }
        (goalNextBtn7.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
    }

    // 텍스트를 3초마다 띄워주는 함수들
    private fun visibleText(text: TextView, btn: ImageButton, backimage : ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleBtn(btn, backimage)
        }, 3000)
    }
    private fun visibleText(text: TextView, text2: TextView, btn: ImageButton, backimage : ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleText(text2,btn, backimage)
        }, 3000)
    }
    private fun visibleBtn(btn: ImageButton, backimage : ImageView){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
            backimage.visibility = View.VISIBLE
        }, 2000)
    }

    // User의 이름에서 맨 앞 한글자만 제외하고 반환하는 함수
    fun getFirstName(): String {
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

    // 목표 입력 후 EditText 제외 다른 부분 누르면 키보드가 내려가게 하는 함수
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
    }


    // Goal, GoalText를 DB에 저장하는 PHP 호출
    private fun updateGoalVolley(context: Context, pKey:String, testPKey:String, goal: String, goalText:String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_goal.php"
        val queue = Volley.newRequestQueue(this)
        isDoingNetwork = 1
        val request: StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if(response.toInt() == 0){
                    goalLayout7.visibility = View.INVISIBLE
                    updateDoneSection(this, pKey,testPKey)
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    isDoingNetwork =0
                    netWorkError()
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                isDoingNetwork =0
            }
        ) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["Pkey"] = pKey
                params["testPkey"] = testPKey
                params["goal"] = goal
                params["goalText"] = goalText
                return params
            }
        }
        queue.add(request)
    }

    // 목표세우기가 끝났음을 알리기 위해 DB의 CurrentProgress를 +1 시키는 PHP 호출
    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)
        isDoingNetwork =1

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if(response.toInt() == 0){
                    finish()
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    isDoingNetwork =0
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                isDoingNetwork =0
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