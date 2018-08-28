Vendor:       Zaval CE Group
Distribution: Linux
Name:         jrc-editor
Packager:     support@zaval.org

License:      GPLv2
Group:        Development/Applications
Autoreqprov:  on
Version:      2.0.0
Release:      1
Summary:      JRC Editor is world-leading tool to support NLS data for Java.
Source:       jrc-editor-%{version}.tar.gz
URL:          http://www.zaval.org/products/jrc-editor/
BuildRoot:    %{_tmppath}/%{name}-%{version}-build

%description    
    
Zaval JRC Editor is not a simply editing tool - it provides rich and flexible 
way to manage text-based resources. In fact, you are able to make any distributed 
translations as you want in several forms outside JRCE. But in all cases you 
will need to merge all changes after translation and do a clean up texts being 
translated; and JRC Editor provides all necessary features to accomplish these 
tasks.

The Zaval Java Resource Editor can be used for new and existing software 
localization, resources synchronization and any other resources manipulations.

It provides full support for any language specific resources (it depends on the 
fonts and font metrics settings of the host OS at your computer). The target of 
this tool is localization strings manipulation for all Java-based software that 
has appropriate support embedded.

The Zaval JRC Editor is best used for regular access to various resource files. 
You can add your own language support to the existing software if strings are not 
hard-coded to the software. One of the greatest things in the internationalization 
is that you don't need to make code changes.

Another great area in this tool usage is resource bundle synchronization. Our tool 
can handle this task easily - it compares the files set and highlights all differences. 
It allows separating development process and resource management process. That's why 
this tool can be used as part of your software pack to provide 3rd party localization.

%prep
%setup -n jrc-editor-%{version}

%build
%install

install -d -m 755  %{buildroot}/usr/bin
install -d -m 755  %{buildroot}/usr/lib/jrc-editor
install -d -m 755  %{buildroot}/usr/lib/jrc-editor/classes
install -d -m 755  %{buildroot}/usr/lib/jrc-editor/doc
install -d -m 755  %{buildroot}/usr/lib/jrc-editor/doc/images
install -d -m 755  %{buildroot}/usr/lib/jrc-editor/images
install -d -m 755  %{buildroot}/usr/lib/jrc-editor/sample

install -m 644  src/*.kde *.ico %{buildroot}/usr/lib/jrc-editor
install -m 755  *.sh            %{buildroot}/usr/lib/jrc-editor
install -m 644  classes/*.jar   %{buildroot}/usr/lib/jrc-editor/classes
install -m 644  doc/Authors doc/Bugs doc/CHANGELOG doc/COPYRIGHT doc/SUPPORT doc/*.pdf doc/*.html doc/*.txt doc/*.url %{buildroot}/usr/lib/jrc-editor/doc
install -m 644  doc/images/*    %{buildroot}/usr/lib/jrc-editor/doc/images
install -m 644  images/*        %{buildroot}/usr/lib/jrc-editor/images
install -m 644  sample/*        %{buildroot}/usr/lib/jrc-editor/sample

%files

/usr/lib/jrc-editor/*

%config
%doc
%post

if [ ! -f /usr/bin/jrc-editor ] ; then 
    ln -s /usr/lib/jrc-editor/jrc-editor.sh /usr/bin/jrc-editor
fi
if [ ! -f /usr/bin/jrc-split ] ; then 
    ln -s /usr/lib/jrc-editor/jrc-split.sh /usr/bin/jrc-split
fi
