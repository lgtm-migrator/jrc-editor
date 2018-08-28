#!/bin/sh 
set -e 
myrealpath=`readlink "$0"` || myrealpath=`echo "$0"` 
myrealpath=`dirname $myrealpath` 
cd "$myrealpath" 
java -jar classes/jrc-editor.jar $* 

