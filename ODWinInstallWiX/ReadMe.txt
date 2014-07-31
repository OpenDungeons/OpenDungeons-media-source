======================================================================
Building Open Dungeons from source on Windows and making a msi-package
======================================================================

Before you can build the game and make a msi-package you need some additional software, follow these steps:

1) Download:

http://www.ogre3d.org/download/sdk/
 * We will need the MinGW version

http://www.cegui.org.uk/wiki/index.php/Downloads
 * We will again need the MinGW version

http://www.sfml-dev.org/download.php
 * We will again need the MinGW version

2) Setup environment variables:

 a) Make OGRE_HOME point to the base of the Ogre SDK,
 b) likewise let SFMLDIR point to the base of the SFML SDK,
 c) likewise let CEGUIDIR point to the base of the CEGUI SDK,
 d) and finally let BOOST_ROOT point to the boost directory inside of the Ogre root dir, which 
contains the Boost libraries.

3) Install tools and setup more environtment variables:

Install MinGW: http://sourceforge.net/projects/mingw/files/Automated%20MinGW%20Installer/mingw-get-
inst
 * 1) Use the latest repository, 2) select C++ and MSYS Basic, during install.
 * Add the bin dir of the MinGW install dir to the Windows Path.
 * Also make MINGWDIR point to the install dir of MinGW.

Install WiX 3.5 or later: http://wix.sourceforge.net/index.html
 * Add the bin folder of the WiX install to the Windows Path.

We need two files from Paraffine: http://www.wintellect.com/CS/blogs/jrobbins/archive/2010/03/10/4107.aspx
 * Copy the files Paraffine.exe and Paraffine.pdb from http://www.wintellect.com/CS/files/folders/7420/download.aspx into the bin folder of the Wix Installation.

Install GIT and SVN using these links:

SVN: http://www.sliksvn.com/en/download

GIT: http://git-scm.com/download
 * Download the msysGit version.
 * Install GIT using unix-line-endings.

4) Install CMake using this link:

Cmake: http://www.cmake.org/cmake/resources/software.html
 * Remember to check the option to add Cmake to the path.

5) Now run the ODBuild.bat script which will make a folder ODBuildWork where it will build the game and finally make these files:

Bitmaps
Game
License.rtf
OD-Logo.ico
OpenDungeons.msi
OpenDungeons.wixobj
OpenDungeons.wixpdb
OpenDungeons.wxs
OpenDungeonsFragment.wixobj
OpenDungeonsFragment.wxs

Where OpenDungeons.msi will be the finished msi-installer package and Game contains a working version of the game.