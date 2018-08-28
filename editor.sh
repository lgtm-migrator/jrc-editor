set -e

myrealpath=`realpath $0`
myrealpath=`dirname $myrealpath`
cd $myrealpath
java -cp classes/jrc-editor.jar org.zaval.tools.i18n.translator.Main
