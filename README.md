# geeruh

## Konfiguracja projektu i środowiska programistycznego

### Checkstyle

W Intellij należy pobrać [plugin Checkstyle](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea). Następnie w
Settings > Tools > Checkstyle należy dodać nowe entry w Configuration File i wybrać plik z
projektu `config/checkstyle/checkstyle.xml`. Następnie warto dodać check w sekcji Commit > Show Commit Options (gear
icon) > Scan with Checkstyle.