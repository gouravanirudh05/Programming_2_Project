#ifndef ROOM_MANAGEMENT_H
#define ROOM_MANAGEMENT_H

#include <string>

class RoomManagement {
public:
    static void addRoom(const std::string &roomName, int capacity, double price);
    static void updateRoom(int roomId, const std::string &roomName, int capacity, double price);
    static void removeRoom(int roomId);
};

#endif // ROOM_MANAGEMENT_H