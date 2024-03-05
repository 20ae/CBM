<?php
$db_con = mysqli_connect("localhost", "root", "990218");

if ($db_con){
#echo "DB 연결 성공<p>";
} else {
  echo "DB 연결 실패<p>";
}
  
$db_sec = mysqli_select_db($db_con, "cbm");
if ($db_sec) {
#echo "DB select OK <p>";
} else {
  echo "DB 연결 실패<p>";
}
?>