# Countries

Countries is a Java application for displaying countries and their cities.

## Installation

build the project and run docker compose command.

```bash
docker compose up
```

## Description and usage

### CityController Endpoints

The CityController provides RESTful endpoints for managing city data within the application. Below are the available endpoints and their functionalities:

##  GET /cities: Retrieves a paginated list of all cities.

###  Parameters:

•  page: The page number to retrieve (default is 0).

•  size: The number of records per page (default is 5).

###  Responses:

•  200 OK: Successfully retrieved cities.

•  204 No Content: No cities found.

##  GET /cities/list: Lists all unique city names.

###  Responses:

•  200 OK: Successfully retrieved unique cities.

##  GET /cities/filter: Gets all cities by a specified country name.

###  Parameters:

•  countryName: The name of the country to filter cities by.

###  Responses:

•  200 OK: Successfully retrieved cities for the specified country.

•  404 Not Found: No cities found for the specified country.

##  GET /cities/search: Searches for cities by a specified city name.

###  Parameters:

•  cityName: The name of the city to search for.

###  Responses:

•  200 OK: Successfully retrieved cities for the specified name.

•  404 Not Found: No cities found for the specified name.

##  PUT /cities/{id}/edit: Edits the details of an existing city by ID. This endpoint is protected and requires the ROLE_EDITOR authority.
### For the testing proposes you can use a user with username: admin and password: admin to edit a city

###  Parameters:

•  id: The ID of the city to edit.

•  city: The new name of the city.

•  logo: A multipart file containing the new logo of the city.

###  Responses:

•  202 Accepted: City has been successfully updated.

•  401 Unauthorized: Unauthorized access to the endpoint.

•  403 Forbidden: Access is forbidden due to insufficient permissions.

•  404 Not Found: City with the provided ID not found.

•  500 Internal Server Error: Failed to update the city.

Each endpoint is designed to handle specific operations related to city data management, ensuring a clear and organized API structure for client applications to interact with. Ensure that proper authentication and authorization mechanisms are in place to protect sensitive endpoints.