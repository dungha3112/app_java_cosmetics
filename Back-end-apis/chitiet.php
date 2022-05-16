<?php 
include "connect.php";
$total = 4;
$page = $_POST['page'];
$pos = ($page-1)* $total;

$loaisanpham = $_POST['loaisanpham'];

$query = 'SELECT * FROM `sanpham` WHERE `loaisanpham`= '.$loaisanpham.' LIMIT   '.$pos.' , '.$total.' 
	';
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
print_r(json_encode($arr)) ;
 ?>