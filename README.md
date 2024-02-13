# Blogging Application Readme

This readme provides information about a blogging application developed using Spring Boot and Hibernate concepts. The application includes entities for users, posts, comments, and user roles. Additionally, it provides instructions for obtaining a JWT token to perform certain operations.

## Application Setup

1. **Clone Repository**: Clone the repository to your local machine.

2. **Database Configuration**: Configure your database settings in `application.properties` file located in `src/main/resources`.

3. **Run Application**: Run the application using your preferred IDE or by executing `mvn spring-boot:run` command in the terminal.

4. **Obtain Encoded Password**: Upon running the application, check the console logs to find the encoded password for the admin user specified in the main file. 

5. **Save Encoded Password**: Use the encoded password obtained in the previous step and save it in the password column for the respective user role in your database.

6. **Generate JWT Token**: After saving the encoded password, generate the JWT token by following the steps below.

## Obtaining JWT Token

To obtain a JWT token for performing POST, PUT, and DELETE operations:

1. **Select No Auth in Authorization**: In your API client (e.g., Postman), select "No Auth" in the authorization section.

2. **Add Authorization Header**: In the Headers section, add `Authorization` as the key and `Bearer <generated token>` as the value. Replace `<generated token>` with the JWT token generated by the application.

3. **Perform Operations**: With the JWT token added in the Authorization header, you can now perform POST, PUT, and DELETE operations on the application's endpoints.

## Endpoints

The application provides the following endpoints for managing users, posts, comments, and user roles:

- `/api/users`: CRUD operations for managing users.
- `/api/posts`: CRUD operations for managing posts.
- `/api/comments`: CRUD operations for managing comments.
- `/api/user-roles`: CRUD operations for managing user roles.

Please refer to the API documentation or codebase for detailed information about the available endpoints and their usage.

## Security

The application uses JWT (JSON Web Token) for authentication and authorization. It provides secure access to the endpoints by generating and validating JWT tokens.

## Dependencies

The application utilizes the following dependencies:

- Spring Boot
- Hibernate
- Spring Security
- JWT (JSON Web Token)

## Additional Notes

- Ensure to secure your application in production environments by configuring proper security measures such as HTTPS, CORS policies, and input validation.

- Keep your database credentials and sensitive information secure and avoid exposing them in the codebase or configuration files.

- Regularly update your dependencies and follow best practices for secure coding to mitigate potential security vulnerabilities.

Feel free to contribute to the project by submitting pull requests, reporting issues, or suggesting improvements.

---

This readme provides an overview of the blogging application developed using Spring Boot and Hibernate concepts. Follow the instructions provided to set up the application, obtain a JWT token, and perform operations securely. For any further assistance or inquiries, please contact the project contributors.