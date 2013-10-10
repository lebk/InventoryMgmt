#!/usr/bin/perl

$path = "/opt/vmfactory/logs";
$file_to_del = "vmfactory.log.";
$expire_day = 10;

opendir(ML,$path);
@counter = readdir(ML);
closedir(ML);

foreach $file_name(@counter)
{
	chomp($file_name);
	if (index($file_name,$file_to_del) == 0) #if file_name starts with "vmfactory.log."
	{
		$file_path = join("/",$path,$file_name); #absolute path
		$time_intval = 0 + time() - (stat($file_path))[9]; #change string into number, time_intval is in second
		if ($time_intval > $expire_day *24*3600)
		{
			print"deleting $file_path \n";#unnecessary
			unlink($file_path);
		}
	}
}