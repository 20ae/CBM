package ac.duksung.cbm_android

import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.webkit.WebSettings
import android.webkit.WebView
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_log.*
import org.apache.http.util.EncodingUtils

class WebViewTouchListener : OnTouchListener {
    var downY = 0f
    override fun onTouch(v: View, event: MotionEvent): Boolean {
        if (event.pointerCount > 1) {
            return true
        }
        when (event.action) {
            MotionEvent.ACTION_DOWN -> downY = event.y
            MotionEvent.ACTION_MOVE, MotionEvent.ACTION_CANCEL, MotionEvent.ACTION_UP ->
                event.setLocation(event.x, downY)
        }
        return false
    }

}

class ReportActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_report)

        // 뒤로 가기 버튼
        val reportBackBtn = findViewById<ImageButton>(R.id.reportBackBtn)

        // 뒤로 가기 버튼을 누르면 setting으로 이동
        reportBackBtn.setOnClickListener {
            finish()
        }

        // 웹뷰에 post로 전달할 데이터값
        val userPKey = MySharedPreferences.getUserPKey(this)
        val groupType = MySharedPreferences.getGroupType(this)

        val reportWebView = findViewById<WebView>(R.id.reportWebView)
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/cbm_graph.php"
        var postData = "user_id=" + userPKey + "&" + "type=" + groupType
        reportWebView.postUrl(url, EncodingUtils.getBytes(postData, "base64"))
        reportWebView.settings.setJavaScriptEnabled(true)
        reportWebView.isVerticalScrollBarEnabled = false
        reportWebView.setOnTouchListener(WebViewTouchListener())

        // 크기 조정
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        (reportWebView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (80.0/480.0)).toInt()
        }
    }
}
