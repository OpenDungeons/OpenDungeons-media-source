rd /s /q ODBuildWork
mkdir ODBuildWork
cd ODBuildWork
REM We use call as git does not return propper exit codes which makes the script exit prematurely :(
call git clone git://opendungeons.git.sourceforge.net/gitroot/opendungeons/opendungeons opendungeons
cd opendungeons
call git checkout -b development remotes/origin/development
cd ..
svn export https://opendungeons.svn.sourceforge.net/svnroot/opendungeons/media opendungeons\build
cd opendungeons
cd build
REM Removes the .git folders in build and all subfolders.
REM FOR /F "tokens=*" %%G IN ('DIR /B /AD /S *.svn*') DO RMDIR /S /Q "%%G"
FOR /F "tokens=*" %%G IN ('DIR /B /AD /S *.git*') DO RMDIR /S /Q "%%G"
cmake -G "MinGW Makefiles" ..
mingw32-make
mkdir plugins
copy "%OGRE_HOME%\bin\Release\Plugin_CgProgramManager.dll" plugins
copy "%OGRE_HOME%\bin\Release\Plugin_ParticleFX.dll" plugins
copy "%OGRE_HOME%\bin\Release\RenderSystem_Direct3D9.dll" plugins
copy "%OGRE_HOME%\bin\Release\RenderSystem_GL.dll" plugins
copy "%CEGUIDIR%\bin\CEGUIBase.dll" .
copy "%CEGUIDIR%\bin\CEGUIExpatParser.dll" .
copy "%CEGUIDIR%\bin\CEGUIFalagardWRBase.dll" .
copy "%CEGUIDIR%\bin\CEGUIOgreRenderer.dll" .
copy "%OGRE_HOME%\bin\Release\cg.dll" .
copy "%OGRE_HOME%\bin\Release\libboost_thread-mgw45-mt-1_44.dll" .
copy "%MINGWDIR%\bin\libgcc_s_dw2-1.dll" .
copy "%OGRE_HOME%\bin\Release\libOIS.dll" .
copy "%SFMLDIR%\extlibs\bin\libsndfile-1.dll" .
copy "%MINGWDIR%\bin\libstdc++-6.dll" .
copy "%OGRE_HOME%\bin\Release\OgreMain.dll" .
copy "%OGRE_HOME%\bin\Release\OgreRTShaderSystem.dll" .
copy "%SFMLDIR%\extlibs\bin\openal32.dll" .
copy "%SFMLDIR%\lib\sfml-audio.dll" .
copy "%SFMLDIR%\lib\sfml-system.dll" .
REM Create the materials.zip file
mkdir materialsZip
FOR /F "tokens=*" %%G IN ('dir /B /A *.material /D /S') DO copy "%%G" materialsZip
cd materialsZip
7z a materials.zip *
cd ..
move materialsZip\materials.zip .
rd /s /q materialsZip
REM Create the models.zip file
mkdir modelsZip
FOR /F "tokens=*" %%G IN ('dir /B /A *.mesh *.skeleton /D /S') DO copy "%%G" modelsZip
cd modelsZip
7z a models.zip *
cd ..
move modelsZip\models.zip .
rd /s /q modelsZip
REM Create the textures.zip file
cd materials\textures
7z a textures.zip *
cd ..\..
move materials\textures\textures.zip .
REM Create the shaders.zip file
mkdir shadersZip
FOR /F "tokens=*" %%G IN ('dir /B /A *.cg *.hlsl *.glsl /D /S') DO copy "%%G" shadersZip
cd shadersZip
7z a shaders.zip *
cd ..
move shadersZip\shaders.zip .
rd /s /q shadersZip
mkdir graphics
move materials.zip graphics
move models.zip graphics
move textures.zip graphics
move shaders.zip graphics
del resources.cfg
move resources.zip.cfg resources.cfg
rd /s /q CMakeFiles
rd /s /q models
rd /s /q materials
cd ../..
move opendungeons\build Game
svn export https://opendungeons.svn.sourceforge.net/svnroot/opendungeons/mediaSource/ODWinInstallWiX/
move ODWinInstallWiX\OD-Logo.ico .
move ODWinInstallWiX\License.rtf .
move ODWinInstallWiX\Bitmaps .
move ODWinInstallWiX\OpenDungeons.wxs .
move ODWinInstallWiX\OpenDungeonsFragment.wxs .
move ODWinInstallWiX\sed.vbs .
echo ^<?xml version="1.0" encoding="utf-8"?^> > VersionFragment.wxs
echo ^<Include^> >> VersionFragment.wxs
echo ^<?define VERSION = >> VersionFragment.wxs
findstr /R /C:"const std::string ODApplication::VERSION  *= " opendungeons\source\ODApplication.cpp | cscript //Nologo sed.vbs "s/.* = *//" | cscript //Nologo sed.vbs "s/;//" >> VersionFragment.wxs
echo ?^> >> VersionFragment.wxs
echo ^</Include^> >> VersionFragment.wxs
rmdir /s /q opendungeons
rmdir /s /q Bitmaps\.svn
rmdir /s /q ODWinInstallWiX
REM You can make a new fragment file by running: paraffin -dir .\Game -custom OD -g -ext .wxs OpenDungeonsFragment.wxs
paraffin -update OpenDungeonsFragment.wxs
del OpenDungeonsFragment.wxs
move OpenDungeonsFragment.PARAFFIN OpenDungeonsFragment.wxs
candle OpenDungeons.wxs OpenDungeonsFragment.wxs
light OpenDungeons.wixobj OpenDungeonsFragment.wixobj  -ext WixUIExtension -out OpenDungeons.msi
