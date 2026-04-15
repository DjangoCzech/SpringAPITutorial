# School API Demo (Spring Boot)

Tento projekt je vyukova ukazka REST API pro webovy projekt. Tema: studenti, predmety a zapisy studentu do predmetu.

Cil je, aby se vyklad + live coding vesel do cca 90 minut a studenti videli:
- navrh API
- vrstvy Controller -> Service -> Repository
- JPA entity + relace
- validace requestu
- centralizovane zpracovani chyb
- data v JSON
- data v H2 databazi (vcetne SQL seedu)

## 1. Co je potreba predem
1. Nainstalovat JDK 17.
2. Nainstalovat Maven 3.9+ (nebo pouzit IDE s Maven podporou).
3. Otevrit projekt v IDE (IntelliJ / VS Code).

## 2. Struktura projektu a konfigurace
1. Projekt je Maven Spring Boot aplikace.
2. `pom.xml` obsahuje zavislosti: Web, Validation, JPA, H2, Test.
3. `application.yml` zapina H2 konzoli na `/h2-console` a konfiguruje in-memory DB.

## 3. Spusteni projektu
1. Otevrit terminal ve slozce projektu.
2. Spustit:
   ```bash
   mvn spring-boot:run
   ```
3. API pobezi na `http://localhost:8080`.
4. H2 konzole pobezi na `http://localhost:8080/h2-console`.
5. Webova stranka (HTML/CSS/JS) pobezi na `http://localhost:8080`.

## 4. Prvni test API (bez seedu)
1. `GET /api/students`
2. `GET /api/courses`
3. `GET /api/enrollments`

Na cistem startu budou seznamy prazdne.

## 5. Naplneni dat z JSON pres API
1. Nahled JSON dat:
   - `GET /api/admin/json-preview`
2. Import JSON do DB:
   - `POST /api/admin/import-json`
3. Overeni:
   - `GET /api/students`
   - `GET /api/courses`
   - `GET /api/enrollments`

Poznamka: endpoint importu je didakticky zamereny. Pro produkci by se resil idempotentni import a audit.

## 6. Naplneni dat primo v H2 konzoli (SQL seed)
1. Spustit aplikaci.
2. Otevrit H2 konzoli: `http://localhost:8080/h2-console`.
3. JDBC URL nechat: `jdbc:h2:mem:schooldb;MODE=PostgreSQL;DB_CLOSE_DELAY=-1;DB_CLOSE_ON_EXIT=FALSE`
4. User: `sa`, heslo prazdne.
5. V SQL editoru spustit obsah souboru `src/main/resources/db/h2/seed.sql`.
6. Overit data prikazy:
   ```sql
   SELECT * FROM students;
   SELECT * FROM courses;
   SELECT * FROM enrollments;
   ```

## 7. API endpointy pro vyuku
1. Studenti
   - `GET /api/students`
   - `GET /api/students/{id}`
   - `POST /api/students`
   - `PUT /api/students/{id}`
   - `DELETE /api/students/{id}`
2. Predmety
   - `GET /api/courses`
   - `GET /api/courses/{id}`
   - `POST /api/courses`
   - `PUT /api/courses/{id}`
   - `DELETE /api/courses/{id}`
3. Zapisy
   - `GET /api/enrollments`
   - `GET /api/enrollments/{id}`
   - `POST /api/enrollments`
   - `PATCH /api/enrollments/{id}/grade`
   - `DELETE /api/enrollments/{id}`
4. Dashboard
   - `GET /api/dashboard/stats`
5. Admin
   - `GET /api/admin/json-preview`
   - `POST /api/admin/import-json`

## 8. Jednoducha webova stranka (HTML/CSS/JS)
1. Stranka je ve slozce `src/main/resources/static`.
2. Spring Boot ji obslouzi automaticky jako staticky obsah.
3. Otevri v prohlizeci:
   - `http://localhost:8080`
4. Klikni na tlacitko "Nacist studenty z API".
5. Stranka zavola `GET /api/students` a vykresli studenty do tabulky.

## 9. Request priklady
1. `POST /api/students`
   ```json
   {
     "firstName": "Karel",
     "lastName": "Urban",
     "email": "karel.urban@school.cz",
     "studyYear": 2
   }
   ```
2. `POST /api/courses`
   ```json
   {
     "code": "API301",
     "title": "Navrh REST API",
     "teacherName": "Ing. Eva Mala",
     "credits": 5
   }
   ```
3. `POST /api/enrollments`
   ```json
   {
     "studentId": 1,
     "courseId": 1,
     "enrolledAt": "2026-04-15"
   }
   ```
4. `PATCH /api/enrollments/1/grade`
   ```json
   {
     "grade": "A"
   }
   ```

## 10. Co vysvetlit studentum (90 min plan)
1. 0-15 min: architektura projektu, zavislosti, bootstrap aplikace.
2. 15-35 min: entity, relace, repository a jak funguje JPA.
3. 35-55 min: service vrstva a business pravidla.
4. 55-75 min: controllery, validace a error handling.
5. 75-90 min: import dat (JSON + SQL), test endpointu a diskuze o produkcnich rozdilech.

## 11. Popis kazdeho vygenerovaneho souboru (co dela + proc se tak jmenuje)
1. `.gitignore`
   - Co dela: ignoruje build artefakty a IDE soubory.
   - Proc nazev: standardni nazev pro Git pravidla ignorovani.
2. `pom.xml`
   - Co dela: definuje Maven projekt, Spring Boot parent a zavislosti.
   - Proc nazev: Maven standard ocekava konfiguraci v souboru `pom.xml`.
3. `README.md`
   - Co dela: dokumentace od konfigurace po test API.
   - Proc nazev: bezny a ocekavany vstupni dokument projektu.
4. `src/main/resources/application.yml`
   - Co dela: centralni konfigurace Springu, DB a H2 konzole.
   - Proc nazev: Spring Boot automaticky nahrava `application.yml`.
5. `src/main/resources/data/sample-data.json`
   - Co dela: zdrojova ukazkova data pro import pres API.
   - Proc nazev: `sample-data` rika, ze jde o ukazkova data; `.json` urcuje format.
6. `src/main/resources/db/h2/seed.sql`
   - Co dela: SQL seed pro rychle naplneni H2 databaze.
   - Proc nazev: `seed.sql` je bezny nazev pro inicializacni data.
7. `src/main/java/cz/schoolapi/demo/SchoolApiApplication.java`
   - Co dela: startovni trida Spring Boot aplikace s `main` metodou.
   - Proc nazev: nazev odpovida nazvu aplikace + konvence `Application`.
8. `src/main/java/cz/schoolapi/demo/model/Grade.java`
   - Co dela: enum povolenych znamek.
   - Proc nazev: vystizne pojmenovava typ hodnoty (znamka).
9. `src/main/java/cz/schoolapi/demo/model/Student.java`
   - Co dela: JPA entita studenta.
   - Proc nazev: nazev entity odpovida domene "student".
10. `src/main/java/cz/schoolapi/demo/model/Course.java`
   - Co dela: JPA entita predmetu.
   - Proc nazev: nazev entity odpovida domene "course".
11. `src/main/java/cz/schoolapi/demo/model/Enrollment.java`
   - Co dela: JPA entita zapisu studenta do predmetu.
   - Proc nazev: "enrollment" presne popisuje vztah student-predmet.
12. `src/main/java/cz/schoolapi/demo/repository/StudentRepository.java`
   - Co dela: DB pristup ke studentum pres JpaRepository.
   - Proc nazev: konvence Spring Data je `<Entity>Repository`.
13. `src/main/java/cz/schoolapi/demo/repository/CourseRepository.java`
   - Co dela: DB pristup k predmetum + hledani podle kodu.
   - Proc nazev: konvence `<Entity>Repository`.
14. `src/main/java/cz/schoolapi/demo/repository/EnrollmentRepository.java`
   - Co dela: DB pristup k zapisum + kontrola duplicity student/predmet.
   - Proc nazev: konvence `<Entity>Repository`.
15. `src/main/java/cz/schoolapi/demo/dto/StudentRequest.java`
   - Co dela: vstupni payload pro vytvoreni/upravu studenta + validace.
   - Proc nazev: `Request` znaci data prichazejici z HTTP requestu.
16. `src/main/java/cz/schoolapi/demo/dto/StudentResponse.java`
   - Co dela: vystupni payload studenta vracen z API.
   - Proc nazev: `Response` znaci data odchazejici klientovi.
17. `src/main/java/cz/schoolapi/demo/dto/CourseRequest.java`
   - Co dela: vstupni payload predmetu + validace.
   - Proc nazev: konvence `Request` pro vstup.
18. `src/main/java/cz/schoolapi/demo/dto/CourseResponse.java`
   - Co dela: vystupni payload predmetu.
   - Proc nazev: konvence `Response` pro vystup.
19. `src/main/java/cz/schoolapi/demo/dto/EnrollmentRequest.java`
   - Co dela: vstupni payload pro vytvoreni zapisu.
   - Proc nazev: konvence `Request`.
20. `src/main/java/cz/schoolapi/demo/dto/EnrollmentResponse.java`
   - Co dela: vystupni payload zapisu vcetne studenta a predmetu.
   - Proc nazev: konvence `Response`.
21. `src/main/java/cz/schoolapi/demo/dto/GradeUpdateRequest.java`
   - Co dela: specialni request pro zmenu znamky.
   - Proc nazev: nazev primo rika, ze meni grade.
22. `src/main/java/cz/schoolapi/demo/dto/DashboardStatsResponse.java`
   - Co dela: vraci agregovane statistiky API.
   - Proc nazev: `DashboardStats` rika, ze jde o souhrnna cisla.
23. `src/main/java/cz/schoolapi/demo/dto/JsonSeedPayload.java`
   - Co dela: mapuje strukturu JSON souboru pro import.
   - Proc nazev: `SeedPayload` znamena data urcena k naseti.
24. `src/main/java/cz/schoolapi/demo/exception/ResourceNotFoundException.java`
   - Co dela: vyjimka pro chybu 404 (nenalezeno).
   - Proc nazev: jasne popisuje, co se stalo.
25. `src/main/java/cz/schoolapi/demo/exception/BusinessRuleException.java`
   - Co dela: vyjimka pro poruseni business pravidla.
   - Proc nazev: nazev vysvetluje domenovy problem.
26. `src/main/java/cz/schoolapi/demo/exception/ApiErrorResponse.java`
   - Co dela: jednotny format chybove odpovedi API.
   - Proc nazev: `ApiErrorResponse` popisuje strukturu odpovedi.
27. `src/main/java/cz/schoolapi/demo/exception/GlobalExceptionHandler.java`
   - Co dela: centralni mapovani vyjimek na HTTP statusy.
   - Proc nazev: `Global` + `Handler` rika, ze obsluhuje chyby globalne.
28. `src/main/java/cz/schoolapi/demo/service/StudentService.java`
   - Co dela: business logika studentu.
   - Proc nazev: konvence `<Domena>Service`.
29. `src/main/java/cz/schoolapi/demo/service/CourseService.java`
   - Co dela: business logika predmetu.
   - Proc nazev: konvence `<Domena>Service`.
30. `src/main/java/cz/schoolapi/demo/service/EnrollmentService.java`
   - Co dela: business logika zapisu a znamek.
   - Proc nazev: konvence `<Domena>Service`.
31. `src/main/java/cz/schoolapi/demo/service/DashboardService.java`
   - Co dela: pripravuje agregovane statistiky.
   - Proc nazev: nazev odpovida oblasti dashboard dat.
32. `src/main/java/cz/schoolapi/demo/service/JsonSeedService.java`
   - Co dela: cte JSON soubor a importuje ho do DB.
   - Proc nazev: `JsonSeed` popisuje zdroj a ucel.
33. `src/main/java/cz/schoolapi/demo/controller/StudentController.java`
   - Co dela: REST endpointy pro studenty.
   - Proc nazev: konvence `<Domena>Controller`.
34. `src/main/java/cz/schoolapi/demo/controller/CourseController.java`
   - Co dela: REST endpointy pro predmety.
   - Proc nazev: konvence `<Domena>Controller`.
35. `src/main/java/cz/schoolapi/demo/controller/EnrollmentController.java`
   - Co dela: REST endpointy pro zapisy + zmenu znamky.
   - Proc nazev: konvence `<Domena>Controller`.
36. `src/main/java/cz/schoolapi/demo/controller/DashboardController.java`
   - Co dela: endpoint se statistikami.
   - Proc nazev: odpovida dashboard API casti.
37. `src/main/java/cz/schoolapi/demo/controller/AdminController.java`
   - Co dela: technicke endpointy pro preview/import seedu.
   - Proc nazev: jde o administracni (ne bezne uzivatelske) operace.
38. `src/main/resources/static/index.html`
   - Co dela: hlavni webova stranka s tabulkou studentu a tlacitkem pro nacitani dat.
   - Proc nazev: `index.html` je standardni vychozi stranka webu.
39. `src/main/resources/static/styles.css`
   - Co dela: ciste CSS styly pro vzhled stranky.
   - Proc nazev: `styles.css` je bezny nazev centralniho souboru se styly.
40. `src/main/resources/static/app.js`
   - Co dela: JavaScript logika pro volani API a vykresleni studentu.
   - Proc nazev: `app.js` obsahuje hlavni chovani frontend aplikace.

## 12. Co je idealni rozsireni po hodine
1. Pridat OpenAPI/Swagger dokumentaci.
2. Pridat testy (unit + integration).
3. Vymenit H2 za PostgreSQL a pridat Flyway migrace.
4. Pridat autentizaci (Spring Security + JWT).

## 13. Licencni poznamka
Data v JSON/SQL jsou fiktivni a urcena pouze pro vyuku.
