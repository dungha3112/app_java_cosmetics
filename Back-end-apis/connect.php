 <?php 
$conn = mysqli_connect("localhost", //Sever chứa DB
 "root",             //User Admin(mặc định là root)
 "",                //Pass Admin
 "mypham");               //Tên DB
mysqli_query($conn, "SET NAMES 'utf8'");

if ($conn) {
	//echo "connect success";
}else{
	//echo "connect failure";
}


 ?>