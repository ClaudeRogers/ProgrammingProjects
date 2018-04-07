<?php
require_once ('src\jpgraph.php');
require_once ('src\jpgraph_line.php');
include('connectAndPullInfo.php');

$datay1 = array_default_key(findAverageSpeedPerDay());
$datay2 = array_default_key(findTotalVolumePerDay());

// Setup the graph
$graph = new Graph(500,400);
$graph->SetScale('intlin');

$theme_class=new UniversalTheme;

$graph->SetTheme($theme_class);
$graph->img->SetAntiAliasing(false);
$graph->title->Set('Average Speed and Total Volume for the Month of January');
$graph->SetBox(false);

$graph->img->SetAntiAliasing();

$graph->yaxis->HideZeroLabel();
$graph->yaxis->HideLine(false);
$graph->yaxis->HideTicks(false,false);
$graph->xaxis->HideTicks(false,false);
$graph->yaxis->SetTitle('Average Speed and Total Volume');
$graph->xaxis->SetTitle('Days of January', 'middle');

$graph->xgrid->Show();
$graph->xgrid->SetLineStyle('solid');
$graph->xgrid->SetColor('#E3E3E3');

// Create the first line
$p1 = new LinePlot($datay1);
$graph->Add($p1);
$p1->SetColor('#6495ED');
$p1->SetLegend('Average Speed (mph)');

// Create the second line
$p2 = new LinePlot($datay2);
$graph->Add($p2);
$p2->SetColor('#B22222');
$p2->SetLegend('Total Volume (Thousands)');

$graph->legend->SetFrameWeight(1);

// Output line
$graph->Stroke();
?>