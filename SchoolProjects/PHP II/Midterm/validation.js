function enterMemFormValidate() {
	//The string that contains the errors in the script
	var errorString = ""; 
	
	//trimming the memberID after declaring the var
	var memberID = document.getElementById("memberID").value;
	memberID = memberID.trim();
	
	//trimming the firstName after declaring the var
	var firstName = document.getElementById("firstName").value;
	firstName = firstName.trim();
	
	//trimming the lastName after declaring the var
	var lastName = document.getElementById("lastName").value;
	lastName = lastName.trim();
	
	//trimming and replacing dashes in the licenseNumber after declaring the var
	var licenseNumber = document.getElementById("licenseNumber").value;
	licenseNumber = licenseNumber.replace(/-/g, "").trim();
	
	//trimming the licenseState after declaring the var
	var licenseState = document.getElementById("licenseState").value;
	licenseState = licenseState.trim();
	
	//trimming the email after declaring the var
	var email = document.getElementById("email").value;
	email = email.trim();
	
	//the formatting for a valid email
	var mailformat = /^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/;
	
	//Checks if memberID is empty
	if (memberID !== ""){ 
		//Checks if memberID is not a number or if the length is not 6
		if ((isNaN(memberID) == true) || (memberID.length != 6)){
			errorString +="Member ID field must only be numbers, and 6 characters long. \n"
		}
	}
	//if the memberID is not empty, is a number, and is 6 characters in length
	else {
		errorString += "Member ID field cannot be blank. \n";
	}
	
	//checks if firstName is empty
	if (firstName !== "") {
		//checks if firstName is larger than 20 characters
		if (firstName.length > 20) {
			errorString += "First Name field length cannot be longer than 20 characters. \n";
		}
	}
	//if firstName is not empty nor larger than 20 characters
	else {
		errorString += "First Name field cannot be blank. \n";
	}
	
	//checks if lastName is empty
	if (lastName !== "") {
		//checks if lastName is larger than 20 characters
		if (lastName.length > 20) {
			errorString += "Last Name field length cannot be longer than 20 characters. \n";
		}
	}
	//if lastName is not empty nor larger than 20 characters
	else {
		errorString += "Last Name field cannot be blank. \n";
	}
	
	//if licenseNumber is not empty
	if (licenseNumber !== "") {
		//if licenseNumber length is not 9
		if (licenseNumber.length != 9) {
			errorString += "Driver's License Number field must be 9 characters. \n";
		}
	}
	//if licenseNumber is empty
	else {
		errorString += "Driver's License Number field cannot be blank. \n";
	}
	
	//if licenseState is not empty
	if (licenseState !== ""){
		//if licenseState length is not 2
		if (licenseState.length != 2){
			errorString += "Driver's License State field length cannot be longer than 2 characters. \n";
		}
	}
	//if licenseState is empty
	else {
		errorString += "Driver's License State field cannot be blank. \n";
	}
	
	//if email is not empty
	if (email !== ""){
		//if email is not valid email
		if (email.match(mailformat) === null){
			errorString += "Email Address field contains an invalid email. \n";
		}
	}
	//if email is empty
	else {
		errorString += "Email Address field cannot be blank. \n";
	}
	
	//if errorString is not empty, print out string and do not submit
	if (errorString !== ""){
		alert(errorString);
		return false;
	}
	//if errorString is empty, submit
	else {
		return true;
	}
}

function enterMovFormValidate() {
	//The string that contains the errors in the script
	var errorString = ""; 
	
	var numberFormat = /^\d{0,3}(\.\d{0,2}){0,1}$/;
	
	//trimming the movieID after declaring the var
	var movieID = document.getElementById("movieID").value;
	movieID = movieID.trim();
	
	//trimming the movieTitle after declaring the var
	var movieTitle = document.getElementById("movieTitle").value;
	movieTitle = movieTitle.trim();
	
	//trimming the movieValue after declaring the var
	var movieValue = document.getElementById("movieValue").value;
	movieValue = movieValue.replace("$", "").trim();
	
	//trimming the copiesInStore after declaring the var
	var copiesInStore = document.getElementById("copiesInStore").value;
	copiesInStore = copiesInStore.trim();
	
	//trimming the movieCategory after declaring the var
	var movieCategory = document.getElementById("movieCategory").value;
	movieCategory = movieCategory.trim();
	
	//Checks if movieID is empty
	if (movieID !== ""){ 
		//Checks if movieID is not a number or if the length is not 6
		if ((isNaN(movieID) == true) || (movieID.length != 6)){
			errorString +="Movie ID field must only be numbers, and 6 characters long. \n"
		}
	}
	//if the movieID is not empty, is a number, and is 6 characters in length
	else {
		errorString += "Movie ID field cannot be blank. \n";
	}
	
	//checks if movieTitle is empty
	if (movieTitle !== "") {
		//checks if movieTitle length is greater than 50
		if (movieTitle.length > 50){
			errorString += "Movie Title field length cannot be greater than 50 characters. \n";
		}
	}
	//if movieTitle is empty
	else {
		errorString += "Movie Title field cannot be blank. \n";
	}
	
	//checks if movieValue is empty
	if (movieValue !== "") {
		//if movieValue is not a number, or has more than 2 decimal places, or larger than the parameters set
		if ((isNaN(movieValue) == true) || (!movieValue.match(numberFormat))){
			errorString += "Movie Value field must be a number between $0 and $999, with only two decimal places. \n";
		}
	}
	//if movieValue is empty
	else {
		errorString += "Movie Value field cannot be blank. \n";
	} 
	
	//checks if copiesInStore is empty
	if (copiesInStore !== ""){
		//if copiesInStore is not a number, or the length is too great
		if ((isNaN(copiesInStore) == true) || (copiesInStore.length > 2)){
			errorString += "Copies In Store field must be a number, and have a length of 2 or less. \n";
		}
	}
	//if copiesInStore is empty
	else {
		errorString += "Copies in Store field must not be blank. \n";
	}
	
	//checks if movieCategory is empty
	if (movieCategory !== ""){
		//if length is greater than 30
		if(movieCategory.length > 30) {
			errorString += "Movie Category field length can only be up to 30 characters. \n ";
		}
	}
	// if movieCategory is empty
	else {
		errorString += "Movie Category field cannot be blank. \n";
	}
	
	//if errorString is not empty, print out string and do not submit
	if (errorString !== ""){
		alert(errorString);
		return false;
	}
	//if errorString is empty, submit
	else {
		return true;
	}
}

function enterRentFormValidate() {
	//The string that contains the errors in the script
	var errorString = ""; 
	
	//trimming the memberID after declaring the var
	var memberID = document.getElementById("memberID").value;
	memberID = memberID.trim();
	
	//trimming the movieID after declaring the var
	var movieID = document.getElementById("movieID").value;
	movieID = movieID.trim();
	
	//trimming the paymentMethod after declaring the var
	var paymentMethod = document.getElementById("paymentMethod").value;
	paymentMethod = paymentMethod.trim();
	
	//regular expression for the date formatting 
	var dateFormat =  /^\d{4}\-\d{2}\-\d{2}$/;
	
	//trimming the checkoutDate after declaring the var
	var checkoutDate = document.getElementById("checkoutDate").value;
	checkoutDate = checkoutDate.trim();
	
	//trimming the returnDate after declaring the var
	var returnDate = document.getElementById("returnDate").value;
	returnDate = returnDate.trim();
	
	//Creating a Date object to hold the dates to later compare and see if returnDate is later than checkoutDate, as a user cannot return a movie before checking out said movie
	var retDate = new Date(returnDate.split("/"));
	var checkDate = new Date(checkoutDate.split("/"));
	
	//Checks if movieID is empty
	if (movieID !== ""){ 
		//Checks if movieID is not a number or if the length is not 6
		if ((isNaN(movieID) == true) || (movieID.length != 6)){
			errorString +="Movie ID field must only be numbers, and 6 characters long. \n"
		}
	}
	//if the movieID is not empty, is a number, and is 6 characters in length
	else {
		errorString += "Movie ID field cannot be blank. \n";
	}
	
	//Checks if memberID is empty
	if (memberID !== ""){ 
		//Checks if memberID is not a number or if the length is not 6
		if ((isNaN(memberID) == true) || (memberID.length != 6)){
			errorString +="Member ID field must only be numbers, and 6 characters long. \n"
		}
	}
	//if the memberID is not empty, is a number, and is 6 characters in length
	else {
		errorString += "Member ID field cannot be blank. \n";
	}
	
	//Checks if paymentMethod is empty
	if (paymentMethod !== ""){
		//Checks if paymentMethod length is greater than 15 characters
		if (paymentMethod.length > 15){
			errorString += "Payment Method field length cannot be greater than 15 characters. \n";
		}
	}
	//if paymentMethod is empty
	else {
		errorString += "Payment Method field cannot be blank. \n";
	}
	
	//checks if checkout date is empty
	if (checkoutDate !== ""){
		//if date format doesn't match
		if (!checkoutDate.match(dateFormat)){
			errorString += "Checkout Date field must follow the YYYY-MM-DD date formatting. \n";
		}
	}
	//if checkout date is empty
	else {
		errorString += "Checkout Date field cannot be blank. \n";
	}
	
	//checks if return date matches the formatting.  This doesn't check if returnDate is empty as returnDate can be null or empty because when a movie is checked out, it has not been returned yet.
	if (returnDate !== ""){
		if (!returnDate.match(dateFormat)){
			errorString += "Return Date field must follow the YYYY-MM-DD date formatting. \n";
		} 
		//If return date is not empty, then must check if return date is after checkout date. If return date is not after checkout date, throw and error in the errorstring
		if (checkDate > retDate){
			errorString += "Return Date field must be a date after the value entered in Checkout Date field. \n";
		}
	}

	//if errorString is not empty, print out string and do not submit
	if (errorString !== ""){
		alert(errorString);
		return false;
	}
	//if errorString is empty, submit
	else {
		return true;
	}
}

function enterReturnFormValidate() {
	//The string that contains the errors in the script
	var errorString = ""; 
	
	//regular expression for the date formatting 
	var dateFormat =  /^\d{4}\-\d{2}\-\d{2}$/;
	
	//trimming the checkoutDate after declaring the var
	var checkoutDate = document.getElementById("checkoutDate").value;
	checkoutDate = checkoutDate.trim();
	
	//trimming the returnDate after declaring the var
	var returnDate = document.getElementById("returnDate").value;
	returnDate = returnDate.trim();
	
	//Creating a Date object to hold the dates to later compare and see if returnDate is later than checkoutDate, as a user cannot return a movie before checking out said movie
	var retDate = new Date(returnDate.split("-"));
	var checkDate = new Date(checkoutDate.split("-"));
	
	//checks if return date matches the formatting.  This doesn't check if returnDate is empty as returnDate can be null or empty because when a movie is checked out, it has not been returned yet.
	if (returnDate !== ""){
		if (!returnDate.match(dateFormat)){
			errorString += "Return Date field must follow the YYYY-MM-DD date formatting. \n";
		} 
		//If return date is not empty, then must check if return date is after checkout date. If return date is not after checkout date, throw and error in the errorstring
		if (checkDate > retDate){
			errorString += "Return Date field must be a date after the value entered in Checkout Date field. \n";
		}
	}
	else {
		errorString += "Return Date field must not be empty. \n";
	}

	//if errorString is not empty, print out string and do not submit
	if (errorString !== ""){
		alert(errorString);
		return false;
	}
	//if errorString is empty, submit
	else {
		return true;
	}
}
