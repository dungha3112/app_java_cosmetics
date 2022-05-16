<?php 
include "connect.php";
$query = "SELECT * FROM `loaisanpham`";
$data = mysqli_query($conn, $query);
$mangloaisp = array();
while ($row = mysqli_fetch_assoc($data)) {
	$mangloaisp[] = ($row);	
}
// kiem tra
if (!empty($mangloaisp)) {
	$arr = [
		'success' => true ,
		'message' => "thanh cong",
		'result'  => $mangloaisp	
	];	
}else {
	$arr = [
		'success' => false ,
		'message' => "that bai",
		'result'  => $mangloaisp	
	];	
}
echo json_encode($arr);
 ?>
 
