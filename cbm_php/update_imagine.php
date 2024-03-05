<?php
include_once 'dbConnection.php';

$testLogPkey = (int)$_POST["testLogPkey"];
$videoNum = (int)$_POST["videoNum"];
$ImagineLevel=(int)$_POST["imagineLevelText"];
$FocusLevel=(int)$_POST["focusLevelText"];


$testlog_check = "select * from TestLog where Pkey =  '".$testLogPkey."'";
$check_result = mysqli_query($db_con, $testlog_check);

$query =  "update TestLog SET ImagineLevel".$videoNum." = '".$ImagineLevel."', FocusLevel".$videoNum." = '".$FocusLevel."', UpdateDate = now() where Pkey = '".$testLogPkey."'";
$imagine_update = mysqli_query($db_con, $query);

echo $imagine_update;

?>