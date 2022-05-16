<?php 
include "connect.php";

$iduser = $_POST['iduser'];
$diachi = $_POST['diachi'];
$sdt = $_POST['sodienthoai'];
$email = $_POST['email'];
$soluong = $_POST['soluong'];
$ghichu = $_POST['ghichu'];
$ngaydat = $_POST['ngaydat'];
$tongtien = $_POST['tongtien'];

$chitietdonhang = $_POST['chitietdonhang'];
	
$query = 'INSERT INTO `donhang`(`iduser`, `soluong`, `diachi`, `sodienthoai`, `email`, `ghichu`, `ngaydat`, `tongtien`) VALUES ("'.$iduser.'", "'.$soluong.'", "'.$diachi.'", "'.$sdt.'", "'.$email.'" ,"'.$ghichu.'", "'.$ngaydat.'" , "'.$tongtien.'")';

$data = mysqli_query($conn, $query);
if ($data == true) {
	$query = 'SELECT id AS iddonhang FROM `donhang` WHERE `iduser` = '.$iduser.'  ORDER BY id DESC LIMIT 1';
	$data = mysqli_query($conn, $query);
	while ($row = mysqli_fetch_assoc($data)) {
	$iddonhang = ($row);	
	}
	if (!empty($iddonhang)) {
		// co don hang
		$chitietdonhang = json_decode($chitietdonhang, true);
		// duyet qua tưng phan tu
		foreach ($chitietdonhang as $key => $value) {
			$truyvan = 'INSERT INTO `chitietdonhang`(`iddonhang`, `idsp`, `tensp`, `hinhsp`, `soluong`, `giasp`) VALUES ('.$iddonhang["iddonhang"].' , "'.$value["idsp"].'", "'.$value["tensp"].'", "'.$value["hinhsp"].'" , "'.$value["soluong"].'" , "'.$value["giasp"].'")';
		
			$data = mysqli_query($conn, $truyvan);
		}
		if ($data == true) {
			$arr = [
				'success' => true ,
				'message' => "thanh cong"
			];
		}else{
			$arr = [
				'success' => false ,
				'message' => "that bai"
			];
		}
			print_r(json_encode($arr));
		}	
	}else{
		// ko co don hang
		$arr = [
			'success' => false ,
			'message' => "that bai",
			
		];
		print_r(json_encode($arr));
	}




 ?>