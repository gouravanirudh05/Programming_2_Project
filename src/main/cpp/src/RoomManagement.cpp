#include "RoomManagement.h"
#include "MySQLConnection.h"  // Use singleton for database connection
#include <mysql_driver.h>
#include <mysql_connection.h>
#include <cppconn/statement.h>
#include <cppconn/resultset.h>
#include <cppconn/exception.h>
#include <memory>
#include <iostream>

void RoomManagement::addRoom(const std::string &roomName, int capacity, double price) {
    // Use Singleton to get a MySQL connection and execute query
    try {
        sql::Connection *con = MySQLConnectionManager::getConnection();

        // Create a statement and execute the query
        std::unique_ptr<sql::Statement> stmt(con->createStatement());
        std::unique_ptr<sql::ResultSet> res(stmt->executeQuery("SELECT * FROM rooms"));

        // Process the result set
        while (res->next()) {
            std::cout << "Room ID: " << res->getInt("id") << " Room Name: " << res->getString("name") << std::endl;
        }

    } catch (sql::SQLException &e) {
        std::cout << "SQLException: " << e.what() << std::endl;
    }
}

void RoomManagement::updateRoom(int roomId, const std::string &roomName, int capacity, double price) {
    // SQL query to update room information
}

void RoomManagement::removeRoom(int roomId) {
    // SQL query to remove room by roomId
}