<?php
$errors = 0; //The amount of errors in the code
$SQLstring = "";
//sets up DB connection using localhost, root, and no password
$DBConnect = @mysql_connect("localhost", "root", "");
if ($DBConnect === FALSE){
	echo"<p>Unable to connect to the database server.</p><p>Error code " . mysql_errno() . ": " . mysql_error() . "</p>";
}
else {
	$DBName = "phpii_final_clauderogersiii";
	if (!@mysql_select_db($DBName, $DBConnect)){
		$SQLstring = "CREATE DATABASE $DBName;";
		$QueryResult = @mysql_query($SQLstring, $DBConnect);
		if ($QueryResult === FALSE){
			echo "<p>Unable to execute the query. </p><p>Error code " . mysql_errno($DBConnect) . ": " . mysql_error($DBConnect) . "</p>";
			++$errors;
		}
		if ($errors == 0){
			mysql_select_db($DBName, $DBConnect);
			
			$tableName = "i71_north";
			$SQLstring = "SHOW TABLES LIKE '$tableName'";

			$QueryResult = mysql_query($SQLstring, $DBConnect);
			if (mysql_num_rows($QueryResult) == 0){
				$SQLstring = "CREATE TABLE $tableName (date DATE NOT NULL, hour INT(2) NOT NULL, volume INT(5) NOT NULL, speed INT(3) NOT NULL, PRIMARY KEY(date, hour))";
				
				$QueryResult = @mysql_query($SQLstring, $DBConnect);
				if ($QueryResult === FALSE) {
					echo "<p>Unable to create table. </p><p>Error code ". mysql_errno($DBConnect) . ": " . mysql_error($DBConnect) . "</p>";
					++$errors;
				}
				
				if ($errors == 0){
					$SQLstring = "SELECT * FROM $tableName";
					$QueryResult = @mysql_query($SQLString, $DBConnect);
					if (@mysql_num_rows($QueryResult) == 0){
						$SQLstring = "LOAD DATA LOCAL INFILE 'I_71_North.txt' INTO TABLE i71_north";
						
						$QueryResult = @mysql_query($SQLstring, $DBConnect);
						if ($QueryResult === FALSE) {
							echo "<p>Unable to add values into table. </p><p>Error code ". mysql_errno($DBConnect) . ": " . mysql_error($DBConnect) . "</p>";
							++$errors;
						}
					}
				}
			}
		}
	}		
}
?>