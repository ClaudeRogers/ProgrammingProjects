<?php // content="text/plain; charset=utf-8"
require_once ('src\jpgraph.php');
require_once ('src\jpgraph_pie.php');
require_once ('src\jpgraph_pie3d.php');
include('connectAndPullInfo.php');
 
$data = findAverageSpeeds();
 
$graph = new PieGraph(500,400);
$graph->SetShadow();
$graph->title->Set("Average Speeds in the Month of January");
 
$p1 = new PiePlot3D($data);
$p1->SetSize(0.5);
$p1->SetCenter(0.45);
$p1->SetLegends(array('<40mph', '>=40mph and <50mph', '>=50mph and <=60mph', '>60mph'));
$graph->legend->SetFrameWeight(1);
$graph->legend->SetColumns(1);
$graph->legend->SetPos(0.63, 0.8);
 
$graph->Add($p1);
$graph->Stroke();
 
?>