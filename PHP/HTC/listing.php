<?php
include('db_class.php'); 

$query = mysql_query("Select * from tbl_htc_records");
while($list = mysql_fetch_assoc($query))
{
	$listing_array['list'][] = $list;

}

echo json_encode($listing_array);

?>