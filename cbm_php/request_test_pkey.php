<?php
include_once 'dbConnection.php';

$pkey = $_POST["pkey"];

$test_check = "select * from Test where userPkey = '".$pkey."'";
$check_result = mysqli_query($db_con, $test_check);

if($check_result -> num_rows == 0){
  $test_insert = "insert into Test (userPkey, type, status, insertDate) values('".$pkey."', 1, 1, now())";
  $insert_result = mysqli_query($db_con, $test_insert);
}

$get_test_pkey = mysqli_query($db_con, "select * from Test where userPkey = '".$pkey."'");
$test_pkey = mysqli_fetch_row($get_test_pkey);
echo $test_pkey[0];

?>