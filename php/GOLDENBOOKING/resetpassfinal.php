<?php
error_reporting(0);
include_once("dbconnect.php");
$email = $_POST['email'];
$oldpass = $_POST['key'];
$newpassword = sha1($_POST['newpass']);
 $sqlupdate = "UPDATE User SET Password = '$newpassword' WHERE Email = '$email' AND Password = '$oldpass'";
  if ($conn->query($sqlupdate) === TRUE){
        echo "<font color='green'><h2><br><br>SUCCESS. PLEASE LOGIN USING NEW PASSWORD</h2></font>";
  }else{
      echo "<font color='red'><h2><br><br>FAILED!!!</h2></font>";;
  }

 
?>