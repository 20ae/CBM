package ac.duksung.cbm_android

import android.app.AlertDialog
import android.content.Context
import android.content.DialogInterface
import android.media.MediaPlayer
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.text.Editable
import android.text.TextWatcher
import android.util.DisplayMetrics
import android.util.Log
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import com.android.volley.NetworkError
import com.android.volley.Response
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_test.*

class TrainingCBMActivity : AppCompatActivity() {

    var mediaPlayer : MediaPlayer = MediaPlayer() //음성파일 관련 변수
    var cbmDegreeTrans : Int = 0 // 해당 차수에 해당되는 동영상 위치
    var isPlaying = false //플레이 확인 변수
    var isDataLoad = false //데이터로드확인변수
    var isDoingNetwork = 0 //통신 전역변수 0:클릭가능
    var isSendingData = 0
    //각종 변수
    var testPkey = ""
    var testLogPkey = ""
    var thinkCount = 0 //cbm이 몇번째 음성인지를 나타내는 변수


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_training_cbm)

        testPkey = MySharedPreferences.getTestPKey(this)
        testLogPkey = MySharedPreferences.getTestLogPKey(this)

        //위치재조정하기위한 값
        val metrics : DisplayMetrics = resources.displayMetrics
        val height = metrics.heightPixels
        val width = metrics.widthPixels

        //차수를 알아오는 함수
        requestCount(this,testPkey)

        //첫번째 장면 오브젝트
        val trainCBMLayout = findViewById<ConstraintLayout>(R.id.TrainCBMLayout1)
        val trainCBMNextButton = findViewById<ImageButton>(R.id.TrainCBMNextBut)
        val textview = findViewById<TextView>(R.id.TrainCBMTextView)
        val textview2 = findViewById<TextView>(R.id.TrainCBMTextView2)
        val image = findViewById<ImageView>(R.id.TrainCBMImageView)
        val buttonBack = findViewById<ImageView>(R.id.TrainCBMNextButBack)
        textview.visibility = View.INVISIBLE
        textview2.visibility = View.INVISIBLE
        buttonBack.visibility = View.INVISIBLE
        trainCBMNextButton.visibility = View.INVISIBLE
        trainCBMLayout.visibility = View.VISIBLE
        Handler().postDelayed({
            textview.visibility = View.VISIBLE
        },2000)
        Handler().postDelayed({
            textview2.visibility = View.VISIBLE
            buttonBack.visibility = View.VISIBLE
            trainCBMNextButton.visibility = View.VISIBLE
        },4000)

        //위치재조정
        val params = image.layoutParams
        params.width = (width * (239.3/320.0)).toInt()
        params.height = (height *(171.3/480.0)).toInt()
        image.layoutParams = params
        (image.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (50.0/480.0)).toInt()
        }
        (textview.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (27.0/480.0)).toInt()
        }
        (textview2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (20.0/480.0)).toInt()
        }
        (trainCBMNextButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //두번째 장면 오브젝트
        val trainCBMLayout2 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout2)
        val image2 = findViewById<ImageView>(R.id.TrainCBMImageView2)
        val replyButton = findViewById<ImageButton>(R.id.TrainCBMNextBut_2)
        mediaPlayer  = MediaPlayer()
        trainCBMLayout2.visibility = View.INVISIBLE
        //위치재조정
        val params2 = image2.layoutParams
        params2.width = (width * (231.6/320.0)).toInt()
        params2.height = (height * (200.7/480.0)).toInt()
        image2.layoutParams = params2
        (image2.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (105.0/480.0)).toInt()
        }
        (replyButton.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //두번째-2 장면 오브젝트
        val trainCBMLayout2_2 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout2_2)
        val no_button = findViewById<ImageButton>(R.id.no_button)
        val yes_button = findViewById<ImageButton>(R.id.yes_button)
        trainCBMLayout2_2.visibility = View.INVISIBLE

        //세번째 장면 오브젝트
        val trainCBMEditText = findViewById<EditText>(R.id.TrainCBMEditText)
        val trainCBMLayout3 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout3)
        val trainCBMNextButton2 = findViewById<ImageButton>(R.id.TrainCBMNextBut2)
        val trainCBMButBack2 = findViewById<ImageView>(R.id.TrainCBMNextButBack2)
        val textview3 = findViewById<TextView>(R.id.TrainCMBTextView3)
        val textview4 = findViewById<TextView>(R.id.TrainCBMTextView4)
        trainCBMLayout3.visibility = View.INVISIBLE
        textview4.visibility = View.INVISIBLE
        trainCBMNextButton2.visibility = View.INVISIBLE
        trainCBMButBack2.visibility = View.INVISIBLE
        //위치재조정
        (textview3.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (75.0/480.0)).toInt()
        }
        (trainCBMEditText.layoutParams as ConstraintLayout.LayoutParams).apply {
            topMargin = (height * (28.0/480.0)).toInt()
            bottomMargin = (height * (28.0/480.0)).toInt()
        }
        (textview4.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (20.0/480.0)).toInt()
        }
        (trainCBMNextButton2.layoutParams as ConstraintLayout.LayoutParams).apply {
            bottomMargin = (height * (61.0/480.0)).toInt()
        }

        //첫번째 장면 버튼 관련 메소드
        trainCBMNextButton.setOnClickListener(){
            val number : String = (thinkCount+cbmDegreeTrans).toString()
            playerAudio(number,mediaPlayer,trainCBMLayout)
        }

        //두번째 장면 버튼 관련 메소드
        replyButton.setOnClickListener {
            val number : String = (thinkCount+cbmDegreeTrans).toString()
            isPlaying = false
            mediaPlayer.pause()
            replayAudio(number, mediaPlayer)
        }

        //두번째-2 장면 관련 함수
        yes_button.setOnClickListener {
            trainCBMLayout2_2.visibility = View.INVISIBLE
            trainCBMLayout3.visibility = View.VISIBLE
        }
        no_button.setOnClickListener {
            trainCBMLayout2_2.visibility = View.INVISIBLE
            trainCBMLayout3.visibility = View.VISIBLE
        }

        //세번째 장면 관련 함수
        trainCBMEditText.addTextChangedListener(object: TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if(trainCBMEditText.text.toString().trim().isNullOrBlank()){
                    textview4.visibility = View.INVISIBLE
                    trainCBMNextButton2.visibility = View.INVISIBLE
                    trainCBMButBack2.visibility = View.INVISIBLE
                }
                else{
                    textview4.visibility = View.VISIBLE
                    trainCBMNextButton2.visibility = View.VISIBLE
                    trainCBMButBack2.visibility = View.VISIBLE
                }
            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
        //세번째 장면 버튼 관련 메소드
        trainCBMNextButton2.setOnClickListener{
            if(trainCBMEditText.text.toString().trim().isNullOrBlank()){
                //Toast.makeText(this, "텍스트 입력해주세요.", Toast.LENGTH_SHORT).show()
            }
            else{
                thinkCount = thinkCount + 1
                //update하기
                val think_str = trainCBMEditText.text.toString().trim()
                updateThink(this, thinkCount, think_str, testLogPkey)

            }
        }
    }

    override fun onResume() {
        super.onResume()
        if(isPlaying){
            mediaPlayer.start()
        }
    }

    override fun onPause() {
        super.onPause()
        if(isPlaying){
            mediaPlayer.pause()
        }
    }
    override fun onDestroy() {
        super.onDestroy()
        if(isPlaying){
            mediaPlayer.stop()
        }
    }
    //인터넷 연결 확인 함수
    private fun getNetworkConnected() :Boolean{
        val cm = getSystemService(CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
        if (activeNetwork != null)
            return true
        return false
    }

    //오디오 플레이
    private fun playerAudio(number: String, mediaPlayer: MediaPlayer, turnOffLayout: ConstraintLayout){

        val trainCBMLayout2 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout2)
        val trainCBMLayout2_2 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout2_2)
        //네트워크 연결확인
        var networkCheck = getNetworkConnected()
        //네트워크 실패 시 호출
        if(!networkCheck){
            netWorkError()
            return
        }
        val trainCBMAudioUri =
                "https://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/cbm/" + number + ".mp3"
        try{
            mediaPlayer.setDataSource(this, Uri.parse(trainCBMAudioUri))
            isDataLoad = true
        }
        catch (e:Exception){
            isDataLoad = false //데이터로드 실패!!
            return
        }

        try{
            mediaPlayer.prepare() //prepare할 때 wifi연결이 안되면 에러가 남
        }
        catch (e:Exception){
            netWorkError()
            return
        }
        //이 구간부터는 다음뷰로 이동하고 음성재생
        mediaPlayer.start()
        turnOffLayout.visibility = View.INVISIBLE
        trainCBMLayout2.visibility = View.VISIBLE
        isPlaying = true
        //음원 영상이 끝나면 호출
        mediaPlayer.setOnCompletionListener {
            trainCBMLayout2.visibility = View.INVISIBLE
            mediaPlayer.stop()
            mediaPlayer.release()
            isPlaying = false
            isDataLoad = false
            trainCBMLayout2_2.visibility = View.VISIBLE

        }
        //음원 영상 에러 시
        mediaPlayer.setOnErrorListener { mediaPlayer, i, i2 ->
            isPlaying = false
            true
        }

    }

    private fun replayAudio(number: String, mediaPlayer: MediaPlayer){
        //네트워크 연결확인
        var networkCheck = getNetworkConnected()
        //네트워크 실패 시 호출
        if(!networkCheck) {
            netWorkError()
            return
        }
        //전에 데이터로드 실패 시 데이터 로드 과정이 필요하므로,
        if(!isDataLoad){
            val trainCBMAudioUri =
                "https://healingmindcenter.s3.ap-northeast-2.amazonaws.com/cbm_app/cbm/" + number + ".mp3"
            try{
                mediaPlayer.setDataSource(this, Uri.parse(trainCBMAudioUri))
                isDataLoad = true
            }
            catch (e:Exception){
                isDataLoad = false //데이터로드 실패!!
                return
            }

            try{
                mediaPlayer.prepare() //prepare할 때 wifi연결이 안되면 에러가 남
            }
            catch (e:Exception){
                netWorkError()
                return
            }
        }

        //이 구간부터는 다음뷰로 이동하고 음성재생
        mediaPlayer.seekTo(0)
        mediaPlayer.start()
        isPlaying = true
    }

    private fun requestCount(context: Context, testPKey: String){

        val url = "https://healingmindcenter.com/cbm_app/cbm_api/request_count_test.php"
        val queue = Volley.newRequestQueue(this)


        val request : StringRequest = object : StringRequest(
            Method.POST,url,
            Response.Listener { response ->
               // Toast.makeText(context, response.toString(), Toast.LENGTH_LONG).show()
                var result = response.toInt()

                if(result >= 0){
                    if(result < 4) {
                        cbmDegreeTrans = 1
                    }
                    else{
                        cbmDegreeTrans = 1 + 13*(result - 4)
                    }

                }
                else{
                    netWorkError2()
                }

            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, error.toString(), Toast.LENGTH_LONG).show()
                netWorkError2()
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["testPKey"]=testPKey
                return params
            }
        }
        queue.add(request)
    }

    private fun updateThink(context: Context, think_count:Int, think:String, testLogPKey:String){
        val url = "https://healingmindcenter.com/cbm_app/cbm_api/update_think.php"
        val queue = Volley.newRequestQueue(this)

        val trainCBMLayout3 = findViewById<ConstraintLayout>(R.id.TrainCBMLayout3)
        val trainCBMEditText = findViewById<EditText>(R.id.TrainCBMEditText)

        val request : StringRequest = object : StringRequest(
            Method.POST, url,
            Response.Listener { response ->
                var result = response.toInt()
                if(result >= 0){ //통신성공
                    isSendingData = isSendingData +1
                    //초기화 및 다음 단계로 넘어가기
                    trainCBMEditText.text = null
                    if(thinkCount < 13){
                        if(isSendingData == thinkCount) {
                            val number: String = (thinkCount + cbmDegreeTrans).toString()
                            mediaPlayer = MediaPlayer()
                            playerAudio(number,mediaPlayer,trainCBMLayout3)
                        }
                    }
                    else{
                        if(isDoingNetwork == 0) {
                            updateDoneSection(this, testLogPkey, testPkey)
                        }
                    }


                }else{
                    //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                    netWorkError()
                    thinkCount = isSendingData
                }
            },
            Response.ErrorListener { error ->
                //Toast.makeText(context, "통신에 실패하였습니다. 다시 실행해 주세요.", Toast.LENGTH_LONG).show()
                netWorkError()
                thinkCount = isSendingData
            }
        ){
            override fun getParams(): MutableMap<String, String> {
                val params : MutableMap<String,String> = HashMap()
                params["testLogPkey"] = testLogPKey
                params["think"] = think
                params["thinkCount"] = (think_count).toString()
                return params
            }
        }
        queue.add(request)


    }

    private fun updateDoneSection(context: Context, pKey:String, testPKey: String){
        val url = "https://healingmindcenter.com/cbm_app/cbm_api/request_done_section.php"
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