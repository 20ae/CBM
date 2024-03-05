package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.provider.MediaStore
import android.util.DisplayMetrics
import android.view.View
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_edu_cbm.*
import kotlinx.android.synthetic.main.activity_log.*
import kotlinx.android.synthetic.main.activity_training_mind.*

class EduCBMActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isPlaying = false//플레이 유무 변수
    var isStartSeek = 0 //재생위치
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edu_cbm)

        val testPkey = MySharedPreferences.getTestPKey(this)
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)

        val videocontroller = MediaController(this)
        val eduCbmVideo = findViewById<VideoView>(R.id.eduCbmVideo)
        val eduCbmBtn0 = findViewById<ImageButton>(R.id.btnEduNext0)
        val eduCbmBtn1 = findViewById<ImageButton>(R.id.btnEduNext1)
        val eduCbmBtn2 = findViewById<ImageButton>(R.id.btnEduNext2)
        val eduCbmBtn3 = findViewById<ImageButton>(R.id.btnEduNext3)
        val eduCbmBtn4 = findViewById<ImageButton>(R.id.btnEduNext4)
        val eduCbmBtn6 = findViewById<ImageButton>(R.id.btnEduNext6)
        //동적 간격 생성하기
        autoLayoutSpace()
        //layout0
        eduCbmLayout0.visibility= View.VISIBLE
        visibleText(eduCbmTxt0_1,eduCbmBtn0,btnGreenEduBack0)
        eduCbmBtn0.setOnClickListener {
            invisibleLayout(eduCbmLayout0, eduCbmLayout1)
            visibleText(eduCbmTxt1_2,eduCbmBtn1,btnGreenEduBack1)
        }
        //layout1
        eduCbmBtn1.setOnClickListener {
            eduCbmTxt2_2.text = "이것이 바로 이 훈련에서\n\n"+getFirstName()+"님이 해주실 일이에요."
            invisibleLayout(eduCbmLayout1,eduCbmLayout2)
            visibleText(eduCbmTxt2_1,eduCbmTxt2_2,eduCbmBtn2,btnGreenEduBack2)
        }
        //layout2
        eduCbmBtn2.setOnClickListener {
            invisibleLayout(eduCbmLayout2,eduCbmLayout3)
            visibleText(eduCbmTxt3_1,eduCbmBtn3,btnGreenEduBack3)
        }
        //layout3
        eduCbmBtn3.setOnClickListener {
            invisibleLayout(eduCbmLayout3,eduCbmLayout4)
            visibleText(eduCbmTxt4_1,eduCbmBtn4,btnGreenEduBack4)
        }
        //layout4
        eduCbmBtn4.setOnClickListener {
            invisibleLayout(eduCbmLayout4, eduCbmLayout5)
            Handler().postDelayed({
                playEduCbmVideo(this,videocontroller,eduCbmVideo)
            }, 3000)
        }
        //layout6
        eduCbmBtn6.setOnClickListener {
            if (isDoingNetwork == 0){
                updateDoneSection(this, testLogPkey, testPkey)

            }
        }

    }
    //동적으로 간격 생성(글자랑 그림)
    private fun autoLayoutSpace(){
        //동적위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels
        /**Layout1**/
        (eduCbmTxt0.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (105.0/480.0)).toInt()
        }
        (eduCbmTxt0_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (50.0/480.0)).toInt()
        }
        (btnEduNext0.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout2**/
        (eduCbmTxt1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (115.0/480.0)).toInt()
        }
        (eduCbmTxt1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (60.0/480.0)).toInt()
        }
        (btnEduNext1.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout3**/
        (eduCbmTxt2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (70.0/480.0)).toInt()
        }
        (eduCbmTxt2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (24.0/480.0)).toInt()
        }
        (eduCbmTxt2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (24.0/480.0)).toInt()
        }
        (btnEduNext2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout4**/
        (eduCbmTxt3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (85.0/480.0)).toInt()
        }
        (eduCbmTxt3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (36.0/480.0)).toInt()
        }
        (eduCbmTxt3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (90.0/480.0)).toInt()
        }
        (btnEduNext3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout5**/
        val params = cbmEduImage4.layoutParams
        params.width = (width * (181.0/320.0)).toInt()
        params.height = (height * (105.0/480.0)).toInt()
        cbmEduImage4.layoutParams = params
        (cbmEduImage4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (30.0/480.0)).toInt()
        }
        (eduCbmTxt4.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (90.0/480.0)).toInt()
        }
        (eduCbmTxt4_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (35.0/480.0)).toInt()
        }
        (btnEduNext4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
        /**Layout6**/
        (eduCbmVideo.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (112.0/480.0)).toInt()
        }
        /**Layout7**/
        (eduCbmTxt6.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (79.0/480.0)).toInt()
        }
        (eduCbmTxt6_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (25.0/480.0)).toInt()
        }
        (eduCbmTxt6_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (25.0/480.0)).toInt()
        }
        (btnEduNext6.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }
    }

    private fun visibleText(text: TextView, btn: ImageButton, imageView: ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleBtn(btn,imageView)
        }, 3000)
    }
    private fun visibleText(text: TextView, text2: TextView, btn: ImageButton, imageView: ImageView){
        Handler().postDelayed({
            text.visibility = View.VISIBLE
            visibleText(text2,btn,imageView)
        }, 3000)
    }
    private fun visibleBtn(btn: ImageButton, imageView: ImageView){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
            imageView.visibility=View.VISIBLE
        }, 3000)
    }
    private fun visibleBtn(btn: ImageButton){
        Handler().postDelayed({
            btn.visibility = View.VISIBLE
        }, 3000)
    }
    fun invisibleLayout(layout: ConstraintLayout,layout2: ConstraintLayout){
        layout.visibility = View.INVISIBLE
        layout2.visibility = View.VISIBLE
    }
    //성떼고 이름만 가져오기
    fun getFirstName(): String {
        //local userName
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


    private fun playEduCbmVideo(context: Context, videocontroller: MediaController, videoView: VideoView){
        videoView.visibility=View.VISIBLE
        //플레이어 컨트롤러 안보이게
        //videoView.setMediaController(videocontroller)
        videoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_cbm/1.mp4"))
        videoView.requestFocus()
        videoView.seekTo(0); //0초에서 시작하도록
        videoView.start()
        isPlaying = true
        //다 재생되면 자동 종료
        videoView.setOnCompletionListener(MediaPlayer.OnCompletionListener {
            videoView.stopPlayback()
            isPlaying = false
            videoView.visibility = View.INVISIBLE
            Handler().postDelayed({
                invisibleLayout(eduCbmLayout5,eduCbmLayout6)
                visibleText(eduCbmTxt6_2,eduCbmTxt6_3,btnEduNext6,btnGreenEduBack6)
            }, 2000)
        })
        //영상오류 리스너
        videoView.setOnErrorListener { mediaPlayer, i, i2 ->
            videoView.stopPlayback()
            videoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_cbm/1.mp4"))
            videoView.requestFocus()
            videoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> videoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        val eduCbmVideo = findViewById<VideoView>(R.id.eduCbmVideo)
        if(isPlaying){
            isStartSeek = eduCbmVideo.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        val eduCbmVideo = findViewById<VideoView>(R.id.eduCbmVideo)
        if(isPlaying){
            eduCbmVideo.seekTo(isStartSeek)
            eduCbmVideo.start()
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
                if(response.toInt() == 0) {
                    eduCbmLayout6.visibility=View.INVISIBLE
                    finish()
                    //val intent = Intent(this, HomeActivity::class.java)
                    //startActivity(intent)
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    isDoingNetwork =0
                }
            },
            Response.ErrorListener { error ->
               // Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                isDoingNetwork =0
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