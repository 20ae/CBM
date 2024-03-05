package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.slider.Slider
import kotlinx.android.synthetic.main.activity_log.*

class CheckConditionActivity : AppCompatActivity() {

    var status_count : Int = 0 //현재 차수
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    //횟수(1-첫번째 레이아웃,2-두번째 레이아웃 .. 등)
    var count = 1
    //두번째 장면 result값
    var resultRadio = false
    var resultRadio2 = false
    //세번째 장면 result값
    var resultRadio3 = false
    //네트워크 관련변수
    var network2Session = false
    var network3Session = false
    var network4Session = false
    var network5Session = false
    var network6Session = false
    var network7Session = false
    var network8Session = false
    var network9Session = false
    var network10Session = false
    var network11Session = false
    var network12Session = false
    var network13Session = false
    var network14Session = false
    var network15Session = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_check_condition)
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        //pkey변수
        val testPkey = MySharedPreferences.getTestPKey(this)
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)

        //현재차수 가져오기
        requestCount(this,testPkey)

        //위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        //첫번째 장면
        val checkLayout = findViewById<ConstraintLayout>(R.id.CheckLayout)
        val button = findViewById<ImageButton>(R.id.CheckNextBut)
        val text = findViewById<TextView>(R.id.CheckTextView)
        val text2 = findViewById<TextView>(R.id.CheckTextView2)
        val image = findViewById<ImageView>(R.id.CheckImageView)
        val buttonBack = findViewById<ImageView>(R.id.CheckButBack)
        text2.visibility = View.INVISIBLE
        image.visibility = View.INVISIBLE
        buttonBack.visibility = View.INVISIBLE
        button.visibility= View.INVISIBLE

        checkLayout.visibility = View.VISIBLE
        Handler().postDelayed({
            image.visibility = View.VISIBLE
        },2000)
        Handler().postDelayed({
            text2.visibility = View.VISIBLE
            buttonBack.visibility = View.VISIBLE
            button.visibility = View.VISIBLE
        },4000)

        //위치재조정
        (text.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (95.0/480.0)).toInt()
        }
        (text2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (20.0/480.0)).toInt()
        }
        (image.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (25.0/480.0)).toInt()
        }
        val params = image.layoutParams
        params.width = (width *(149.8/320.0)).toInt()
        params.height = (height * (156.0/480.0)).toInt()
        image.layoutParams = params
        (button.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //두번째 장면
        val checkLayout2 = findViewById<ConstraintLayout>(R.id.CheckLayout2)
        val checkRadioGroup = findViewById<RadioGroup>(R.id.CheckRadioGroup)
        val checkRadioGroup2 = findViewById<RadioGroup>(R.id.CheckRadioGroup2)
        resultRadio = false
        resultRadio2 = false
        val text3 = findViewById<TextView>(R.id.CheckTextView3)
        val text4 = findViewById<TextView>(R.id.CheckTextView4)
        //checkedRadioGroup과 checkedRadioGroup2 체크 확인 변수
        var checkedRG = false
        var checkedRG2 = false
        val button2 = findViewById<ImageButton>(R.id.CheckNextBut2)
        val button2Back = findViewById<ImageView>(R.id.CheckButBack2)
        checkLayout2.visibility = View.INVISIBLE
        button2.visibility = View.INVISIBLE
        button2Back.visibility = View.INVISIBLE

        //위치재조정
        (text3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (78.0/480.0)).toInt()
        }
        (text4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }
        (button2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //세번째 장면
        val checkLayout3 = findViewById<ConstraintLayout>(R.id.CheckLayout3)
        val checkRadioGroup3 = findViewById<RadioGroup>(R.id.CheckRadioGroup3)
        resultRadio3 = false
        val text5 = findViewById<TextView>(R.id.CheckTextView8)
        //checkRadioGroup3 체크 확인 변수
        var checkedRG3 = false
        val button3 = findViewById<ImageButton>(R.id.CheckNextBut3)
        val button3Back = findViewById<ImageView>(R.id.CheckButBack3)
        checkLayout3.visibility = View.INVISIBLE
        button3.visibility = View.INVISIBLE
        button3Back.visibility = View.INVISIBLE

        //위치 재조정
        (text5.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (115.0/480.0)).toInt()
        }
        (checkRadioGroup3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }
        (button3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //네번째 장면
        val checkLayout4 = findViewById<ConstraintLayout>(R.id.CheckLayout4)
        val checkRadioGroup4_1 = findViewById<RadioGroup>(R.id.CheckRadioGroup4_1)
        val checkRadioGroup4_2 = findViewById<RadioGroup>(R.id.CheckRadioGroup4_2)
        val checkRadioGroup4_3 = findViewById<RadioGroup>(R.id.CheckRadioGroup4_3)
        val checkRadioGroup4_4 = findViewById<RadioGroup>(R.id.CheckRadioGroup4_4)
        var resultRadio4 = 0
        val text6 = findViewById<TextView>(R.id.CheckTextView9)
        //checkRadioGroup4 체크 확인 변수
        var checkedRG4 = false
        val button4 = findViewById<ImageButton>(R.id.CheckNextBut4)
        val button4Back = findViewById<ImageView>(R.id.CheckButBack4)
        checkLayout4.visibility = View.INVISIBLE
        button4.visibility = View.INVISIBLE
        button4Back.visibility = View.INVISIBLE

        val params4_1 = checkRadioGroup4_2.getLayoutParams()
        params4_1.height = (height * (45.0/ 480.0)).toInt()
        checkRadioGroup4_2.layoutParams = params4_1

        val params4_2 = checkRadioGroup4_3.getLayoutParams()
        params4_2.height = (height * (45.0/ 480.0)).toInt()
        checkRadioGroup4_3.layoutParams = params4_2
        (button4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //다섯번째 장면
        val checkLayout5 = findViewById<ConstraintLayout>(R.id.CheckLayout5)
        val checkBox = findViewById<CheckBox>(R.id.CheckBox)
        val checkBox2 = findViewById<CheckBox>(R.id.CheckBox2)
        val checkBox3 = findViewById<CheckBox>(R.id.CheckBox3)
        val checkBox4 = findViewById<CheckBox>(R.id.CheckBox4)
        val checkBox5 = findViewById<CheckBox>(R.id.CheckBox5)
        val checkBox6 = findViewById<CheckBox>(R.id.CheckBox6)
        val checkEditText = findViewById<EditText>(R.id.CheckEditText)
        var resultCB = 0
        val text7 = findViewById<TextView>(R.id.CheckTextView14)
        //checkBox 체트 확인 변수
        var checkedCB = false
        val button5 = findViewById<ImageButton>(R.id.CheckNextBut5)
        val button5Back = findViewById<ImageView>(R.id.CheckButBack5)
        checkLayout5.visibility = View.INVISIBLE
        button5.visibility = View.INVISIBLE
        button5Back.visibility = View.INVISIBLE

        //위치재조정
        (text7.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (70.0/480.0)).toInt()
        }
        (checkBox.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (18.0/480.0)).toInt()
        }
        (checkBox2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (checkBox3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (checkBox4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (checkBox5.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (checkBox6.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (button5.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }


        //여섯번째 장면
        val checkLayout6 = findViewById<ConstraintLayout>(R.id.CheckLayout6)
        val depBar = findViewById<Slider>(R.id.DepressionBar)
        var depValue = 0
        val depLayout = findViewById<ConstraintLayout>(R.id.DepLevel)
        val text8 = findViewById<TextView>(R.id.CheckTextView20)
        val button6 = findViewById<ImageButton>(R.id.CheckNextBut6)
        val button6Back = findViewById<ImageView>(R.id.CheckButBack6)
        checkLayout6.visibility = View.INVISIBLE
        button6.visibility = View.INVISIBLE
        button6Back.visibility = View.INVISIBLE
        val params2 = depLayout.layoutParams
        params2.width = (width * 1.012).toInt()
        depLayout.layoutParams = params2
        val params3 = depBar.layoutParams
        params3.width = (width * (270.0/320.0)).toInt()
        depBar.layoutParams = params3
        (text8.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button6.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //일곱번째 장면
        val checkLayout7 = findViewById<ConstraintLayout>(R.id.CheckLayout7)
        val sadBar = findViewById<Slider>(R.id.SadBar)
        var sadValue = 0
        val sadLayout = findViewById<ConstraintLayout>(R.id.SadLevel)
        val text9 = findViewById<TextView>(R.id.CheckTextView21)
        val button7 = findViewById<ImageButton>(R.id.CheckNextBut7)
        val button7Back = findViewById<ImageView>(R.id.CheckButBack7)
        checkLayout7.visibility = View.INVISIBLE
        button7.visibility = View.INVISIBLE
        button7Back.visibility = View.INVISIBLE
        val params4 = sadLayout.layoutParams
        params4.width = (width * 1.012).toInt()
        sadLayout.layoutParams = params4
        val params5 = sadBar.layoutParams
        params5.width = (width * (270.0/320.0)).toInt()
        sadBar.layoutParams = params5
        (text9.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button7.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //여덟번째 장면
        val checkLayout8 = findViewById<ConstraintLayout>(R.id.CheckLayout8)
        val anxietyBar = findViewById<Slider>(R.id.AnxietyBar)
        var anxietyValue = 0
        val anxietyLayout = findViewById<ConstraintLayout>(R.id.AnxietyLevel)
        val text10 = findViewById<TextView>(R.id.CheckTextView22)
        val button8 = findViewById<ImageButton>(R.id.CheckNextBut8)
        val button8Back = findViewById<ImageView>(R.id.CheckButBack8)
        checkLayout8.visibility = View.INVISIBLE
        button8.visibility = View.INVISIBLE
        button8Back.visibility = View.INVISIBLE
        val params6 = anxietyLayout.layoutParams
        params6.width = (width * 1.012).toInt()
        anxietyLayout.layoutParams = params6
        val params7 = anxietyBar.layoutParams
        params7.width = (width * (270.0/320.0)).toInt()
        anxietyBar.layoutParams = params7
        (text10.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button8.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //아홉번째 장면
        val checkLayout9 = findViewById<ConstraintLayout>(R.id.CheckLayout9)
        val fearBar = findViewById<Slider>(R.id.FearBar)
        var fearValue = 0
        val fearLayout = findViewById<ConstraintLayout>(R.id.FearLevel)
        val text11 = findViewById<TextView>(R.id.CheckTextView23)
        val button9 = findViewById<ImageButton>(R.id.CheckNextBut9)
        val button9Back = findViewById<ImageView>(R.id.CheckButBack9)
        checkLayout9.visibility = View.INVISIBLE
        button9.visibility = View.INVISIBLE
        button9Back.visibility = View.INVISIBLE
        val params8 = fearLayout.layoutParams
        params8.width = (width * 1.012).toInt()
        fearLayout.layoutParams = params8
        val params9 = fearBar.layoutParams
        params9.width = (width * (270.0/320.0)).toInt()
        fearBar.layoutParams = params9
        (text11.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button9.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열번째 장면
        val checkLayout10 = findViewById<ConstraintLayout>(R.id.CheckLayout10)
        val lonelyBar = findViewById<Slider>(R.id.LonelyBar)
        var lonelyValue = 0
        val lonelyLayout = findViewById<ConstraintLayout>(R.id.LonelyLevel)
        val text12 = findViewById<TextView>(R.id.CheckTextView24)
        val button10 = findViewById<ImageButton>(R.id.CheckNextBut10)
        val button10Back = findViewById<ImageView>(R.id.CheckButBack10)
        checkLayout10.visibility = View.INVISIBLE
        button10.visibility = View.INVISIBLE
        button10Back.visibility = View.INVISIBLE
        val params10 = lonelyLayout.layoutParams
        params10.width = (width * 1.012).toInt()
        lonelyLayout.layoutParams = params10
        val params11 = lonelyBar.layoutParams
        params11.width = (width * (270.0/320.0)).toInt()
        lonelyBar.layoutParams = params11
        (text12.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button10.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열한번째 장면
        val checkLayout11 = findViewById<ConstraintLayout>(R.id.CheckLayout11)
        val hurtBar = findViewById<Slider>(R.id.HurtBar)
        var hurtValue = 0
        val hurtLayout = findViewById<ConstraintLayout>(R.id.HurtLevel)
        val text13 = findViewById<TextView>(R.id.CheckTextView25)
        val button11 = findViewById<ImageButton>(R.id.CheckNextBut11)
        val button11Back = findViewById<ImageView>(R.id.CheckButBack11)
        checkLayout11.visibility = View.INVISIBLE
        button11.visibility = View.INVISIBLE
        button11Back.visibility = View.INVISIBLE
        val params12 = hurtLayout.layoutParams
        params12.width = (width * 1.012).toInt()
        hurtLayout.layoutParams = params12
        val params13 = hurtBar.layoutParams
        params13.width = (width * (270.0/320.0)).toInt()
        hurtBar.layoutParams = params13
        (text13.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (107.0/480.0)).toInt()
        }
        (button11.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열두번째 장면
        val checkLayout12 = findViewById<ConstraintLayout>(R.id.CheckLayout12)
        val selfAngryBar = findViewById<Slider>(R.id.SelfAngryBar)
        var selfAngryValue = 0
        val selfAngryLayout = findViewById<ConstraintLayout>(R.id.SelfAngryLevel)
        val text14 = findViewById<TextView>(R.id.CheckTextView26)
        val button12 = findViewById<ImageButton>(R.id.CheckNextBut12)
        val button12Back = findViewById<ImageView>(R.id.CheckButBack12)
        checkLayout12.visibility = View.INVISIBLE
        button12.visibility = View.INVISIBLE
        button12Back.visibility = View.INVISIBLE
        val params14 = selfAngryLayout.layoutParams
        params14.width = (width * 1.012).toInt()
        selfAngryLayout.layoutParams = params14
        val params15 = selfAngryBar.layoutParams
        params15.width = (width * (270.0/320.0)).toInt()
        selfAngryBar.layoutParams = params15
        (text14.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button12.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열세번째 장면
        val checkLayout13 = findViewById<ConstraintLayout>(R.id.CheckLayout13)
        val otherAngryBar = findViewById<Slider>(R.id.OtherAngryBar)
        var otherAngryValue = 0
        val otherAngryLayout = findViewById<ConstraintLayout>(R.id.OtherAngryLevel)
        val text15 = findViewById<TextView>(R.id.CheckTextView27)
        val button13 = findViewById<ImageButton>(R.id.CheckNextBut13)
        val button13Back = findViewById<ImageView>(R.id.CheckButBack13)
        checkLayout13.visibility = View.INVISIBLE
        button13.visibility = View.INVISIBLE
        button13Back.visibility = View.INVISIBLE
        val params16 = otherAngryLayout.layoutParams
        params16.width = (width * 1.012).toInt()
        otherAngryLayout.layoutParams = params16
        val params17 = otherAngryBar.layoutParams
        params17.width = (width * (270.0/320.0)).toInt()
        otherAngryBar.layoutParams = params17
        (text15.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button13.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열네번째 장면
        val checkLayout14 = findViewById<ConstraintLayout>(R.id.CheckLayout14)
        val shamefulBar = findViewById<Slider>(R.id.ShamefulBar)
        var shamefulValue = 0
        val shamefulLayout = findViewById<ConstraintLayout>(R.id.ShamefulLevel)
        val text16 = findViewById<TextView>(R.id.CheckTextView28)
        val button14 = findViewById<ImageButton>(R.id.CheckNextBut14)
        val button14Back = findViewById<ImageView>(R.id.CheckButBack14)
        checkLayout14.visibility = View.INVISIBLE
        button14.visibility = View.INVISIBLE
        button14Back.visibility = View.INVISIBLE
        val params18 = shamefulLayout.layoutParams
        params18.width = (width * 1.012).toInt()
        shamefulLayout.layoutParams = params18
        val params19 = shamefulBar.layoutParams
        params19.width = (width * (270.0/320.0)).toInt()
        shamefulBar.layoutParams = params19
        (text16.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button14.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //열다섯번째 장면
        val checkLayout15 = findViewById<ConstraintLayout>(R.id.CheckLayout15)
        val vainBar = findViewById<Slider>(R.id.VainBar)
        var vainValue = 0
        val vainLayout = findViewById<ConstraintLayout>(R.id.VainLevel)
        val text17 = findViewById<TextView>(R.id.CheckTextView29)
        val button15 = findViewById<ImageButton>(R.id.CheckNextBut15)
        val button15Back = findViewById<ImageView>(R.id.CheckButBack15)
        checkLayout15.visibility = View.INVISIBLE
        button15.visibility = View.INVISIBLE
        button15Back.visibility = View.INVISIBLE
        val params20 = vainLayout.layoutParams
        params20.width = (width * 1.012).toInt()
        vainLayout.layoutParams = params20
        val params21 = vainBar.layoutParams
        params21.width = (width * (270.0/320.0)).toInt()
        vainBar.layoutParams = params21
        (text17.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (149.0/480.0)).toInt()
        }
        (button15.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //첫번째 장면 버튼 action
        button.setOnClickListener {
            checkLayout.visibility = View.INVISIBLE
            checkLayout2.visibility = View.VISIBLE
            count = count + 1
        }

        //두번째 장면 radioButton 감지 및 버튼 action
        checkRadioGroup.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.CheckRGBut1 -> resultRadio = true
                R.id.CheckRGBut2 -> resultRadio = false
            }
            if(!checkedRG){
                checkedRG = true
                if(checkedRG&&checkedRG2){
                    button2.visibility = View.VISIBLE
                    button2Back.visibility = View.VISIBLE
                }
            }

        }
        checkRadioGroup2.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.CheckRG2But1 -> resultRadio2 = true
                R.id.CheckRG2But2 -> resultRadio2 = false
            }
            if(!checkedRG2){
                checkedRG2 = true
                if(checkedRG&&checkedRG2){
                    button2.visibility = View.VISIBLE
                    button2Back.visibility = View.VISIBLE
                }
            }

        }
        button2.setOnClickListener {
            if(!network2Session){
                updateCheckCondition(this,testLogPkey,testPkey,count,resultRadio,resultRadio2)
                network2Session = true
            }
        }

        //세번째 장면 radioButton 감지 및 버튼 action
        checkRadioGroup3.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.CheckRG3But1 -> resultRadio3 = true
                R.id.CheckRG3But2 -> resultRadio3 = false
            }
            if(!checkedRG3){
                checkedRG3 = true
                button3.visibility = View.VISIBLE
                button3Back.visibility = View.VISIBLE
            }
        }
        button3.setOnClickListener {
            if(!network3Session){
                updateCheckCondition(this,testLogPkey,testPkey,count,resultRadio3)
                network3Session = true
            }
        }

        //네번째 장면 radioButton 감지 및 버튼 action
        /*
        checkRadioGroup4_1.setOnCheckedChangeListener { group, checkedId ->
            when(checkedId){
                R.id.CheckRG4But1 -> resultRadio4 = 0
                R.id.CheckRG4But2 -> resultRadio4 = 1
                R.id.CheckRG4But3 -> resultRadio4 = 2
                R.id.CheckRG4But4 -> resultRadio4 = 3
            }
            if(!checkedRG4){
                checkedRG4 = true
                button4.visibility = View.VISIBLE
                button4Back.visibility = View.VISIBLE
            }
        }
        */
        checkRadioGroup4_1.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && checkedRG4 && resultRadio4 != 0) {
                checkedRG4 = false
                checkRadioGroup4_2.clearCheck()
                checkRadioGroup4_3.clearCheck()
                checkRadioGroup4_4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.CheckRG4But1 -> resultRadio4 = 0
            }
            checkedRG4 = true
            if (checkedRG4) {
                button4.visibility = View.VISIBLE
                button4Back.visibility = View.VISIBLE
            } else {
                button4.visibility = View.INVISIBLE
                button4Back.visibility = View.INVISIBLE
            }
        }
        checkRadioGroup4_2.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && checkedRG4 && resultRadio4 != 1) {
                checkedRG4 = false
                checkRadioGroup4_1.clearCheck()
                checkRadioGroup4_3.clearCheck()
                checkRadioGroup4_4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.CheckRG4But2 -> resultRadio4 = 1
            }
            checkedRG4 = true
            if (checkedRG4) {
                button4.visibility = View.VISIBLE
                button4Back.visibility = View.VISIBLE
            } else {
                button4.visibility = View.INVISIBLE
                button4Back.visibility = View.INVISIBLE
            }
        }
        checkRadioGroup4_3.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && checkedRG4 && resultRadio4 != 2) {
                checkedRG4 = false
                checkRadioGroup4_1.clearCheck()
                checkRadioGroup4_2.clearCheck()
                checkRadioGroup4_4.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.CheckRG4But3 -> resultRadio4 = 2
            }
            checkedRG4 = true
            if (checkedRG4) {
                button4.visibility = View.VISIBLE
                button4Back.visibility = View.VISIBLE
            } else {
                button4.visibility = View.INVISIBLE
                button4Back.visibility = View.INVISIBLE
            }
        }
        checkRadioGroup4_4.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId != -1 && checkedRG4 && resultRadio4 != 3) {
                checkedRG4 = false
                checkRadioGroup4_1.clearCheck()
                checkRadioGroup4_2.clearCheck()
                checkRadioGroup4_3.clearCheck()
            }
            when (checkedId) { // 체크된 라디오 버튼의 번호 저장
                R.id.CheckRG4But4 -> resultRadio4 = 3
            }
            checkedRG4 = true
            if (checkedRG4) {
                button4.visibility = View.VISIBLE
                button4Back.visibility = View.VISIBLE
            } else {
                button4.visibility = View.INVISIBLE
                button4Back.visibility = View.INVISIBLE
            }
        }

        button4.setOnClickListener {
            if(!network4Session){
                updateCheckCondition(this,testLogPkey,testPkey,count,resultRadio4)
                network4Session = true
            }
        }
        
        //다섯번째 장면 checkBox 감지 및 버튼 action
        checkBox.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + 1
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - 1
                if((!checkBox2.isChecked)&&(!checkBox3.isChecked)
                    &&(!checkBox4.isChecked)&&(!checkBox5.isChecked)&&(!checkBox6.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        checkBox2.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + 2
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - 2
                if((!checkBox.isChecked)&&(!checkBox3.isChecked)
                    &&(!checkBox4.isChecked)&&(!checkBox5.isChecked)&&(!checkBox6.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        checkBox3.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + (Math.pow(2.0,2.0)).toInt()
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - (Math.pow(2.0,2.0)).toInt()
                if((!checkBox.isChecked)&&(!checkBox2.isChecked)
                    &&(!checkBox4.isChecked)&&(!checkBox5.isChecked)&&(!checkBox6.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        checkBox4.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + (Math.pow(2.0,3.0)).toInt()
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - (Math.pow(2.0,3.0)).toInt()
                if((!checkBox.isChecked)&&(!checkBox2.isChecked)
                    &&(!checkBox3.isChecked)&&(!checkBox5.isChecked)&&(!checkBox6.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        checkBox5.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + (Math.pow(2.0,4.0)).toInt()
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - (Math.pow(2.0,4.0)).toInt()
                if((!checkBox.isChecked)&&(!checkBox2.isChecked)
                    &&(!checkBox3.isChecked)&&(!checkBox4.isChecked)&&(!checkBox6.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        checkBox6.setOnCheckedChangeListener { buttonView, isChecked ->
            if(isChecked){
                resultCB = resultCB + (Math.pow(2.0,5.0)).toInt()
                if(!checkedCB){
                    checkedCB = true
                    button5.visibility = View.VISIBLE
                    button5Back.visibility = View.VISIBLE
                }
            }
            else{
                resultCB = resultCB - (Math.pow(2.0,5.0)).toInt()
                if((!checkBox.isChecked)&&(!checkBox2.isChecked)
                    &&(!checkBox3.isChecked)&&(!checkBox4.isChecked)&&(!checkBox5.isChecked)&&(checkedCB)){
                    checkedCB = false
                    button5.visibility = View.INVISIBLE
                    button5Back.visibility = View.INVISIBLE
                }
            }
        }
        button5.setOnClickListener {
            val result = resultCB.and(32)
            if(result==0){
                if(!network5Session){
                    updateCheckCondition(this,testLogPkey,testPkey,count,resultCB,"")
                    network5Session = true
                }
            }
            else{
                if(checkEditText.text.toString().trim().isNullOrBlank()){
                    val alert : AlertDialog.Builder = AlertDialog.Builder(this)
                    alert.setTitle("알림")
                    alert.setMessage("기타에 내용을 넣어주세요.")
                    alert.setPositiveButton("확인",
                        DialogInterface.OnClickListener { dialog, which -> })
                    (alert.create()).show()
                }
                else{
                    updateCheckCondition(this,testLogPkey,testPkey,count,resultCB,checkEditText.text.toString().trim())
                }

            }
        }

        //여섯번째 슬라이더 감지 및 버튼 action
        depBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                depValue = slider.value.toInt()
                if(depValue > 0){
                    //Toast.makeText(this@CheckConditionActivity,depValue.toString(),Toast.LENGTH_LONG).show()
                    button6.visibility = View.VISIBLE
                    button6Back.visibility = View.VISIBLE
                }
            }
        })

        button6.setOnClickListener {
            if(!network6Session){
                updateCheckCondition(this,testLogPkey,testPkey,count,depValue)
                network6Session = true
            }
        }

        //일곱번째 슬라이더 감지 및 버튼 action
        sadBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                sadValue = slider.value.toInt()
                if(sadValue > 0){
                    //Toast.makeText(this@CheckConditionActivity,sadValue.toString(),Toast.LENGTH_LONG).show()
                    button7.visibility = View.VISIBLE
                    button7Back.visibility = View.VISIBLE
                }
            }
        })

        button7.setOnClickListener {
            if(!network7Session) {
                updateCheckCondition(this, testLogPkey, testPkey, count, sadValue)
            }
        }

        //여덟번째 슬라이더 감지 및 버튼 action
        anxietyBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                anxietyValue = slider.value.toInt()
                if(anxietyValue>0){
                    //Toast.makeText(this@CheckConditionActivity,anxietyValue.toString(),Toast.LENGTH_LONG).show()
                    button8.visibility = View.VISIBLE
                    button8Back.visibility = View.VISIBLE
                }
            }
        })

        button8.setOnClickListener {
            if(!network8Session){
                network8Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,anxietyValue)
            }
        }

        //아홉번째 슬라이더 감지 및 버튼 action
        fearBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                fearValue = slider.value.toInt()
                if(fearValue > 0){
                    //Toast.makeText(this@CheckConditionActivity,fearValue.toString(),Toast.LENGTH_LONG).show()
                    button9.visibility = View.VISIBLE
                    button9Back.visibility = View.VISIBLE
                }
            }
        })

        button9.setOnClickListener {
            if(!network9Session) {
                network9Session = true
                updateCheckCondition(this, testLogPkey, testPkey, count, fearValue)
            }
        }

        //열번째 슬라이더 감지 및 버튼 action
        lonelyBar.addOnSliderTouchListener(object: Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                lonelyValue = slider.value.toInt()
                if(lonelyValue > 0){
                    //Toast.makeText(this@CheckConditionActivity,lonelyValue.toString(),Toast.LENGTH_LONG).show()
                    button10.visibility = View.VISIBLE
                    button10Back.visibility = View.VISIBLE
                }
            }
        })

        button10.setOnClickListener {
            if(!network10Session){
                network10Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,lonelyValue)
            }
        }

        //열한번째 슬라이더 감지 및 버튼 action
        hurtBar.addOnSliderTouchListener(object : Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                //Use the value
                hurtValue = slider.value.toInt()
                if(hurtValue>0){
                    //Toast.makeText(this@CheckConditionActivity,hurtValue.toString(),Toast.LENGTH_LONG).show()
                    button11.visibility = View.VISIBLE
                    button11Back.visibility = View.VISIBLE
                }
            }
        })

        button11.setOnClickListener {
            if(!network11Session){
                network11Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,hurtValue)
            }
        }

        //열두번째 슬라이더 감지 및 버튼 action
        selfAngryBar.addOnSliderTouchListener(object:Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                selfAngryValue = slider.value.toInt()
                if(selfAngryValue>0){
                    //Toast.makeText(this@CheckConditionActivity,selfAngryValue.toString(),Toast.LENGTH_LONG).show()
                    button12.visibility = View.VISIBLE
                    button12Back.visibility = View.VISIBLE
                }
            }
        })

        button12.setOnClickListener {
            if(!network12Session){
                network12Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,selfAngryValue)
            }
        }

        //열세번째 슬라이더 감지 및 버튼 action
        otherAngryBar.addOnSliderTouchListener(object:Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                otherAngryValue = slider.value.toInt()
                if(otherAngryValue>0){
                    //Toast.makeText(this@CheckConditionActivity,otherAngryValue.toString(),Toast.LENGTH_LONG).show()
                    button13.visibility = View.VISIBLE
                    button13Back.visibility = View.VISIBLE
                }
            }
        })

        button13.setOnClickListener {
            if(!network13Session){
                network13Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,otherAngryValue)
            }
        }

        //열네번째 슬라이더 감지 및 버튼 action
        shamefulBar.addOnSliderTouchListener(object:Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                shamefulValue = slider.value.toInt()
                if(shamefulValue > 0){
                    //Toast.makeText(this@CheckConditionActivity,shamefulValue.toString(),Toast.LENGTH_LONG).show()
                    button14.visibility = View.VISIBLE
                    button14Back.visibility = View.VISIBLE
                }
            }
        })
        button14.setOnClickListener {
            if(!network14Session){
                network14Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,shamefulValue)
            }
        }

        //열다섯번째 슬라이더 감지 및 버튼 action
        vainBar.addOnSliderTouchListener(object: Slider.OnSliderTouchListener{
            override fun onStartTrackingTouch(slider: Slider) {

            }

            override fun onStopTrackingTouch(slider: Slider) {
                vainValue = slider.value.toInt()
                if(vainValue>0){
                    //Toast.makeText(this@CheckConditionActivity,vainValue.toString(),Toast.LENGTH_LONG).show()
                    button15.visibility = View.VISIBLE
                    button15Back.visibility = View.VISIBLE
                }
            }
        })


        button15.setOnClickListener {
            if(!network15Session){
                network15Session = true
                updateCheckCondition(this,testLogPkey,testPkey,count,vainValue)
            }
        }

    }

    private fun requestCount(context: Context, testPKey: String){

        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_count_test.php"

        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST,url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                var result = response.toInt()

                if(result >= 0){
                    status_count = result
                }
                else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError2()
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError2()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["testPKey"]=testPKey
                return params
            }
        }
        queue.add(request)
    }

    private fun next3page(){
        val checkLayout2 = findViewById<ConstraintLayout>(R.id.CheckLayout2)
        val checkLayout3 = findViewById<ConstraintLayout>(R.id.CheckLayout3)
        val checkLayout6 = findViewById<ConstraintLayout>(R.id.CheckLayout6)
        checkLayout2.visibility = View.INVISIBLE
        if(resultRadio||resultRadio2){
            checkLayout3.visibility = View.VISIBLE
            count = count + 1
        }
        else{
            checkLayout6.visibility = View.VISIBLE
            count = count + 4
        }
    }
    private fun next4page(){
        val checkLayout3 = findViewById<ConstraintLayout>(R.id.CheckLayout3)
        val checkLayout4 = findViewById<ConstraintLayout>(R.id.CheckLayout4)
        val checkLayout6 = findViewById<ConstraintLayout>(R.id.CheckLayout6)
        checkLayout3.visibility = View.INVISIBLE
        if(resultRadio3){
            checkLayout4.visibility = View.VISIBLE
            count = count + 1
        }
        else{
            checkLayout6.visibility = View.VISIBLE
            count = count + 3
        }
    }
    private fun next5page(){
        val checkLayout4 = findViewById<ConstraintLayout>(R.id.CheckLayout4)
        val checkLayout5 = findViewById<ConstraintLayout>(R.id.CheckLayout5)
        checkLayout4.visibility = View.INVISIBLE
        checkLayout5.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next6page(){
        val checkLayout5 = findViewById<ConstraintLayout>(R.id.CheckLayout5)
        val checkLayout6 = findViewById<ConstraintLayout>(R.id.CheckLayout6)
        checkLayout5.visibility = View.INVISIBLE
        checkLayout6.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next7page(){
        val checkLayout6 = findViewById<ConstraintLayout>(R.id.CheckLayout6)
        val checkLayout7 = findViewById<ConstraintLayout>(R.id.CheckLayout7)
        checkLayout6.visibility = View.INVISIBLE
        checkLayout7.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next8page(){
        val checkLayout7 = findViewById<ConstraintLayout>(R.id.CheckLayout7)
        val checkLayout8 = findViewById<ConstraintLayout>(R.id.CheckLayout8)
        checkLayout7.visibility = View.INVISIBLE
        checkLayout8.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next9page(){
        val checkLayout8 = findViewById<ConstraintLayout>(R.id.CheckLayout8)
        val checkLayout9 = findViewById<ConstraintLayout>(R.id.CheckLayout9)
        checkLayout8.visibility = View.INVISIBLE
        checkLayout9.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next10page(){
        val checkLayout9 = findViewById<ConstraintLayout>(R.id.CheckLayout9)
        val checkLayout10 = findViewById<ConstraintLayout>(R.id.CheckLayout10)
        checkLayout9.visibility = View.INVISIBLE
        checkLayout10.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next11page(){
        val checkLayout10 = findViewById<ConstraintLayout>(R.id.CheckLayout10)
        val checkLayout11 = findViewById<ConstraintLayout>(R.id.CheckLayout11)
        checkLayout10.visibility = View.INVISIBLE
        checkLayout11.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next12page(){
        val checkLayout11 = findViewById<ConstraintLayout>(R.id.CheckLayout11)
        val checkLayout12 = findViewById<ConstraintLayout>(R.id.CheckLayout12)
        checkLayout11.visibility = View.INVISIBLE
        checkLayout12.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next13page(){
        val checkLayout12 = findViewById<ConstraintLayout>(R.id.CheckLayout12)
        val checkLayout13 = findViewById<ConstraintLayout>(R.id.CheckLayout13)
        checkLayout12.visibility = View.INVISIBLE
        checkLayout13.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next14page(){
        val checkLayout13 = findViewById<ConstraintLayout>(R.id.CheckLayout13)
        val checkLayout14 = findViewById<ConstraintLayout>(R.id.CheckLayout14)
        checkLayout13.visibility = View.INVISIBLE
        checkLayout14.visibility = View.VISIBLE
        count = count + 1
    }

    private fun next15page(){
        val checkLayout14 = findViewById<ConstraintLayout>(R.id.CheckLayout14)
        val checkLayout15 = findViewById<ConstraintLayout>(R.id.CheckLayout15)
        checkLayout14.visibility = View.INVISIBLE
        checkLayout15.visibility = View.VISIBLE
        count = count + 1
    }

    private fun updateCheckCondition(context: Context, pKey: String,testPKey: String, count : Int, value: Int){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_check_condition.php"
        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                var result = response.toInt()
                if(result >= 0){
                    when(count){
                        4 -> next5page()
                        6 -> next7page()
                        7 -> next8page()
                        8 -> next9page()
                        9 -> next10page()
                        10 -> next11page()
                        11 -> next12page()
                        12 -> next13page()
                        13 -> next14page()
                        14 -> next15page()
                        15 ->{
                            if(isDoingNetwork == 0){
                                updateDoneSection(this, pKey, testPKey)
                            }
                        }
                        else ->{
                            //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                            netWorkError()
                            when(count){
                                4 -> network4Session = false
                                6 -> network6Session = false
                                7 -> network7Session = false
                                8 -> network8Session = false
                                9 -> network9Session = false
                                10 -> network10Session = false
                                11 -> network11Session = false
                                12 -> network12Session = false
                                13 -> network13Session = false
                                14 -> network14Session = false
                                15 -> network15Session = false
                            }
                        }
                    }
                }
                else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    when(count){
                        4 -> network4Session = false
                        6 -> network6Session = false
                        7 -> network7Session = false
                        8 -> network8Session = false
                        9 -> network9Session = false
                        10 -> network10Session = false
                        11 -> network11Session = false
                        12 -> network12Session = false
                        13 -> network13Session = false
                        14 -> network14Session = false
                        15 -> network15Session = false
                    }
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                when(count){
                    4 -> network4Session = false
                    6 -> network6Session = false
                    7 -> network7Session = false
                    8 -> network8Session = false
                    9 -> network9Session = false
                    10 -> network10Session = false
                    11 -> network11Session = false
                    12 -> network12Session = false
                    13 -> network13Session = false
                    14 -> network14Session = false
                    15 -> network15Session = false
                }
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]=pKey
                params["TestPkey"]=testPKey
                params["Count"] = count.toString()
                params["Value"] = value.toString()
                params["Value2"] = ""
                params["SelfCheck5Text"] = ""
                return params
            }
        }
        queue.add(request)
    }

    private fun updateCheckCondition(context: Context, pKey: String,testPKey: String, count : Int, value: Boolean){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_check_condition.php"
        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                var result = response.toInt()
                if(result >=0){
                    if(count==3){
                        next4page()
                    }
                }
                else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    network3Session = false
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                network3Session = false
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]= pKey
                params["TestPkey"]= testPKey
                params["Count"] = count.toString()
                params["Value"] = if(value) "1" else "0"
                params["Value2"] = ""
                params["SelfCheck5Text"] = ""
                return params
            }
        }
        queue.add(request)
    }

    private fun updateCheckCondition(context: Context, pKey: String,testPKey: String, count : Int, value: Boolean, value2: Boolean){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_check_condition.php"
        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                var result = response.toInt()
                if(result >=0){
                    if(count==2){
                        next3page()
                    }
                }
                else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    network2Session = false
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                network2Session = false
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]= pKey
                params["TestPkey"]= testPKey
                params["Count"] = count.toString()
                params["Value"] = if(value) "1" else "0"
                params["Value2"] = if(value2) "1" else "0"
                params["SelfCheck5Text"] = ""
                return params


            }
        }
        queue.add(request)
    }

    private fun updateCheckCondition(context: Context, pKey: String,testPKey: String, count : Int, value: Int, text:String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_check_condition.php"
        val queue = Volley.newRequestQueue(this)
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                var result = response.toInt()
                if(result>=0){
                    next6page()
                }
                else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    network5Session = false
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                network5Session = false
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]=pKey
                params["TestPkey"]=testPKey
                params["Count"] = count.toString()
                params["Value"] = value.toString()
                params["Value2"] = ""
                params["SelfCheck5Text"] = text
                return params
            }
        }
        queue.add(request)
    }

    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)
        isDoingNetwork =1
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                if(response.toInt() == 0){
                    finish()
                    if(status_count == 3){
                        val intent = Intent(this,FinishActivity::class.java)
                        startActivity(intent)
                    }
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    isDoingNetwork =0
                    netWorkError()
                    network15Session = false
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                isDoingNetwork =0
                netWorkError()
                network15Session = false
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
    //키보드내리기
    fun closeKeyboard(v : View)
    {
        if(v != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
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
    //네트워크 실패 시 호출
    private fun netWorkError2(){
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("네트워크 연결에 실패했습니다.")
        alert.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> finish()})
        alert.show()
    }
}