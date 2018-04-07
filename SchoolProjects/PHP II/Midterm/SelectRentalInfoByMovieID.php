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
		<h2>Select Rental Info by Movie ID</h2>
		<p>This will go through the rentals and select distinct movieIDs and put them in a dropdown list for the user to pick one and bring up all that movies's rentals.</p>
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
					$SQLQuery = "SELECT DISTINCT movieID FROM rentals;";
					$queryResult = mysql_query($SQLQuery);
					
					//if query is not successful
					if (!$queryResult){
						echo "Could not run query: " .mysql_error();
					}
					//else, the query is successful
					else {
						//testing is movieID has any values
						if (mysql_num_rows($queryResult)==0){
							//if movieID has no values
							echo "There are no movies in the database";
						}
						//else, movies are in database
						else {
							echo '<form method="POST" action="#">';
							echo '<table><tr><td><strong>MovieID:</strong></td>';
							//printing out a droplist list for the user to select a movie from the list of available movieID's
							echo '<td><select name="MovieIDDropDown">';
							//a for loop that adds each value to a new array
							while ($row = mysql_fetch_array($queryResult)){
								$options[] = '<option value="'.$row['movieID'].'">'.$row['movieID'].'</option>';
							}
							//sorts the options array to natural order
							natsort($options);
							//printing out the options
							foreach ($options as $value){
								echo $options[$value];
							}
							//closing the drop down select list
							echo '</select></td></tr>';
							echo '<tr><td>&nbsp;</td>';
							echo '<td> <input type="submit" name="submit" value="Submit Form" /> </td></tr></table>';
							
							//testing if the button is selected, and if so display the information requested
							if(isset($_POST['submit'])){
								//setting $value to the option selected by the use
								$value = $_POST['MovieIDDropDown'];
								
								//query to select all info about a certain user
								$SQLQuery = 'SELECT * FROM rentals WHERE movieID = "'.$value.'";';
								$queryResult = mysql_query($SQLQuery);
								
								//if query is not successful
								if (!$queryResult){
									echo "Could not run query: " .mysql_error();
								}
								//else, the query is successful
								else {
									echo '<hr/><table style="BORDER-COLLAPSE: collapse" cellSpacing="0" cellPadding="3" border="1">'.
									'<tr><td><strong>Member ID </strong></td>'.
									'<td><strong>Movie ID</strong></td>'.
									'<td><strong>Checkout Date</strong></td>'.
									'<td><strong>Return Date</strong></td>'.
									'<td><strong>Payment Method</strong></td></tr>';
									while ($row = mysql_fetch_array($queryResult)){
										echo '<tr><td>'.$row[0].'</td>'.
											'<td>'.$row[1].'</td>'.
											'<td>'.$row[2].'</td>'.
											'<td>'.$row[3].'</td>'.
											'<td>'.$row[4].'</td></tr>';
									}
								}
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