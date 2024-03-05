package ac.duksung.cbm_android

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.method.ScrollingMovementMethod
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout

class QnAActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_qna)

        // 뒤로 가기 버튼
        val qnaBackBtn = findViewById<ImageButton>(R.id.qnaBackBtn)

        // 뒤로 가기를 눌렀을 때 어디로 갈지 나타내는 변수
        var count = 0

        // 질문 버튼
        val qBtn1 = findViewById<Button>(R.id.qBtn1)
        val qBtn2 = findViewById<Button>(R.id.qBtn2)
        val qBtn2_1 = findViewById<Button>(R.id.qBtn2_1)
        val qBtn3 = findViewById<Button>(R.id.qBtn3)
        val qBtn3_1 = findViewById<Button>(R.id.qBtn3_1)
        val qBtn4 = findViewById<Button>(R.id.qBtn4)
        val qBtn4_1 = findViewById<Button>(R.id.qBtn4_1)
        val qBtn5 = findViewById<Button>(R.id.qBtn5)
        val qBtn5_1 = findViewById<Button>(R.id.qBtn5_1)
        val qBtn6 = findViewById<Button>(R.id.qBtn6)
        val qBtn6_1 = findViewById<Button>(R.id.qBtn6_1)

        //레이아웃(circle+button)
        val qlayout1 = findViewById<ConstraintLayout>(R.id.qlayout1)
        val qlayout2 = findViewById<ConstraintLayout>(R.id.qlayout2)
        val qlayout2_1 = findViewById<ConstraintLayout>(R.id.qlayout2_1)
        val qlayout3 = findViewById<ConstraintLayout>(R.id.qlayout3)
        val qlayout3_1 = findViewById<ConstraintLayout>(R.id.qlayout3_1)
        val qlayout4 = findViewById<ConstraintLayout>(R.id.qlayout4)
        val qlayout4_1 = findViewById<ConstraintLayout>(R.id.qlayout4_1)
        val qlayout5 = findViewById<ConstraintLayout>(R.id.qlayout5)
        val qlayout5_1 = findViewById<ConstraintLayout>(R.id.qlayout5_1)
        val qlayout6 = findViewById<ConstraintLayout>(R.id.qlayout6)
        val qlayout6_1 = findViewById<ConstraintLayout>(R.id.qlayout6_1)

        // 답변 텍스트
        val answer1 = findViewById<TextView>(R.id.answer1)
        val answer2 = findViewById<TextView>(R.id.answer2)
        val answer3 = findViewById<TextView>(R.id.answer3)
        val answer4 = findViewById<TextView>(R.id.answer4)
        val answer5 = findViewById<TextView>(R.id.answer5)
        val answer6 = findViewById<TextView>(R.id.answer6)
        answer1.movementMethod = ScrollingMovementMethod()
        answer2.movementMethod = ScrollingMovementMethod()
        answer3.movementMethod = ScrollingMovementMethod()
        answer4.movementMethod = ScrollingMovementMethod()
        answer5.movementMethod = LinkMovementMethod()
        answer6.movementMethod = LinkMovementMethod()

        // 모든 컴포넌트를 숨기는 함수
        fun deleteView(){
            qlayout1.visibility = View.INVISIBLE
            qlayout2.visibility = View.INVISIBLE
            qlayout3.visibility = View.INVISIBLE
            qlayout4.visibility = View.INVISIBLE
            qlayout5.visibility = View.INVISIBLE
            qlayout6.visibility = View.INVISIBLE

            qlayout2_1.visibility = View.INVISIBLE
            qlayout3_1.visibility = View.INVISIBLE
            qlayout4_1.visibility = View.INVISIBLE
            qlayout5_1.visibility = View.INVISIBLE
            qlayout6_1.visibility = View.INVISIBLE

            answer1.visibility = View.INVISIBLE
            answer2.visibility = View.INVISIBLE
            answer3.visibility = View.INVISIBLE
            answer4.visibility = View.INVISIBLE
            answer5.visibility = View.INVISIBLE
            answer6.visibility = View.INVISIBLE
        }

        // 처음 자주묻는질문 페이지를 들어오면 질문 목록만 보이게 하기
        deleteView()
        qlayout1.visibility = View.VISIBLE
        qlayout2.visibility = View.VISIBLE
        qlayout3.visibility = View.VISIBLE
        qlayout4.visibility = View.VISIBLE
        qlayout5.visibility = View.VISIBLE
        qlayout6.visibility = View.VISIBLE

        // 뒤로 가기 버튼 눌렀을 때 (목록에서 뒤로 가기 하면 설정으로 이동, 답변에서 뒤로 가기 하면 질문 목록으로 이동)
        qnaBackBtn.setOnClickListener{
            if(count == 0){
                finish()
            }
            else{
                count = 0
                deleteView()
                qlayout1.visibility = View.VISIBLE
                qlayout2.visibility = View.VISIBLE
                qlayout3.visibility = View.VISIBLE
                qlayout4.visibility = View.VISIBLE
                qlayout5.visibility = View.VISIBLE
                qlayout6.visibility = View.VISIBLE
            }
        }

        // 각 버튼을 누르면 그에 맞는 질문과 답변이 나오게 하기
        qBtn1.setOnClickListener {
            if(count==0){
                count++
                deleteView()
                qlayout1.visibility = View.VISIBLE
                answer1.visibility = View.VISIBLE
            }
            else{
                count = 0
                deleteView()
                qlayout1.visibility = View.VISIBLE
                qlayout2.visibility = View.VISIBLE
                qlayout3.visibility = View.VISIBLE
                qlayout4.visibility = View.VISIBLE
                qlayout5.visibility = View.VISIBLE
                qlayout6.visibility = View.VISIBLE
            }

        }

        qBtn2.setOnClickListener {
            count++
            deleteView()
            qlayout2_1.visibility = View.VISIBLE
            answer2.visibility = View.VISIBLE
        }

        qBtn2_1.setOnClickListener {
            count = 0
            deleteView()
            qlayout1.visibility = View.VISIBLE
            qlayout2.visibility = View.VISIBLE
            qlayout3.visibility = View.VISIBLE
            qlayout4.visibility = View.VISIBLE
            qlayout5.visibility = View.VISIBLE
            qlayout6.visibility = View.VISIBLE
        }

        qBtn3.setOnClickListener {
            count++
            deleteView()
            qlayout3_1.visibility = View.VISIBLE
            answer3.visibility = View.VISIBLE
        }

        qBtn3_1.setOnClickListener {
            count = 0
            deleteView()
            qlayout1.visibility = View.VISIBLE
            qlayout2.visibility = View.VISIBLE
            qlayout3.visibility = View.VISIBLE
            qlayout4.visibility = View.VISIBLE
            qlayout5.visibility = View.VISIBLE
            qlayout6.visibility = View.VISIBLE
        }

        qBtn4.setOnClickListener {
            count++
            deleteView()
            qlayout4_1.visibility = View.VISIBLE
            answer4.visibility = View.VISIBLE
        }

        qBtn4_1.setOnClickListener {
            count = 0
            deleteView()
            qlayout1.visibility = View.VISIBLE
            qlayout2.visibility = View.VISIBLE
            qlayout3.visibility = View.VISIBLE
            qlayout4.visibility = View.VISIBLE
            qlayout5.visibility = View.VISIBLE
            qlayout6.visibility = View.VISIBLE
        }

        qBtn5.setOnClickListener {
            count++
            deleteView()
            qlayout5_1.visibility = View.VISIBLE
            answer5.visibility = View.VISIBLE
        }

        qBtn5_1.setOnClickListener {
            count = 0
            deleteView()
            qlayout1.visibility = View.VISIBLE
            qlayout2.visibility = View.VISIBLE
            qlayout3.visibility = View.VISIBLE
            qlayout4.visibility = View.VISIBLE
            qlayout5.visibility = View.VISIBLE
            qlayout6.visibility = View.VISIBLE
        }

        qBtn6.setOnClickListener {
            count++
            deleteView()
            qlayout6_1.visibility = View.VISIBLE
            answer6.visibility = View.VISIBLE
        }

        qBtn6_1.setOnClickListener {
            count = 0
            deleteView()
            qlayout1.visibility = View.VISIBLE
            qlayout2.visibility = View.VISIBLE
            qlayout3.visibility = View.VISIBLE
            qlayout4.visibility = View.VISIBLE
            qlayout5.visibility = View.VISIBLE
            qlayout6.visibility = View.VISIBLE
        }
    }
}
