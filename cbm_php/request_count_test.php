<?php
include_once 'dbConnection.php';

$testPKey = $_POST["testPKey"];

$testlog_check = "select * from TestLog where TestPKey = '".$testPKey."' and Status = 10";
$check_result = mysqli_query($db_con, $testlog_check);
$testlog_check2 = "select * from TestLog where TestPKey = '".$testPKey."' and Status = 1";
$check_result2 = mysqli_query($db_con, $testlog_check2);
$result = ($check_result -> num_rows) + ($check_result2 -> num_rows);
if($result > 0){ 
	echo (int)$result;
}else{
	echo 0;
}


?>