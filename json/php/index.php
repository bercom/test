<?php
$data = array(
'par1' => $_GET[par1],
'par2' => $_GET[par2]
); 
print (json_encode($data)); 
echo shell_exec("dir c:\\*.txt");
?>