<?php
include_once 'dbConnection.php';

$Pkey = (int)$_POST["Pkey"];
$testPkey = (int)$_POST["testPkey"];

$testlog_currentProgress_update = mysqli_query($db_con, "update TestLog SET CurrentProgress = CurrentProgress + 1, UpdateDate = now() where  PKey =  '".$Pkey."' and TestPKey='".$testPkey."'");
$insert_result = mysqli_query($db_con, $testlog_currentProgress_update);

?>