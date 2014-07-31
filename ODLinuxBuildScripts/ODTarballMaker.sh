#!/bin/sh 
# Making a source tar file for OpenDungeons
mkdir OpenDungeons
cd OpenDungeons
svn co https://opendungeons.svn.sourceforge.net/svnroot/opendungeons/media .
svn co https://opendungeons.svn.sourceforge.net/svnroot/opendungeons/licenses licenses
cd .. 
git clone git://opendungeons.git.sourceforge.net/gitroot/opendungeons/opendungeons OpenDungeonsCode
cd OpenDungeonsCode
git checkout -b development remotes/origin/development
cd .. 
cp -R OpenDungeonsCode/* OpenDungeons
rm -rf OpenDungeonsCode
VERSION=`grep -m 1 "const std::string ODApplication::VERSION" OpenDungeons/source/ODApplication.cpp | sed 's:.*VERSION.*= *"::' | sed 's:".*::'`
find OpenDungeons -name ".svn" -exec rm -rf {} \;
find OpenDungeons -name ".git" -exec rm -rf {} \;
cd OpenDungeons
echo 'The "source code" is licensed under GNU GPL v3.0, see the included file "GPL_3.0" or' > licenses/COPYING
echo ' http://www.gnu.org/licenses/gpl-3.0-standalone.html' >> licenses/COPYING
echo 'for an online version.' >> licenses/COPYING
echo ''  >> licenses/COPYING
echo 'The "content" (models, textures, sounds, music, etc.) is licensed under CC-BY-SA 3.0, see the included file "CC-BY-SA_3.0" or' >> licenses/COPYING
echo ' http://creativecommons.org/licenses/by-sa/3.0/legalcode' >> licenses/COPYING
echo 'for an online version.' >> licenses/COPYING
echo '' >> licenses/COPYING
echo 'The following files constitue the "source code":' >> licenses/COPYING
ls -R CMakeLists.txt README cmake source scripts >> licenses/COPYING
echo '' >> licenses/COPYING
echo 'The following files constitue the "content":' >> licenses/COPYING
ls -R gui lang levels levels_git materials models music sounds >> licenses/COPYING
cd ..
mv OpenDungeons opendungeons-$VERSION
tar czf opendungeons_$VERSION.orig.tar.gz opendungeons-$VERSION
rm -rf opendungeons-$VERSION
