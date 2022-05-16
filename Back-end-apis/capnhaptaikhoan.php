<?php 
include "connect.php";

$email = $_POST['email'];
$pass = $_POST['pass'];
$username = $_POST['username'];
$sodienthoai = $_POST['sodienthoai'];
$diachi = $_POST['diachi'];
//$id = $_POST['id'];
//check data
// $query = 'SELECT * FROM `user` WHERE `email`= "'.$email.'" ';
// $data = mysqli_query($conn, $query);
// $numrow  = mysqli_num_rows($data);
// if ($numrow ==1) {
// 	$arr = [
// 		'success' => false ,
// 		'message' => "email đã tồn tại",	
// 		//'result'  => $mangloaisp	
// 	];
// }else{
	// update
	$query1 = 'UPDATE `user` SET  `pass`="'.$pass.'",`username`="'.$username.'",`sodienthoai`="'.$sodienthoai.'",`diachi`="'.$diachi.'" WHERE email="'.$email.'" ';
	$data1 = mysqli_query($conn, $query1);


	// kiem tra
	if ($data1 == true) {
		$arr = [
			'success' => true ,
			'message' => "thanh cong",
			// 'result'  => $mangloaisp	
		];	
	}else {
		$arr = [
			'success' => false ,
			'message' => "that bai",
			//'result'  => $mangloaisp	
		];	
	}


print_r(json_encode($arr)) ;
 ?>