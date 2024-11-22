#ifndef RESERVATION_CPP_H
#define RESERVATION_CPP_H

#include <jni.h>
#include <vector>
#include <string>

// Define the Reservation structure
struct Reservation {
    int reservationId;
    std::string roomDetails;
    std::string guestName;
    std::string startDateTime;
    std::string endDateTime;
    double totalAmount;
};

// Function declarations for JNI
extern "C" {
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_saveReservationsNative(JNIEnv*, jobject);
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_loadReservationsNative(JNIEnv*, jobject);
}

#endif // RESERVATION_CPP_H
