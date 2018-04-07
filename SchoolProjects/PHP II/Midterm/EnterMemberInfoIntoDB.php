<!DOCTYPE html 
  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title>Enter Member Information</title>
<script src="validation.js"></script>
<link rel="stylesheet" type="text/css" href="styles.css" >
</head>
<body>
<div id="body">
	<?php 
		include("nav.php");
	?>
	<div id="content">
		<h2>Enter Member Information</h2>
		<hr />
		<form onsubmit="return enterMemFormValidate()" name="enterMemberInfo" id="enterMemberInfo" method="POST" action="EnterMemberInfoIntoDB.php">
		<table name="memberInfoTable" id="memberInfoTable" >
			<tr>
				<td><strong>Member ID: </strong></td>
				<td><input type="text" name="memberID" id="memberID" /></td>
			</tr>
			<tr>
				<td><strong>First Name: </strong></td>
				<td><input type="text" name="firstName" id="firstName" /></td>
			</tr>
			<tr>
				<td><strong>Last Name:</strong></td>
				<td><input type="text" name="lastName" id="lastName" /></td>
			</tr>
			<tr>
				<td><strong>Driver's License Number: </strong></td>
				<td><input type="text" name="licenseNumber" id="licenseNumber" /></td>
			</tr>
			<tr>
				<td><strong>Driver's License State: </strong></td>
				<td><input type="text" name="licenseState" id="licenseState" /></td>
			</tr>
			<tr>
				<td><strong>Email Address: </strong></td>
				<td><input type="text" name="email" id="email" /></td>
			</tr>
			<tr>
				<td>&nbsp;</td>
				<td><input type="submit" value="Submit Form" name="submitForm" id="submitForm" /> <input type="reset" value="Reset Form" />
		</table>
		</form>
		<?php 
			$memberID = trim($_POST["memberID"]);
			$firstName = trim(ucfirst(strtolower($_POST["firstName"])));
			$lastName = trim(ucfirst(strtolower($_POST["lastName"])));
			$licenseNumber = trim(str_replace("-", "", $_POST["licenseNumber"]));
			$licenseState = trim(strtoupper($_POST["licenseState"]));
			$email = trim($_POST["email"]);

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
					$tableName = "members";
					$SQLString = 'INSERT INTO '.$tableName.' VALUES(' .
					$memberID.', "'.$lastName.'", "'.$firstName.'", "'.$licenseNumber.'", "'.
					$licenseState.'", "'.$email.'");';
					$queryResult = mysql_query ($SQLString);
					if (!$queryResult){
						echo "Could not run query: " .mysql_error();
					}
					else {
						echo "<h4>Member ID: $memberID successfully added to database</h4>";
					}
				}
			mysql_close($DBConnect);	
			}
		?>
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>