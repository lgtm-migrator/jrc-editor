#it's a linux bugfix for russian locale
java -cp `pwd`/classes/jrc-editor.jar -Dfile.encoding=KOI8-R -Dkey.locale.conversion=on org.zaval.tools.i18n.translator.Main
