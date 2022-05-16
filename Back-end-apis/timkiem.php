<?php 
include "connect.php";
$search = $_POST['search'];


$query = "SELECT * FROM `sanpham` WHERE `tensanpham` LIKE '%".$search."%' ";
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);	
}
// kiem tra
if (!empty($result)) {
	$arr = [
		'success' => true ,
		'message' => "thanh cong",
		'result'  => $result	
	];	
}else {
	$arr = [
		'success' => false ,
		'message' => "that bai",
		'result'  => $result	
	];	
}
print_r(json_encode($arr)) ;
 ?>