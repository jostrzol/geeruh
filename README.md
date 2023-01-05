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

### Migracje bazy danych

1. Zmiana modelu persystencji.
2. Tunel ssh do maszyny z bazą danych sprzed zmiany. Baza powinna działać na porcie 5432, posiadać bazę danych 'geeruh' 
   i użytkownika 'geeruh'.
3. Generacja pliku różnicowego za pomocą pluginu Liquibase do Gradle:
   ```sh
   ./gradlew diffChangeLog -PchangelogName=<nazwa-changeloga> -PdbPassword=<haslo-do-bazy=danych>
   ```
4. Powstanie plik `src/db/changes/<nazwa-changeloga>.geeruh` zawierająca zmianę. Należy zweryfikować poprawność zmiany.
   
   W szczególności mogą pojawiać się wpisy podobne do:
   ```groovy
   changeSet(id: '''1672928324393-1''', author: '''tuco (generated)''') {
      dropUniqueConstraint(constraintName: '''UC_USERSLOGIN_COL''', tableName: '''users''')
   }

   changeSet(id: '''1672928324393-2''', author: '''tuco (generated)''') {
      addUniqueConstraint(columnNames: '''login''', constraintName: '''UC_USERSLOGIN_COL''', tableName: '''users''')
   }
   ```
   Należy je usunąć bo nic nie wnoszą.
5. (opcjonalne) Przerwać tunel ssh.
6. Zacommitować plik do repozytorium kodu.

Po takiej operacji przy następnym wdrożeniu backendu nastąpi również migracja bazy danych opisana w wygenerowanym pliku.