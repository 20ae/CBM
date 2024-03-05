package ac.duksung.cbm_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.webkit.WebView
import org.apache.http.util.EncodingUtils

class PrivacyPolicyActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_privacy_policy)

        val userPKey = MySharedPreferences.getUserPKey(this)
        val groupType = MySharedPreferences.getGroupType(this)

        val policyWebView = findViewById<WebView>(R.id.policyWebView)
        val url = "http://healingmindcenter.com/cbm_app/privacy_policy.php"
        var postData = "user_id=" + userPKey + "&" + "type=" + groupType
        policyWebView.postUrl(url,EncodingUtils.getBytes(postData, "base64"))
        policyWebView.settings.setJavaScriptEnabled(true)
        policyWebView.isVerticalScrollBarEnabled = false
        policyWebView.setOnTouchListener(WebViewTouchListener())
    }
}