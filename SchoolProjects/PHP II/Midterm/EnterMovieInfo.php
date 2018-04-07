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
		<p>Like Enter Member Information, all of these have validation as well.<br /> Movie ID can only be numbers, and only 6 characters. <br />Movie title and category has character limit as well like the First Name and Last Name in the member info page. <br />Movie Value allows for $ in the value, and must be a number between 0 and 999. Decimals are allowed.<br />Copies in Store can only be numbers.<br/>All cannot be blank, and all trim spaces as well.</p>
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
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>