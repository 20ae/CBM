<?php
include_once 'dbConnection.php';

$Pkey = (int)$_POST["Pkey"];
$TestPkey = (int)$_POST["TestPkey"];
$count = (int)$_POST["Count"];
$value = (int)$_POST["Value"];
$value2 = (int)$_POST["Value2"];
$selfCheck5Text = $_POST["SelfCheck5Text"];
$mysqli_check = "select * from TestLog where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
$mysqli_result = mysqli_query($db_con, $mysqli_check);

if(($mysqli_result)&&($mysqli_result -> num_rows > 0)&&($count>=2)){
	
		if($count == 2){
			$update_check = "update TestLog SET SelfCheck1 = " .$value. ",SelfCheck2 = " .$value2. ", UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
			$update_result = mysqli_query($db_con,$update_check);
			echo $update_result;
		}
		else if($count == 3){
			$update_check = "update TestLog SET SelfCheck" . $count  ." = " .$value. ", UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
			$update_result = mysqli_query($db_con,$update_check);
			echo $update_result;
		}
		else if($count == 4){
			$update_check = "update TestLog SET SelfCheck" . $count  ." = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
			$update_result = mysqli_query($db_con,$update_check);
			echo $update_result;
			
		}
		else if($count==5){

			if($value&32){
				$update_check = "update TestLog SET SelfCheck5 = '".$value."',SelfCheck5Text = '".$selfCheck5Text."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
				$update_result = mysqli_query($db_con,$update_check);
				echo $update_result;
			}
			else{
				$update_check = "update TestLog SET SelfCheck5 = '".$value."',SelfCheck5Text = NULL, UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
				$update_result = mysqli_query($db_con,$update_check);
				echo $update_result;
			}
			
		}
		else{
			switch($count){
				case 6:
					$update_check = "update TestLog SET DepressionLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 7:
					$update_check = "update TestLog SET SadLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 8:
					$update_check = "update TestLog SET AnxietyLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 9:
					$update_check = "update TestLog SET FearLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 10:
					$update_check = "update TestLog SET LonelyLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 11:
					$update_check = "update TestLog SET HurtLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 12:
					$update_check = "update TestLog SET SelfAngryLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 13:
					$update_check = "update TestLog SET OtherAngryLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				case 14:
					$update_check = "update TestLog SET ShamefulLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;
				default:
					$update_check = "update TestLog SET VainLevel = '".$value."', UpdateDate = now() where PKey ='".$Pkey."' and TestPKey = '".$TestPkey."'";
					$update_result = mysqli_query($db_con,$update_check);
					echo $update_result;
				break;

			}
		}
	 
} else {
  echo "-1";
}

?>