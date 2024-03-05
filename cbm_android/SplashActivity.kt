package ac.duksung.cbm_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        val splashMoon = findViewById<ImageView>(R.id.splashMoon)
        val splashText = findViewById<TextView>(R.id.splashText)

        // 화면 높이 가져오기
        val metrics = resources.displayMetrics
        val height = metrics.heightPixels

        // 간격을 화면 높이에 맞게 조정
        (splashMoon.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (130.0/480.0)).toInt()
        }

        (splashText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (26.0/480.0)).toInt()
        }

        // 3초 뒤 로그인 화면으로 이동
        Handler().postDelayed({
            val intent = Intent(this, LoginActivity::class.java)
            startActivity(intent)
            finish()
        },3000)
    }
}