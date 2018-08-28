CC=cl
CFLAGS=-nologo -c -MT -GX -DWIN32 -DNDEBUG -D_WINDOWS
LINK=link
LDFLAGS=-nologo -out:$@ 
RSC=rc
RSCFLAGS=-d NDEBUG -d WIN32 -d _WINDOWS

LIBS=advapi32.lib user32.lib

all: ../../jrc-editor.exe

../../jrc-editor.exe: jrc-editor.obj jrc-editor.res 
    $(LINK) $(LDFLAGS) jrc-editor.obj jrc-editor.res $(LIBS)

.c.obj:
    $(CC) $(CFLAGS) $<

.cpp.obj:
    $(CC) $(CFLAGS) $<

.rc.res:
    $(RSC) $(RSCFLAGS) $<

clean:
    del *.obj ../../jrc-editor.exe *.res

