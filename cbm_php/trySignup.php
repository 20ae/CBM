<?php
include_once 'dbConnection.php';

$name = $_POST["name"];
$id = $_POST["id"];
$group_type = (int)$_POST["group_type"];

$member_check = "select * from User where id = '".$id."' and group_type = '".$group_type."'";
$check_result = mysqli_query($db_con, $member_check);

if($check_result -> num_rows > 0){
  echo "-1";
}else{
  $member_signup = "insert into User (name, id, group_type) values ('".$name."', '".$id."', '".$group_type."')";
  $signup_result = mysqli_query($db_con, $member_signup);
  echo "1";
}
?>