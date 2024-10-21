#ifndef USER_AUTH_H
#define USER_AUTH_H

#include <string>

class UserAuth {
public:
    static std::string authenticateUser(const std::string& username, const std::string& password);
};

#endif // USER_AUTH_H
