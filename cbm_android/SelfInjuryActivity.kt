package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_relax_video.*

class SelfInjuryActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isPlaying = false //플레이 유무 변수
    var isStartSeek = 0 //재생위치

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_self_injury)

        //훈련집단인지 비교집단인지 받아오기
        val group_type = MySharedPreferences.getGroupType(this)

        //위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        //첫번째 장면
        val injuryLayout = findViewById<ConstraintLayout>(R.id.InjuryLayout)
        val text = findViewById<TextView>(R.id.InjuryTextView)
        val text2 = findViewById<TextView>(R.id.InjuryTextView2)
        val text3 = findViewById<TextView>(R.id.InjuryTextView3)
        val injuryButBack = findViewById<ImageView>(R.id.InjuryButBack)
        val injuryNextBut = findViewById<ImageButton>(R.id.InjuryNextBut)
        injuryLayout.visibility = View.VISIBLE
        text2.visibility = View.INVISIBLE
        text3.visibility = View.INVISIBLE
        injuryButBack.visibility = View.INVISIBLE
        injuryNextBut.visibility = View.INVISIBLE
        Handler().postDelayed({
            text2.visibility = View.VISIBLE
        },2000)
        Handler().postDelayed({
            text3.visibility = View.VISIBLE
            injuryButBack.visibility = View.VISIBLE
            injuryNextBut.visibility = View.VISIBLE
        },4000)
        //위치재조정
        (text.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (93.0/480.0)).toInt()
        }
        (text2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (48.0/480.0)).toInt()
        }
        (text3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (20.0/480.0)).toInt()
        }
        (injuryNextBut.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //두번째 장면
        val injuryLayout2 = findViewById<ConstraintLayout>(R.id.InjuryLayout2)
        val injuryVideoView = findViewById<VideoView>(R.id.InjuryVideoView)
        val injuryNextBut2 = findViewById<ImageButton>(R.id.InjuryNextBut2)
        val injuryButBack2 = findViewById<ImageView>(R.id.InjuryButBack2)
        val videoUri : Uri = Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_self_injury/1.mp4")
        val videoUri2 : Uri = Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_self_injury/2.mp4")
        //val videoController : MediaController = MediaController(this) //컨트롤러 바 생성
        //injuryVideoView.setMediaController(videoController)
        //훈련집단과 비교집단의 영상이 다름
        if(group_type.toInt() == 0){
            injuryVideoView.setVideoURI(videoUri)
        }
        else if(group_type.toInt() == 1){
            injuryVideoView.setVideoURI(videoUri2)
        }
        injuryLayout2.visibility = View.INVISIBLE
        injuryNextBut2.visibility = View.INVISIBLE
        injuryButBack2.visibility = View.INVISIBLE
        (injuryVideoView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (112.0/480.0)).toInt()
        }
        (injuryNextBut2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }


        //세번째 장면
        val injuryLayout3 = findViewById<ConstraintLayout>(R.id.InjuryLayout3)
        val text4 = findViewById<TextView>(R.id.InjuryTextView4)
        val text5 = findViewById<TextView>(R.id.InjuryTextView5)
        val text6 = findViewById<TextView>(R.id.InjuryTextView6)
        val injuryButBack3 = findViewById<ImageView>(R.id.InjuryButBack3)
        val injuryNextBut3 = findViewById<ImageButton>(R.id.InjuryNextBut3)
        injuryLayout3.visibility = View.INVISIBLE
        text5.visibility = View.INVISIBLE
        text6.visibility = View.INVISIBLE
        injuryButBack3.visibility = View.INVISIBLE
        injuryNextBut3.visibility = View.INVISIBLE

        injuryNextBut.setOnClickListener {
            injuryLayout.visibility = View.INVISIBLE
            injuryLayout2.visibility = View.VISIBLE
            playinjuryVideo(injuryVideoView,injuryNextBut2,injuryButBack2,group_type.toInt())
        }

        injuryNextBut2.setOnClickListener {
            injuryLayout2.visibility = View.INVISIBLE
            injuryLayout3.visibility = View.VISIBLE
            Handler().postDelayed({
                text5.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                text6.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                injuryButBack3.visibility = View.VISIBLE
                injuryNextBut3.visibility = View.VISIBLE

            },6000)
        }
        //위치재조정
        (text4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (93.0/480.0)).toInt()
        }
        (text5.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (40.0/480.0)).toInt()
        }
        (text6.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (40.0/480.0)).toInt()
        }
        (injuryNextBut3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        injuryNextBut3.setOnClickListener {
            val pkey = MySharedPreferences.getTestLogPKey(this)
            val testPKey = MySharedPreferences.getTestPKey(this)
            if(isDoingNetwork ==0){
                updateDoneSection(this,pkey,testPKey)
            }

        }
    }


    private fun playinjuryVideo(injuryVideoView: VideoView, injuryNextBut2:ImageButton,injuryButBack2:ImageView, group_type: Int){
        injuryVideoView.requestFocus()
        injuryVideoView.seekTo(0)
        injuryVideoView.start()
        isPlaying = true

        injuryVideoView.setOnCompletionListener {
            isPlaying = false
            injuryVideoView.stopPlayback()
            injuryNextBut2.visibility = View.VISIBLE
            injuryButBack2.visibility = View.VISIBLE
        }
        //영상오류 리스너
        injuryVideoView.setOnErrorListener { mediaPlayer, i, i2 ->
            injuryVideoView.stopPlayback()
            if(group_type == 0){
                injuryVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_self_injury/1.mp4"))
            }
            else if(group_type == 1){
                injuryVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_self_injury/2.mp4"))
            }

            injuryVideoView.requestFocus()
            injuryVideoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> injuryVideoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        val injuryVideoView = findViewById<VideoView>(R.id.InjuryVideoView)
        if(isPlaying){
            isStartSeek = injuryVideoView.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        val injuryVideoView = findViewById<VideoView>(R.id.InjuryVideoView)
        if(isPlaying){
            injuryVideoView.seekTo(isStartSeek)
            injuryVideoView.start()
        }
    }


    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
        val queue = Volley.newRequestQueue(this)
        isDoingNetwork =1
        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                if (response.toInt() == 0){
                    finish()
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    isDoingNetwork =0
                    netWorkError()
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                isDoingNetwork =0
                netWorkError()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["Pkey"]=pKey
                params["testPkey"]=testPKey
                return params
            }
        }
        queue.add(request)
    }
    //알림창
    override fun onBackPressed() {
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("진행중에 나가시면 저장되지 않습니다.\n정말 종료하시겠습니까?")
        alert.setPositiveButton("취소",
            DialogInterface.OnClickListener { dialog, which -> })
        alert.setNegativeButton("확인",
            DialogInterface.OnClickListener { dialog, which -> super.onBackPressed() })
        (alert.create()).show()
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
}