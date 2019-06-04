<?php
error_reporting(0);
include_once("dbconnect.php");
$location = $_POST['HSLOCATION'];

if (strcasecmp($location, "null") == 0)
{
   $sql = "SELECT * FROM HomeStay"; 
}
  
               
else{
   $sql = "SELECT * FROM HomeStay WHERE Location='$location'";
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