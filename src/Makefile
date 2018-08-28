all:
    make -f Makefile.2

depend:
    find . -name '*.java' -print | sed -e 's:^\./::g' >filelist-all
    cat Makefile.1 >Makefile.2
    jdepend -v 6 -u filelist-all >>Makefile.2

clean:
    make -f Makefile.2 clean

install:
    make -f Makefile.2 install

#EOF