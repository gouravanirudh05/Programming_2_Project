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
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_saveReservationsNative(JNIEnv* env, jobject obj) {
    // Get the class and field ids
    jclass reservationClass = env->FindClass("com/operatoroverloaded/hotel/models/Reservation");
    jclass InMemoryReservationStoreClass = env->GetObjectClass(obj);
    jfieldID reservationArrayField = env->GetFieldID(InMemoryReservationStoreClass, "reservationData", "Ljava/util/ArrayList;");
    jobject reservationArray = env->GetObjectField(obj, reservationArrayField);

    // Get the class and method ids for ArrayList
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
    jint length = env->CallIntMethod(reservationArray, arrayListSize);

    // Get the field ids of the Reservation class
    jfieldID reservationIdField = env->GetFieldID(reservationClass, "reservationId", "I");
    jfieldID roomIDField = env->GetFieldID(reservationClass, "roomID", "Ljava/lang/String;");
    jfieldID customerIDField = env->GetFieldID(reservationClass, "customerID", "Ljava/lang/String;");
    jfieldID startDateTimeField = env->GetFieldID(reservationClass, "startDateTime", "Lcom/operatoroverloaded/hotel/models/DateTime;");
    jfieldID endDateTimeField = env->GetFieldID(reservationClass, "endDateTime", "Lcom/operatoroverloaded/hotel/models/DateTime;");
    jfieldID billIdField = env->GetFieldID(reservationClass, "billId", "I");
    jfieldID totalAmountField = env->GetFieldID(reservationClass, "totalAmount", "D");

    // TODO: change the path to the file as necessary
    std::ofstream outFile("reservations.txt");
    if (!outFile) {
        std::cerr << "Error opening reservations.txt for writing" << std::endl;
        return;
    }

    // Get the class and method ids for DateTime
    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
    jmethodID getDateString = env->GetMethodID(dateTimeClass, "getDateString", "()Ljava/lang/String;");
    jmethodID getTimeString = env->GetMethodID(dateTimeClass, "getTimeString", "()Ljava/lang/String;");

    for (jint i = 0; i < length; i++) {
        // Get the reservation object
        jobject reservationObject = env->CallObjectMethod(reservationArray, arrayListGet, i);

        // Get the fields of the reservation object
        jint reservationId = env->GetIntField(reservationObject, reservationIdField);
        jstring roomID = (jstring) env->GetObjectField(reservationObject, roomIDField);
        jstring customerID = (jstring) env->GetObjectField(reservationObject, customerIDField);
        jobject startDateTime = env->GetObjectField(reservationObject, startDateTimeField);
        jobject endDateTime = env->GetObjectField(reservationObject, endDateTimeField);
        jint billId = env->GetIntField(reservationObject, billIdField);
        jdouble totalAmount = env->GetDoubleField(reservationObject, totalAmountField);

        // Get the date and time strings using the DateTime class method getDateString() and getTimeString()
        jstring startDate = (jstring) env->CallObjectMethod(startDateTime, getDateString);
        jstring startTime = (jstring) env->CallObjectMethod(startDateTime, getTimeString);
        jstring endDate = (jstring) env->CallObjectMethod(endDateTime, getDateString);
        jstring endTime = (jstring) env->CallObjectMethod(endDateTime, getTimeString);

        // Convert the strings to native strings
        const char *nativeRoomID = env->GetStringUTFChars(roomID, nullptr);
        const char *nativeCustomerID = env->GetStringUTFChars(customerID, nullptr);
        const char *nativeStartDate = env->GetStringUTFChars(startDate, nullptr);
        const char *nativeStartTime = env->GetStringUTFChars(startTime, nullptr);
        const char *nativeEndDate = env->GetStringUTFChars(endDate, nullptr);
        const char *nativeEndTime = env->GetStringUTFChars(endTime, nullptr);

        // Write the reservation data to the file
        outFile << reservationId << "-" << nativeRoomID << "-" << nativeCustomerID << "-"
                << nativeStartDate << "-" << nativeStartTime << "-"
                << nativeEndDate << "-" << nativeEndTime << "-"
                << billId << "-" << totalAmount << std::endl;

        // Release the local references
        env->ReleaseStringUTFChars(roomID, nativeRoomID);
        env->ReleaseStringUTFChars(customerID, nativeCustomerID);
        env->ReleaseStringUTFChars(startDate, nativeStartDate);
        env->ReleaseStringUTFChars(startTime, nativeStartTime);
        env->ReleaseStringUTFChars(endDate, nativeEndDate);
        env->ReleaseStringUTFChars(endTime, nativeEndTime);
        env->DeleteLocalRef(reservationObject);
        env->DeleteLocalRef(startDateTime);
        env->DeleteLocalRef(endDateTime);
    }

    outFile.close();
    std::cout << "Reservations saved successfully" << std::endl;
}

// JNI method to load reservations from a space-separated file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_loadReservationsNative(JNIEnv *env, jobject obj) {
    std::ifstream inFile("reservations.txt");
    if (!inFile) {
        std::cerr << "Error opening reservations.txt for reading" << std::endl;
        return;
    }

    std::string file;
    file.assign((std::istreambuf_iterator<char>(inFile)), (std::istreambuf_iterator<char>()));
    inFile.close();

    // Get the class and field ids
    jclass reservationClass = env->FindClass("com/operatoroverloaded/hotel/models/Reservation");
    jclass InMemoryReservationStoreClass = env->GetObjectClass(obj);
    jfieldID reservationArrayField = env->GetFieldID(InMemoryReservationStoreClass, "reservationData", "Ljava/util/ArrayList;");
    jobject reservationArray = env->GetObjectField(obj, reservationArrayField);
    jmethodID reservationInit = env->GetMethodID(reservationClass, "<init>", "(ILjava/lang/String;Ljava/lang/String;Lcom/operatoroverloaded/hotel/models/DateTime;Lcom/operatoroverloaded/hotel/models/DateTime;ID)V");

    // Get the class and method ids for ArrayList
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

    long long i = 0;
    while (i < file.size()) {
        // Get the reservation id
        long long j = i;
        while (file[j] != '-') j++;
        jint reservationId = std::stoi(file.substr(i, j - i));
        i = j + 1;

        // Get the room ID
        j = i;
        while (file[j] != '-') j++;
        jstring roomID = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the customer ID
        j = i;
        while (file[j] != '-') j++;
        jstring customerID = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the start date
        j = i;
        while (file[j] != '-') j++;
        jstring startDate = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the start time
        j = i;
        while (file[j] != '-') j++;
        jstring startTime = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the end date
        j = i;
        while (file[j] != '-') j++;
        jstring endDate = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the end time
        j = i;
        while (file[j] != '-') j++;
        jstring endTime = env->NewStringUTF(file.substr(i, j - i).c_str());
        i = j + 1;

        // Get the bill id
        j = i;
        while (file[j] != '-') j++;
        jint billId = std::stoi(file.substr(i, j - i));
        i = j + 1;

        // Get the total amount
        j = i;
        while (file[j] != '\n' && j < file.size()) j++;
        jdouble totalAmount = std::stod(file.substr(i, j - i));
        i = j + 1;

        // Create DateTime objects for start and end times
        jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
        jmethodID dateTimeInit = env->GetMethodID(dateTimeClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;)V");
        jobject startDateTime = env->NewObject(dateTimeClass, dateTimeInit, startDate, startTime);
        jobject endDateTime = env->NewObject(dateTimeClass, dateTimeInit, endDate, endTime);

        // Create the reservation object and add it to the list
        jobject reservationObject = env->NewObject(reservationClass, reservationInit, reservationId, roomID, customerID, startDateTime, endDateTime, billId, totalAmount);
        env->CallBooleanMethod(reservationArray, arrayListAdd, reservationObject);

        // Release the local references
        env->DeleteLocalRef(roomID);
        env->DeleteLocalRef(customerID);
        env->DeleteLocalRef(startDate);
        env->DeleteLocalRef(startTime);
        env->DeleteLocalRef(endDate);
        env->DeleteLocalRef(endTime);
        env->DeleteLocalRef(startDateTime);
        env->DeleteLocalRef(endDateTime);
        env->DeleteLocalRef(reservationObject);
    }

    std::cout << "Reservations loaded successfully" << std::endl;
}
