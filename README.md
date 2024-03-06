# Spring Boot Blog Application

This is a simple blog application built using Spring Boot framework. It allows admin to create, read, update, and delete blog posts, while user will register and comment on posts.

## Features

- User authentication and authorization
- CRUD operations for blog posts
- Commenting on blog posts
- Pagination of blog posts
- Search functionality

## Technologies Used

- Spring Boot
- Spring Security
- Spring Data JPA
- MySQL (or any other database of your choice)

## Setup

1. **Clone the repository:**

    ```bash
    git clone <repository_url>
    ```

2. **Configure the database:**

    - Create a MySQL database named `spring_boot_blog`.
    - Open `src/main/resources/application.properties` and configure your database settings:

        ```properties
        spring.datasource.url=jdbc:mysql://localhost:3306/spring_boot_blog
        spring.datasource.username=<your_username>
        spring.datasource.password=<your_password>
        ```
3. **Access the application:**

    Open your web browser and navigate to [http://localhost:8080](http://localhost:8080)

## Usage

- **Login:** Use the provided credentials to log in. You can also register if you don't have an account.
- **View Blog Posts:** Click on a blog post title to view its details, including comments.
- **Search:** Utilize the search bar to search for specific blog posts.
- **Pagination:** Navigate through multiple pages of blog posts using pagination controls.

## Contributing

Contributions are welcome! If you'd like to contribute to this project, please fork the repository and submit a pull request with your changes.

## License

This project is licensed under the [MIT License](LICENSE).
