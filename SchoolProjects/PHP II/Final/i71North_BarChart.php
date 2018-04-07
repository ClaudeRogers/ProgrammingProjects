<?php
require_once ('src\jpgraph.php');
require_once ('src\jpgraph_bar.php');
include('connectAndPullInfo.php');

//Data
$data1y = findAverageSpeedByDay();
$data2y = findTotalVolumeByDay();

// Create the graph. These two calls are always required
$graph = new Graph(500,400);    
$graph->SetScale('textlin');
 
$graph->SetShadow();
$graph->img->SetMargin(40,30,20,40);
 
// Create the bar plots
$b1plot = new BarPlot($data1y);
$b1plot->SetFillColor('orange');
$b1plot->SetLegend('Average Speed (mph)');
$b2plot = new BarPlot($data2y);
$b2plot->SetFillColor('blue');
$b2plot->SetLegend('Total Volume of Cars (Thousands)');
$graph->legend->SetFrameWeight(1);
 
// Create the grouped bar plot
$gbplot = new GroupBarPlot(array($b1plot,$b2plot));
 
// ...and add it to the graPH
$graph->Add($gbplot);
 
$graph->title->Set('Average Speed and Total Volume per Day of Week');
$graph->xaxis->SetTitle('Days of the week', 'middle');
$graph->yaxis->SetTitle('Average Speed/Total Volume', 'middle');
$graph->xaxis->SetTickLabels(array('Sun.','Mon.','Tues.','Wed.', 'Thur.', 'Fri.', 'Sat.'));


 
// Display the graph
$graph->Stroke();
?>