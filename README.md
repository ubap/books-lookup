[![Build Status](https://travis-ci.com/ubap/books-lookup.svg?branch=master)](https://travis-ci.com/ubap/books-lookup)

### Books lookup tool
This tool allows you to search for books and compare their prices.

It is written in Java, and utilises Spring Boot framework. Thymeleaf is used for the html templates. Frontend is written in jQuery and Bootstrap.

The application is written so the service providers can easily be added or removed.
Currently Google Books is used as a Book Library. Ebay and Google Books are used as book stores. [Fixer](https://fixer.io) service is used to resolve currency exchange rates.

Demo version of this tool is hosted at [http://bookslookup.eu](http://bookslookup.eu).
It is using free API keys, which are heavily limited in number of requests, so service outage is nothing unexpected.

Example book prices comparisons:
* Book of African-American Quotations - [http://bookslookup.eu/search/Book%20of%20African-American%20Quotations/book/0486112446,9780486112442](http://bookslookup.eu/search/Book%20of%20African-American%20Quotations/book/0486112446,9780486112442)
* Tribes on the Hill - [http://bookslookup.eu/search/Tribes%20on%20the%20Hill/book/0897890728,9780897890724](http://bookslookup.eu/search/Tribes%20on%20the%20Hill/book/0897890728,9780897890724)

Software requirements:
* Java 8
* Git - to clone the repository
* Following steps assume that Linux is used.

How to configure:
*  Files `src/main/resources/application.properties` and `src/main/resources/application-prod.properties` contain the configuration.
`application.properties` is used when application is not started in production mode. When the application is started in production mode `application-prod.properties` is used.
 This file has 3 lines that have to be filled in:
    ```
    server.port = 8080 # the port application will run on
    keys.googleApi = # put here your key to Google API service
    keys.ebaySecurityAppName = # put here your Ebay security application name
    keys.fixerApi = # but here your Fixer API key
    logging.level.com.ubap.bookslookup = DEBUG
    ```

How to build:
1. Clone this repository `git clone https://github.com/ubap/books-lookup`
2. `cd bookslookup/`
3. `./gradlew build`
4. The jar will be created under `build/libs` directory.

How to run:
* Directly from gradle `./gradlew bootRun`
* Or execute the jar `java -jar build/libs/bookslookup-0.0.1-SNAPSHOT.jar`
* To run in production mode (port on port 80), append `-Dspring.profiles.active=prod` to the command:
```
./gradlew bootRun -Dspring.profiles.active=prod
# or
java -Dspring.profiles.active=prod -jar build/libs/bookslookup-0.0.1-SNAPSHOT.jar 
```