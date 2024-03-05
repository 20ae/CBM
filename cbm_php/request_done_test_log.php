<?php
include_once 'dbConnection.php';

$Pkey = $_POST["Pkey"];
$testPkey = $_POST["testPkey"];

$testlog_Currentprogress_check = "select Currentprogress from TestLog where  PKey =  '".$Pkey."' and TestPKey='".$testPkey."'";
$check_result = mysqli_query($db_con, $testlog_Currentprogress_check);

if(mysqli_fetch_row($check_result)[0] >= 2){
  $testlog_status_update = mysqli_query($db_con, "update TestLog SET Status = 10, UpdateDate = now() where  PKey =  '".$Pkey."' and TestPKey='".$testPkey."'");
  $update_result = mysqli_query($db_con, $testlog_status_update);
}
?>