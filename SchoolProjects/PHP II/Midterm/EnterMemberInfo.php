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
		<p>Here I have put in full validation for the memberID field can only be 6 characters and only numbers. <br />First name and Last name has validation where it can only be up to 20 characters and that is because the VarChar in the database is up to 20.<br />Driver's License allows for a 9 character number, and it will also strip slashes so if a user types 111-111-111 or 111111111 both will be accepted. <br />State only allows 2 letters.<br />Email has validation to only allow legitimate email formatting.<br />All fiels also trim the spaces, and cannot be blank.  Feel free to visit the validation.js to view all the validation code.</p>
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
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>