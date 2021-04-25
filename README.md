# Trade Reporting Engine

## Design

1. Java 11
2. Spring Boot application
   
#### Web
1. Open API specification to be generated from the TradingController
2. Simple TradingController with single endpoint `/trades` to return filtered trades
3. TradingController supports JSON and XML representations

#### Service

1. Simple TradingService which will apply the filters
2. Filters and implemented as Predicates which are inserted into the retrieved 
   stream of Trades
3. Filters injected into the TradingService are Spring managed beans 
   (of type Predicate<Trade>) which are created by the TradingConfiguration
4. Simple ConfirmationService which will map from the XML model to the JPA 
   model and insert into the TradingRepository
5. Rudimentary filter configuration is extracted to the TradingConfiguration to 
   allow for name and currency modification.  The anagram filter has no configuration.   
6. Both services will be provided with the TradingRepository to manage Trades.   

#### Data

1. H2 is used as an in memory database
2. Flyway is used to create (and migrate) a simple schema.
3. A single class is required to represent the Trade 
4. A single class is required to represent the TradeRepository
5. The Trade represents the flattened data extracted from the XML, with 
   the addition of a generated UUID for the primary key.

#### Import

1. A single ConfirmationImport command is configured with the packaged 
   sample events xml files as Resource[] and the ConfirmationService.
2. The ConfirmationImport is responsible for streaming the files, mapping
   the XML into the RequestConfirmation and saving as a Trade using the
   ConfirmationService.

#### Omissions amd Improvements

1. No tracing (which I would add with Spring Sleuth)
2. No container based testing (which I would add with TestContainers)
3. No Webflux (which I would add with webflux + r2dbc-h2 + reactor)
4. No API endpoint to support the import of RequestConfirmations(s)
5. No PostgreSQL (replace the in memory H2 using a production spring profile)
6. The Trade JPA model should be exposed out through the API using an API
   specific domain model
7. No HATEOAS support (which I would add with Spring Hateoas)   
8. Filters are not configurable at runtime
9. Filters are not definable at runtime

## Build and Run

Build the application

```bash
./mvnw clean install
```

Run the application

```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

Exercise the application

```bash
curl --request GET \
     --header "Accept: application/json" \
     http://localhost:8080/trades
```

Additional documentation can be found by generating the generic documentation

```bash
./mvnw package site
```
