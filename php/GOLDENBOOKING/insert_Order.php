<?php
error_reporting(0);
include_once("dbconnect.php");
$email = $_POST["email"];
$phone = $_POST["PhoneNumber"];
$orderid = $_POST['orderid'];
$total = $_POST['total'];
$from = $_POST['from'];
$ID = $_POST['HomeStayID'];
$name = $_POST['HomeStayName'];
$to = $_POST['to'];
$sql ="INSERT INTO Ordered(orderid,total,fromDate,toDate,PhoneNumber,Email,HomeStayID,HomeStayName) VALUES ('$orderid','$total','$from','$to','$phone','$email','$ID','$name')"; 
    if ($conn->query($sql) == TRUE){
        echo "success";
    }else {
        echo "failed:". mysqli_error($conn);
    } 
?>
