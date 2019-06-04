<?php
error_reporting(0);
include_once("dbconnect.php");
$location = $_POST['HSLOCATION'];
$checkin = date('Y-m-d', strtotime($_POST['checkIn']));
 $checkout = date('Y-m-d', strtotime($_POST['checkOut']));
if (strcasecmp($location, "null") == 0)
{

 $sql = "SELECT HomeStay.HomeStayID,HomeStay.HomeStayName,HomeStay.HomeStayPhone,HomeStay.HomeStayAddress,HomeStay.Location,HomeStay.description,HomeStay.price FROM HomeStay,Ordered WHERE !(Ordered.HomeStayID=HomeStay.HomeStayID AND Ordered.fromDate<='$checkin' AND Ordered.toDate>='$checkout' ) ";
}
else
{
     $sql = "SELECT HomeStay.HomeStayID,HomeStay.HomeStayName,HomeStay.HomeStayPhone,HomeStay.HomeStayAddress,HomeStay.Location,HomeStay.description,HomeStay.price FROM HomeStay,Ordered WHERE HomeStay.Location=$location AND !(Ordered.HomeStayID=HomeStay.HomeStayID AND Ordered.fromDate<='$checkin' AND Ordered.toDate>='$checkout' ) ";
}


$result = $conn->query($sql);
if ($result->num_rows > 0) {
 $response["homestay"] = array();
  while ($row = $result ->fetch_assoc()){
    $HomeStayList = array();
    $HomeStayList[HSID] = $row["HomeStayID"];
    $HomeStayList[HSNAME] = $row["HomeStayName"];
    $HomeStayList[HSPHONE] = $row["HomeStayPhone"];
    $HomeStayList[HSADDRESS] = $row["HomeStayAddress"];
    $HomeStayList[HSLOCATION] = $row["Location"];
    $HomeStayList[HSDESCRIPTION] = $row["description"];
    $HomeStayList[HSPRICE] = $row["price"];
   
     array_push($response["homestay"], $HomeStayList);
 
   }
    
echo json_encode($response);
}
else{
    echo "nodata";
}
?>