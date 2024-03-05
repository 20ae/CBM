<?php
include_once 'dbConnection.php';

$tpkey = $_POST["tpkey"];

$testlog_check = "select * from TestLog where TestPkey = '".$tpkey."' and status =1";
$check_result = mysqli_query($db_con, $testlog_check);

if($check_result -> num_rows == 0){
  $testlog_insert = "insert into TestLog (TestPkey,  status, insertDate,currentProgress) values('".$tpkey."', 1, now(),0)";
  $insert_result = mysqli_query($db_con, $testlog_insert);
  $get_testlog_pkey = mysqli_query($db_con, "select Pkey from TestLog where TestPkey='".$tpkey."' order by insertdate desc limit 1"); 
  $testlog_pkey = mysqli_fetch_row($get_testlog_pkey);
  echo $testlog_pkey[0];
}
else{
$get_testlog_pkey = mysqli_query($db_con, "select Pkey from TestLog where TestPkey='".$tpkey."' and status =1 order by insertdate desc limit 1"); 
$testlog_pkey = mysqli_fetch_row($get_testlog_pkey);
echo $testlog_pkey[0];
}

?>
