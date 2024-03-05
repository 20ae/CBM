package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import org.w3c.dom.Text

class ApproachActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_approach)

        //훈련집단인지 비교집단인지 받아오기
        val group_type = MySharedPreferences.getGroupType(this)

        //위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels

        //첫번째 뷰
        val approachLayout = findViewById<ConstraintLayout>(R.id.ApproachLayout1)
        val approachBut = findViewById<ImageButton>(R.id.ApproachNextBut)
        val text_2 = findViewById<TextView>(R.id.ApproachTextView)
        val text = findViewById<TextView>(R.id.ApproachTextView2)
        val buttonBack = findViewById<ImageView>(R.id.ApproachNextButBack)
        approachLayout.visibility = View.VISIBLE
        text.visibility = View.INVISIBLE
        approachBut.visibility = View.INVISIBLE
        buttonBack.visibility = View.INVISIBLE
        //시간차
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            approachBut.visibility = View.VISIBLE
            buttonBack.visibility = View.VISIBLE
        },2000)
        //위치재조정
        (text_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (193.0/480.0)).toInt()
        }
        (text.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (18.0/480.0)).toInt()
        }
        (approachBut.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //두번째 뷰
        val approachLayout2 = findViewById<ConstraintLayout>(R.id.ApproachLayout2)
        val approachBut2 = findViewById<ImageButton>(R.id.ApproachNextBut2)
        val text2_2 = findViewById<TextView>(R.id.ApproachTextView3)
        val text2 = findViewById<TextView>(R.id.ApproachTextView4)
        val text3 = findViewById<TextView>(R.id.ApproachTextView5)
        val text3_1 = findViewById<TextView>(R.id.ApproachTextView5_1)
        val text4 = findViewById<TextView>(R.id.ApproachTextView6)
        val buttonBack2 = findViewById<ImageView>(R.id.ApproachNextButBack2)
        approachLayout2.visibility = View.INVISIBLE
        text2.visibility = View.INVISIBLE
        text3.visibility = View.INVISIBLE
        text3_1.visibility = View.INVISIBLE
        text4.visibility = View.INVISIBLE
        approachBut2.visibility = View.INVISIBLE
        buttonBack2.visibility = View.INVISIBLE
        approachBut.setOnClickListener {
            approachLayout.visibility = View.INVISIBLE
            approachLayout2.visibility = View.VISIBLE

            if(group_type.toInt() == 0){
                Handler().postDelayed({
                    text2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    text3.visibility = View.VISIBLE
                },4000)
                Handler().postDelayed({
                    text4.visibility = View.VISIBLE
                    approachBut2.visibility = View.VISIBLE
                    buttonBack2.visibility = View.VISIBLE
                },6000)
            }
            else if(group_type.toInt() == 1){
                Handler().postDelayed({
                    text2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    text3_1.visibility = View.VISIBLE
                },4000)
                Handler().postDelayed({
                    text4.visibility = View.VISIBLE
                    approachBut2.visibility = View.VISIBLE
                    buttonBack2.visibility = View.VISIBLE
                },6000)
            }

        }
        (text2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (104.0/480.0)).toInt()
        }
        (text2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (33.0/480.0)).toInt()
        }
        (text3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (33.0/480.0)).toInt()
        }
        (text3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (33.0/480.0)).toInt()
        }
        (text4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (18.0/480.0)).toInt()
        }
        (approachBut2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        approachBut2.setOnClickListener {
            val testPkey = MySharedPreferences.getTestPKey(this)
            val testLogPkey = MySharedPreferences.getTestLogPKey(this)
            if(isDoingNetwork == 0){
                updateDoneSection(this, testLogPkey,testPkey)
            }
        }

    }


    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)

        isDoingNetwork=1

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                if (response.toInt() == 0){
                    finish()
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    isDoingNetwork =0
                }
            },
            Response.ErrorListener { error ->
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