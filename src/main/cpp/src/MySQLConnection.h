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
    MySQLConnectionManager();

public:
    static sql::Connection* getConnection();
    ~MySQLConnectionManager();
};