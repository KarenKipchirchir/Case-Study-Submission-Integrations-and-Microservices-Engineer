# Case Study Submission — Integrations and Microservices Engineer

## Running the Spring Boot Application Locally

### 1. Prerequisites

Ensure the following tools are installed:

* **Java JDK 21**
* **Git**
* **Apache Maven**
* **MySQL Server**
* **Postman** (optional, for testing the APIs)

Verify your installations:

```bash
java -version
git --version
mvn -version
```

### 2. Clone the Repository

Open a terminal and run:

```bash
git clone https://github.com/KarenKipchirchir/Case-Study-Submission-Integrations-and-Microservices-Engineer.git
```

Navigate to the project directory:

```bash
cd Case-Study-Submission-Integrations-and-Microservices-Engineer
```

### 3. Configure the Database

Ensure MySQL is running and create the database configured for the application.

Check `src/main/resources/application.yml` for the required database name, connection URL, username, password, and any other application-specific configuration.

Update the configuration with your local MySQL credentials. Do not commit actual database passwords or other secrets to GitHub.

Ensure that any required external services are accessible before starting the application.

### 4. Run the Application

From the project root directory, run:

```bash
mvn clean spring-boot:run
```

Alternatively, compile and package the application first:

```bash
mvn clean package
```

Then run the generated JAR:

```bash
java -jar target/<APPLICATION_NAME>.jar
```

Replace `<APPLICATION_NAME>.jar` with the actual JAR filename generated in the `target` directory.

By default, the application runs on port `8080`, unless configured otherwise.

### 5. Access Swagger UI

The application includes Swagger UI for exploring and testing the available API endpoints.

Once the application has started, open:

**http://localhost:8080/swagger-ui/index.html**

The OpenAPI specification is typically available at:

**http://localhost:8080/v3/api-docs**

These URLs assume the default Swagger UI and OpenAPI paths have not been customized.

### 6. Test the APIs Using Postman

A Postman collection containing working sample requests is included with the submission.

To use it:

1. Open Postman.
2. Select **Import**.
3. Import the provided Postman collection file.
4. Review the sample requests and update any environment-specific values, if necessary.
5. Send the requests to test the application.

Ensure the Spring Boot application and any required external services are running before executing the requests.

### 7. Troubleshooting

| Issue                       | Possible solution                                                                              |
| --------------------------- | ---------------------------------------------------------------------------------------------- |
| Java version error          | Ensure JDK 21 is installed and configured correctly.                                           |
| MySQL connection failure    | Verify that MySQL is running and that the database and credentials match `application.yml`.    |
| Port already in use         | Stop the process occupying port `8080` or configure a different port.                          |
| Application startup failure | Review the console logs and verify the application configuration and required dependencies.    |
| Swagger UI unavailable      | Confirm the application has started and check whether the Swagger UI path has been customized. |
| API request fails           | Verify the request URL, HTTP method, headers, request body, and required external services.    |

### Notes

* Run all Maven commands from the project root directory.
* Keep credentials and secrets out of source control.
* Refer to `application.yml` for application-specific configuration.
* Use the included Postman collection for sample API requests or Swagger UI to explore the endpoints interactively.
