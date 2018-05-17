<?php
include('db_class.php'); 

/*$_REQUEST['date'] = 'date';
$_REQUEST['time'] = 'time';
$_REQUEST['bg'] = 'bg';
$_REQUEST['cp'] = 'cp';
$_REQUEST['qa'] = 'qa';
$_REQUEST['bi'] = 'bi';
$_REQUEST['comments'] = 'comments';*/
	
if(count($_REQUEST) >= '7')
{
	$data = $_REQUEST;
	$query = mysql_query("Insert INTO tbl_htc_records (`htc_date`, `htc_time`, `htc_bg`, `htc_cp`, `htc_qa`, `htc_bi`, `htc_comments`) VALUES ('".$_REQUEST['date']."','".$_REQUEST['time']."','".$_REQUEST['bg']."','".$_REQUEST['cp']."','".$_REQUEST['qa']."','".$_REQUEST['bi']."','".$_REQUEST['comments']."')");
	
	if (!$query)
	{
		$result_query = array('msg'=>'false');
	}
	else
	{
		$result_query = array('msg'=>'true');
	}
	echo json_encode($result_query);
	
}
else
{
$result_query = array('msg'=>'false');
	echo json_encode($result_query);
}
?>
