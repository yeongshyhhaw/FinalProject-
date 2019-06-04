<?php
error_reporting(0);
include_once("dbconnect.php");
$userid = $_POST['email'];
$sql = "SELECT * FROM User WHERE Email = '$userid'";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["user"] = array();
    while ($row = $result ->fetch_assoc()){
        $userarray = array();
        $userarray[name] = $row["Name"];
        $userarray[location] = $row["Location"];
        $userarray[latitude] = $row["Latitude"];
         array_push($response["user"], $userarray);
    }
    echo json_encode($response);
}

?>