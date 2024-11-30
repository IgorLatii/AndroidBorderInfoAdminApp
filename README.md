# Border Info Management App

## Project Description
This Android application is designed to facilitate management of a remote database related to border crossing regulations in the Republic of Moldova. The primary goal of the app is to provide administrators with an intuitive tool to manage database entries directly from a mobile device. It supports such operations as retrieving, adding, updating, and deleting information about border regulations.

## Key Features
- **Administrator Authentication**: Access to the admin panel is secured with a username and password.
- **CRUD Operations**:
  - **GET**: Retrieve database information based on a specific keyword.
  - **POST**: Add new entries to the database.
  - **PUT**: Update existing entries in the database.
  - **DELETE**: Remove entries based on a specific keyword.
- **User-friendly Interface**: A clean and simple UI design for quick interaction with database records.

## Libraries and Technologies Used
- **Retrofit**: For seamless API communication with the remote backend.
- **Gson**: For parsing JSON responses from the API.
- **Spring Boot (Backend)**: Provides the REST API for CRUD operations.

## Possible Improvements
1. **HTTPS Support**: Enhance security by fully transitioning to HTTPS connections.
2. **Role-Based Access Control**: Implement differentiated access levels for administrators and regular users.
3. **Error Handling**: Provide more descriptive error messages and offline support for better user experience.
4. **Push Notifications**: Notify administrators of updates or changes in the database.
5. **Data Validation**: Add more robust input validation to minimize errors.

This application demonstrates the integration of a mobile frontend with a RESTful backend, showcasing API interactions.
