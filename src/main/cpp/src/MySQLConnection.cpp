#include <mysql_driver.h>
#include <mysql_connection.h>
#include <cppconn/statement.h>
#include <cppconn/resultset.h>
#include <cppconn/exception.h>
#include <memory>
#include <iostream>

class MySQLConnectionManager {
private:
    static std::unique_ptr<sql::Connection> connection;
    static sql::mysql::MySQL_Driver *driver;

    MySQLConnectionManager() {}  // Private constructor for singleton

public:
    static sql::Connection* getConnection() {
        if (!connection) {
            driver = sql::mysql::get_mysql_driver_instance();
            connection.reset(driver->connect("tcp://127.0.0.1:3306", "username", "password"));
            connection->setSchema("hotel");
        }
        return connection.get();
    }

    // Destructor to close the connection when the application shuts down
    ~MySQLConnectionManager() {
        if (connection) {
            connection->close();
        }
    }
};

// Initialize the static members
std::unique_ptr<sql::Connection> MySQLConnectionManager::connection = nullptr;
sql::mysql::MySQL_Driver* MySQLConnectionManager::driver = nullptr;
