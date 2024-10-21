#include "UserAuth.h"
#include "MySQLConnectionManager.h"
#include <mysql_driver.h>
#include <mysql_connection.h>
#include <cppconn/prepared_statement.h>
#include <cppconn/resultset.h>
#include <stdexcept>

std::string UserAuth::authenticateUser(const std::string& username, const std::string& password) {
    try {
        sql::Connection *con = MySQLConnectionManager::getConnection();
        std::unique_ptr<sql::PreparedStatement> pstmt(con->prepareStatement("SELECT password, role FROM users WHERE username = ?"));
        pstmt->setString(1, username);
        std::unique_ptr<sql::ResultSet> res(pstmt->executeQuery());

        if (res->next()) {
            std::string dbPassword = res->getString("password");
            std::string role = res->getString("role");

            if (dbPassword == password) {
                return role; // Authentication successful, return user role
            } else {
                throw std::runtime_error("Invalid password");
            }
        } else {
            throw std::runtime_error("User not found");
        }
    } catch (sql::SQLException &e) {
        throw std::runtime_error("Database error: " + std::string(e.what()));
    }
}
