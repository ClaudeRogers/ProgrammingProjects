<?php
include('createAndFillDatabase.php');

//Declaring some variables used in the code
$tableName = "i71_north";
$daysOfTheWeek = ["Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday"];
$results = [];

if ($errors == 0) {
	$SQLString = "SELECT * FROM $tableName";
	$QueryResult = @mysql_query($SQLString, $DBConnect);
	if (mysql_num_rows($QueryResult) == 0) {
		echo "<p>Error: Zero entries found. Please try again.</p>";
		++$errors;
	}
	else {
		while($values = mysql_fetch_assoc($QueryResult)){
			$results[] = $values;
		}
	}
}

function array_default_key($array) {
    $arrayTemp = array();
    $i = 0;
    foreach ($array as $key => $val) {
        $arrayTemp[$i] = $val;
        $i++;
    }
    return $arrayTemp;
}

function findAverageSpeedPerDay(){
	global $results;
	$currentDate = '2008-01-01';
	$counter = 0;
	$speed = 0;
	$totalSpeed = 0;
	$avgSpeed = [];
	
	foreach ($results as $value){
		if ($value['date'] == $currentDate){
			$speed = $value['speed'];
			$totalSpeed += $speed;
			$counter++;
		}
		else {
			$avgSpeed["$currentDate"] = round(($totalSpeed / $counter), 2);
			$currentDate = $value['date'];
			$speed = $value['speed'];
			$totalSpeed = $speed;
			$counter=1;	
		}
	}
	$avgSpeed["$currentDate"] = round(($totalSpeed / $counter), 2);
	return $avgSpeed;
}

function findTotalVolumePerDay(){
	global $results;
	$currentDate = '2008-01-01';
	$counter = 0;
	$volume = 0;
	$totalVolume = 0;
	$totalVolumeArray = [];
	
	foreach ($results as $value){
		if ($value['date'] == $currentDate){
			$volume = $value['volume'];
			$totalVolume += $volume;
			$counter++;
		}
		else {
			$totalVolumeArray[$currentDate] = $totalVolume/1000;
			$currentDate = $value['date'];
			$volume = $value['volume'];
			$totalVolume = $volume;
			$counter=0;	
		}
	}
	$totalVolumeArray[$currentDate] = $totalVolume/1000;
	return $totalVolumeArray;
}

function findAverageSpeedByDay(){
	global $daysOfTheWeek;
	$counter = 0;
	$currentDayIndex = 2;
	$currentDay = $daysOfTheWeek[$currentDayIndex];
	$avgSpeedPerDay = array_default_key(findAverageSpeedPerDay());
	$totalSumSpeed = [0, 0, 0, 0, 0, 0, 0];
	$numOfDaysOfWeek = [0, 0, 0, 0, 0, 0, 0];
	$avgSpeed = [1, 0, 0, 0, 0, 0, 0];
	
	foreach ($avgSpeedPerDay as $value){
		$totalSumSpeed[$currentDayIndex] = ((int)$totalSumSpeed[$currentDayIndex]+(int)$avgSpeedPerDay[$counter]);
		$numOfDaysOfWeek[$currentDayIndex] = (int)((int)$numOfDaysOfWeek[$currentDayIndex] + 1);
		$counter++;
		if ($currentDayIndex == 6){
			$currentDayIndex = 0;
		}
		else {
			$currentDayIndex++;
		}
	}
	
	for ($i = 0; $i <= 6; $i++){
		$avgSpeed[$i] = ((int)$totalSumSpeed[$i]/(int)$numOfDaysOfWeek[$i]);
	}
	
	return $avgSpeed;
}

function findTotalVolumeByDay(){
	global $daysOfTheWeek;
	$counter = 0;
	$currentDayIndex = 2;
	$currentDay = $daysOfTheWeek[$currentDayIndex];
	$avgVolumePerDay = array_default_key(findTotalVolumePerDay());
	$totalSumVolume = [0, 0, 0, 0, 0, 0, 0];
	$numOfDaysOfWeek = [0, 0, 0, 0, 0, 0, 0];
	$avgVolume = [0, 0, 0, 0, 0, 0, 0];
	
	foreach ($avgVolumePerDay as $value){
		$totalSumVolume[$currentDayIndex] = ((int)$totalSumVolume[$currentDayIndex]+(int)$avgVolumePerDay[$counter]);
		$numOfDaysOfWeek[$currentDayIndex] = (int)((int)$numOfDaysOfWeek[$currentDayIndex] + 1);
		$counter++;
		if ($currentDayIndex == 6){
			$currentDayIndex = 0;
		}
		else {
			$currentDayIndex++;
		}
	}
	
	for ($i = 0; $i <= 6; $i++){
		$avgVolume[$i] = ((int)$totalSumVolume[$i]/(int)$numOfDaysOfWeek[$i]);
	}
	
	return $avgVolume;
}

function findAverageSpeeds(){
	global $results;
	$speeds = [0, 0, 0, 0]; //Holds the amount of times a speed is in a certain category
	//$speeds = [lessThan40, 40-50, 50-60, greaterThan60];
	
	foreach ($results as $value){
		switch (true) {
			case ($value['speed'] < 40):
				$speeds[0] = $speeds[0]+1;
				break;
				
			case (($value['speed'] >= 40) && ($value['speed'] < 50)):
				$speeds[1] = $speeds[1]+1;
				break;
				
			case (($value['speed'] >= 50) && ($value['speed'] <= 60)):
				$speeds[2] = $speeds[2]+1;
				break;
				
			case ($value['speed'] > 60):
				$speeds[3] = $speeds[3]+1;
				break;
		}
	}
	
	return $speeds;
}

function findAvgSpeedOnHolidayVsNorm(){
	$findAverageSpeedPerDay = array_default_key(findAverageSpeedPerDay());
	$findTotalVolumePerDay = array_default_key(findTotalVolumePerDay());
	$AvgSpeedAndVol = [];
	$currentDayIndex = 2;
	$counter = 0;
	
	foreach ($findAverageSpeedPerDay as $key => $value) {
		$counter++;
		$avgSpeedAndVol[$currentDayIndex][] = [$key, $value[0], $findTotalVolumePerDay[$counter][0]];
	}
	
return $avgSpeedAndVol;
}
?>