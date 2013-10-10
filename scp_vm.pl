#!/usr/bin/perl -w
use strict;
use Getopt::Std;

###################---------------######################

###################---------------######################

#Get the argurment from Java

print "This script will be used to move the configured VM to file server\n";

print "the number of argv are: @ARGV\n";

if ( @ARGV != 3 ) {
        print "there should be three arguments passed into:\n";
        print "1: the VM will be copied\n";
        print "2: the user which copies the VM:\n";
        print "3: the location where the VM will be copied to:\n";

        print "e.g: scp -r \$var0 \$var1\@\$var2\n";

        print "scp -r scptest root\@10.65.84.93:/fileserver/vmimages_byvmfactory\n";

        die $!;

}

my @args = ( "scp -r $ARGV[0] $ARGV[1]\@$ARGV[2]" );

&doSystemCommand(@args);

sub doSystemCommand {
        print "Executing [$_[0]] \n";
        my $returnCode = system($_[0]);

        if ( $returnCode != 0 ) {
                die "Failed executing [$_[0]]\n";
        }
}
