package ac.duksung.cbm_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.widget.ImageButton
import android.widget.TextView

class CustomerCenterActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_customer_center)

        // 뒤로 가기 버튼
        val customerCenterBackBtn = findViewById<ImageButton>(R.id.customerCenterBackBtn)

        // 뒤로 가기 버튼을 누르면 setting으로 이동
        customerCenterBackBtn.setOnClickListener{
                finish()
        }

        // 상담센터 내용 텍스트
        val customerCenterText = findViewById<TextView>(R.id.customerCenterText)
        customerCenterText.setText("자해, 자살과 관련된 강한 충동을 느끼는 경우\n24시간 이용 가능한 상담전화를 통해 전문가의\n상담을 받을 수 있습니다. \n\n자살예방 상담전화 1393 \n\n정신건강 상담전화 1577-0199\n\n\n또는 지금 현재 있는 곳과 가장 가까운 응급실로\n연락을 하시면 도움을 받으실 수 있습니다. \n\n\n본 프로그램과 관련하여 궁금한 점이 있는 경우 \n담당 연구원의 프로필 링크를 눌러 1:1 채팅으로\n문의할 수 있습니다.\n\n남지현 연구원\nhttps://open.kakao.com/me/njh1\n\n안찬영 연구원\nhttps://open.kakao.com/me/acy2\n\n\n")
        customerCenterText.movementMethod = LinkMovementMethod()
    }
}