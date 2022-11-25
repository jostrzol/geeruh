# geeruh

## Konfiguracja projektu i środowiska programistycznego

### Uruchamianie projektu

```sh
./gradlew run
```

Serwis będzie dostępny pod `http://localhost:8080`

### Testy

1. Jednostkowe: 
    ```sh
    ./gradlew test
    ```
2. Integracyjne:
    ```sh
    ./gradlew integrationTest
    ```
3. Raport pokrycia testów (dostępny w `./build/reports/tests/`):
    ```sh
    ./gradlew jacocoTestReport
    ```
   
### OpenAPI

Dokumentacja endpoiontów OpenAPI dostępna po uruchomieniu serwisu pod:
* `http://localhost:8080/swagger-ui.html` (wersja interaktywna - swagger)
* `http://localhost:8080/v3/api-docs` (wersja json)

### Checkstyle

W Intellij należy pobrać [plugin Checkstyle](https://plugins.jetbrains.com/plugin/1065-checkstyle-idea). Następnie w
Settings > Tools > Checkstyle należy dodać nowe entry w Configuration File i wybrać plik z
projektu `config/checkstyle/checkstyle.xml`. Następnie warto dodać check w sekcji Commit > Show Commit Options (gear
icon) > Scan with Checkstyle.