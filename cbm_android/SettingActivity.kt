package ac.duksung.cbm_android

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.marginTop
import kotlinx.android.synthetic.main.activity_setting.*

class SettingActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_setting)

        //훈련집단인지 비교집단인지 받아오기
        val group_type = MySharedPreferences.getGroupType(this)

        val nameKor = findViewById<TextView>(R.id.nameKor)
        val goReport = findViewById<Button>(R.id.goReport)
        val goQnA = findViewById<Button>(R.id.goQnA)
        val goCustomerCenter = findViewById<Button>(R.id.goCustomerCenter)
        val goHome = findViewById<ImageButton>(R.id.homeButton)
        val report_icon = findViewById<ImageView>(R.id.report_icon)
        val qna_icon = findViewById<ImageView>(R.id.qna_icon)
        val customercenter_icon = findViewById<ImageView>(R.id.customercenter_icon)

        if(group_type.toInt() == 0){
            goReport.visibility = View.VISIBLE
            goQnA.visibility = View.VISIBLE
            goCustomerCenter.visibility = View.VISIBLE
            report_icon.visibility = View.VISIBLE
            qna_icon.visibility = View.VISIBLE
            customercenter_icon.visibility = View.VISIBLE
        }
        else if(group_type.toInt() == 1){
            goReport.visibility = View.INVISIBLE
            goQnA.visibility = View.VISIBLE
            goCustomerCenter.visibility = View.VISIBLE
            report_icon.visibility = View.INVISIBLE
            qna_icon.visibility = View.VISIBLE
            customercenter_icon.visibility = View.VISIBLE
            (qna_icon.layoutParams as ConstraintLayout.LayoutParams).apply {
                topMargin = report_icon.marginTop
            }
        }

        nameKor.text = getFirstName() + " 님"
        goReport.setOnClickListener{
            val intent = Intent(this, ReportActivity::class.java)
            startActivity(intent)
        }

        goQnA.setOnClickListener{
            val intent = Intent(this, QnAActivity::class.java)
            startActivity(intent)
        }

        goCustomerCenter.setOnClickListener{
            val intent = Intent(this, CustomerCenterActivity::class.java)
            startActivity(intent)
        }

        goHome.setOnClickListener {
           finish()
        }
    }

    // User의 이름에서 맨 앞 한글자만 제외하고 반환하는 함수
    fun getFirstName(): String {
        val userName = MySharedPreferences.getUserName(this)
        var nameToken = userName.chunked(1)
        var firstName = ""
        if (userName.length >=2){
            for (i in 1 until userName.length){
                firstName += nameToken[i]
            }
        }else{
            firstName = userName
        }
        return firstName
    }
}