#include "ReservationCPP.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <vector>
#include <string>

// Simulate storage
std::vector<Reservation> reservationStore;

// Utility function to convert a Reservation to a space-separated string
std::string reservationToString(const Reservation& reservation) {
    std::ostringstream oss;
    oss << reservation.reservationId << " "
        << reservation.roomDetails << " "
        << reservation.customerID << " "
        << reservation.startDateTime << " "
        << reservation.endDateTime << " "
        << reservation.totalAmount;
    return oss.str();
}

// Utility function to parse a space-separated line into a Reservation
Reservation stringToReservation(const std::string& line) {
    std::istringstream iss(line);
    std::string token;
    Reservation reservation;

    // Read each value separated by spaces
    std::getline(iss, token, ' ');
    reservation.reservationId = std::stoi(token);

    std::getline(iss, reservation.roomDetails, ' ');
    std::getline(iss, reservation.customerID, ' ');
    std::getline(iss, reservation.startDateTime, ' ');
    std::getline(iss, reservation.endDateTime, ' ');

    std::getline(iss, token, ' ');
    reservation.totalAmount = std::stod(token);

    return reservation;
}

// JNI method to save reservations to a space-separated file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_saveReservationsNative(JNIEnv*, jobject) {
    std::ofstream outFile("reservations.txt");
    if (!outFile) {
        std::cerr << "Error opening reservations.txt for writing" << std::endl;
        return;
    }

    for (const auto& reservation : reservationStore) {
        outFile << reservationToString(reservation) << std::endl;
    }

    outFile.close();
    std::cout << "Reservations saved successfully" << std::endl;
}

// JNI method to load reservations from a space-separated file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_loadReservationsNative(JNIEnv*, jobject) {
    std::ifstream inFile("reservations.txt");
    if (!inFile) {
        std::cerr << "Error opening reservations.txt for reading" << std::endl;
        return;
    }

    reservationStore.clear();
    std::string line;
    while (std::getline(inFile, line)) {
        reservationStore.push_back(stringToReservation(line));
    }

    inFile.close();
    std::cout << "Reservations loaded successfully" << std::endl;
}
