🏥 MediTrack – Medicine Inventory Management System
MediTrack Pro is a comprehensive, console-based application designed to manage a hospital's medicine inventory efficiently. It provides a dual-interface system for doctors and pharmacists, ensuring seamless communication and workflow for medicine requests, dispensing, and inventory control. The system is built with Java and backed by a MySQL database for robust data persistence.
MediTrack Pro - Hospital Medicine Inventory Management System
MediTrack Pro is a comprehensive, console-based application designed to manage a hospital's medicine inventory efficiently. It provides a dual-interface system for doctors and pharmacists, ensuring seamless communication and workflow for medicine requests, dispensing, and inventory control. The system is built with Java and backed by a MySQL database for robust data persistence.

✨ Key Features
Role-Based Access Control: Separate, feature-rich menus for Doctors and Pharmacists to ensure users only have access to relevant functionalities.

Interactive & User-Friendly CLI: An enhanced command-line interface with color-coded and styled text for improved readability and user experience.

Comprehensive Inventory Management:

Add new medicines and batches with details like category, quantity, and expiry date.

Dispense medicines with automatic inventory updates.

View the entire inventory at a glance.

First-Expired, First-Out (FEFO) Logic: Utilizes a PriorityQueue to manage medicine batches, ensuring that those expiring soonest are dispensed first, minimizing waste.

Doctor-Pharmacist Workflow:

Doctors can request medicines directly through the system.

Pharmacists can view, approve, and dispense pending requests in a streamlined manner.

Analytics and Insights:

Usage Prediction: Forecasts future medicine needs based on historical dispensing data.

Anomaly Detection: Flags unusual spikes in medicine consumption to alert pharmacists of potential issues.

Expired Medicine Alerts: Automatically checks for and reports on expired or soon-to-expire medicine batches.

Reporting System:

Generate detailed inventory reports.

Save reports to the database for historical record-keeping.

View past reports directly from the application.

Persistent Data Storage: All inventory data, usage logs, medicine requests, and reports are stored in a MySQL database.

📸 Sneak Peek

Main Login Menu:
==============================================
   MediTrack Pro
==============================================

Welcome to the most interactive medicine management system!
Let's keep your hospital running smoothly.

==============================================

LOGIN MENU
╭────────────────────────────────────╮
│  [1] ➔ Doctor                      │
│  [2] ➔ Pharmacist                  │
╰────────────────────────────────────╯

 [0] ➔ Exit System

Enter your choice (0-2):

Pharmacist Portal:

===== PHARMACIST PORTAL =====

 PHARMACIST MENU
╭────────────────────────────────────────╮
│     Inventory Management               │
│    [1] ➔ Add New Medicine Batch       │
│    [2] ➔ Dispense Medicine Directly   │
│    [3] ➔ View Inventory               │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Request Handling                    │
│    [4] ➔ View Pending Requests        │
│    [5] ➔ Approve & Dispense Request    │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Analytics                           │
│    [6] ➔ Predict Medicine Usage         │
│    [7] ➔ Check for Usage Anomalies      │
│    [8] ➔ Check for expired medicines   │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Reporting                           │
│    [9] ➔ Generate Inventory Report     │
╰────────────────────────────────────────╯

[0] ➔ Logout

Choose an option (0-9):

MediTrack Pro - Hospital Medicine Inventory Management System
MediTrack Pro is a comprehensive, console-based application designed to manage a hospital's medicine inventory efficiently. It provides a dual-interface system for doctors and pharmacists, ensuring seamless communication and workflow for medicine requests, dispensing, and inventory control. The system is built with Java and backed by a MySQL database for robust data persistence.

✨ Key Features
Role-Based Access Control: Separate, feature-rich menus for Doctors and Pharmacists to ensure users only have access to relevant functionalities.

Interactive & User-Friendly CLI: An enhanced command-line interface with color-coded and styled text for improved readability and user experience.

Comprehensive Inventory Management:

Add new medicines and batches with details like category, quantity, and expiry date.

Dispense medicines with automatic inventory updates.

View the entire inventory at a glance.

First-Expired, First-Out (FEFO) Logic: Utilizes a PriorityQueue to manage medicine batches, ensuring that those expiring soonest are dispensed first, minimizing waste.

Doctor-Pharmacist Workflow:

Doctors can request medicines directly through the system.

Pharmacists can view, approve, and dispense pending requests in a streamlined manner.

Analytics and Insights:

Usage Prediction: Forecasts future medicine needs based on historical dispensing data.

Anomaly Detection: Flags unusual spikes in medicine consumption to alert pharmacists of potential issues.

Expired Medicine Alerts: Automatically checks for and reports on expired or soon-to-expire medicine batches.

Reporting System:

Generate detailed inventory reports.

Save reports to the database for historical record-keeping.

View past reports directly from the application.

Persistent Data Storage: All inventory data, usage logs, medicine requests, and reports are stored in a MySQL database.

📸 Sneak Peek
(Imagine screenshots of the application here, showcasing the main menu, doctor's portal, and pharmacist's portal.)

Main Login Menu:

==============================================
   MediTrack Pro
==============================================

Welcome to the most interactive medicine management system!
Let's keep your hospital running smoothly.

==============================================

LOGIN MENU
╭────────────────────────────────────╮
│  [1] ➔ Doctor                      │
│  [2] ➔ Pharmacist                  │
╰────────────────────────────────────╯

 [0] ➔ Exit System

Enter your choice (0-2):
Pharmacist Portal:

===== PHARMACIST PORTAL =====

 PHARMACIST MENU
╭────────────────────────────────────────╮
│     Inventory Management               │
│    [1] ➔ Add New Medicine Batch       │
│    [2] ➔ Dispense Medicine Directly   │
│    [3] ➔ View Inventory               │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Request Handling                    │
│    [4] ➔ View Pending Requests        │
│    [5] ➔ Approve & Dispense Request    │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Analytics                           │
│    [6] ➔ Predict Medicine Usage         │
│    [7] ➔ Check for Usage Anomalies      │
│    [8] ➔ Check for expired medicines   │
╰────────────────────────────────────────╯
╭────────────────────────────────────────╮
│    Reporting                           │
│    [9] ➔ Generate Inventory Report     │
╰────────────────────────────────────────╯

[0] ➔ Logout

Choose an option (0-9):

🛠️ Tech Stack
Backend: Java
Database: MySQL

🚀 Getting Started
Follow these instructions to get a copy of the project up and running on your local machine.

Prerequisites
Java Development Kit (JDK) 11 or higher

MySQL Server

An IDE like IntelliJ IDEA or Eclipse (optional, for development)

1. Database Setup
You need to create a MySQL database and the required tables for the application to work.

Create the Database:

SQL

CREATE DATABASE hospital_inventory;
Use the Database:

SQL

USE hospital_inventory;
Create the Tables: Run the following SQL script to create all the necessary tables.

SQL

CREATE TABLE Medicine (
    id VARCHAR(255) PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    category VARCHAR(255)
);

CREATE TABLE Batch (
    batch_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id VARCHAR(255),
    quantity INT NOT NULL,
    expiry_date DATE NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id)
);

CREATE TABLE MedicineRequest (
    request_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id VARCHAR(255),
    quantity INT NOT NULL,
    requested_by VARCHAR(255),
    request_date DATE NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id)
);

CREATE TABLE UsageLog (
    log_id INT AUTO_INCREMENT PRIMARY KEY,
    medicine_id VARCHAR(255),
    usage_date DATE NOT NULL,
    quantity INT NOT NULL,
    FOREIGN KEY (medicine_id) REFERENCES Medicine(id)
);

CREATE TABLE Report (
    report_id INT AUTO_INCREMENT PRIMARY KEY,
    generated_date DATE NOT NULL,
    content TEXT
);
2. Configure the Application
Open the db/DatabaseManager.java file.

Update the URL, USER, and PASSWORD constants with your MySQL database connection details.

Java

public class DatabaseManager {
    private static final String URL = "jdbc:mysql://localhost:3306/hospital_inventory";
    private static final String USER = "your_mysql_username";
    private static final String PASSWORD = "your_mysql_password"; // change this!
    // ...
}
3. Compile and Run
You can compile and run the project from your terminal.

Navigate to the source directory:

Bash

cd path/to/your/project/src
Compile all Java files:

Bash

javac */*.java *.java
Run the application:

Bash

java Main
📂 Project Structure
The project is organized into several packages to separate concerns and improve maintainability:

Main.java: The entry point of the application. It contains the main loop and the user interface logic for the different menus.

model: Contains the Plain Old Java Objects (POJOs) that represent the core entities of the system.

Medicine.java: Represents a medicine.

Batch.java: Represents a batch of a specific medicine.

MedicineRequest.java: Represents a request made by a doctor.

UserRole.java: An enum for user roles.

service: Contains the business logic of the application.

InventoryManager.java: Handles all operations related to managing the inventory.

UsageAnalyzer.java: Provides analytics features like usage logging and prediction.

AlertSystem.java: Detects anomalies in medicine usage.

ReportGenerator.java: Creates and saves inventory reports.

db: Manages the database connection.

DatabaseManager.java: A utility class to establish a connection with the MySQL database.

util: Contains utility classes.

InputUtil.java: A helper class for reading user input from the console (inferred from Main.java).

📖 How to Use the Application
Login:

Start the application.

Choose to log in as a Doctor or a Pharmacist.

Doctor Portal:

View Medicine Inventory: See a list of all available medicines and their quantities.

Request New Medicine: Submit a request for a specific medicine and quantity.

Pharmacist Portal:

Inventory Management:

Add new medicine batches to the inventory.

Directly dispense medicine and log the transaction.

View the current status of the entire inventory.

Request Handling:

View all pending requests from doctors.

Approve and dispense medicine for a request, which automatically updates the inventory.

Analytics:

Predict the daily usage of a medicine.

Check if the recent usage of a medicine is anomalous.

Scan the inventory for any expired batches.

Reporting:

Generate a complete inventory report.

Save the report to the database for future reference.
