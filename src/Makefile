MAKE=nmake -nologo

all:
    $(MAKE) -f Makefile.2

depend:
    xfind . -name '*.java' -print | sed -e 's:^\./::g' >filelist-all
    cat Makefile.1 >Makefile.2
    jdepend -v 6 -u filelist-all >>Makefile.2
    rm filelist-all

clean:
    $(MAKE) -f Makefile.2 clean

install:
    $(MAKE) -f Makefile.2 install

#EOF