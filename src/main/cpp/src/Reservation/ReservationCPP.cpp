#include "ReservationCPP.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <vector>

// Simulate storage
std::vector<Reservation> reservationStore;

// Utility function to convert a Reservation to a CSV line
std::string reservationToCSV(const Reservation& reservation) {
    std::ostringstream oss;
    oss << reservation.reservationId << ","
        << reservation.roomDetails << ","
        << reservation.guestName << ","
        << reservation.startDateTime << ","
        << reservation.endDateTime << ","
        << reservation.totalAmount;
    return oss.str();
}

// Utility function to parse a CSV line into a Reservation
Reservation csvToReservation(const std::string& line) {
    std::istringstream iss(line);
    std::string token;
    Reservation reservation;

    std::getline(iss, token, ',');
    reservation.reservationId = std::stoi(token);

    std::getline(iss, reservation.roomDetails, ',');
    std::getline(iss, reservation.guestName, ',');
    std::getline(iss, reservation.startDateTime, ',');
    std::getline(iss, reservation.endDateTime, ',');

    std::getline(iss, token, ',');
    reservation.totalAmount = std::stod(token);

    return reservation;
}

// JNI method to save reservations to a file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_saveReservationsNative(JNIEnv*, jobject) {
    std::ofstream outFile("reservations.csv");
    if (!outFile) {
        std::cerr << "Error opening reservations.csv for writing" << std::endl;
        return;
    }

    for (const auto& reservation : reservationStore) {
        outFile << reservationToCSV(reservation) << std::endl;
    }

    outFile.close();
    std::cout << "Reservations saved successfully" << std::endl;
}

// JNI method to load reservations from a file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_loadReservationsNative(JNIEnv*, jobject) {
    std::ifstream inFile("reservations.csv");
    if (!inFile) {
        std::cerr << "Error opening reservations.csv for reading" << std::endl;
        return;
    }

    reservationStore.clear();
    std::string line;
    while (std::getline(inFile, line)) {
        reservationStore.push_back(csvToReservation(line));
    }

    inFile.close();
    std::cout << "Reservations loaded successfully" << std::endl;
}
