<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" 
"http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=iso-8859-1" />
</head>
<body>
<?php 
include ("connectAndPullInfo.php");

print_r(findAvgSpeedOnHolidayVsNorm());
echo "<hr />";

print_r(findAverageSpeedPerDay());
echo "<hr />";/*
print_r(findTotalVolumePerDay()); 
echo "<hr />";
print_r(findAverageSpeedByDay()); //Average speed is a little off
echo "<hr />";
print_r(findTotalVolumeByDay());*/
?>
</body>
</html>