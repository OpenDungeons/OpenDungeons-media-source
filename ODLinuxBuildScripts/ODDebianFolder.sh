#!/bin/sh 
# Prepare the Debian folder for building a package
if [ "$1" = '' ]; then
  echo "Submit the target distribution as argument, e.g. maverick, natty, etc."
  exit 1
fi
Dist=$1
cd opendungeons*
cd licenses
FILELICENSE=`cat COPYING | sed 's/^/   /'`
cd ..
rm -rf debian
cp -R ../debian debian
cd debian
eval "echo \"`cat copyright`\"" > copyright2
rm copyright
mv copyright2 copyright
cp ../../changelog.$Dist changelog
