<!DOCTYPE html 
  PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN"
  "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<title>PHP II Midterm - Claude Rogers III</title>
<link rel="stylesheet" type="text/css" href="styles.css" >
</head>
<body>
<div id="body">
	<?php 
		include("nav.php");
	?>
	<div id="content">
		<h2>Select Overdue Rental Information</h2>
		<hr />
		<p>Here is where one can view the overdue and late movies in the datebase.</p>
		<p><strong>Overdue Movies</strong> are movies that have not yet been returned, but have been checked out for more than 7 days.  Example, if somebody checks out a movie on 2015-02-01 (Feb 1st), the user has until 2015-02-08 (Feb 8th) to return the movie and not be late or overdue.</p>
		<p><strong>Late Movies</strong> are movies that were returned after the 7 day maximum.  Example, If someone checks out a movie on 2015-02-1 (Feb 1st), and then returned the movie on 2015-02-09 (Feb 9th) then that movie becomes late as it was returned after 7 days from checkout. </p>
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
					////if there is at least one element in returnDate that is Null, or if there is at least one movie not yet returned
					$OverdueQuery = 'SELECT * FROM rentals WHERE ((CURDATE() - checkoutDate > 7) AND (returnDate IS NULL));';
					$OverdueQueryResult = mysql_query($OverdueQuery);
					
					//checks if movie returned, was returned late (after 7 days)
					$LateQuery = 'SELECT * FROM rentals WHERE (returnDate - checkoutDate > 7);';
					$LateQueryResult = mysql_query($LateQuery);
					
					//If query cannot run
					if (!$OverdueQueryResult){
						echo "Could not run query: ".mysql_error();
					}
					//else, query ran
					else {
						//if there is at least one element in returnDate that is Null, or if there is at least one movie not yet returned
						if (mysql_num_rows($OverdueQueryResult)>0){
							echo '<hr><h3 style="margin-bottom:0px;"><u>Overdue Movies</u></h3>'.
								'<table style="BORDER-COLLAPSE: collapse" cellSpacing="0" cellPadding="3" border="1"><tr><td><strong>Member ID</strong></td>'.
								'<td><strong>Movie ID</strong></td>'.
								'<td><strong>Checkout Date</strong></td>'.
								'<td><strong>Return Date</strong></td>'.
								'<td><strong>Payment Method</strong></td></tr>';
							while ($row = mysql_fetch_array($OverdueQueryResult)){
								echo '<tr><td>'.$row['memberID'].'</td>'.
									'<td>'.$row['movieID'].'</td>'.
									'<td>'.$row['checkoutDate'].'</td>'.
									'<td>NOT RETURNED</td>'.
									'<td>'.$row['paymentMethod'].'</td></tr>';
							}
							echo '</table>';
						}
						else {
							echo '<hr><h3 style="margin-bottom:0px;"><u>Overdue Movies</u></h3><p>There are no movies currently checked out that are overdue</p>';
						}
					}
					
					//If query cannot run
					if (!$LateQueryResult){
						echo "Could not run query: ".mysql_error();
					}
					//else, query ran
					else {
						//if there is at least one element in returnDate that is Null, or if there is at least one movie not yet returned
						if (mysql_num_rows($LateQueryResult)>0){
							echo '<hr><h3 style="margin-bottom:0px;"><u>Late Movies</u></h3>'.
								'<table style="BORDER-COLLAPSE: collapse" cellSpacing="0" cellPadding="3" border="1"><tr><td><strong>Member ID</strong></td>'.
								'<td><strong>Movie ID</strong></td>'.
								'<td><strong>Checkout Date</strong></td>'.
								'<td><strong>Return Date</strong></td>'.
								'<td><strong>Payment Method</strong></td></tr>';
							while ($row = mysql_fetch_array($LateQueryResult)){
								echo '<tr><td>'.$row['memberID'].'</td>'.
									'<td>'.$row['movieID'].'</td>'.
									'<td>'.$row['checkoutDate'].'</td>'.
									'<td>'.$row['returnDate'].'</td>'.
									'<td>'.$row['paymentMethod'].'</td></tr>';
							}
							echo '</table>';
						}
						else {
							echo '<hr><h3 style="margin-bottom:0px;"><u>Late Movies</u></h3><p>There are no movies returned that have been returned late</p>';
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