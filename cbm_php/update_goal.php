<?php
include_once 'dbConnection.php';

$Pkey = (int)$_POST["Pkey"];
$testPkey = (int)$_POST["testPkey"];
$goal=(int)$_POST["goal"];
$goalText=$_POST["goalText"];

$testlog_goal_update = mysqli_query($db_con, "update TestLog SET Goal = '".$goal."', GoalText = '".$goalText."', UpdateDate = now() where  PKey =  '".$Pkey."' and TestPKey='".$testPkey."'");
$update_result = mysqli_query($db_con, $testlog_goal_update);
?>