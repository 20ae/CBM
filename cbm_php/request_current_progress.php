<?php
include_once 'dbConnection.php';

$pKey = $_POST["pKey"];
$testPKey = $_POST["testPKey"];

$get_current_progress = mysqli_query($db_con,"select currentProgress from TestLog where pKey = '".$pKey."' and TestPKey = '".$testPKey."'");
$current_progress = mysqli_fetch_row($get_current_progress);


 echo $current_progress[0];



?>