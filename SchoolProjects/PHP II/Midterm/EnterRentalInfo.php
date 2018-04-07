<!DOCTYPE html 
  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title>Enter Rental Information</title>
<script src="validation.js"></script>
<link rel="stylesheet" type="text/css" href="styles.css" >
</head>
<body>
<div id="body">
	<?php 
		include("nav.php");
	?>
	<div id="content">
		<h2>Enter Rental Information</h2>
		<p>Member ID and Movie ID are limited and validated the same as the Movie Info and Member Info pages.<br/>Checkout Date and Return Date are formated to allow the (YYYY-MM-DD) format.<br/><strong>Return Date is NOT required</strong><br/>Payment Method is limited to a certain number of characters.<br/>Most cannot be blank, and all are trimmed.</p>
		<hr />
		<form onsubmit="return enterRentFormValidate()" name="enterRentForm" id="enterRentForm" method="POST" action="EnterRentalInfoIntoDB.php">
		<table name="movieInfoTable" id="movieInfoTable" >
			<tr>
				<td><strong>Member ID*: </strong></td>
				<td><input type="text" name="memberID" id="memberID" /></td>
			</tr>
			<tr>
				<td><strong>Movie ID*: </strong></td>
				<td><input type="text" name="movieID" id="movieID" /></td>
			</tr>
			<tr>
				<td><strong>Checkout Date*: (YYYY-MM-DD) </strong></td>
				<td><input type="text" name="checkoutDate" id="checkoutDate" /></td>
			</tr>
			<tr>
				<td><strong>Return Date: (YYYY-MM-DD) </strong></td>
				<td><input type="text" name="returnDate" id="returnDate" /></td>
			</tr>
			<tr>
				<td><strong>Payment Method*:</strong></td>
				<td><input type="text" name="paymentMethod" id="paymentMethod" /></td>
			</tr>
			<tr>
				<td>* = required;</td>
				<td><input type="submit" value="Submit Form" name="submitForm" id="submitForm" /> <input type="reset" value="Reset Form" />
		</table>
		</form>
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>