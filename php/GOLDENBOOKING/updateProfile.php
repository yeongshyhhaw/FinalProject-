<?php
error_reporting(0);
include_once("dbconnect.php");
$email = $_POST['email'];
$oldpassword = sha1($_POST['opassword']);
$newpassword = sha1($_POST['npassword']);
$phone = $_POST['phone'];
$name = $_POST['name'];


$sqlcheck = "SELECT * FROM User WHERE PhoneNumber = '$phone' AND Password = '$oldpassword'";
$result = $conn->query($sqlcheck);
if ($result->num_rows > 0) {
$sqlupdate = "UPDATE User SET PhoneNumber = '$phone', Password = '$newpassword', Name = '$name' WHERE Email = '$email' AND Password = '$oldpassword'";

  if ($conn->query($sqlupdate) === TRUE){
        echo 'success';
  }else{
      echo 'failed';
  }   
}else{
    echo "failed";
}

 
?>