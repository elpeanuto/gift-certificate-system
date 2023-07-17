### Task

This version is an extension of REST API Advanced module and covers following topics:

1. Spring Security framework
2. Oauth2 and OpenId Connect
3. JWT token

#### Application requirements

1. Spring Security should be used as a security framework.
2. Application should support only stateless user authentication and verify integrity of JWT token.
3. Users should be stored in a database with some basic information and a password.

User Permissions:

     - Guest:
        * Read operations for main entity.
        * Signup.
        * Login.
     - User:
        * Make an order on main entity.
        * All read operations.
     - Administrator (can be added only via database call):
        * All operations, including addition and modification of entities.

To access the Jenkins instance associated with this project, you will need the following credentials:

- **Username**: developer
- **Password/API Token**: developer

Please use these credentials to log in to Jenkins and perform necessary actions, such as building jobs or canceling builds.