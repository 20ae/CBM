<?php
include_once 'dbConnection.php';

$pkey = $_POST["pkey"];
$testPkey = $_POST["testPkey"];
$mood1=(int)$_POST["mood1"];
$mood2=(int)$_POST["mood2"];
$emotion=(int)$_POST["emotion"];
$when=$_POST["when"];
$who=$_POST["who"];
$where=$_POST["where"];
$total=$_POST["total"];


$testlog_check = "select * from TestLog where PKey =  '".$pkey."'";
$check_result = mysqli_query($db_con, $testlog_check);

$testlog_update = mysqli_query($db_con, "update TestLog SET MoodLevel1 = '".$mood1."', MoodLevel2 = '".$mood2."',  EmotionLevel = '".$emotion."', FeelingWhen = '".$when."', FeelingWhere = '".$where."', FeelingWho = '".$who."', FeelingTotal = '".$total."', UpdateDate = now() where  PKey =  '".$pkey."' and TestPKey='".$testPkey."'");
$insert_result = mysqli_query($db_con, $testlog_update);

?>