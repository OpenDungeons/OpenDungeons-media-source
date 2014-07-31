=======================
Ubuntu/Debian Packaging
=======================

./ODTarballMaker.sh
tar -xzvf opendungeons*
cd opendungeons*
# This should only be done the first time.
# To remove the older pbuilder environment run:
# sudo pbuilder --clean
# For other Ubuntu distributions insert the correct deb mirror line but keep the AndrewFenn PPA
sudo pbuilder create --distribution $(lsb_release -cs) --othermirror "deb http://archive.ubuntu.com/ubuntu $(lsb_release -cs) main restricted universe multiverse|deb http://ppa.launchpad.net/andrewfenn/ogredev/ubuntu maverick main"
# Build the source package
debuild -S
# Check that the package builds correctly
sudo pbuilder build ../*.dsc
# If everything went well upload to PPA
cd ..
dput ppa:svenskmand/opendungeons *source.changes

