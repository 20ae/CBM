package ac.duksung.cbm_android

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.*
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import android.media.MediaPlayer.OnCompletionListener
import android.os.Handler
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.constraintlayout.widget.ConstraintLayout
import kotlinx.android.synthetic.main.activity_home.*
import kotlinx.android.synthetic.main.activity_relax_ot.*
import kotlinx.android.synthetic.main.activity_training_relax.*
import kotlin.math.round
import kotlin.math.roundToInt

class TrainingRelaxActivity : AppCompatActivity() {
    var count = 0 // 차수를 받아오는 변수
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isPlaying = false //플레이 유무 변수
    var isStartSeek = 0 //재생위치
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_relax)
        //설명
        val trainingRelaxText1_1 = findViewById<TextView>(R.id.trainingRelaxText1_1)
        val trainingRelaxText1_3 = findViewById<TextView>(R.id.trainingRelaxText1_3)
        val trainingRelaxText1_2 = findViewById<TextView>(R.id.trainingRelaxText1_2)
        val trainingRelaxText2_1 = findViewById<TextView>(R.id.trainingRelaxText2_1)
        val trainingRelaxText2_2 = findViewById<TextView>(R.id.trainingRelaxText2_2)
        val trainingRelaxImage1 = findViewById<ImageView>(R.id.trainingRelaxImage1)
        val trainingRelaxNextButton = findViewById<ImageButton>(R.id.trainingRelaxNextButton)
        val trainingRelaxbtnBlueBack = findViewById<ImageView>(R.id.trainingRelaxbtnBlueBack)
        var trainingRelaxtextPageNum = 0
        //비디오 실행
        val trainingRelaxvideoView = findViewById<VideoView>(R.id.trainingRelaxVideoView) // 함수에서는 trainingRelaxVideoView 변수사용은 trainingRelaxvideoView
        val videocontroller = MediaController(this) //배포시에 이 부분 제거해야함!!!!
        //현재 testLogPkey 받아오기
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)
        requestCount(this, testPkey)

        trainingRelaxText1_1.visibility = View.VISIBLE
        trainingRelaxText1_3.visibility = View.VISIBLE
        trainingRelaxImage1.visibility = View.INVISIBLE
        trainingRelaxText1_2.visibility = View.INVISIBLE
        trainingRelaxText2_1.visibility = View.INVISIBLE
        trainingRelaxText2_2.visibility = View.INVISIBLE
        trainingRelaxNextButton.visibility = View.INVISIBLE
        trainingRelaxbtnBlueBack.visibility = View.INVISIBLE

        trainingRelaxvideoView.visibility = View.INVISIBLE


        //동적 간격 조정
        (trainingRelaxText1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.152f)
        }
        (trainingRelaxText1_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.027f)
        }
        (trainingRelaxImage1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.041f)
        }
        (trainingRelaxText1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.027f)
        }
        (trainingRelaxNextButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        (trainingRelaxvideoView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.141f)
        }
        (trainingRelaxText2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.287f)
        }
        (trainingRelaxText2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.072f)
        }

        //설명부분
        if (trainingRelaxtextPageNum ==0){
            Handler().postDelayed({
                trainingRelaxImage1.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                trainingRelaxText1_2.visibility = View.VISIBLE
            },4000)
            Handler().postDelayed({
                trainingRelaxNextButton.visibility = View.VISIBLE
                trainingRelaxbtnBlueBack.visibility = View.VISIBLE
            },6000)
        }

        trainingRelaxNextButton.setOnClickListener(){
            trainingRelaxtextPageNum += 1
            if(trainingRelaxtextPageNum>=1&&trainingRelaxtextPageNum<=2){
                trainingRelaxNextButton.visibility = View.INVISIBLE
                trainingRelaxbtnBlueBack.visibility = View.INVISIBLE
            }

            if(trainingRelaxtextPageNum == 1){
                trainingRelaxText1_1.visibility = View.INVISIBLE
                trainingRelaxText1_3.visibility = View.INVISIBLE
                trainingRelaxImage1.visibility = View.INVISIBLE
                trainingRelaxText1_2.visibility = View.INVISIBLE

                trainingRelaxvideoView.visibility = View.VISIBLE
                playTrainingRelaxVideo(this, videocontroller, count)
            }
            else if(trainingRelaxtextPageNum == 2){
                trainingRelaxvideoView.visibility = View.INVISIBLE

                trainingRelaxText2_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    trainingRelaxText2_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    trainingRelaxNextButton.visibility = View.VISIBLE
                    trainingRelaxbtnBlueBack.visibility = View.VISIBLE
                },4000)
            }
            else {
                //trainingRelaxText2_1.visibility = View.INVISIBLE
                //trainingRelaxText2_2.visibility = View.INVISIBLE
                if (isDoingNetwork == 0) {
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

    private fun playTrainingRelaxVideo(context: Context, videocontroller: MediaController, count:Int){
        val trainingRelaxVideoNum = count-4
        //trainingRelaxVideoView.setMediaController(videocontroller)
        trainingRelaxVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/relax/"+trainingRelaxVideoNum+".mp4"))
        trainingRelaxVideoView.requestFocus()
        trainingRelaxVideoView.seekTo(0); //0초에서 시작하도록
        trainingRelaxVideoView.start();
        isPlaying = true
        //다 재생되면 자동 종료
        trainingRelaxVideoView.setOnCompletionListener(OnCompletionListener {
            isPlaying = false
            trainingRelaxVideoView.stopPlayback()
            trainingRelaxNextButton.visibility = View.VISIBLE
            trainingRelaxbtnBlueBack.visibility = View.VISIBLE
        })
        //영상오류 리스너
        trainingRelaxVideoView.setOnErrorListener { mediaPlayer, i, i2 ->
            trainingRelaxVideoView.stopPlayback()
            trainingRelaxVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/relax/"+trainingRelaxVideoNum+".mp4"))
            trainingRelaxVideoView.requestFocus()
            trainingRelaxVideoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> trainingRelaxVideoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        val trainingRelaxvideoView = findViewById<VideoView>(R.id.trainingRelaxVideoView)
        if(isPlaying){
            isStartSeek = trainingRelaxvideoView.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        val trainingRelaxvideoView = findViewById<VideoView>(R.id.trainingRelaxVideoView)
        if(isPlaying){
            trainingRelaxvideoView.seekTo(isStartSeek)
            trainingRelaxvideoView.start()
        }
    }

    private fun updateCurrentProgress (context: Context, testLogPkey:String, testPkey:String){
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"

        val queue = Volley.newRequestQueue(this)

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

    // 전체 차수를 알려주는 api 연결 함수
    fun requestCount(context: Context, testPKey: String) {
        val url = "http://healingmindcenter.com/cbm_app/cbm_api/request_count_test.php"
        val queue = Volley.newRequestQueue(this)
        val request: StringRequest = @SuppressLint("SetTextI18n")
        object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                //count = response.toInt()
                var result = response.toInt()
                if(result>=0){
                    count = result
                }
                else{
                    netWorkError2()
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "서버와의 통신이 실패하였습니다. 다시 시도해주세요.", Toast.LENGTH_LONG).show()
                //finish()
                netWorkError2()
            }
        ) {
            override fun getParams(): MutableMap<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["testPKey"] = testPKey
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
    //네트워크 실패 시 호출
    private fun netWorkError2(){
        val alert : AlertDialog.Builder = AlertDialog.Builder(this)
        alert.setTitle("알림")
        alert.setMessage("네트워크 연결에 실패했습니다.")
        alert.setPositiveButton("확인",
            DialogInterface.OnClickListener { dialog, which -> finish()})
        alert.show()
    }
}