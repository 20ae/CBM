package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import kotlinx.android.synthetic.main.activity_training_mind.*
import android.media.MediaPlayer.OnCompletionListener
import android.os.Build
import android.os.Handler
import android.util.Log
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.constraintlayout.widget.ConstraintLayout
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.google.android.material.slider.Slider
import kotlin.math.round
import kotlin.math.roundToInt


class TrainingMindActivity : AppCompatActivity() {
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isSendingData = 0
    var videoNum = 1
    var isPlaying = false //플레이 유무 변수
    var isStartSeek = 0 //재생위치

    @RequiresApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_mind)
        //설명
        val mindText1_1 = findViewById<TextView>(R.id.mindText1_1)
        val mindText1_2 = findViewById<TextView>(R.id.mindText1_2)
        val mindText2_1 = findViewById<TextView>(R.id.mindText2_1)
        val mindText2_2 = findViewById<TextView>(R.id.mindText2_2)
        val mindText3_1 = findViewById<TextView>(R.id.mindText3_1)
        val mindText3_2 = findViewById<TextView>(R.id.mindText3_2)
        val mindText3_3 = findViewById<TextView>(R.id.mindText3_3)
        val mindText4_1 = findViewById<TextView>(R.id.mindText4_1)
        val mindText4_2 = findViewById<TextView>(R.id.mindText4_2)
        val mindText4_3 = findViewById<TextView>(R.id.mindText4_3)
        val mindText5_1 = findViewById<TextView>(R.id.mindText5_1)
        val mindImage1 = findViewById<ImageView>(R.id.mindImage1)
        val mindImage5 = findViewById<ImageView>(R.id.mindImage5)
        val textNextButton = findViewById<ImageButton>(R.id.textNextButton)
        val mindbtnGreenBack = findViewById<ImageView>(R.id.mindbtnGreenBack)
        var textPageNum = 0
        //비디오 실행
        var mindvideoView = findViewById<VideoView>(R.id.mindVideoView)
        var videocontroller = MediaController(this) //배포시에 이 부분 제거해야함!!!!!!
        //Slider
        val sliderLevelText = findViewById<ConstraintLayout>(R.id.sliderLevelText)
        val sliderLevelText1 = findViewById<TextView>(R.id.sliderLevelText1)
        val sliderLevelText2 = findViewById<TextView>(R.id.sliderLevelText2)
        val sliderLevelText3 = findViewById<TextView>(R.id.sliderLevelText3)
        val sliderLevelText4 = findViewById<TextView>(R.id.sliderLevelText4)
        val sliderLevelText5 = findViewById<TextView>(R.id.sliderLevelText5)
        val sliderLevelText2_0 = findViewById<ConstraintLayout>(R.id.sliderLevelText2_0)
        val sliderLevelText2_1 = findViewById<TextView>(R.id.sliderLevelText2_1)
        val sliderLevelText2_2 = findViewById<TextView>(R.id.sliderLevelText2_2)
        val sliderLevelText2_3 = findViewById<TextView>(R.id.sliderLevelText2_3)
        val sliderLevelText2_4 = findViewById<TextView>(R.id.sliderLevelText2_4)
        val sliderLevelText2_5 = findViewById<TextView>(R.id.sliderLevelText2_5)
        val sliderLevelNumber = findViewById<ConstraintLayout>(R.id.sliderLevelNumber)
        val sliderLevelNumber1 = findViewById<TextView>(R.id.sliderLevelNumber1)
        val sliderLevelNumber2 = findViewById<TextView>(R.id.sliderLevelNumber2)
        val sliderLevelNumber3 = findViewById<TextView>(R.id.sliderLevelNumber3)
        val sliderLevelNumber4 = findViewById<TextView>(R.id.sliderLevelNumber4)
        val sliderLevelNumber5 = findViewById<TextView>(R.id.sliderLevelNumber5)
        val sliderLevelNumber6 = findViewById<TextView>(R.id.sliderLevelNumber6)
        val sliderLevelNumber7 = findViewById<TextView>(R.id.sliderLevelNumber7)
        val sliderLevelNumber8 = findViewById<TextView>(R.id.sliderLevelNumber8)
        val sliderLevelNumber9 = findViewById<TextView>(R.id.sliderLevelNumber9)
        //imagineLevel
        val imagineLevelSlider = findViewById<Slider>(R.id.imagineLevelSlider)
        val imagineLevelQuestion = findViewById<TextView>(R.id.imagineLevelQuestion)
        var imagineLevel = ""
        val imagineLevelButton = findViewById<ImageButton>(R.id.imagineLevelButton)
        //focusLevel
        val focusLevelSlider = findViewById<Slider>(R.id.focusLevelSlider)
        val focusLevelQuestion = findViewById<TextView>(R.id.focusLevelQuestion)
        var focusLevel = ""
        val focusLevelButton = findViewById<ImageButton>(R.id.focusLevelButton)
        //동적 간격 조정
        (mindText1_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.179f)
        }
        (mindImage1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.035f)
        }
        (mindText1_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.035f)
        }
        (mindText2_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.231f)
        }
        (mindText2_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.143f)
        }
        (mindText3_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.187f)
        }
        (mindText3_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.093f)
        }
        (mindText3_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.073f)
        }
        (mindText4_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.187f)
        }
        (mindText4_2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.083f)
        }
        (mindText4_3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.083f)
        }
        (mindText5_1.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.198f)
        }
        (mindImage5.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.076f)
        }
        (mindvideoView.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = changeDP(0.141f)
        }
        (imagineLevelQuestion.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.095f)
        }
        (focusLevelQuestion.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.095f)
        }
        (textNextButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        (imagineLevelButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        (focusLevelButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.127f)
        }
        (sliderLevelText.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.164f)
        }
        (sliderLevelText2_0.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = changeDP(0.164f)
        }

        (imagineLevelSlider.layoutParams as ConstraintLayout.LayoutParams).apply {
            width = changeWidth(300.0f,320.0f)
        }
        (focusLevelSlider.layoutParams as ConstraintLayout.LayoutParams).apply {
            width = changeWidth(300.0f,320.0f)
        }
        (sliderLevelNumber.layoutParams as ConstraintLayout.LayoutParams).apply{
            width = changeWidth(1.014f)
        }


        //현재 testLogPkey 받아오기
        val testLogPkey = MySharedPreferences.getTestLogPKey(this)
        val testPkey = MySharedPreferences.getTestPKey(this)

        //심상 교육 시작 시 설명 시작 부분만 visible
        mindText1_1.visibility = View.VISIBLE
        mindImage1.visibility = View.VISIBLE
        mindText1_2.visibility = View.INVISIBLE
        mindText2_1.visibility = View.INVISIBLE
        mindText2_2.visibility = View.INVISIBLE
        mindText3_1.visibility = View.INVISIBLE
        mindText3_2.visibility = View.INVISIBLE
        mindText3_3.visibility = View.INVISIBLE
        mindText4_1.visibility = View.INVISIBLE
        mindText4_2.visibility = View.INVISIBLE
        mindText4_3.visibility = View.INVISIBLE
        mindText5_1.visibility = View.INVISIBLE
        mindImage5.visibility = View.INVISIBLE
        textNextButton.visibility = View.INVISIBLE
        mindbtnGreenBack.visibility = View.INVISIBLE

        mindvideoView.visibility = View.INVISIBLE

        sliderLevelText2_0.visibility = View.INVISIBLE
        sliderLevelText.visibility = View.INVISIBLE
        sliderLevelNumber.visibility = View.INVISIBLE

        imagineLevelQuestion.visibility = View.INVISIBLE
        imagineLevelSlider.visibility = View.INVISIBLE
        imagineLevelButton.visibility = View.INVISIBLE

        focusLevelQuestion.visibility = View.INVISIBLE
        focusLevelSlider.visibility = View.INVISIBLE
        focusLevelButton.visibility = View.INVISIBLE

        //설명부분
        if (textPageNum ==0){
            Handler().postDelayed({
                mindText1_2.visibility = View.VISIBLE
            },2000)
            Handler().postDelayed({
                textNextButton.visibility = View.VISIBLE
                mindbtnGreenBack.visibility = View.VISIBLE
            },4000)
        }

        textNextButton.setOnClickListener(){
            textPageNum += 1
            textNextButton.visibility = View.INVISIBLE
            mindbtnGreenBack.visibility = View.INVISIBLE

            if(textPageNum ==1){
                mindText1_1.visibility = View.INVISIBLE
                mindImage1.visibility = View.INVISIBLE
                mindText1_2.visibility = View.INVISIBLE

                mindText2_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    mindText2_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    textNextButton.visibility = View.VISIBLE
                    mindbtnGreenBack.visibility = View.VISIBLE
                },4000)
            }
            else if(textPageNum == 2){
                mindText2_1.visibility = View.INVISIBLE
                mindText2_2.visibility = View.INVISIBLE

                mindText3_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    mindText3_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    mindText3_3.visibility = View.VISIBLE
                },4000)
                Handler().postDelayed({
                    textNextButton.visibility = View.VISIBLE
                    mindbtnGreenBack.visibility = View.VISIBLE
                },6000)
            }
            else if(textPageNum == 3){
                mindText3_1.visibility = View.INVISIBLE
                mindText3_2.visibility = View.INVISIBLE
                mindText3_3.visibility = View.INVISIBLE

                mindvideoView.visibility = View.VISIBLE
                if(isSendingData==0){
                    playMindVideo(this,videocontroller,videoNum)
                    videoNum += 1
                    isSendingData = 1
                }
            }
            else if (textPageNum == 4){
                mindText4_1.visibility = View.INVISIBLE
                mindText4_2.visibility = View.INVISIBLE
                mindText4_3.visibility = View.INVISIBLE

                mindvideoView.visibility = View.VISIBLE
                if(isSendingData==0){
                    playMindVideo(this,videocontroller,videoNum)
                    isSendingData = 1
                }
            }
            else if (textPageNum == 5){
                if(isDoingNetwork == 0) {
                    updateCurrentProgress(this, testLogPkey, testPkey)
                }
            }
        }

        // imagineLevel 입력 시에 다음 버튼 활성화
        imagineLevelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                imagineLevel = slider.value.toInt().toString()
                //Toast.makeText(this@TrainingMindActivity, imagineLevel,Toast.LENGTH_LONG).show()
                imagineLevelButton.visibility = View.VISIBLE
                mindbtnGreenBack.visibility = View.VISIBLE
            }
        })

        // imagineLevel에서 다음 버튼 클릭 시
        imagineLevelButton.setOnClickListener(){
            imagineLevelQuestion.visibility =View.INVISIBLE
            imagineLevelSlider.visibility = View.INVISIBLE
            sliderLevelText2_0.visibility = View.INVISIBLE
            imagineLevelButton.visibility = View.INVISIBLE
            mindbtnGreenBack.visibility = View.INVISIBLE
            focusLevelQuestion.visibility =View.VISIBLE
            focusLevelSlider.visibility = View.VISIBLE
            focusLevelSlider.value = 1.0f
            sliderLevelText.visibility = View.VISIBLE
        }

        // focusLevel 입력 시에 다음 버튼 활성화
        focusLevelSlider.addOnSliderTouchListener(object : Slider.OnSliderTouchListener {
            override fun onStartTrackingTouch(slider: Slider) {
            }

            override fun onStopTrackingTouch(slider: Slider) {
                focusLevel = slider.value.toInt().toString()
                //Toast.makeText(this@TrainingMindActivity, focusLevel,Toast.LENGTH_LONG).show()
                focusLevelButton.visibility = View.VISIBLE
                mindbtnGreenBack.visibility = View.VISIBLE
            }
        })

        // focusLevel에서 다음 버튼 클릭 시
        focusLevelButton.setOnClickListener(){
            //1번 영상은 데이터 넘기지 말고 2번부터
            if ((videoNum!=1)&&(isSendingData==0)){
                updateImagine(this, testLogPkey, videoNum-1, imagineLevel, focusLevel)
            }

        }
    }

    private fun changeDP(value : Float) : Int{
        val displayMetrics = resources.displayMetrics
        val dp = round(displayMetrics.heightPixels * value )
        val result = dp.roundToInt()
        return result
    }
    private fun changeWidth(value1 : Float, value2 : Float) : Int{
        val displayMetrics = resources.displayMetrics
        val dp = round((displayMetrics.widthPixels) * value1 / value2)
        val result = dp.roundToInt()
        return result
    }
    private fun changeWidth(value1: Float) : Int{
        val displayMetrics = resources.displayMetrics
        val dp = round((displayMetrics.widthPixels) * value1)
        val result = dp.roundToInt()
        return result
    }

    private fun playMindVideo(context: Context, videocontroller: MediaController, videoNum: Int){
        //mindVideoView.setMediaController(videocontroller)
        mindVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_imagine/"+videoNum+".mp4"))
        mindVideoView.requestFocus()
        mindVideoView.seekTo(0) //0초에서 시작하도록
        mindVideoView.start()
        isPlaying = true
        //다 재생되면 자동 종료
        mindVideoView.setOnCompletionListener(OnCompletionListener {
            mindVideoView.stopPlayback()
            mindVideoView.visibility = View.INVISIBLE
            isSendingData=0
            isPlaying = false
            if(videoNum==1){
                mindVideoView.visibility = View.INVISIBLE

                mindText4_1.visibility = View.VISIBLE
                Handler().postDelayed({
                    mindText4_2.visibility = View.VISIBLE
                },2000)
                Handler().postDelayed({
                    mindText4_3.visibility = View.VISIBLE
                },4000)
                Handler().postDelayed({
                    textNextButton.visibility = View.VISIBLE
                    mindbtnGreenBack.visibility = View.VISIBLE
                },6000)
            }
            else{
                sliderLevelNumber.visibility = View.VISIBLE
                sliderLevelText2_0.visibility = View.VISIBLE
                imagineLevelQuestion.visibility =View.VISIBLE
                imagineLevelSlider.visibility = View.VISIBLE
                imagineLevelSlider.value = 1.0f
            }
        })
        mindVideoView.setOnErrorListener { mediaPlayer, i, i2 ->
            mindVideoView.stopPlayback()
            mindVideoView.setVideoURI(Uri.parse("http://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/training_imagine/"+videoNum+".mp4"))
            mindVideoView.requestFocus()
            mindVideoView.seekTo(0)
            val alert : AlertDialog.Builder = AlertDialog.Builder(this)
            alert.setTitle("알림")
            alert.setMessage("미디어 플레이어 오류로 인해\n동영상이 멈췄습니다.\n처음부터 다시 재생하겠습니다.")
            alert.setPositiveButton("확인",
                DialogInterface.OnClickListener { dialog, which -> mindVideoView.start()})
            alert.show()
            true
        }
    }
    //홈버튼 누르고 다시 앱으로 돌아올 때 영상이 해당 위치에서 재생하기
    override fun onPause() {
        super.onPause()
        var mindvideoView = findViewById<VideoView>(R.id.mindVideoView)
        if(isPlaying){
            isStartSeek = mindvideoView.currentPosition
        }
    }

    override fun onResume() {
        super.onResume()
        var mindvideoView = findViewById<VideoView>(R.id.mindVideoView)
        if(isPlaying){
            mindvideoView.seekTo(isStartSeek)
            mindvideoView.start()
        }
    }


    private fun updateImagine(context: Context, testLogPkey:String, VideoNum:Int, imagineLevel:String, focusLevelText:String){

        val url = "http://healingmindcenter.com/cbm_app/cbm_api/update_imagine.php"

        val queue = Volley.newRequestQueue(this)
        isSendingData = 1

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                if (response.toInt() == 0){
                    sliderLevelNumber.visibility = View.INVISIBLE
                    sliderLevelText.visibility = View.INVISIBLE
                    focusLevelQuestion.visibility =View.INVISIBLE
                    focusLevelSlider.visibility = View.INVISIBLE
                    focusLevelButton.visibility = View.INVISIBLE
                    mindbtnGreenBack.visibility = View.INVISIBLE

                    if (videoNum==6){
                        mindVideoView.visibility = View.INVISIBLE

                        mindText5_1.visibility = View.VISIBLE
                        mindImage5.visibility = View.VISIBLE
                        Handler().postDelayed({
                            textNextButton.visibility = View.VISIBLE
                            mindbtnGreenBack.visibility = View.VISIBLE
                        },2000)
                    }
                    else{

                        videoNum += 1
                        mindVideoView.visibility = View.VISIBLE
                        playMindVideo(this,MediaController(this),videoNum)
                    }
                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    isSendingData =0
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                isSendingData =0
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["testLogPkey"] = testLogPkey
                params["videoNum"] = VideoNum.toString().trim()
                params["imagineLevelText"] = imagineLevel
                params["focusLevelText"] = focusLevelText

                return params
            }
        }
        queue.add(request)
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
                    netWorkError()
                    isDoingNetwork =0
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                isDoingNetwork =0
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