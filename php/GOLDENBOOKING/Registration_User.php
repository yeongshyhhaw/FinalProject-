<?php
error_reporting(0);
include_once("dbconnect.php");
$email =$_POST["email"];
$name =$_POST["name"];
$password =$_POST["password"];
$passwordsha = sha1($password);
$phone = $_POST["phoneNumber"];



if (strlen($email) > 0){
    $sql = "INSERT INTO User(Email,Name, Password, PhoneNumber) 
    VALUES ('$email','$name','$passwordsha','$phone')";
    
    if ($conn->query($sql) === TRUE){
       echo "success";
    }else {
        echo "failed";
    }}
    else{
        echo"No Data";
    }

?>
