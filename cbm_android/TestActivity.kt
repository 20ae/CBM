package ac.duksung.cbm_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class TestActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_test)

        val btn = findViewById<Button>(R.id.button)

        btn.setOnClickListener{
            val intent = Intent(this, LogActivity::class.java)
            startActivity(intent)
        }

    /*
        val queue = Volley.newRequestQueue(this)
        val url = "http://192.168.0.9/request_create_log.php"
        val stringRequest = object : StringRequest(
            Request.Method.POST, url,
            Response.Listener<String> { response ->
                textView.text = "Create Log: ${response}"
            },
            Response.ErrorListener { textView.text = "That didn't work!" }) {
            override fun getParams(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                //params["name"] =
                //params["id"] =
                return params
            }
        }
        queue.add(stringRequest)
    */
    }
}