<?php
 define("DB_PLATFORM","htc");
 define("DB_SERVER","localhost");
 define("DB_USER","root");
 define("DB_PASSWORD","");
  define("DB_PCONNECT",true);

$phpmyadmin_connect = mysql_connect(DB_SERVER,DB_USER,DB_PASSWORD);
$db_connect = mysql_select_db(DB_PLATFORM,$phpmyadmin_connect);
?>