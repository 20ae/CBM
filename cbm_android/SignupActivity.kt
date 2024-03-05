package ac.duksung.cbm_android

import android.R.attr
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.graphics.Paint
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.MotionEvent
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.R.attr.button




class SignupActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val signUpText = findViewById<TextView>(R.id.signUpText)
        val signUpGroup = findViewById<RadioGroup>(R.id.signUpGroup)
        val signUpNameText = findViewById<TextView>(R.id.signUpNameText)
        val signUpNameEditText = findViewById<EditText>(R.id.signUpName)
        val signUpNumberText = findViewById<TextView>(R.id.signUpNumberText)
        val signUpNumberEditText = findViewById<EditText>(R.id.signUpNumber)
        val signup = findViewById<Button>(R.id.signup)
        val policyBtn = findViewById<Button>(R.id.policyButton)
        // 화면 높이 가져오기
        val metrics = resources.displayMetrics
        val height = metrics.heightPixels

        // RadioGroup, EditText, Button 높이를 화면 높이에 맞게 조정
        val params1 = signUpGroup.getLayoutParams()
        params1.height = (height * (35.0/ 480.0)).toInt()
        signUpGroup.layoutParams = params1

        val params2 = signUpNameEditText.getLayoutParams()
        params2.height = (height * (33.0/ 480.0)).toInt()
        signUpNameEditText.layoutParams = params2

        val params3 = signUpNumberEditText.getLayoutParams()
        params3.height = (height * (33.0/ 480.0)).toInt()
        signUpNumberEditText.layoutParams = params3

        val params4 = signup.getLayoutParams()
        params4.height = (height * (33.0/ 480.0)).toInt()
        signup.layoutParams = params4


        // 간격을 화면 높이에 맞게 조정
        (signUpText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }

        (signUpGroup.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (24.0/480.0)).toInt()
        }

        (signUpNameText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (18.0/480.0)).toInt()
        }

        (signUpNameEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (7.0/480.0)).toInt()
        }

        (signUpNumberText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (16.0/480.0)).toInt()
        }

        (signUpNumberEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (7.0/480.0)).toInt()
        }

        (signup.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (34.0/480.0)).toInt()
        }

        (signup.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (34.0/480.0)).toInt()
        }

        policyBtn.paintFlags = policyBtn.getPaintFlags() or Paint.UNDERLINE_TEXT_FLAG
        policyBtn.text = "개인정보처리방침"

        var groupType = ""
        signUpGroup.setOnCheckedChangeListener { radioGroup, checkedid ->
            when (checkedid) {
                R.id.signUpGroupA -> groupType = "0"
                R.id.signUpGroupB -> groupType = "1"
            }
        }
        policyBtn.setOnClickListener {
            val intent = Intent(this, PrivacyPolicyActivity::class.java)
            startActivity(intent)
        }
        signup.setOnClickListener {
            val signUpName = findViewById<EditText>(R.id.signUpName).text.toString().trim()
            val signUpNumber = findViewById<EditText>(R.id.signUpNumber).text.toString().trim()
            if (groupType.equals("")){
                Toast.makeText(this, "그룹을 선택하세요", Toast.LENGTH_SHORT).show()
            }
            else if (signUpName.isNullOrBlank()) {
                Toast.makeText(this, "이름을 입력하세요", Toast.LENGTH_SHORT).show()
            }
            else if(signUpNumber.isNullOrBlank()){
                Toast.makeText(this, "참가번호를 입력하세요", Toast.LENGTH_SHORT).show()
            } else {
                if(isDoingNetwork == 0){
                    signupVolley(this, signUpName, signUpNumber, groupType)
                    Log.d("testsignup", groupType)
                    Log.d("testsignup", signUpName)
                    Log.d("testsignup", signUpNumber)
                }
            }
        }
    }
        private fun signupVolley(context: Context,  uname: String, unumber: String, ugrouptype: String) {
            val url = "http://healingmindcenter.com/cbm_app/cbm_api/trySignup.php"
            val queue = Volley.newRequestQueue(this)

            isDoingNetwork = 1

            val request: StringRequest = object : StringRequest(
                Method.POST, url,
                Response.Listener { response ->

                    if (response.toInt() == -1) {
                        isDoingNetwork = 0
                        val builder = AlertDialog.Builder(this)
                            .setMessage("이미 등록된 회원입니다.")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                })
                        builder.show()


                    } else {
                        val builder = AlertDialog.Builder(this)
                            .setMessage("회원 등록되었습니다.")
                            .setPositiveButton("확인",
                                DialogInterface.OnClickListener { dialog, id ->
                                    var intent = Intent(this, LoginActivity::class.java)
                                    startActivity(intent)
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                    startActivity(intent)
                                })
                        builder.show()

                    }
                },
                Response.ErrorListener { error ->
                    Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                    isDoingNetwork =0
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
    }
