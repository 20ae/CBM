<?php
include_once 'dbConnection.php';

$testLogPkey = (int)$_POST["testLogPkey"];
$think = $_POST["think"];
$thinkCount = (int)$_POST["thinkCount"];
$think = addslashes("$think");
$testPKey_check = "select * from TestLog where PKey ='".$testLogPkey."'";
$testPKey_result = mysqli_query($db_con, $testPKey_check);

if($testPKey_result -> num_rows > 0){
  
  if($thinkCount<=13 && $thinkCount>0){
	  $update_think = "update TestLog SET Think" . $thinkCount . " = '".$think."', UpdateDate = now() where Pkey='".$testLogPkey."'";
	  //$update_result = mysqli_query($db_con,"update TestLog SET Think" . $thinkCount . " = '".$think."', UpdateDate = now() where Pkey='".$testLogPkey."'");
	  $update_result = mysqli_query($db_con,$update_think);
      echo $update_result;
  }
  
  
  
} else {
  echo "-1";
}

?>