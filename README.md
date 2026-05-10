# CityTransitManagementSystem
City Transit Management System is a Java-based desktop application designed to manage bus transport operations efficiently. It includes features for managing passengers, operators, buses, routes, tickets, and payments, with database integration to ensure organized and reliable data handling.”
# 🚌 City Transit Management System

A Java-based desktop application for managing intercity bus transit operations, built as an OOP semester project. The system digitalizes manual ticketing, route management, and revenue tracking for local bus services in the Sukkur region of Sindh, Pakistan.

---

## 👤 Group Members

| Full Name | CMS / Student ID | Section |
|-----------|-----------------|---------|
| Zara Bibi | 023-25-0189 | C |

---

## 📌 Purpose

Public intercity bus services in semi-urban areas of Sindh still rely on manual ticketing and handwritten records, leading to duplicate bookings, revenue errors, and poor passenger experience. This system replaces those manual processes with a structured, database-driven Java application that allows passengers to book/cancel tickets and view schedules, while operators can monitor revenue and passenger load.

---

## 🗂️ Main Modules

### Model / Entity Classes
- **`User.java`** — Abstract base class with `viewOptions()` abstract method
- **`Passenger.java`** — Extends `User`, implements `BookAble`, `SeeInfo`, `Payable` interfaces
- **`Operator.java`** — Extends `User`, manages revenue and bus operations
- **`Bus.java`** — Handles seat management (booking, cancelling, seat numbering)
- **`Ticket.java`** — Ticket entity + inner `TicketSystem` ActionListener class
- **`Route.java`** — Route entity with ID and name
- **`Schedule.java`** — Schedule entity (bus name, scheduled time, estimated time)

### Interfaces
- **`BookAble.java`** — `TicketBooking()`, `TicketCancelling()`, `ViewBookingHistory()`
- **`SeeInfo.java`** — `viewSchedule()`, `viewBusStops()`
- **`Payable.java`** — `processPayment()`, `viewPaymentHistory()`, `requestRefund()`

### DAO (Data Access) Classes
- **`TicketDAO.java`** — Insert, cancel, query tickets; generate IDs
- **`PaymentDAO.java`** — Make payments, view history, process refunds
- **`ScheduleDAO.java`** — Show all schedules or by bus
- **`BusStopDAO.java`** — Show stops by bus, buses by stop, all stops
- **`OperatorDAO.java`** — Revenue reports (total, daily, by route), passenger load, bus list
- **`DBConnection.java`** — MySQL connection utility (singleton-style static method)

### UI / View Classes
- **`MainScreen.java`** — Entry point; choose Passenger or Operator login
- **`PassengerLogin.java`** — Passenger authentication
- **`PassengerView.java`** — Passenger dashboard (schedule, tickets, stops, payments)
- **`OperatorLogin.java`** — Operator authentication
- **`OperatorView.java`** — Operator dashboard (revenue, load, buses, reports)
- **`RevenueView.java`** — Revenue sub-menu (total, date-wise, route-wise)
- **`StopSystem.java`** — Stop lookup UI

---

## ⚙️ Key OOP Features

| Concept | Where Used |
|---------|-----------|
| **Abstract Class** | `User` — abstract `viewOptions()` method |
| **Inheritance** | `Passenger` and `Operator` both extend `User` |
| **Interfaces** | `BookAble`, `SeeInfo`, `Payable` — all implemented by `Passenger` |
| **Polymorphism** | `Passenger` and `Operator` override `viewOptions()` differently |
| **Encapsulation** | All fields private with getters/setters in all entity classes |
| **Exception Handling** | Try-catch blocks throughout all DAO and UI classes |
| **Collections / Arrays** | Arrays for bus names, boarding/destination points in `TicketDAO` |
| **Database (MySQL)** | Full CRUD via JDBC — tickets, payments, schedules, buses, stops |

---

## 🗄️ Database

- **DBMS:** MySQL 8.0
- **Database name:** `city_transit`
- **Key tables:** `Buses`, `Routes`, `Stops`, `Bus_Stop`, `Schedule`, `Passengers`, `Ticket`, `Payment`, `Revenue`, `Operator`
- **Connector:** MySQL Connector/J 9.6.0 (included in project folder)

---

## ▶️ How to Run

### Requirements
- JDK 8 or higher
- MySQL 8.0 running locally
- MySQL Connector JAR: `mysql-connector-j-9.6.0.jar` (included in project zip)

### Steps

1. **Set up the database:**
   - Open MySQL Workbench or command line
   - Run the SQL script to create the `city_transit` database and tables

2. **Update DB credentials (if needed):**
   - Open `DBConnection.java`
   - Change `USER` and `PASSWORD` to match your MySQL setup

3. **Compile:**
```bash
javac -cp .;mysql-connector-j-9.6.0/mysql-connector-j-9.6.0/mysql-connector-j-9.6.0.jar *.java
```

4. **Run:**
```bash
java -cp .;mysql-connector-j-9.6.0/mysql-connector-j-9.6.0/mysql-connector-j-9.6.0.jar MainScreen
```
> On Mac/Linux replace `;` with `:` in the classpath

---

## 🎬 Demo Video

▶️ [Watch on YouTube](https://youtu.be/402HOx5PrOY?si=qKhG5CnzY2yCTm-b)

---

## 🔗 GitHub Repository

[https://github.com/ZaraSajad123/CityTransitManagementSystem](https://github.com/ZaraSajad123/CityTransitManagementSystem)

---

*Spring 2026 — OOP Project — Instructor: Dr. Adil Khan*
