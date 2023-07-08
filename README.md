# SimpleCurrencyExchange
Simple Currency Exchange - Rest Api 
using Spring Boot 3, JPA, H2 Database (in-memory), NBP Web Api

Build app with Maven

## Explore Rest APIs

The app defines following APIs.

    GET /api/account/{id}
    GET /api/account/{id}/{code}
    POST /api/account
    PUT /api/account/{id}/exchange/{code}
    
    example:
    
    - view current account data
    http://localhost:8080/api/account/1

    - view account data, show balance in PLN
    http://localhost:8080/api/account/1/PLN

    - create new USD account
    http://localhost:8080/api/account
    accountBalance -> initial balance in PLN
    
    Accept: application/json
    Content-Type: application/json
    {
        "ownerFirstName" : "Jan",
        "ownerLastName" : "Kowalski",
        "accountBalance" : 1000
    }
    
    - convert USD account to PLN (currency conversion by current NBP mid rate)  
    http://localhost:8080/api/account/1/exchange/PLN

    - return back from PLN to USD (currency conversion by current NBP mid rate) 
    http://localhost:8080/api/account/1/exchange/USD
