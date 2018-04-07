<!DOCTYPE html 
  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title>Enter Movie Information</title>
<script src="validation.js"></script>
<link rel="stylesheet" type="text/css" href="styles.css" >
</head>
<body>
<div id="body">
	<?php 
		include("nav.php");
	?>
	<div id="content">
		<h2>Enter Movie Information</h2>
		<hr />
		<form onsubmit="return enterMovFormValidate()" name="enterMovieInfo" id="enterMovieInfo" method="POST" action="EnterMovieInfoIntoDB.php">
		<table name="movieInfoTable" id="movieInfoTable" >
			<tr>
				<td><strong>Movie ID: </strong></td>
				<td><input type="text" name="movieID" id="movieID" /></td>
			</tr>
			<tr>
				<td><strong>Movie Title: </strong></td>
				<td><input type="text" name="movieTitle" id="movieTitle" /></td>
			</tr>
			<tr>
				<td><strong>Movie Category: </strong></td>
				<td><input type="text" name="movieCategory" id="movieCategory" /></td>
			</tr>
			<tr>
				<td><strong>Movie Value:</strong></td>
				<td><input type="text" name="movieValue" id="movieValue" /></td>
			</tr>
			<tr>
				<td><strong>Copies in Store: </strong></td>
				<td><input type="text" name="copiesInStore" id="copiesInStore" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Submit Form" name="submitForm" id="submitForm" /> <input type="reset" value="Reset Form" />
		</table>
		</form>
		<?php 
			$movieID = trim($_POST["movieID"]);
			$movieTitle = trim(ucfirst(strtolower($_POST["movieTitle"])));
			$movieValue = trim(str_replace("$", "", $_POST["movieValue"]));
			$copiesInStore = trim($_POST["copiesInStore"]);
			$movieCategory = trim($_POST["movieCategory"]);

			//sets up the DB connection using localhost, root, and no password
			$DBConnect = @mysql_connect("localhost", "root", "");
			//if connection is false, print out the error code and details
			if ($DBConnect === FALSE) {
				echo "<p>Unable to connect to the database server.</p><p>Error code ".mysql_errno().": ".mysql_error()."</p>";
			}
			//if connection is successful
			else {
				$DBName = "MovieRentalStore";
				if (!@mysql_select_db($DBName, $DBConnect)){
					echo "<p>There are no entries in the database!</p>";
				}
				else {
					$tableName = "movies";
					$SQLString = 'INSERT INTO '.$tableName.' VALUES(' .
					$movieID.', "'.$movieTitle.'", "'.$movieCategory.'", '.$movieValue.', '.$copiesInStore.');';
					$queryResult = mysql_query ($SQLString);
					if (!$queryResult){
						echo "Could not run query: " .mysql_error();
					}
					else {
					echo "<h4>Movie ID: $movieID successfully added to database</h4>";
					
					}
				
				}
			mysql_close($DBConnect);
			}
			
		?>
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>