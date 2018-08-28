#it's a linux bugfix for russian locale
set -e

myrealpath=`realpath $0`
myrealpath=`dirname $myrealpath`
cd $myrealpath
java -cp classes/jrc-editor.jar -Dfile.encoding=KOI8-R -Dkey.locale.conversion=on org.zaval.tools.i18n.translator.Main

