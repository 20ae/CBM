package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.graphics.Typeface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Spannable
import android.text.SpannableStringBuilder
import android.text.style.StyleSpan
import android.util.DisplayMetrics
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class OrientationActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_orientation)

        //위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        //글씨체 style를 bold로 변경
        val text6 = findViewById<TextView>(R.id.OrinTextView6)
        val orinText6 = text6.text.toString()
        val builder_orinText6 = SpannableStringBuilder(orinText6)

        val text8 = findViewById<TextView>(R.id.OrinTextView8)
        val orinText8 = text8.text.toString()
        val builder_orinText8 = SpannableStringBuilder(orinText8)

        val text10 = findViewById<TextView>(R.id.OrinTextView10)
        val orinText10 : String = text10.text.toString()
        val builder_orinText10 = SpannableStringBuilder(orinText10)

        val text17 = findViewById<TextView>(R.id.OrinTextView17)
        val orinText17 : String = text17.text.toString()
        val builder_orinText17 = SpannableStringBuilder(orinText17)

        val text23 = findViewById<TextView>(R.id.OrinTextView23)
        val orinText23 : String = text23.text.toString()
        val builder_orinText23 = SpannableStringBuilder(orinText23)

        val text24 = findViewById<TextView>(R.id.OrinTextView24)
        val orinText24 : String = text24.text.toString()
        val builder_orinText24 = SpannableStringBuilder(orinText24)

        builder_orinText6.setSpan(StyleSpan(Typeface.BOLD) , 0,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        builder_orinText6.setSpan(StyleSpan(Typeface.BOLD), 14,24, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder_orinText8.setSpan(StyleSpan(Typeface.BOLD), 13,26, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder_orinText10.setSpan(StyleSpan(Typeface.BOLD),4,11, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder_orinText17.setSpan(StyleSpan(Typeface.BOLD),46,85,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder_orinText23.setSpan(StyleSpan(Typeface.BOLD),8,13,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        builder_orinText24.setSpan(StyleSpan(Typeface.BOLD),15,18,Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)

        text6.text = builder_orinText6
        text8.text = builder_orinText8
        text10.text = builder_orinText10
        text17.text = builder_orinText17
        text23.text = builder_orinText23
        text24.text = builder_orinText24

        //첫번째 장면
        val orinLayout = findViewById<ConstraintLayout>(R.id.OrinLayout)
        val orinImage = findViewById<ImageView>(R.id.OrinImageView)
        val text = findViewById<TextView>(R.id.OrinTextView)
        val text2 = findViewById<TextView>(R.id.OrinTextView2)
        val orinButBack = findViewById<ImageView>(R.id.OrinNextButBack)
        val orinNextBut = findViewById<ImageButton>(R.id.OrinNextBut)
        orinLayout.visibility = View.VISIBLE
        text2.visibility = View.INVISIBLE
        orinImage.visibility = View.INVISIBLE
        orinButBack.visibility = View.INVISIBLE
        orinNextBut.visibility = View.INVISIBLE
        Handler().postDelayed({
            orinImage.visibility = View.VISIBLE
        },2000)
        Handler().postDelayed({
            text2.visibility = View.VISIBLE
        },4000)
        Handler().postDelayed({
            orinButBack.visibility = View.VISIBLE
            orinNextBut.visibility = View.VISIBLE
        },6000)
        (text.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (85.0/480.0)).toInt()
        }
        (orinNextBut.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        val params = orinImage.layoutParams
        params.width = (width * (180.0/320.0)).toInt()
        params.height = (height * (136.0/480.0)).toInt()
        orinImage.layoutParams = params
        (orinImage.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (text2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }

        //두번째 장면
        val orinLayout2 = findViewById<ConstraintLayout>(R.id.OrinLayout2)
        val text3 = findViewById<TextView>(R.id.OrinTextView3)
        //val text6 = findViewById<TextView>(R.id.OrinTextView6)
        val orinButBack2 = findViewById<ImageView>(R.id.OrinNextButBack2)
        val orinNextBut2 = findViewById<ImageButton>(R.id.OrinNextBut2)
        orinLayout2.visibility = View.INVISIBLE
        text6.visibility = View.INVISIBLE
        orinButBack2.visibility = View.INVISIBLE
        orinNextBut2.visibility = View.INVISIBLE
        (text3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (106.0/480.0)).toInt()
        }
        (text6.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (55.0/480.0)).toInt()
        }
        (orinNextBut2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //세번째 장면
        val orinLayout3 = findViewById<ConstraintLayout>(R.id.OrinLayout3)
        val orinImage2 = findViewById<ImageView>(R.id.OrinImageView2)
        val text7 = findViewById<TextView>(R.id.OrinTextView7)
        //val text8 = findViewById<TextView>(R.id.OrinTextView8)
        val orinButBack3 = findViewById<ImageView>(R.id.OrinNextButBack3)
        val orinNextBut3 = findViewById<ImageButton>(R.id.OrinNextBut3)
        orinLayout3.visibility = View.INVISIBLE
        orinImage2.visibility = View.INVISIBLE
        text8.visibility = View.INVISIBLE
        orinButBack3.visibility = View.INVISIBLE
        orinNextBut3.visibility = View.INVISIBLE
        (text7.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (80.0/480.0)).toInt()
        }
        val params2 = orinImage2.layoutParams
        params2.width = (width * (205.0/320.0)).toInt()
        params2.height = (height * (115.1/480.0)).toInt()
        orinImage2.layoutParams = params2
        (orinImage2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (18.0/480.0)).toInt()
        }
        (text8.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (18.0/480.0)).toInt()
        }
        (orinNextBut3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //네번째 장면
        val orinLayout4 = findViewById<ConstraintLayout>(R.id.OrinLayout4)
        val text9 = findViewById<TextView>(R.id.OrinTextView9)
        //val text10 = findViewById<TextView>(R.id.OrinTextView10)
        val orinButBack4 = findViewById<ImageView>(R.id.OrinNextButBack4)
        val orinNextBut4 = findViewById<ImageButton>(R.id.OrinNextBut4)
        orinLayout4.visibility = View.INVISIBLE
        text10.visibility = View.INVISIBLE
        orinButBack4.visibility = View.INVISIBLE
        orinNextBut4.visibility = View.INVISIBLE
        (text9.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (100.0/480.0)).toInt()
        }
        (text10.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (69.0/480.0)).toInt()
        }
        (orinNextBut4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //다섯번째 장면
        val orinLayout5 = findViewById<ConstraintLayout>(R.id.OrinLayout5)
        val text11 = findViewById<TextView>(R.id.OrinTextView11)
        val text12 = findViewById<TextView>(R.id.OrinTextView12)
        val text13 = findViewById<TextView>(R.id.OrinTextView13)
        val text14 = findViewById<TextView>(R.id.OrinTextView14)
        val orinButBack5 = findViewById<ImageView>(R.id.OrinNextButBack5)
        val orinNextBut5 = findViewById<ImageButton>(R.id.OrinNextBut5)
        orinLayout5.visibility = View.INVISIBLE
        text12.visibility = View.INVISIBLE
        text13.visibility = View.INVISIBLE
        text14.visibility = View.INVISIBLE
        orinButBack5.visibility = View.INVISIBLE
        orinNextBut5.visibility = View.INVISIBLE
        (text11.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (70.0/480.0)).toInt()
        }
        (text12.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (text13.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (20.0/480.0)).toInt()
        }
        (text14.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (30.0/480.0)).toInt()
        }
        (orinNextBut5.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //여섯번째 장면
        val orinLayout6 = findViewById<ConstraintLayout>(R.id.OrinLayout6)
        val text15 = findViewById<TextView>(R.id.OrinTextView15)
        val text16 = findViewById<TextView>(R.id.OrinTextView16)
        //val text17 = findViewById<TextView>(R.id.OrinTextView17)
        val orinButBack6 = findViewById<ImageView>(R.id.OrinNextButBack6)
        val orinNextBut6 = findViewById<ImageButton>(R.id.OrinNextBut6)
        orinLayout6.visibility = View.INVISIBLE
        text16.visibility = View.INVISIBLE
        text17.visibility = View.INVISIBLE
        orinButBack6.visibility = View.INVISIBLE
        orinNextBut6.visibility = View.INVISIBLE
        (text15.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (75.0/480.0)).toInt()
        }
        (text16.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (25.0/480.0)).toInt()
        }
        (text17.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (25.0/480.0)).toInt()
        }
        (orinNextBut6.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        //추가된 페이지-1
        val orinLayout6_1 = findViewById<ConstraintLayout>(R.id.OrinLayout6_1)
        val orinButBack6_1 = findViewById<ImageView>(R.id.OrinNextButBack8)
        val orinNextBut6_1 = findViewById<ImageButton>(R.id.OrinNextBut8)
        val text21 = findViewById<TextView>(R.id.OrinTextView21)
        val text22 = findViewById<TextView>(R.id.OrinTextView22)
        val orinImage3 = findViewById<ImageView>(R.id.OrinImageView3)
        orinLayout6_1.visibility = View.INVISIBLE
        text22.visibility = View.INVISIBLE
        orinNextBut6_1.visibility = View.INVISIBLE
        orinButBack6_1.visibility = View.INVISIBLE
        (text21.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin =(height * (60.0/480.0)).toInt()
        }
        val params3 = orinImage3.layoutParams
        params3.width = (width * (212.0/320.0)).toInt()
        params3.height = (height * (123.0/480.0)).toInt()
        orinImage3.layoutParams = params3
        (orinImage3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (text22.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (15.0/480.0)).toInt()
        }
        (orinNextBut6_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }


        //추가된 페이지-2
        val orinLayout6_2 =findViewById<ConstraintLayout>(R.id.OrinLayout6_2)
        val orinButBack6_2 = findViewById<ImageView>(R.id.OrinNextButBack9)
        val orinNextBut6_2 = findViewById<ImageButton>(R.id.OrinNextBut9)
        orinLayout6_2.visibility = View.INVISIBLE
        text24.visibility = View.INVISIBLE
        orinNextBut6_2.visibility = View.INVISIBLE
        orinButBack6_2.visibility = View.INVISIBLE
        (text23.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (100.0/480.0)).toInt()
        }
        (text24.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (50.0/480.0)).toInt()
        }
        (orinNextBut6_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //일곱번째 장면
        val orinLayout7 = findViewById<ConstraintLayout>(R.id.OrinLayout7)
        val text18 = findViewById<TextView>(R.id.OrinTextView18)
        val text19 = findViewById<TextView>(R.id.OrinTextView19)
        val text20 = findViewById<TextView>(R.id.OrinTextView20)
        val orinButBack7 = findViewById<ImageView>(R.id.OrinNextButBack7)
        val orinNextBut7 = findViewById<ImageButton>(R.id.OrinNextBut7)
        orinLayout7.visibility = View.INVISIBLE
        text19.visibility = View.INVISIBLE
        text20.visibility = View.INVISIBLE
        orinButBack7.visibility = View.INVISIBLE
        orinNextBut7.visibility = View.INVISIBLE
        (text18.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (89.0/480.0)).toInt()
        }
        (text19.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (28.0/480.0)).toInt()
        }
        (text20.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (28.0/480.0)).toInt()
        }
        (orinNextBut7.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //첫번째 장면의 버튼 액션
        orinNextBut.setOnClickListener {
            orinLayout.visibility = View.INVISIBLE
            orinLayout2.visibility = View.VISIBLE
            Handler().postDelayed({
                text6.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                orinButBack2.visibility = View.VISIBLE
                orinNextBut2.visibility = View.VISIBLE
            },4000)
        }

        //두번째 장면의 버튼 액션
        orinNextBut2.setOnClickListener {
            orinLayout2.visibility = View.INVISIBLE
            orinLayout3.visibility = View.VISIBLE
            Handler().postDelayed({
                orinImage2.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                text8.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                orinButBack3.visibility = View.VISIBLE
                orinNextBut3.visibility = View.VISIBLE
            },6000)
        }

        //세번째 장면의 버튼 액션
        orinNextBut3.setOnClickListener {
            orinLayout3.visibility = View.INVISIBLE
            orinLayout4.visibility = View.VISIBLE
            Handler().postDelayed({
                text10.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                orinButBack4.visibility = View.VISIBLE
                orinNextBut4.visibility = View.VISIBLE
            },4000)
        }

        //네번째 장면의 버튼 액션
        orinNextBut4.setOnClickListener {
            orinLayout4.visibility = View.INVISIBLE
            orinLayout5.visibility = View.VISIBLE
            Handler().postDelayed({
                text12.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                text13.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                text14.visibility = View.VISIBLE
            },6000)
            Handler().postDelayed({
                orinButBack5.visibility = View.VISIBLE
                orinNextBut5.visibility = View.VISIBLE
            },8000)

        }

        //다섯번째 장면의 버튼 액션
        orinNextBut5.setOnClickListener {
            orinLayout5.visibility = View.INVISIBLE
            orinLayout6.visibility = View.VISIBLE
            Handler().postDelayed({
                text16.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                text17.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                orinButBack6.visibility = View.VISIBLE
                orinNextBut6.visibility = View.VISIBLE
            },6000)
        }

        //여섯번째 장면의 버튼 액션
        orinNextBut6.setOnClickListener {
            orinLayout6.visibility = View.INVISIBLE
            orinLayout6_1.visibility = View.VISIBLE
            Handler().postDelayed({
                text22.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                orinNextBut6_1.visibility = View.VISIBLE
                orinButBack6_1.visibility = View.VISIBLE
            },4000)
        }

        //추가된 페이지 장면의 버튼 액션
        orinNextBut6_1.setOnClickListener {
            orinLayout6_1.visibility = View.INVISIBLE
            orinLayout6_2.visibility = View.VISIBLE
            Handler().postDelayed({
                text24.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                orinNextBut6_2.visibility = View.VISIBLE
                orinButBack6_2.visibility = View.VISIBLE
            },4000)
        }

        orinNextBut6_2.setOnClickListener {
            orinLayout6_2.visibility = View.INVISIBLE
            orinLayout7.visibility = View.VISIBLE
            Handler().postDelayed({
                text19.visibility = View.VISIBLE
            },2000)

            Handler().postDelayed({
                text20.visibility = View.VISIBLE
            },4000)

            Handler().postDelayed({
                orinButBack7.visibility = View.VISIBLE
                orinNextBut7.visibility = View.VISIBLE
            },6000)
        }


        //일곱번째 장면의 버튼 액션
        orinNextBut7.setOnClickListener {
            val testPkey = MySharedPreferences.getTestPKey(this)
            val testLogPkey = MySharedPreferences.getTestLogPKey(this)
            if (isDoingNetwork == 0){
                updateDoneSection(this, testLogPkey,testPkey)
            }
        }
    }
    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)
        isDoingNetwork =1
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                if (response.toInt() == 0){
                    finish()
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    isDoingNetwork =0
                    netWorkError()
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