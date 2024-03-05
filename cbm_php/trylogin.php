<?php
include_once 'DBConnection.php';

$id = $_POST["id"];
$pwd = $_POST["pwd"];

$try_login = "select * from user where name ='".$id."' and id ='" .$pwd."'";
$login_result = mysqli_query($db_con, $try_login);

if($login_result -> num_rows > 0){
  $member = mysqli_fetch_row($login_result);
  echo $member[0];
} else {
  echo "-1";
}

?>