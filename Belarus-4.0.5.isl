; *** Inno Setup version 3.0.6+ Belarusian messages (soviet orthography)***
; Translated December 2002 by Anton Kavalenka (anton@inp.minsk.by)
; Verified by Sergey Bychkov (serge_bychkov@mailru.com)
;

[LangOptions]
LanguageName=Belarusian
LanguageID=$0423
; If the language you are translating to requires special font faces or
; sizes, uncomment any of the following entries and change them accordingly.

;DialogFontName=MS Sans Serif
;DialogFontSize=8
;DialogFontStandardHeight=13

;TitleFontName=Arial
;TitleFontSize=29
;WelcomeFontName=Arial
;WelcomeFontSize=12
;CopyrightFontName=Arial
;CopyrightFontSize=8

[Messages]

; *** Application titles
SetupAppTitle=Устаноўка 
SetupWindowTitle=Устаноўка - %1
UninstallAppTitle=Выдаленне
UninstallAppFullTitle=%1 - Выдаленне

; *** Icons
;DefaultUninstallIconName=Выдаленне %1

; *** Misc. common
InformationTitle=Інфармацыя
ConfirmTitle=Пацверджанне
ErrorTitle=Памылка

; *** SetupLdr messages
SetupLdrStartupMessage=Устаноўка праграмы "%1". Далей?
LdrCannotCreateTemp=Немагчыма стварыць часовы файл. Устаноўка спыненая
LdrCannotExecTemp=Немагчыма запусціць файл у  часовай папке. Устаноўка спыненая

; *** Startup error messages
LastErrorMessage=%1.%n%nПамылка %2: %3
SetupFileMissing=Файл %1 адсутнічае ў  часовай папке. Выпраўце хібу, альбо атрымайце новую копію праграмы.
SetupFileCorrupt=Файл устаноўкі пашкоджаны. Атрымайце новую копію праграмы.
SetupFileCorruptOrWrongVer=Файлы устаноўкі пашкоджаныя або несумяшчальныя з бягучай версіей праграмы устаноўкі.
NotOnThisPlatform=Праграма не можа быць запушчаная на %1.
OnlyOnThisPlatform=Праграма павінна быць запушчаная на %1.
WinVersionTooLowError=Праграма патрабуе %1 версіі %2 або старэйшай.
WinVersionTooHighError=Праграма не можа быць усталявана на %1 версіі %2 або старэйшай.
AdminPrivilegesRequired=Патрабуюцца адміністрацыйныя прывілеі для ўстаноўкі праграмы.
PowerUserPrivilegesRequired=Патрабуюцца адміністрацыйныя ці Power User прывілеі для ўстаноўкі праграмы.
SetupAppRunningError=Праграма ўстаноўкі знайшла, што запушчаная праграма "%1".%n%nЗакрыйце ўсе яе копіі, пасля націсніце OK для працяга, або Скасаваць для выхада.
UninstallAppRunningError=Праграма устаноўкі знайшла, што запушчаная праграма "%1".%n%nЗакрыйце ўсе яе копіі, затым націсніце OK для працяга, або Скасаваць для выхада.

; *** Misc. errors
ErrorCreatingDir=Немагчыма стварыць папку "%1"
ErrorTooManyFilesInDir=Немагчыма стварыць файл у папцы "%1", бо яна утрымлівае зашмат файлаў

; *** Setup common messages
ExitSetupTitle=Пакінуць устаноўку
ExitSetupMessage=Устаноўка не скончаная. Калі Вы яе не скончыце, праграма не будзе усталявана.%n%nВы маеце запусціць праграму устаноўкі наступны раз для заканчэння.%n%nПакінуць устаноўку?
AboutSetupMenuItem=&Пра ўстаноўку...
AboutSetupTitle=Пра ўстаноўку
AboutSetupMessage=%1 версіі %2%n%3%n%nхатняя старонка %1:%n%4
AboutSetupNote=

; *** Buttons
ButtonBack=< &Назад
ButtonNext=&Далей >
ButtonInstall=&Усталяваць
ButtonOK=OK
ButtonCancel=Скасаваць
ButtonYes=&Так
ButtonYesToAll=Так для &Ўсіх
ButtonNo=&Не
ButtonNoToAll=Н&е для Ўсіх
ButtonFinish=&Скончыць
ButtonBrowse=&Прагляд...

; *** "Select Language" dialog messages
SelectLanguageTitle=Абярыце мову інсталяцыі
SelectLanguageLabel=Абярыце мову, якая будзе выкарыстоўвацца ў час інсталяцыі:

; *** Common wizard text
ClickNext=Націсніце Далей каб працягнуць або Скасаваць для выхада.
;ClickNextModern=Націсніце Далей каб працягнуць або Скасаваць для выхада.
BeveledLabel=
;InnoSetup

; *** "Welcome" wizard page
;WizardWelcome=Калі ласка запрашаем
WelcomeLabel1=Вас вітае праграма устаноўкі аплікацыі "[name]".
WelcomeLabel2=Праграма [name/ver] будзе усталяваная на Ваш кампутар.%n%nВельмі рэкамэндуецца закрыць усе іншыя праграмы, перад працягам устаноўкі. Гэта дазволіць пазбегнуць канфліктаў пад час устаноўкі.

; *** "Password" wizard page
WizardPassword=Пароль
PasswordLabel1=Устаноўка абаронена паролем.
PasswordLabel3=Увядзіце пароль, затым націсніце Далей для працяга. Пароль адчувальны да рэгістра.
PasswordEditLabel=&Пароль:
IncorrectPassword=Недакладны пароль. Паспрабуйце зноў.

; *** "License Agreement" wizard page
WizardLicense=Ліцэнзійнае пагадненне
LicenseLabel=Прачытайце наступную інфармацыю, для таго каб працягнуць устаноўку.
;LicenseLabel1=Прачытайце Ліцэнзійнае пагадненне. Карыстайцеся паласой пракруткі альбо клавішай "Page Down" для прагляда пагаднення.
;LicenseLabel2=Ці згодныя Вы з усімі ўмовамі Ліцэнзійнага пагаднення? Калі не, устаноўка будзе скасавана. Каб усталяваць праграму "[name]", Вы мусіце прыняць умовы дадзенага пагадення.
LicenseAccepted=Я &Прымаю пагадненне
LicenseNotAccepted=Я &НЕ прымаю пагадненне
LicenseLabel3=Калі ласка прачытайце наступнае ліцэнзійнае пагадненне. Каб пацягнуць устаноўку, Вы мусіце прыняць умовы дадзенага пагадення.

; *** "Information" wizard pages
WizardInfoBefore=Інфармацыя
InfoBeforeLabel=Прачытайце наступную інфармацыю перад працягам устаноўкі.
InfoBeforeClickLabel=Калі будзеце гатовы працягнуць устаноўку, націсніце Далей.
WizardInfoAfter=Інфармацыя
InfoAfterLabel=Прачытайце наступную інфармацыю перад працягам устаноўкі.
InfoAfterClickLabel=Калі будзеце гатовы працягнуць устаноўку, націсніце Далей.

; Preparing setup
WizardPreparing=Рыхтуецца устаноўка
PreparingDesc=Праграма ўстаноўкі рыхтуецца усталяваць [name] на Ваш кампутар.
PreviousInstallNotCompleted=Устаноўка ці выдаленне папярэдней праграмы не було скончана. Вам патрэбна рэстартаваць Ваш кампутар каб скончыць устаноўку.%n%nПасля рэстарту запусціце Устаноўку яшчэ раз, каб скончыць устаноўку [name].
CanNotContinue=Працяг немагчымы. Калі ласка націсніце "Скасаваць" для выхада.

; *** "Select Destination Directory" wizard page
WizardSelectDir=Абярыце папку для устаноўкі
SelectDirDesc=Куды усталяваць праграму "[name]"?
SelectDirLabel=Абярыце папку, у якую Вы жадаеце усталяваць праграму "[name]", затым націсніце Далей.
DiskSpaceMBLabel=Праграма патрабуе як мінімум [mb] MB свабоднай дыскавай прасторы.
ToUNCPathname=Немагчыма усталяваць па шляху, карыстаючаму UNC. Калі Вы спрабуеце усталяваць прграму ў сеці, неабходна падключыць сеткавы дыск.
InvalidPath=Неабходна паказаць поўны сеткавы шлях з літарай дэвайса, напрыклад:%nC:\APP
InvalidDrive=У абраным дэвайсе адсутнічае дыск. Абярыце іншы.
DiskSpaceWarningTitle=Недастаткова месца на дыске
DiskSpaceWarning=Устаноўка патрабуе сама меней %1 KB свободнай прасторы, але на абраным девайсе толькі %2 KB.%n%nПрацягнем негледзячы на гэта?
BadDirName32=Назва папкі не можа ўтрымліваць ні адзін з наступных сымбаляў:%n%n%1
DirExistsTitle=Папка існуе
DirExists=Папка:%n%n%1%n%nужо існуе. Усталяваць у яе?
DirDoesntExistTitle=Папка не існуе
DirDoesntExist=Папка:%n%n%1%n%nне існуе. Стварыць?

; *** "Select Components" wizard page
WizardSelectComponents=Абярыце Кампанэнты
SelectComponentsDesc=Якія кампанэнты мусяць быць усталяваныя?
SelectComponentsLabel2=Абярыце кампанэнты для ўстаноўкі; Пазначце кампанэнты, якія не жадаеце усталёўваць. Націсніце Далей, калі будзеце гатовыя працягнуць устаноўку.
FullInstallation=Поўная ўстаноўка
; if possible don't translate 'Compact' as 'Minimal' (I mean 'Minimal' in your language)
CompactInstallation=Кампактная ўстаноўка
CustomInstallation=Выбарчая ўстаноўка
NoUninstallWarningTitle=Існуюць кампанэнты
NoUninstallWarning=Устаноўка выявіла, што наступные кампанэнты ўжо усталяваныя на Вашым кампутары:%n%n%1%n%nНепазначэнне гэтых кампанэнтаў не выдаліць іх.%n%nПрацягнем?
ComponentSize1=%1 KB
ComponentSize2=%1 MB
ComponentsDiskSpaceMBLabel=Абраныя кампанэнты патрабуюць сама меней [mb] MB месца на дыске.

; *** "Select Additional Tasks" wizard page
WizardSelectTasks=Выбар дадатковых заданняў
SelectTasksDesc=Якія дадатковыя заданні небходна выканаць?
SelectTasksLabel2=Абярыце дадатковыя заданні, якія неабходна выканаць пры ўсталяванні праграмы "[name]", затым націсніце Далей.
ReadyMemoTasks=Дадатковыя заданні:

; *** "Select Start Menu Folder" wizard page
WizardSelectProgramGroup=Абярыце папку ў меню Пуск
SelectStartMenuFolderDesc=Дзе праграме устаноўкі стварыць спасылкі для аплікацыі?
SelectStartMenuFolderLabel=Абярыце папку ў меню Пуск, у якой праграма ўстаноўкі створыць спасылкі на праграмы, потым націсніце Далей.
NoIconsCheck=&Не ствараць спасылкі
MustEnterGroupName=Неабходна ўвесці імя папкі.
BadGroupName=Назва папкі не можа ўключаць ніводнага з наступных сымбаляў:%n%n%1
NoProgramGroupCheck2=&Не ствараць папку ў меню Пуск

; *** "Ready to Install" wizard page
WizardReady=Усё прыгатавана да устаноўкі
ReadyLabel1=Усё прыгатавана да устаноўкі праграмы "[name]" на Ваш кампутар.
ReadyLabel2a=Націсніце Ўсталяваць для працяга ўстаноўкі або Назад калі неабходна зменіць наладкі.
ReadyLabel2b=Націсніце Ўсталяваць для працяга ўстаноўкі.
ReadyMemoUserInfo=Інфармацыя пра карыстальніка:
ReadyMemoDir=Папка устаноўкі:
ReadyMemoType=Тып Устаноўкі:
ReadyMemoComponents=Абраныя кампанэнты:
ReadyMemoGroup=Папка ў меню Пуск:

; *** "Installing" wizard page
WizardInstalling=Устаноўка
InstallingLabel=Чакайце пакуль праграма [name] усталёўваецца на Ваш кампутар.

; *** "Setup Completed" wizard page
;WizardFinished=Устаноўка скончаная
FinishedHeadingLabel=Заканчэнне ўстаноўкі "[name]".
FinishedLabelNoIcons=Устаноўка праграмы "[name]" скончаная.
FinishedLabel=Устаноўка праграмы "[name]" скончаная. Аплікацыя можа быць запушчана усталяванай спасылкай.
ClickFinish=Націсніце Скончыць каб пакінуць устаноўку.
FinishedRestartLabel=Для заканчэння ўстаноўкі праграмы "[name]", неабходна перагрузіць Ваш компутар. Перагрузіць зараз?
FinishedRestartMessage=Для заканчэння ўстаноўкі праграмы "[name]", неабходна перагрузіць Ваш компутар.%n%nПерагрузіць зараз?
ShowReadmeCheck=Так, я жадаю прагледзіць README файл
YesRadio=&Так, перагрузіць кампутар зараз
NoRadio=&Не, я перагружу пазней
; used for example as 'Run MyProg.exe'
RunEntryExec=Выканаць %1
; used for example as 'View Readme.txt'
RunEntryShellExec=Прагледзіць %1

; *** "Setup Needs the Next Disk" stuff
ChangeDiskTitle=Необходны наступны дыск
SelectDirectory=Абярыце папку
SelectDiskLabel2=Устаўце "Дыск %1" і націсніце OK.%n%nКалі файлы на гэтым дыске знаходзяцца ў папке, якая адрознівецца ад папкі ніжэй, увядзіце правільны шлях або націсніце Прагляд.
PathLabel=&Шлях:
FileNotInDir2=Файл "%1" не знойдзены ў "%2". Устаўце патрэбны дыск або абярыце іншую папку.
SelectDirectoryLabel=Вызначце знаходжанне наступнага дыска.

; *** Installation phase messages
SetupAborted=Устаноўка не скончаная.%n%nРазбярыцеся у праблеме а потым запусціце устаноўку спачатку.
EntryAbortRetryIgnore=Націсніце Паўтарыць, каб паспрабаваць зноў, Пракінуць каб працягнуць або Спыніць для адмены устаноўкі.

; *** Installation status messages
StatusCreateDirs=Стварэнне папак...
StatusExtractFiles=Распакоўка файлаў...
StatusCreateIcons=Стварэнне праграмных спасылак...
StatusCreateIniEntries=Стварэнне INI запісаў...
StatusCreateRegistryEntries=Стварэнне галінаў рэестра...
StatusRegisterFiles=Рэгістрацыя файлаў...
StatusSavingUninstall=Запіс інфармацыі для дэінсталяцыі...
StatusRunProgram=Заканчэнне ўстаноўкі...
StatusRollback=Адкат зменаў назад...

; *** Misc. errors
;ErrorInternal=Унутраная памылка %1
ErrorInternal2=Унутраная памылка %1
ErrorFunctionFailedNoCode=%1 не скончана
ErrorFunctionFailed=%1 не скончана; код %2
ErrorFunctionFailedWithMessage=%1 не скончана; код %2.%n%3
ErrorExecutingProgram=Немагчыма выканаць файл:%n%1

; *** "User Information" wizard page
WizardUserInfo=Інфармацыя пра карыстальніка
UserInfoDesc=Калі ласка, увядзіце вашу інфармацыю.
UserInfoName=&Імя карыстальніка:
UserInfoSerial=&Серыйны номер:
UserInfoOrg=&Арганізацыя:
UserInfoNameRequired=Вы мусіце ўвесці імя.

; *** DDE errors
;ErrorDDEExecute=DDE: Памылка пад час "execute" транзакцыі (код: %1)
;ErrorDDECommandFailed=DDE: Каманда не была паспяхова выкананая
;ErrorDDERequest=DDE: Памылка пад час "request" транзакцыі (код: %1)

; *** Registry errors
ErrorRegOpenKey=Памылка адкрыцця ключа рэестра:%n%1\%2
ErrorRegCreateKey=Памылка стварэння ключа рэестра:%n%1\%2
ErrorRegWriteKey=Памылка запісу ў ключ рэестра:%n%1\%2

; *** INI errors
ErrorIniEntry=Памылка стварэння INI сэкцыі ў файле "%1".

; *** File copying errors
FileAbortRetryIgnore=Націсніце Паўтарыць, каб паўтарыць спробу, Пракінуць каб пракінуць файл (не рэкамендавана) або Спыніць для скасавання ўстаноўкі.
FileAbortRetryIgnore2=Націсніце Паўтарыць, каб паўтарыць спробу, Пракінуць каб пракінуць файл (не рэкамендавана) або Спыніць для скасавання ўстаноўкі.
SourceIsCorrupted=Крынічны файл пашкоджаны
SourceDoesntExist=Крынічны файл "%1" не існуе
ExistingFileReadOnly=Існуючы файл мае атрыбут "Толькі для чытання".%n%nНацісніце Паўтарыць для зняцця атрыбута і паўтарэння спробы, Пракінуць каб пракінуць файл, або Спыніць для спынення ўстаноўкі.
ErrorReadingExistingDest=Адбылася памылка пры спробе чытання існуючага файла:
FileExists=Файл ужо існуе.%n%nПерапісаць яго?
ExistingFileNewer=Існуючы файл больш новы, чым той, які усталюецца. Рекамендавана пакінуць існуючы.%n%nПакінуць існуючы файл?
ErrorChangingAttr=Адбылася памылка пры спробе змены атрыбута існуючага файла:
ErrorCreatingTemp=Адбылася памылка пры спробе стварэння файла ў папке ўстаноўкі аплікацыі:
ErrorReadingSource=Адбылася памылка пры спробе чытання крынічнага файла:
ErrorCopying=Адбылася памылка пры спробе капіравання файла:
ErrorReplacingExistingFile=Адбылася памылка пры спробе замены існуючага файла:
ErrorRestartReplace=RestartReplace не скончана:
ErrorRenamingTemp=Адбылася памылка пры спробе перайменавання файла ў папке ўстаноўкі аплікацыі:
ErrorRegisterServer=Немагчыма зарэгістраваць DLL/OCX: %1
ErrorRegisterServerMissingExport=DllRegisterServer функцыя экспарту не знойдзена
ErrorRegisterTypeLib=Немагчыма зарэгістраваць бібліятэку тыпаў: %1

; *** Post-installation errors
ErrorOpeningReadme=Адбылася памылка пры спробе адчынення файла README:
ErrorRestartingComputer=У праграмы устаноўкі не атрымліваецца перагрузіць кампутар. Перагрузіце ўручную.

; *** Uninstaller messages
UninstallNotFound=Файл "%1" не існуе. Выдаленне немагчымае.
UninstallOpenError=Файл "%1" не адчыняецца. Выдаленне немагчымае.
UninstallUnsupportedVer=Дэісталяцыйны log-файл "%1" не ў фармаце які падтрымліваецца гэтай версіей дэінсталятара. Дэінсталяцыя немагчыма
UninstallUnknownEntry=Невядомы запіс (%1) быў знойдзены ў дэінсталяцыйным log-файле
ConfirmUninstall=Вы сапраўды жадаеце поўнасцю выдаліць праграму "%1" і ўсе яе кампанэнты?
OnlyAdminCanUninstall=Гэтая праграма можа быць дэінсталявана выключна карыстальнікам з правамі Адміністратара.
UninstallStatusLabel=Чакайце пакуль %1 выдаляецца з Вашаго кампутара.
UninstalledAll=Праграма "%1" паспяхова выдаленая з Вашага кампутара.
UninstalledMost=Выдаленне праграмы "%1" скончанае.%n%nНекаторые элементы не атрымалася выдаліць. Яны могуць быць выдаленыя ўручную.
UninstallDataCorrupted=Файл "%1" пашкоджаны. Дэінсталяцыя немагчымая
UninstalledAndNeedsRestart=Каб скончыць дэінсталяцыю %1, Ваш кампутар мусіць быць рэстартаваны.%n%nВы жадаеце зрабіць гэта зараз?

; *** Uninstallation phase messages
ConfirmDeleteSharedFileTitle=Выдаліць агульныя файлы?
ConfirmDeleteSharedFile2=Сістэма рапартуе, што наступны агульны файл больш не выкарыстоўваецца ніякай праграмай. Ці жадаеце Вы выдаліць гэты агульны файл?%n%nКалі некаторыя праграмы усё-ж ім карыстаюцца, а файл будзе выдалены, гэтыя праграмы не будуць нармальна працаваць. Калі Вы не ўпэўненыя, абярыце адказ Не. Пакіданне файла ў сістеме не мае ніякіх адмоўных уплываў.
SharedFileNameLabel=Файл:
SharedFileLocationLabel=Месца знаходжання:
WizardUninstalling=Статус выдалення
StatusUninstalling=Выдаляем %1...
