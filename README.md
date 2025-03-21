# Auth E-commerce Nine

Auth E-commerce Nine is a robust authentication and authorization system for e-commerce platforms. It provides features like JWT-based authentication, OAuth2 integration, two-step verification, password reset, and role-based access control.

## Features

- **JWT Authentication**: Secure token-based authentication.
- **OAuth2 Integration**: Login with Google and GitHub.
- **Two-Step Verification**: Adds an extra layer of security.
- **Password Reset**: Secure token-based password reset.
- **Role-Based Access Control**: Admin and user roles with specific privileges.
- **Email Notifications**: AWS SES integration for email delivery.

## Prerequisites

- Java 17
- Maven 3.9.2
- PostgreSQL
- AWS SES credentials for email service

## Installation

1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/auth-ecommerce-nine.git
   cd auth-ecommerce-nine
   ```

2. Configure the database:
   - Update the `application.yml` files in `src/main/resources` and `target/classes` with your PostgreSQL credentials.

3. Configure AWS SES:
   - Set up your AWS credentials in the environment or AWS configuration.

4. Build the project:
   ```bash
   ./mvnw clean install
   ```

5. Run the application:
   ```bash
   ./mvnw spring-boot:run
   ```

6. Access the application:
   - API: `http://localhost:8086`
   - Swagger UI: `http://localhost:8086/swagger-ui.html`

## Usage

### Endpoints

- **Authentication**
  - `POST /api/v1/auth/authenticate`: Login with email and password.
  - `POST /api/v1/auth/register`: Register a new user.
  - `POST /api/v1/auth/logout`: Logout and clear cookies.

- **Two-Step Verification**
  - `POST /api/v1/auth/twoSteps`: Send a verification code to the user's email.

- **Password Reset**
  - `POST /api/v1/auth/generate-token`: Generate a reset token.
  - `POST /api/v1/auth/verify-token`: Verify the reset token.
  - `POST /api/v1/auth/update-password`: Update the password.

- **OAuth2**
  - Login with Google or GitHub.

### Roles and Privileges

- **Admin**: Full access to all resources.
- **User**: Limited access to specific resources.

## Configuration

### Environment Variables

- `SPRING_DATASOURCE_URL`: Database URL.
- `SPRING_DATASOURCE_USERNAME`: Database username.
- `SPRING_DATASOURCE_PASSWORD`: Database password.
- `AWS_ACCESS_KEY_ID`: AWS access key.
- `AWS_SECRET_ACCESS_KEY`: AWS secret key.

### Docker

Run the PostgreSQL database using Docker:
```bash
docker-compose up -d
```

## Testing

Run the tests:
```bash
./mvnw test
```

## License

This project is licensed under the MIT License. See the [LICENSE](LICENSE) file for details.

## Contributing

Contributions are welcome! Please fork the repository and submit a pull request.

## Contact

For inquiries, contact [ikaueveloper@gmail.com](mailto:ikaueveloper@gmail.com).
