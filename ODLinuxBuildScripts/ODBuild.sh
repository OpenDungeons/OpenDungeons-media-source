#!/bin/sh 
# Building and installing OpenDungeons
echo "################################################" > ODBuildLog.txt
echo "#        Building log for OpenDungeons         #" >> ODBuildLog.txt
echo "################################################" >> ODBuildLog.txt
echo "If you get an error then check if you have added the Andrew Fenn PPA and install all needed packages. You can do all of that by:"
echo "sudo add-apt-repository ppa:andrewfenn/ogredev"
echo "sudo apt-get update"
echo "sudo apt-get install subversion git cmake build-essential libois-dev libogre-dev libcegui libcegui-dbg libcegui-dev libfreeimage3-dbg libsfml-dev libopenal-dev"
echo tar -xzvf opendungeons*.tar.gz >> ODBuildLog.txt
tar -xzvf opendungeons*.tar.gz
mv `ls | grep -m 1 opendungeons` OpenDungeons
echo cd OpenDungeons >> ODBuildLog.txt
cd OpenDungeons
echo cmake . >> ../ODBuildLog.txt
cmake . 
echo make -j`grep -m 1 "cpu cores" /proc/cpuinfo | sed 's:cpu cores.*\:.::'` >> ../ODBuildLog.txt
make -j`grep -m 1 "cpu cores" /proc/cpuinfo | sed 's:cpu cores.*\:.::'`
