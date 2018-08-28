#include <windows.h>
#include <stdio.h>
#include <string.h>
#include <ctype.h>

#include <stdlib.h>
#include <io.h>
#include <direct.h>
#include <process.h>

#include <sys/types.h>
#include <time.h>

static char* customJdk = NULL;

static char *getProfile(char* s, char* t)
{
   HKEY key = NULL;
   static char bux[1256];
   unsigned long type = 0, size = 1255;

   strcpy(bux,"SOFTWARE\\");
   strcat(bux,s);
   if(RegOpenKey(HKEY_LOCAL_MACHINE,bux,&key)!=ERROR_SUCCESS){ 
      fprintf(stderr,"Warning: no registry key HKLM\\SOFTWARE\\%s\\%s\n", s, t);
      return NULL;
   }
   if(RegQueryValueEx(key, t, NULL, &type, (unsigned char*)bux, &size)!=ERROR_SUCCESS){
      RegCloseKey(key);
      fprintf(stderr,"Warning: cannot read registry key HKLM\\SOFTWARE\\%s\\%s\n", s, t);
      return NULL;
   }
   RegCloseKey(key);
   return bux;
}

static char* lookupForJavaFromRegistry(char* array[])
{
    int j;
    char* q;

    for(j=0;array[j];++j){
        q = getProfile(array[j], "JavaHome");
        if(q) return q;
    }
    return NULL;
}

char* checkForCustomJdk(char* cJdk)
{
    char buf[MAX_PATH];
    if(cJdk){
        sprintf(buf, "%s/jre/bin/java.exe", cJdk);
        if(!access(buf, 0)) return cJdk;
        sprintf(buf, "%s/bin/java.exe", cJdk);
        if(!access(buf, 0)) return cJdk;
        fprintf(stderr, "No JDK/JRE found at %s\n", cJdk);
    }
    return NULL;
}

char* lookupForJava(char* forced)
{
    char* q;
    static char* jdk_1_4[]={
        "JavaSoft\\Java Development Kit\\1.4.2",
        "JavaSoft\\Java Development Kit\\1.4.1",
        "JavaSoft\\Java Development Kit\\1.4",
        "JavaSoft\\Java Runtime Environment\\1.4.2",
        "JavaSoft\\Java Runtime Environment\\1.4.1",
        "JavaSoft\\Java Runtime Environment\\1.4",
        NULL
    };

    static char* jdk_1_3[]={
        "JavaSoft\\Java Development Kit\\1.3.1",
        "JavaSoft\\Java Development Kit\\1.3",
        "JavaSoft\\Java Runtime Environment\\1.3.1",
        "JavaSoft\\Java Runtime Environment\\1.3",
        NULL
    };

    // Looking for JDK
    customJdk = checkForCustomJdk(customJdk);
    if(customJdk) return customJdk;
    if(forced){
        if(strstr(forced, "=1.3")){
            return lookupForJavaFromRegistry(jdk_1_3);
        }
        else if(strstr(forced, "=1.4")){
            return lookupForJavaFromRegistry(jdk_1_4);
        }
    }
            
    q = lookupForJavaFromRegistry(jdk_1_4);
    if(q) return q;
    return lookupForJavaFromRegistry(jdk_1_3);
}

static void spawnAndWait(char** _argv)
{
    char cmdLine[32768];
    STARTUPINFO startInfo = {
        sizeof(STARTUPINFO),
        NULL, NULL, "Java Resource Editor 2.0",
        0, 0, 0, 0,
        0, 0, 0,
        STARTF_USESHOWWINDOW, SW_SHOW, 0, NULL, 
        NULL, NULL, NULL
    };
    PROCESS_INFORMATION procInfo;

    int j;
    time_t t1, t2;
    for(j=0;_argv[j];++j){ 
        if(_argv[j+1]){ 
            strcat(cmdLine, "\"");
            strcat(cmdLine, _argv[j]); 
            strcat(cmdLine, "\" "); 
        }
        else strcat(cmdLine, _argv[j]); 
    }
    
    FILE* logFile = fopen("jrc-editor.log", "a+t");
    startInfo.hStdError = (HANDLE)_get_osfhandle(fileno(logFile));
    startInfo.hStdOutput = startInfo.hStdError;
    startInfo.hStdInput = GetStdHandle(STD_INPUT_HANDLE);
    startInfo.dwFlags |= STARTF_USESTDHANDLES;
    if(!CreateProcess(NULL, cmdLine, NULL, NULL, TRUE, 
        0L, NULL, NULL, &startInfo, &procInfo)){
            fprintf(stderr, "Error spawning Java Runtime! Code = %X\n", GetLastError());
            MessageBox(NULL, 
                "Java runtime cannot be started. Please check if one of the following\n"
                "versions is installed on your system:\n"
                "           - JDK 1.3 or 1.3.1\n"
                "           - JRE 1.3 or 1.3.1\n"
                "           - JDK 1.4, 1.4.1 or 1.4.2\n"
                "           - JRE 1.4, 1.4.1 or 1.4.2.\n"
                "If none of these versions is available on your system, you can get the\n"
                "version that you prefer from http://java.sun.com. Start JRC Editor again\n"
                "after you have installed Java runtime.",
                "Error", MB_OK);
            fclose(logFile);
        //  unlink("jrc-editor.log");
            exit(0);
    }
    t1 = clock();
    WaitForSingleObject(procInfo.hProcess, INFINITE);
    t2 = clock();
    if((t2 - t1)/CLOCKS_PER_SEC < 1){
        MessageBox(NULL, 
            "JRC Editor cannot be started.\n"
            "\n"
            "Please check if you have started JRC Editor from the correct directory.\n"
            "Make sure that JRC Editor is installed correctly and that JDK/JRE is\n"
            "available.",
            "Error", MB_OK);
    }
    fclose(logFile);
    unlink("jrc-editor.log");
}

char* getJdkFromCmdLine(char* lpCmdLine)
{
    char* q = strstr(lpCmdLine, "-jdk=1.3");
    if(q) return q;
    q = strstr(lpCmdLine, "-jdk=1.4");
    return q;
}

char* consumeOptions(char* cmdline, char* opts)
{
    char *v;
    for(;;){
        while(isspace(*cmdline)) ++cmdline;
        if(*cmdline!='-') break;
        if(cmdline[1]!='D') break;
        for(v=cmdline;!isspace(*v);*opts++=*v++);
        *opts++=' ';
        *opts = '\0';
        cmdline = v;
    }
    return cmdline;
}

void main_run(char* lpCmdLine)
{
    char classPath [MAX_PATH * 10];
    char systemPath[MAX_PATH * 10];

    char rootPlace[MAX_PATH];
    char jdkPlace[MAX_PATH];
    char exePath[MAX_PATH];
    char tmpPlace[MAX_PATH];
    char libPlace[MAX_PATH];
    char dirPlace[MAX_PATH];
    char optPlace[MAX_PATH];
    char *argv[128]={
       exePath,
       optPlace,
       dirPlace,
       "-jar",
       libPlace,
       NULL,
       NULL
    };

    char* jdk;
    char* root;

    root = getProfile("Zaval CE Group\\JRC Editor\\2.0", "Location");
    if(!root){
       char tmp[MAX_PATH], *q; 

       GetModuleFileName(NULL, tmp, MAX_PATH);
       q = strrchr(tmp, '\\');
       if(!q){
          root = getcwd(tmp, MAX_PATH);
          sprintf(rootPlace, "%s", root);
       }
       else{
          *q = '\0';
          sprintf(rootPlace, "%s", tmp);
          *q = '\\';
       }
    }
    else strcpy(rootPlace, root);
    sprintf(libPlace, "%s/classes/jrc-editor.jar", rootPlace);
    sprintf(dirPlace, "-Dmy.root.dir=%s", rootPlace);

    jdk = lookupForJava(getJdkFromCmdLine(lpCmdLine));
    if(!jdk) jdk = getenv("JAVA_HOME");
    if(!jdk){ 
        fprintf(stderr, "Warning: no external JDK/JRE was found.\n");
        sprintf(jdkPlace, "%s\\jre", rootPlace);
    }
    else strcpy(jdkPlace, jdk);
    sprintf(exePath, "%s\\bin\\javaw.exe", jdkPlace);
    *optPlace = 0;
    argv[5] = consumeOptions(lpCmdLine, optPlace);
    if(*optPlace) strcat(optPlace, " ");
    strcat(optPlace, "-Xmx256M");

    fflush(stderr);
    spawnAndWait(argv);
}

int WINAPI WinMain(HINSTANCE hInstance, HINSTANCE hPrevInstance, 
    char* lpCmdLine, int nCmdShow)
{
    main_run(lpCmdLine);
    return 0;
}
