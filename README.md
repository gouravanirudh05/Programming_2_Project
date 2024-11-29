
<a id="readme-top"></a>

<br />
<div align="center">
  <a href="https://github.com/mrtechtroid/Programming_2_Project/blob/main/readme-assets/1.png">
     <img src="readme-assets/logo.png" alt="Logo" width="100" height="100">
  </a>

  <h3 align="center">Hotel Management System</h3>
  <p align="center">
   StreamLine your Hotel operations with ease!
    <br />
    <a href="https://github.com/mrtechtroid/Programming_2_Project/blob/main/readme-assets/SRS.pdf"><strong>Explore the docs»</strong></a>
    <br />
    <br />
  </p>
</div>
<!-- TABLE OF CONTENTS -->

## Table of Contents

- [About The Project](#about-the-project)
  - [Features](#features)
  - [Class Diagram](#class-diagrams)
  - [Built With](#built-with)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Installation](#installation)

- [Usage](#usage)

- [License](#license)


<!-- ABOUT THE PROJECT -->
## About The Project

<img src="readme-assets/image.png" alt="ProductScreenshot" width="800">

The **Hotel Management System** streamlines core hotel operations such as room management, guest billing,reservations and restaurent management. 
### Features
- **Guest Management**: Add, update, and delete guest records.
- **Room Booking**: View availability, manage check-ins/outs.
- **Billing System**: Generate and manage invoices efficiently.
- **Statistics**: Generate occupancy rate and revenue on dashboard dynamically.
- **Scalable Backend**:The backend is designed with modularity in mind, enabling seamless integration with various database systems.
- **DataAccess Layer**-The system uses a well-defined data access layer (DAL) with interfaces and abstract classes. This ensures that database-specific operations (e.g., CRUD) are encapsulated, making it easy to swap or add databases like MySQL, PostgreSQL, MongoDB, or even NoSQL options with minimal changes to the codebase.
<p align="right">(<a href="#readme-top">back to top</a>)</p>

### Class Diagrams
<p align="center">
  <img src="readme-assets/1.png" alt="Class Diagram 1" width="600">  
  <p>
    This UML diagram represents the Customer class hierarchy, where RestaurantCustomer and HotelCustomer inherit from the abstract Customer class. The Customer class includes common attributes like customerId, name, and billing details, along with methods to manage bills and reservations. RestaurantCustomer adds functionality for handling dishes, tables, and servers, while HotelCustomer focuses on managing reservations.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/2.png" alt="Class Diagram 2" width="600">
  <p>
    This UML diagram represents a Bill management system. The Bill class stores information like billId, amount, purchased items, and timestamps for generation and payment. The BillStore class provides abstract methods for adding, removing, updating, and retrieving bills, as well as loading and saving them. The InMemoryBillStore class inherits from BillStore, implementing these methods with an in-memory list of Bill objects for managing bill data.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/3.png" alt="Class Diagram 3" width="600">
  <p>
    This UML diagram represents a hotel reservation system. The Hotel class manages details like name, location, rooms, staff, and reservations. The Reservation class handles booking information, including room details, reservation period, tariff, and associated bills, with methods to calculate bills and display reservation details. The Bill class records financial details such as purchased items, amounts, and timestamps, with methods to manage bill data.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/4.png" alt="Class Diagram 4" width="600">
  <p>
    The UML diagram illustrates the Staff class, defining its attributes like ID, name, salary, and role, and methods for operations such as assigning tasks, calculating service years, and managing retirement. This class serves as a blueprint for representing staff members within the system.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/5.png" alt="Class Diagram 5" width="600">
  <p>
    The UML diagram showcases the DateTime class, which encapsulates the attributes year, month, day, hour, minute, and second to represent a specific date and time. It offers methods to retrieve the current time, determine leap years, validate time and date, check if a date is past the current time, calculate date and time differences, increment days, and format dates and times into strings.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/6.png" alt="Class Diagram 6" width="600">
  <p>
    The UML diagram depicts the Room and RoomType classes. The Room class represents a physical room with its attributes and methods, while the RoomType class defines different room types with their associated properties. A one-to-many relationship exists between the two, where a Room can belong to a specific RoomType, but a RoomType can be associated with multiple Rooms.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/7.png" alt="Class Diagram 7" width="600">
  <p>
    The UML diagram showcases the LogonStore class and its interface, responsible for user management tasks like adding, deleting, and updating users. The InMemoryLogonStore class likely handles data storage and retrieval in memory. The Role class, nested within LogonStore, defines user roles with attributes like access level and email.
  </p>
  <br />
</p>
<p align="center">
  <img src="readme-assets/8.png" alt="Class Diagram 8" width="600">
  <p>
    The UML diagram depicts a Restaurant Management System with three main classes: Table, Restaurant, and Dish. The Table class represents individual tables, the Restaurant class encapsulates the overall restaurant with its tables and dishes, and the Dish class defines the menu items. The relationships between these classes are one-to-many, with a Restaurant having multiple Tables and Dishes.
  </p>
  <br />
</p>


### Built With
This project utilizes the following technologies:

**Front end**
- ![React](https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB)
- [![Next][Next.js]][Next-url]

**MiddleWare**
- ![Spring Boot](https://img.shields.io/badge/Spring%20Boot-6DB33F?style=for-the-badge&logo=springboot&logoColor=white)
- ![Java](https://img.shields.io/badge/Java-ED8B00?style=for-the-badge&logo=java&logoColor=white)

**Backend**
- ![C++](https://img.shields.io/badge/C++-00599C?style=for-the-badge&logo=cplusplus&logoColor=white)

**Tools**
- ![JNI](https://img.shields.io/badge/JNI-2C4F7C?style=for-the-badge&logo=java&logoColor=white)
- ![Maven](https://img.shields.io/badge/Maven-C71A36?style=for-the-badge&logo=apache-maven&logoColor=white)
<p align="right">(<a href="#readme-top">back to top</a>)</p>

<!-- GETTING STARTED -->
## Getting Started
### Prerequisites
- Ensure to use a Linux based Operating system.
- Install Java 21 OpenJDK and Maven.
- Clone the project with:
   ```sh
  git clone https://github.com/mrtechtroid/Programming_2_Project.git`
  ```
### Installation and Usage
- For the first time usage of the project, navigate to the root directory and run `mvn clean install` (This step will take a long time initially, and consists of downloading all required packages, building the frontend, building the cpp libraries, and compiling the java files. 
- To run the GUI:
  ```sh
  mvn spring-boot:run -Dspring-boot.run.arguments="in-memory gui" -Dskip.npm` 
  ```
  (You can also use without the `-Dskip.npm` argument)
- To run the CLI: `
  ```sh
  mvn spring-boot:run -Dspring-boot.run.arguments="in-memory cli" -Dskip.npm`
  ```
  
**The Hotel Management System offers**:

- **Authentication**: Secure access via login/logout (JWT-based).
- **Room & Staff Management**: Admins manage room inventory, bookings, and staff assignments.
- **Billing Systems**: Handle room charges, restaurant orders, and table reservations.
- **Customer Relationship Management**: Maintain guest records for personalized services.

**Admin Features**:
- Manage rooms, bookings, staff, dishes, and tables.
- Generate bills and reserve rooms.

**Usability**:
- **Intuitive User Interface**: The system should provide a clean, user-friendly interface for hotel admins, allowing them to perform operations without specialized training.
- **Mobile Responsiveness**: The customer-facing website (React app) should be fully responsive and usable on mobile devices.
- **Error Handling**: Provide user-friendly error messages in case of invalid inputs, system errors, or failures.

<!-- LICENSE -->
## License
Distributed under MIT License. See `LICENSE.txt` for more information.





<!-- MARKDOWN LINKS & IMAGES -->
<!-- https://www.markdownguide.org/basic-syntax/#reference-style-links -->
[contributors-shield]: https://img.shields.io/github/contributors/othneildrew/Best-README-Template.svg?style=for-the-badge
[contributors-url]: https://github.com/mrtechtroid/Programming_2_Project/graphs/contributors
[forks-shield]: https://img.shields.io/github/forks/othneildrew/Best-README-Template.svg?style=for-the-badge
[forks-url]: https://github.com/mrtechtroid/Programming_2_Project/forks
[stars-shield]: https://img.shields.io/github/stars/othneildrew/Best-README-Template.svg?style=for-the-badge
[stars-url]: https://github.com/othneildrew/Best-README-Template/stargazers
[issues-shield]: https://img.shields.io/github/issues/othneildrew/Best-README-Template.svg?style=for-the-badge
[issues-url]: https://github.com/othneildrew/Best-README-Template/issues
[license-shield]: https://img.shields.io/github/license/othneildrew/Best-README-Template.svg?style=for-the-badge
[license-url]: https://github.com/othneildrew/Best-README-Template/blob/master/LICENSE.txt
[linkedin-shield]: https://img.shields.io/badge/-LinkedIn-black.svg?style=for-the-badge&logo=linkedin&colorB=555
[linkedin-url]: https://linkedin.com/in/othneildrew
[Next.js]: https://img.shields.io/badge/next.js-000000?style=for-the-badge&logo=nextdotjs&logoColor=white
[Next-url]: https://nextjs.org/
[React.js]: https://img.shields.io/badge/React-20232A?style=for-the-badge&logo=react&logoColor=61DAFB
[React-url]: https://reactjs.org/
[Vue.js]: https://img.shields.io/badge/Vue.js-35495E?style=for-the-badge&logo=vuedotjs&logoColor=4FC08D
[Vue-url]: https://vuejs.org/
[Angular.io]: https://img.shields.io/badge/Angular-DD0031?style=for-the-badge&logo=angular&logoColor=white
[Angular-url]: https://angular.io/
[Svelte.dev]: https://img.shields.io/badge/Svelte-4A4A55?style=for-the-badge&logo=svelte&logoColor=FF3E00
[Svelte-url]: https://svelte.dev/
[Laravel.com]: https://img.shields.io/badge/Laravel-FF2D20?style=for-the-badge&logo=laravel&logoColor=white
[Laravel-url]: https://laravel.com
[Bootstrap.com]: https://img.shields.io/badge/Bootstrap-563D7C?style=for-the-badge&logo=bootstrap&logoColor=white
[Bootstrap-url]: https://getbootstrap.com
[JQuery.com]: https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white
[JQuery-url]: https://jquery.com 
