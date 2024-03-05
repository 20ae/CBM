package ac.duksung.cbm_android

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley

class FinishActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_finish)

        //설명
        val finishText1_1 = findViewById<TextView>(R.id.finishText1_1)
        val finishText1_2 = findViewById<TextView>(R.id.finishText1_2)
        val finishImage1 = findViewById<ImageView>(R.id.finishImage1)
        val finishNextBtn = findViewById<ImageButton>(R.id.finishNextBut)
        val finishBtnBack = findViewById<ImageView>(R.id.finishNextButBack)
        //현재 testLogPkey 받아오기
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)

        finishText1_1.visibility = View.VISIBLE
        finishImage1.visibility = View.INVISIBLE
        finishText1_2.visibility = View.INVISIBLE
        finishNextBtn.visibility = View.INVISIBLE
        finishBtnBack.visibility = View.INVISIBLE

        Handler().postDelayed({
            finishImage1.visibility = View.VISIBLE
        },2000)
        Handler().postDelayed({
            finishText1_2.visibility = View.VISIBLE
        },4000)
        Handler().postDelayed({
            //updateCurrentProgress(this, testLogPkey, testPkey)
            finishNextBtn.visibility = View.VISIBLE
            finishBtnBack.visibility = View.VISIBLE
        },6000)
        finishNextBtn.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            finish()
            startActivity(intent)
        }
    }

    private fun updateCurrentProgress (context: Context, testLogPkey:String, testPkey:String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"

        val queue = Volley.newRequestQueue(this)

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
            },
            Response.ErrorListener { error ->
                Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"] = testLogPkey
                params["testPkey"] = testPkey
                return params
            }
        }
        queue.add(request)
    }
}