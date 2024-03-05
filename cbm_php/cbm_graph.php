<?
        $locate_log="./debug.log";


        //$myfile=fopen("testfile.txt","w");
        //$testtxt=var_export($_POST, true)."aaa";
        //$testtxt=json_decode(file_get_contents('php://input'));
        //$testtxt=var_export($testtxt->{'userRequest'}->{'user'}->{'properties'}->{'plusfriendUserKey'}, true);
        //fwrite($myfile,$testtxt);

        //fclose($myfile);
       // error_log("start check sign up\n",3,$locate_log);
       // error_log(var_export(file_get_contents('php://input'),true)."\n", 3, $locate_log);
	
       // $parse_data=json_decode(file_get_contents('php://input'));
       // $chatbot_key=var_export($parse_data->{'userRequest'}->{'user'}->{'properties'}->{'plusfriendUserKey'}, true);
// $chatbot_key=str_replace("'","",$chatbot_key);

	//$user_id=$_GET['user_id'];
        //$test_type=$_GET['type'];
        $user_id=$_POST['user_id'];
        $test_type=$_POST['type'];

        $db_con = mysqli_connect("localhost", "root", "0505");
        if (!$db_con){
                //echo "DB 연결 실패<p>";
                //need to make to return fail
            //    error_log("error connect to db\n",3,$locate_log);
        }

        $db_sel = mysqli_select_db($db_con, "study_db");
        if (!$db_sel) {
                //echo "DB select NO <p>";
                //need to make to return fail
          //      error_log("error select db\n",3,$locate_log);
        }



        //error_log("start search db with chatbot key\n",3,$locate_log);

	//$chatbot_key="QlPrWr9GSlJz";
        $query_str = "select TestLog.MoodLevel1 as MoodLevel1, TestLog.MoodLevel2 as MoodLevel2, TestLog.EmotionLevel as Emotion, TestLog.FeelingWhen as FeelingWhen,
	TestLog.FeelingWhere as FeelingWhere, TestLog.FeelingWho as FeelingWho, TestLog.FeelingTotal as FeelingTotal, TestLog.InsertDate as InsertDate
	from User
	left join Test on Test.UserPKey = User.PKey 
	left join TestLog on Test.PKey = TestLog.TestPKey 
	where User.PKey=". $user_id ." and User.group_type= ".$test_type."
	order by TestLog.PKey asc;";

	$graph_max_width=3000;
	$graph_min_width=500;
	$graph_width=0;
        $result = mysqli_query($db_con, $query_str);
	$labels=null;
	$data=null;
	$temp_week="";
	$data_count=0;
	$mood_array=array();
	$total_array=array();
	while($row = $result->fetch_row()){
                // need to make a form to update
                $mood_level1=$row[0];
                $mood_level2=$row[1];
                $emotion_level=$row[2];
                $feeling_total=$row[6];
                $insert_date=$row[7];
		$insert_hour=date("H",strtotime($insert_date));
		$insert_AMPM="AM";
		$insert_hour=intval($insert_hour,0);
		
		if($insert_hour>12){
			$insert_hour=$insert_hour-12;
			$insert_AMPM="PM";
		}
		$week = array("일","월","화","수","목","금","토");

		$insert_week=$week[date('w', strtotime($insert_date))];

		if($mood_level1>0)
			$mood_level1=$emotion_level;
		else
			$mood_level1=$emotion_level*-1;

		if($labels==null){
		/*	if(strcmp($temp_week,$insert_week)==0){
				$labels="['".$insert_AMPM.$insert_hour."'";
				$data="['".$mood_level1."'";
			} else {
		 */
				$labels="[['".$insert_AMPM.$insert_hour."' , '".$insert_week."']";
				$data="['".$mood_level1."'";
				$temp_week=$insert_week;
		//	}
		} else {
		/*	if(strcmp($temp_week,$insert_week)==0){
				$labels=$labels.", '".$insert_AMPM.$insert_hour."' ";
				$data=$data.", '".$mood_level1."'";
		} else {*/
				$labels=$labels.", ['".$insert_AMPM.$insert_hour."' , '".$insert_week."']";
				$data=$data.", '".$mood_level1."'";
				$temp_week=$insert_week;
		//	}
		}

		$mood2_text="";

		if($mood_level1>0){
			switch($mood_level2){
				case 1 : $mood2_text="행복한"; break;
				case 2 : $mood2_text="편안한"; break;
				case 3 : $mood2_text="기쁜"; break;
				case 4 : $mood2_text="만족스러운"; break;
				case 5 : $mood2_text="감사한"; break;
			}
		} else if($mood_level1<0){
			switch($mood_level2){
				case 1 : $mood2_text="슬픈"; break;
				case 2 : $mood2_text="걱정스러운"; break;
				case 3 : $mood2_text="절망적인"; break;
				case 4 : $mood2_text="화난"; break;
				case 5 : $mood2_text="답답한"; break;
				case 6 : $mood2_text="수치스러운"; break;
				case 7 : $mood2_text="짜증난"; break;
				case 8 : $mood2_text="우울한"; break;
				case 9 : $mood2_text="공허한"; break;
				case 10 : $mood2_text="불안한"; break;
				case 11 : $mood2_text="무서운"; break;
			}
		}
		array_push($mood_array,$mood2_text);
		array_push($total_array,$feeling_total);
	  	//error_log("mood : ".$mood_level1." insert hour : ".$insert_AMPM.$insert_hour."  week : ".$insert_week."\n",3,$locate_log);
	  	//error_log("temp : ".$temp_week." week : ".$insert_week."  \n",3,$locate_log);

		
		$data_count++;
	}
	if($labels==null){
		$labels="[] ";
		$data="[] ";
	}else {
		$labels=$labels."] ";
		$data=$data."] ";
	}
	
	if(($graph_width=$data_count*55)<$graph_min_width)
		$graph_width=$graph_min_width;

	//error_log("labels : ".$labels." data : ".$data."  \n",3,$locate_log);
?>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>기분 일기</title>
<head>
        <meta name="viewport" content="width=device-width, initial-scale=1">
        <script type="text/javascript" src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/3.6.0/chart.min.js"></script>
        <style id="compiled-css" type="text/css">
        canvas{
                display: inline-block
        }

        .chartWrapper {/* w w w. j a v  a2 s.co  m*/
                position: relative;
        }
        .chartWrapper > canvas {
                position: absolute;
                left: 0;
                top: 0;
             pointer-events:none;
        }
        .chartAreaWrapper {
	width: <?= $graph_width ?>px;
                  height: 400px;
                overflow-x: scroll;
        }
        canvas#myChart{
                position: absolute;
                //width: 3000px !important;
                //height: 400px !important;

	}
	.chartImageWrapper{
                position: fixed;
                left:0px;
                top:52px;
        }
        .imgDiv{
                padding: 0px 5px 40px 5px;
                width:40px;
                height:40px;
        }


      </style>


</head>
<body>
<div class="chartWrapper">
         <div class="chartAreaWrapper">
        <canvas id="myChart" role="img" width="<?= $graph_width ?>" height="400" ></canvas>
        </div>
</div>
        <script>
var mood_array = <?php echo json_encode($mood_array)?>;
var total_array = <?php echo json_encode($total_array)?>;

const ctx = document.getElementById('myChart').getContext('2d');
const myChart = new Chart(ctx, {
    type: 'line',
    data: {
        datasets: [{
            label: '기분 일기',
		    data: <?= $data ?>,
            backgroundColor: [
                'rgba(85, 85, 85, 0.2)'
            ],
            borderColor: [
                'rgba(85, 85, 85, 1)'
            ],
            borderWidth: 2,
            tension: 0.4,
                fill:{
                },
                pointRadius:10
        }
        ]
    },
    options: {
        scales: {
                y: {
                        beginAtZero: true,
                        min : -10,
                        max : 10,
                        ticks:{
                                font:{
                                        size:15
                                },
                                stepSize: 50
                        },
                        grid:{
                                drawBorder: false,
                                color: function(context){
                                        if(context.tick.value >0){
                                                return '#ff7800';
                                        } else if(context.tick.value <0){
                                                return '#0000ff';
                                        }
                                        return '#000000';
                                }
                        }
                },
                x: {
                        type: 'category',
				labels: <?= $labels ?>,
			ticks:{
				font:{
                                        size:15
                                }
                        }
                }
        },
        plugins:{
                legend: {
                        labels:{
                                font:{
                                        size:15
                                }
                        }
		},
		tooltip: {
                        callbacks: {
                                label: function(context) {
                                        var label=mood_array[context.dataIndex];
                                        label=label +'\n'+ total_array[context.dataIndex];
                                        return label;
                                }
                        }
                }
        },
        layout:{
                padding:{
                        left: 10,
                        right: 10,
                        top: 0,
                        bottom: 0
                }
        },
        elements:{
                point:{
                        borderWidth:30,
                        hoverBorderWidth:50
        	}
	},
	events: ['click']
    }
});
        </script>
</body>


