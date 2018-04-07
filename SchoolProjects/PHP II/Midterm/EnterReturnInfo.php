<!DOCTYPE html 
  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta content="text/html;charset=utf-8" http-equiv="Content-Type">
<meta content="utf-8" http-equiv="encoding">
<title>Enter Return Information</title>
<script src="validation.js"></script>
<link rel="stylesheet" type="text/css" href="styles.css" >
</head>
<body>
<div id="body">
	<?php 
		include("nav.php");
	?>
	<div id="content">
		<h2>Return a Movie</h2>
		<hr />
		<?php 
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
					//The query to find all entries in the DB where returnDate is null, meaning the movie has not been returned yet
					$SQLQuery = "SELECT * FROM rentals WHERE returnDate IS NULL;";
					
					//running the query
					$notReturned = mysql_query($SQLQuery);
					
					//if the query failed
					if (!$notReturned){
						echo 'Could not run query: '.mysql_error();
						exit;
					}
					//if the query did not fail
					else {
						//creating the form so the user can edit the return date of a non-returned movie.
						echo '<form onsubmit="return enterReturnFormValidate()" action="EnterReturnInfoIntoDB.php" method="POST">'
							.'<table><tr><td><strong>Member ID*:</strong></td>'
							.'<td><input readonly type="text" id="memberID" name="memberID" value="'.$_POST["memberID"].'" /></td></tr>'
							.'<tr><td><strong>Movie ID*:</strong></td>'
							.'<td><input readonly type="text" id="movieID" name="movieID" value="'.$_POST["movieID"].'" /></td></tr>'
							.'<tr><td><strong>Checkout Date*: (YYYY-MM-DD)</strong></td>'
							.'<td><input readonly type="text" id="checkoutDate" name="checkoutDate" value="'.$_POST["checkoutDate"].'" /></td></tr>'
							.'<tr><td><strong>Return Date*: (YYYY-MM-DD)</strong></td>'
							.'<td><input type="text" id="returnDate" name="returnDate" value="" /></td></tr>'
							.'<tr><td><strong>Payment Method*: </strong></td>'
							.'<td><input readonly type="text" id="paymentMethod" name="paymentMethod" value="'.$_POST["paymentMethod"].'" /></td></tr>'
							.'<tr><td>* = required</td><td><input type="submit" value="Return Movie"/></td></tr></table></form>';
						
						if (mysql_num_rows($notReturned) > 0){
							//creating the table show the movies not yet returned
							echo '<table>'
								.'<tr>'
								.'<td><strong>MemberID</strong></td>'
								.'<td><strong>MovieID</strong></td>'
								.'<td><strong>Checkout Date</strong></td>'
								.'<td><strong>Return Date</strong></td>'
								.'<td><strong>Payment Method</strong></td>'
								.'<td>&nbsp;</td>';
								
							//the while loop that will run until it cannot fetch another array	
							while ($row = mysql_fetch_array($notReturned)){
								//the printing of each element of the non-returned movie
								echo '<form action="EnterReturnInfo.php" method="POST"><tr><td><input style="width:75px;" readonly type="text" name="memberID" value="'.$row[0].'" /></td>'
									.'<td><input style="width:75px;" readonly type="text" name="movieID" value="'.$row[1].'" /></td>'
									.'<td><input style="width:100px;" readonly type="text" name="checkoutDate" value="'.$row[2].'" /></td>'
									.'<td><input style="width:100px;" readonly type="text" name="returnDate" value="Not Returned" /></td>'
									.'<td><input style="width:120px;" readonly type="text" name="paymentMethod" value="'.$row[4].'" /></td>'
									.'<td><input type="submit" name="submit" value="Return This Movie"/></td></tr></form>';
							}
							echo '</table>'
								.'</form>';
						}
						else{
							echo '<p>All movies have been returned</p>';
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