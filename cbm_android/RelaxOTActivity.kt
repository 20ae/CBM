package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.media.MediaPlayer.OnCompletionListener
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_relax_ot.*
import kotlinx.android.synthetic.main.activity_training_relax.*
import kotlin.math.round
import kotlin.math.roundToInt

class RelaxOTActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isPlaying = false //플레이 유무 변수
    var isStartSeek = 0 //재생위치
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_relax_ot)
        //설명
        val relaxOTText1_1 = findViewById<TextView>(R.id.relaxOTText1_1)
        val relaxOTText1_2 = findViewById<TextView>(R.id.relaxOTText1_2)
        val relaxOTText1_3 = findViewById<TextView>(R.id.relaxOTText1_3)
        val relaxOTText2_1 = findViewById<TextView>(R.id.relaxOTText2_1)
        val relaxOTText2_2 = findViewById<TextView>(R.id.relaxOTText2_2)
        val relaxOTText2_3 = findViewById<TextView>(R.id.relaxOTText2_3)
        val relaxOTText3_1 = findViewById<TextView>(R.id.relaxOTText3_1)
        val relaxOTNextButton = findViewById<ImageButton>(R.id.relaxOTNextButton)
        val relaxOTbtnGreenBack = findViewById<ImageView>(R.id.relaxOTbtnBlueBack)
        var relaxOTtextPageNum = 0
        //비디오 실행
        val relaxOTvideoView = findViewById<VideoView>(R.id.relaxOTVideoView) // 함수에서는 relaxOTVideoView 변수사용은 relaxOTvideoView
        val videocontroller = MediaController(this) //배포시에 이 부분 제거해야함!!!!
        //동적 간격 조정
        (relaxOTText1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.175f)
        }
        (relaxOTText1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.066f)
        }
        (relaxOTText1_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.066f)
        }
        (relaxOTText2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.235f)
        }
        (relaxOTText2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.093f)
        }
        (relaxOTText2_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.041f)
        }
        (relaxOTvideoView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.141f)
        }
        (relaxOTText3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.341f)
        }
        (relaxOTNextButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        //현재 testLogPkey 받아오기
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)

        //시작 시 설명 시작 부분만 visible
        relaxOTText1_1.visibility = View.VISIBLE
        relaxOTText1_2.visibility = View.INVISIBLE
        relaxOTText1_3.visibility = View.INVISIBLE
        relaxOTText2_1.visibility = View.INVISIBLE
        relaxOTText2_2.visibility = View.INVISIBLE
        relaxOTText2_3.visibility = View.INVISIBLE
        relaxOTText3_1.visibility = View.INVISIBLE
        relaxOTNextButton.visibility = View.INVISIBLE
        relaxOTbtnGreenBack.visibility = View.INVISIBLE

        relaxOTvideoView.visibility = View.INVISIBLE

        //설명부분
        if (relaxOTtextPageNum == 0){
            Handler().postDelayed({
                relaxOTText1_2.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                relaxOTText1_3.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                relaxOTNextButton.visibility = View.VISIBLE
                relaxOTbtnGreenBack.visibility = View.VISIBLE
            },6000)
        }

        relaxOTNextButton.setOnClickListener(){
            relaxOTtextPageNum += 1
            if(relaxOTtextPageNum>=1&&relaxOTtextPageNum<=3){
                relaxOTNextButton.visibility = View.INVISIBLE
                relaxOTbtnGreenBack.visibility = View.INVISIBLE
            }

            if(relaxOTtextPageNum == 1){
                relaxOTText1_1.visibility = View.INVISIBLE
                relaxOTText1_2.visibility = View.INVISIBLE
                relaxOTText1_3.visibility = View.INVISIBLE

                relaxOTText2_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    relaxOTText2_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    relaxOTText2_3.visibility = View.VISIBLE
                    relaxOTNextButton.visibility = View.VISIBLE
                    relaxOTbtnGreenBack.visibility = View.VISIBLE
                },4000)

            }
            else if(relaxOTtextPageNum == 2){
                relaxOTText2_1.visibility = View.INVISIBLE
                relaxOTText2_2.visibility = View.INVISIBLE
                relaxOTText2_3.visibility = View.INVISIBLE

                relaxOTvideoView.visibility = View.VISIBLE
                playRelaxOTVideo(this, videocontroller)
            }
            else if(relaxOTtextPageNum == 3){
                relaxOTvideoView.visibility = View.INVISIBLE

                relaxOTText3_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    relaxOTNextButton.visibility = View.VISIBLE
                    relaxOTbtnGreenBack.visibility = View.VISIBLE
                },2000)
            }
            else {
                if(isDoingNetwork ==0){
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

    private fun playRelaxOTVideo(context: Context, videocontroller: MediaController){
        //relaxOTVideoView.setMediaController(videocontroller)
        relaxOTVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/relax_ot/1.mp4"))
        relaxOTVideoView.requestFocus()
        relaxOTVideoView.seekTo(0); //0초에서 시작하도록
        relaxOTVideoView.start();
        isPlaying = true
        //다 재생되면 자동 종료
        relaxOTVideoView.setOnCompletionListener(OnCompletionListener {
            isPlaying = false
            relaxOTVideoView.stopPlayback()
            relaxOTNextButton.visibility = View.VISIBLE
            relaxOTbtnBlueBack.visibility = View.VISIBLE
        })
        //영상오류 리스너
        relaxOTVideoView.setOnErrorListener { mediaPlayer, i, i2 ->
            relaxOTVideoView.stopPlayback()
            relaxOTVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/relax_ot/1.mp4"))
            relaxOTVideoView.requestFocus()
            relaxOTVideoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> relaxOTVideoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        val relaxOTvideoView = findViewById<VideoView>(R.id.relaxOTVideoView)
        if(isPlaying){
            isStartSeek = relaxOTvideoView.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        val relaxOTvideoView = findViewById<VideoView>(R.id.relaxOTVideoView)
        if(isPlaying){
            relaxOTvideoView.seekTo(isStartSeek)
            relaxOTvideoView.start()
        }
    }

    private fun updateCurrentProgress (context: Context, testLogPkey:String, testPkey:String){
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
    //키보드내리기
    fun closeKeyboard(v : View)
    {
        if(v != null)
        {
            val inputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(v.windowToken, 0)
        }
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