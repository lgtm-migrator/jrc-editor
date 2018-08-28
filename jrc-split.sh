#!/bin/sh 
set -e 
myrealpath=`readlink "$0"` || myrealpath=`echo "$0"` 
myrealpath=`dirname $myrealpath` 
cd "$myrealpath" 
java -cp classes/jrc-editor.jar org.zaval.tools.i18n.translator.Split $* 
