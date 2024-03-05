package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_training_mind.*
import android.media.MediaPlayer.OnCompletionListener
import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_relax_video.*
import kotlin.math.round
import kotlin.math.roundToInt

class RelaxVideoActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isPlaying = false //플레이 유무 변수
    var isStartSeek = 0 //재생위치
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relax_video)
        //설명
        val relaxVideoText1_1 = findViewById<TextView>(R.id.relaxVideoText1_1)
        val relaxVideoText1_2 = findViewById<TextView>(R.id.relaxVideoText1_2)
        val relaxVideoText1_3 = findViewById<TextView>(R.id.relaxVideoText1_3)
        val relaxVideoText2_1 = findViewById<TextView>(R.id.relaxVideoText2_1)
        val relaxVideoText2_2 = findViewById<TextView>(R.id.relaxVideoText2_2)
        val relaxVideoText3_1 = findViewById<TextView>(R.id.relaxVideoText3_1)
        val relaxVideoText3_2 = findViewById<TextView>(R.id.relaxVideoText3_2)
        val relaxVideoImage1 = findViewById<ImageView>(R.id.relaxVideoImage1)
        val relaxVideoNextButton = findViewById<ImageButton>(R.id.relaxVideoNextButton)
        val relaxVideobtnGreenBack = findViewById<ImageView>(R.id.relaxVideobtnGreenBack)
        var relaxtextPageNum = 0
        //비디오 실행
        val relaxvideoView = findViewById<VideoView>(R.id.relaxVideoView) // 함수에서는 relaxVideoView 변수사용은 relaxvideoView
        val videocontroller = MediaController(this) //배포시에 이 부분 제거해야함!!!!
        //동적 간격 조정
        (relaxVideoText1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.145f)
        }
        (relaxVideoText1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.035f)
        }
        (relaxVideoImage1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.043f)
        }
        (relaxVideoText1_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.041f)
        }
        (relaxvideoView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.141f)
        }
        (relaxVideoText2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.247f)
        }
        (relaxVideoText2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.15f)
        }
        (relaxVideoText3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.239f)
        }
        (relaxVideoText3_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.125f)
        }
        (relaxVideoNextButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        //현재 testLogPkey 받아오기
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)

        //이완훈련 교육 시작 시 설명 시작 부분만 visible
        relaxVideoText1_1.visibility = View.VISIBLE
        relaxVideoText1_2.visibility = View.INVISIBLE
        relaxVideoImage1.visibility = View.INVISIBLE
        relaxVideoText1_3.visibility = View.INVISIBLE
        relaxVideoText2_1.visibility = View.INVISIBLE
        relaxVideoText2_2.visibility = View.INVISIBLE
        relaxVideoText3_1.visibility = View.INVISIBLE
        relaxVideoText3_2.visibility = View.INVISIBLE
        relaxVideoNextButton.visibility = View.INVISIBLE
        relaxVideobtnGreenBack.visibility = View.INVISIBLE

        relaxvideoView.visibility = View.INVISIBLE

        //설명부분
        if (relaxtextPageNum ==0){
            Handler().postDelayed({
                relaxVideoText1_2.visibility = View.VISIBLE
                relaxVideoImage1.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                relaxVideoText1_3.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                relaxVideoNextButton.visibility = View.VISIBLE
                relaxVideobtnGreenBack.visibility = View.VISIBLE
            },6000)
        }

        relaxVideoNextButton.setOnClickListener(){
            relaxtextPageNum += 1
            if(relaxtextPageNum >= 1 &&relaxtextPageNum<=3){
                relaxVideoNextButton.visibility = View.INVISIBLE
                relaxVideobtnGreenBack.visibility = View.INVISIBLE
            }

            if(relaxtextPageNum ==1){
                relaxVideoText1_1.visibility = View.INVISIBLE
                relaxVideoText1_2.visibility = View.INVISIBLE
                relaxVideoImage1.visibility = View.INVISIBLE
                relaxVideoText1_3.visibility = View.INVISIBLE

                relaxvideoView.visibility = View.VISIBLE
                playRelaxVideo(this, videocontroller)
            }
            else if(relaxtextPageNum == 2){
                relaxvideoView.visibility = View.INVISIBLE

                relaxVideoText2_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    relaxVideoText2_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    relaxVideoNextButton.visibility = View.VISIBLE
                    relaxVideobtnGreenBack.visibility = View.VISIBLE
                },4000)
            }
            else if(relaxtextPageNum == 3){
                relaxVideoText2_1.visibility = View.INVISIBLE
                relaxVideoText2_2.visibility = View.INVISIBLE

                relaxVideoText3_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    relaxVideoText3_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    relaxVideoNextButton.visibility = View.VISIBLE
                    relaxVideobtnGreenBack.visibility = View.VISIBLE
                },4000)
            }
            else {
                if(isDoingNetwork == 0) {
                    updateCurrentProgress(this, testLogPkey, testPkey)
                }
            }
        }
    }

    private fun changeDP(value : Float) : Int{
        var displayMetrics = resources.displayMetrics
        var dp = round(displayMetrics.heightPixels * value )
        var result = dp.roundToInt()
        return result
    }

    private fun playRelaxVideo(context: Context, videocontroller: MediaController){
        //relaxVideoView.setMediaController(videocontroller)
        relaxVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_relax/1.mp4"))
        relaxVideoView.requestFocus()
        relaxVideoView.seekTo(0); //0초에서 시작하도록
        relaxVideoView.start();
        isPlaying = true
        //다 재생되면 자동 종료
        relaxVideoView.setOnCompletionListener(OnCompletionListener {
            relaxVideoView.stopPlayback()
            relaxVideoNextButton.visibility = View.VISIBLE
            relaxVideobtnGreenBack.visibility = View.VISIBLE
            isPlaying = false
        })
        relaxVideoView.setOnErrorListener { mediaPlayer, i, i2 ->
            relaxVideoView.stopPlayback()
            relaxVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_relax/1.mp4"))
            relaxVideoView.requestFocus()
            relaxVideoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> relaxVideoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        if(isPlaying){
            isStartSeek = relaxVideoView.currentPosition
        }
    }
    override fun onResume() {
        super.onResume()
        if(isPlaying){
            relaxVideoView.seekTo(isStartSeek)
            relaxVideoView.start()
        }
    }

    private fun updateCurrentProgress (context: Context, testLogPkey:String, testPkey:String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"

        val queue = Volley.newRequestQueue(this)
        isDoingNetwork=1
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
                params["Pkey"] = testLogPkey
                params["testPkey"] = testPkey
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