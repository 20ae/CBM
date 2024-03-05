package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_goal.*
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity() {
    var isDoingNetwork = 0  //통신 전역변수 0:클릭가능
    @RequiresApi(Build.VERSION_CODES.R)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        val loginText = findViewById<TextView>(R.id.loginText)
        val group = findViewById<RadioGroup>(R.id.loginGroup)
        val nameText = findViewById<TextView>(R.id.loginNameText)
        val nameEditText = findViewById<EditText>(R.id.loginName)
        val numberText = findViewById<TextView>(R.id.loginNumberText)
        val numberEditText = findViewById<EditText>(R.id.loginNumber)
        val login = findViewById<Button>(R.id.login)
        val signup = findViewById<Button>(R.id.goSignup)

        // 화면 높이 가져오기
        val metrics = resources.displayMetrics
        val height = metrics.heightPixels

        // RadioGroup, EditText, Button 높이를 화면 높이에 맞게 조정
        val params1 = group.getLayoutParams()
        params1.height = (height * (35.0/ 480.0)).toInt()
        group.layoutParams = params1

        val params2 = nameEditText.getLayoutParams()
        params2.height = (height * (33.0/ 480.0)).toInt()
        nameEditText.layoutParams = params2

        val params3 = numberEditText.getLayoutParams()
        params3.height = (height * (33.0/ 480.0)).toInt()
        numberEditText.layoutParams = params3

        val params4 = login.getLayoutParams()
        params4.height = (height * (33.0/ 480.0)).toInt()
        login.layoutParams = params4


        // 간격을 화면 높이에 맞게 조정
        (loginText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }

        (group.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (24.0/480.0)).toInt()
        }

        (nameText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (18.0/480.0)).toInt()
        }

        (nameEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (7.0/480.0)).toInt()
        }

        (numberText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (16.0/480.0)).toInt()
        }

        (numberEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (7.0/480.0)).toInt()
        }

        (login.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (34.0/480.0)).toInt()
        }

        (signup.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (32.0/480.0)).toInt()
        }

        var group_type = ""
        group.setOnCheckedChangeListener{radioGroup, checkedid ->
            when(checkedid){
                R.id.loginGroupA -> group_type = "0"
                R.id.loginGroupB -> group_type = "1"
            }
        }

        login.setOnClickListener {

            val name = findViewById<EditText>(R.id.loginName).text.toString().trim()
            val number = findViewById<EditText>(R.id.loginNumber).text.toString().trim()
            if (group_type.equals("")){
                Toast.makeText(this, "그룹을 선택하세요", Toast.LENGTH_SHORT).show()
            }
            else if (name.isNullOrBlank()) {
                Toast.makeText(this, "이름을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else if(number.isNullOrBlank()){
                Toast.makeText(this, "참가번호를 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else {
                if(isDoingNetwork==0){
                    loginVolley(this, name, number, group_type)
                }
            }
        }

        signup.paintFlags = signup.paintFlags or Paint.UNDERLINE_TEXT_FLAG
        signup.text = getString(R.string.underlined_text)

        signup.setOnClickListener {
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
    }

        private fun loginVolley(context: Context, uname: String, unumber: String, ugrouptype: String) {
            val url = "http://healingmindcenter.com/cbm_app/cbm_api/tryLogin.php"
            val queue = Volley.newRequestQueue(this)
            isDoingNetwork = 1
            val request: StringRequest = object : StringRequest(Method.POST, url,
                Response.Listener { response ->
                    if (response.toInt() == -1) {
                        //Toast.makeText(this, "일치하지 않습니다", Toast.LENGTH_SHORT).show()
                        loginError()
                        isDoingNetwork = 0
                    } else{
                        MySharedPreferences.setUserId(this, unumber)
                        MySharedPreferences.setUserName(this, uname)
                        MySharedPreferences.setUserPKey(this, response.toString())
                        MySharedPreferences.setGroupType(this, ugrouptype)
                        Toast.makeText(
                            context, "${MySharedPreferences.getUserName(this)}님 로그인 되었습니다.", Toast.LENGTH_SHORT
                        ).show()

                        var intent = Intent(this, HomeActivity::class.java)
                        //홈에서 뒤로가기 막음(히스토리 초기화)
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent)
                    }
                },
                Response.ErrorListener { error ->
                    //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    isDoingNetwork = 0
                }
            ) {
                override fun getParams(): Map<String, String> {
                    val params: MutableMap<String, String> = HashMap()
                    params["name"] = uname
                    params["id"] = unumber
                    params["group_type"] = ugrouptype
                    return params
                }
            }
            queue.add(request)
        }

    // 아이디, 참가번호 입력 후 EditText 제외 다른 부분 누르면 키보드가 내려가게 하는 함수
    override fun onTouchEvent(event: MotionEvent): Boolean {
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
        return true
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
    private fun loginError(){
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("일치하지 않습니다.")
        alert.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.show()
    }
}


