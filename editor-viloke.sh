#!/bin/sh

# here's a startup script that works
# even if you invoke the script via a link.
# (that is, if you have the script itself in /opt/jrc-editor/bin/jrc,
# you may link it to /usr/local/bin and run the editor wherever
# you wan to by just commanding "jrc")
#
# Contribued by Panu Hallfors (Viloke Oy)

set -e

myrealpath=`realpath $0`
myrealpath=`dirname $myrealpath`
cd $myrealpath
java -cp classes/jrc-editor.jar org.zaval.tools.i18n.translator.Main
