#include "ReservationCPP.h"
#include <fstream>
#include <sstream>
#include <iostream>
#include <vector>
#include <string>
#include <algorithm>
using namespace std;
// Simulate storage
std::vector<Reservation> reservationStore;

extern "C"{
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

    // Open the file for writing
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

        std::string formattedStartDate = nativeStartDate;
        replace(formattedStartDate.begin(), formattedStartDate.end(), '-', '/');
        std::string formattedEndDate = nativeEndDate;
        replace(formattedEndDate.begin(), formattedEndDate.end(), '-', '/');

        // Write the reservation data to the file
        outFile << reservationId << "-" << nativeRoomID << "-" << nativeCustomerID << "-"
                << formattedStartDate << "-" << nativeStartTime << "-"
                << formattedEndDate << "-" << nativeEndTime << "-"
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
}

// JNI method to load reservations from a space-separated file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_reservationstore_InMemoryReservationStore_loadReservationsNative(JNIEnv *env, jobject obj) {
    // Open the file for reading
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


    // Find the DateTime class
    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
    if (dateTimeClass == nullptr) {
        std::cerr << "Error finding DateTime class" << std::endl;
        return;
    }

    // Get the methodID of DateTime parse method
    jmethodID parseMethod = env->GetStaticMethodID(dateTimeClass, "parse", "(Ljava/lang/String;)Lcom/operatoroverloaded/hotel/models/DateTime;");
    if (parseMethod == nullptr) {
        std::cerr << "Error finding parse method" << std::endl;
        return;
    }

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

        std::string formattedStartDate = env->GetStringUTFChars(startDate, nullptr);
        std::string formattedEndDate = env->GetStringUTFChars(endDate, nullptr);

        std::string startDateTimeStr = formattedStartDate + "-" + std::string(env->GetStringUTFChars(startTime, nullptr));
        std::string endDateTimeStr = formattedEndDate + "-" + std::string(env->GetStringUTFChars(endTime, nullptr));

        // Parse the start and end date time strings into DateTime objects
        jstring startDateTimeJStr = env->NewStringUTF(startDateTimeStr.c_str());
        jstring endDateTimeJStr = env->NewStringUTF(endDateTimeStr.c_str());
        jobject startDateTime = env->CallStaticObjectMethod(dateTimeClass, parseMethod, startDateTimeJStr);
        jobject endDateTime = env->CallStaticObjectMethod(dateTimeClass, parseMethod, endDateTimeJStr);
        env->DeleteLocalRef(startDateTimeJStr);
        env->DeleteLocalRef(endDateTimeJStr);

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

    // Update the reservation store
    env->SetObjectField(obj, reservationArrayField, reservationArray);
}
}
