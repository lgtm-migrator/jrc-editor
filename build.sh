#!/bin/sh
VER=2.0.0
ARCH=i586

rm -rf /usr/src/packages/BUILD/jrc-editor-* 
rm -rf /var/tmp/jrc-edutor-$VER-build 

if [ -d jrc-editor-$VER ] ; then rm -rf jrc-editor-$VER ; fi
mkdir jrc-editor-$VER 

cp -ar images doc sample j*.sh *.ico jrc-editor-$VER
mkdir jrc-editor-$VER/classes
mkdir jrc-editor-$VER/src
cp classes/*.jar jrc-editor-$VER/classes
cp src/*.kde jrc-editor-$VER/src

tar cfzp jrc-editor-$VER.tar.gz jrc-editor-$VER

cp jrc-editor-$VER.tar.gz /usr/src/packages/SOURCES 
rpmbuild -bb src/jrc-editor.spec 
mv /usr/src/redhat/RPMS/$ARCH/jrc-editor-$VER-1.$ARCH.rpm .
mv *.tar.gz *.rpm release

