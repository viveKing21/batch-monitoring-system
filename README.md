Batch Monitoring Java Mini Project
This is a simple Java mini project for batch monitoring, designed to manage two types of members: admin and faculty. The faculty members can register, login, and view all the assigned batches, while the admin has static username and password and can create batches and assign them to faculty members.

Features
Admin functionality:

Login with static username and password
Create batches
Assign batches to faculty members
Faculty functionality:

Register as a faculty member
Login
View all assigned batches
Technologies Used
Java
MySQL (for database management)
JavaFX (for the graphical user interface)
Installation
Clone the repository:

shell
Copy code
git clone https://github.com/your-username/batch-monitoring-mini-project.git
Import the project into your preferred Java IDE (e.g., Eclipse, IntelliJ IDEA).

Set up the MySQL database:

Create a database named batch_monitoring.
Execute the SQL script database.sql located in the project's root directory to create the necessary tables and populate them with sample data.
Configure the database connection:

Open the DBConnection.java file located in the src/main/java/com/batchmonitoring/db directory.
Modify the connection URL, username, and password according to your MySQL configuration.
Build and run the application.

Usage
Run the application.

Admin functionality:

Login using the static username and password.
Create batches by providing the necessary details.
Assign batches to faculty members by selecting a batch and a faculty member from the available options.
Faculty functionality:

Register as a faculty member by providing the required information.
Login using the credentials used during registration.
View all assigned batches.
Screenshots
(Optional) Add screenshots of the application to visually represent the user interface and its functionality. You can place the screenshots in a directory called screenshots and include them here using the following format:

Screenshot 1
Screenshot 2

Contributing
Contributions are welcome! If you have any suggestions, bug reports, or improvements, please open an issue or submit a pull request.

License
This project is licensed under the MIT License.

Acknowledgments
JavaFX Documentation
MySQL Documentation
Java Documentation
