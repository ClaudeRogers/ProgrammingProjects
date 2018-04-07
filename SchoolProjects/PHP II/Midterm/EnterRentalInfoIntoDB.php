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
				<td>* = required</td>
				<td><input type="submit" value="Submit Form" name="submitForm" id="submitForm" /> <input type="reset" value="Reset Form" />
		</table>
		</form>
		<?php 
			$movieID = trim($_POST["movieID"]);
			$memberID = trim($_POST["memberID"]);
			$paymentMethod = trim($_POST["paymentMethod"]);
			$checkoutDate = trim($_POST["checkoutDate"]);
			$returnDate = trim($_POST["returnDate"]);

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
					//query to see if memberID or movieID are currently in the database
					$memberIDSQLQuery = mysql_query("SELECT memberID FROM members WHERE memberID='$memberID';");
					$movieIDSQLQuery = mysql_query("SELECT movieID FROM movies WHERE movieID='$movieID';");
					
					//if query cannot run, tell user to try again
					if(!$memberIDSQLQuery || !$movieIDSQLQuery){
						echo "Could not check if memberID or movieID were in database".mysql_error().
						"<br>Please referesh the page and try again";
					}
					
					//fetch the number of rows that has the memberID or movieID, if they are 0, tell user to input a valid member or movie ID as you cannot rent what doesn't exist, or a non-member cannot rent from the store
					if ((mysql_num_rows($memberIDSQLQuery)==0) || (mysql_num_rows($movieIDSQLQuery)==0)){
						//if moviesID doesn't exist in database
						if (mysql_num_rows($movieIDSQLQuery)==0)
							echo "Movie ID is not in Movies Table, therefor cannot be rented. <br>";
						//if membersID doesn't exist in database
						if (mysql_num_rows($memberIDSQLQuery)==0)
							echo "Member ID is not in Members Table, therefor they cannot rent a movie. <br>";				
					}
					//This is else moviesID and membersID DO exist in the database
					else {
						//if $returnDate is not entered, so if something is only checked out
						if ($returnDate == null){
							//The query to find out how many copies are in store
							$copiesQuery = 'SELECT copiesInStore FROM movies where movieID ="'.$movieID.'";';
							$copiesInStore = mysql_query($copiesQuery);
							
							if (!$copiesInStore){
								echo "Could not run query: " .mysql_error();
							}
							else{
								if (($copiesInStore ==0)||($copiesInStore < 0)){
									echo 'Cannot rent movie: '.$movieID.' because there are 0 copies in store.';
								}
								else {
									$tableName = "rentals";
									$SQLString = 'INSERT INTO '.$tableName.
									'(movieID, memberID, checkoutDate, paymentMethod)'.
									' VALUES(' . $movieID.', '.$memberID.', "'.$checkoutDate.'","'.$paymentMethod.'");';
									$queryResult = mysql_query ($SQLString);
									if (!$queryResult){
										echo "Could not run query: " .mysql_error();
									}
									else {
										echo "<h4>Member ID: $memberID and Movie ID: $movieID successfully added to database</h4>";
									}
									$UpdateQuery = 'UPDATE movies SET copiesInStore = (copiesInStore-1) WHERE movieID = "'.$movieID.'";';
									$queryResult = mysql_query($UpdateQuery);
									
									if (!$queryResult){
										echo "Could not run query: " .mysql_error();
									}
								}
							}
						}
						//else the return date is entered, therefor something being returned
						else {
							$tableName = "rentals";
							$SQLString = 'INSERT INTO '.$tableName.' VALUES(' .
							$memberID.', '.$movieID.', "'.$checkoutDate.'", "'.
							$returnDate.'", "'.$paymentMethod.'");';
							$queryResult = mysql_query ($SQLString);
							if (!$queryResult){
								echo "Could not run query: " .mysql_error();
							}
							else {
								echo "<h4>Member ID: $memberID and Movie ID: $movieID successfully added to database</h4>";
							}
						}
					}
				}
			mysql_close($DBConnect);
			}
		?>
	</div> <!-- content div -->
</div> <!-- body div -->
</body>
</html>