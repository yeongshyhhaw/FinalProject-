<?php
error_reporting(0);
include_once("dbconnect.php");
$email = $_POST['userid'];

$sql = "SELECT * FROM Ordered WHERE Email = '$email'";
$result = $conn->query($sql);
if ($result->num_rows > 0) {
    $response["history"] = array();
    while ($row = $result ->fetch_assoc()){
        $histlist = array();
        $histlist[orderid] = $row["orderid"];
        $histlist[total] = $row["total"];
        $histlist[fromDate] = $row["fromDate"];
        $histlist[toDate] = $row["toDate"];
        $histlist[HomeStayName] = $row["HomeStayName"];
        array_push($response["history"], $histlist);
    }
    echo json_encode($response);
}else{
    echo "nodata". mysqli_error($conn);
}
?>