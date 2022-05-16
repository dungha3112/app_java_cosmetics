<?php 
include "connect.php";
$email = $_POST['email'];
$pass = $_POST['pass'];

$query = 'SELECT * FROM `user` WHERE `email` = "'.$email.'" AND `pass` = "'.$pass.'" ';
$data = mysqli_query($conn, $query);
$result = array();
while ($row = mysqli_fetch_assoc($data)) {
	$result[] = ($row);	
}
// kiem tra
if (!empty($result)) {
	$arr = [
		'success' => true ,
		'message' => "thành công",
		'result'  => $result[0]
	];	
}else {
	$arr = [
		'success' => false ,
		'message' => "thất bại",
		//'result'  => $result	
	];	
}
echo json_encode($arr);
 ?>
 
