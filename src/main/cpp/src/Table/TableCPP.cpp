/*
Data Fields Order:
id|name|email|phone|address|bill_amt|bill_payed|bill_left|<bill_ids (comma-separated)>|<reserved_from: yyyy-mm-dd HH:MM:SS>|<reserved_to: yyyy-mm-dd HH:MM:SS>|<reservation_ids (comma-separated)>*/
#include <bits/stdc++.h>
#include <fstream>
#include <sstream>
#include "com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore.h"
#include <jni.h>

using namespace std;

jstring stdStringToJString(JNIEnv* env, const string& cppString) {
    return env->NewStringUTF(cppString.c_str());
}

string jStringToStdString(JNIEnv* env, jstring javaString) {
    if (javaString == nullptr) return ""; 
    const char* utfString = env->GetStringUTFChars(javaString, nullptr);
    string cppString(utfString);
    env->ReleaseStringUTFChars(javaString, utfString);
    return cppString;
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore_loadFromFile(JNIEnv *env, jobject obj) {
    jclass customerClass = env->FindClass("com/operatoroverloaded/hotel/stores/roomstore/HotelCustomer");
    jclass storeClass = env->GetObjectClass(obj);
    jfieldID customerListField = env->GetFieldID(storeClass, "customers", "Ljava/util/ArrayList;");
    jobject customerList = env->GetObjectField(obj, customerListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
    jmethodID dateTimeConstructor = env->GetMethodID(dateTimeClass, "<init>", "(IIIIII)V");

    ifstream infile("HotelCustomerStore.txt");
    string line;
    while (getline(infile, line)) {
        istringstream ss(line);
        vector<string> tokens;
        string token;
        while (getline(ss, token, '|')) tokens.push_back(token);

        jobject customer = env->NewObject(customerClass, env->GetMethodID(customerClass, "<init>", "()V"));

        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setCustomerId", "(I)V"), stoi(tokens[0]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setName", "(Ljava/lang/String;)V"), stdStringToJString(env, tokens[1]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setEmail", "(Ljava/lang/String;)V"), stdStringToJString(env, tokens[2]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setPhone", "(Ljava/lang/String;)V"), stdStringToJString(env, tokens[3]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setAddress", "(Ljava/lang/String;)V"), stdStringToJString(env, tokens[4]));

        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setBillAmount", "(D)V"), stod(tokens[5]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setBillPayed", "(D)V"), stod(tokens[6]));
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setBillLeft", "(D)V"), stod(tokens[7]));

        istringstream billIds(tokens[8]);
        string bill;
        while (getline(billIds, bill, ',')) {
            env->CallVoidMethod(customer, env->GetMethodID(customerClass, "addBill", "(I)V"), stoi(bill));
        }

        istringstream reservedFrom(tokens[9]);
        int year, month, day, hour, minute, second;
        char delimiter;
        reservedFrom >> year >> delimiter >> month >> delimiter >> day >> hour >> delimiter >> minute >> delimiter >> second;
        jobject fromDateTime = env->NewObject(dateTimeClass, dateTimeConstructor, year, month, day, hour, minute, second);
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setReservedFrom", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), fromDateTime);

        istringstream reservedTo(tokens[10]);
        reservedTo >> year >> delimiter >> month >> delimiter >> day >> hour >> delimiter >> minute >> delimiter >> second;
        jobject toDateTime = env->NewObject(dateTimeClass, dateTimeConstructor, year, month, day, hour, minute, second);
        env->CallVoidMethod(customer, env->GetMethodID(customerClass, "setReservedTo", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), toDateTime);

        istringstream reservationIds(tokens[11]);
        string reservation;
        while (getline(reservationIds, reservation, ',')) {
            env->CallVoidMethod(customer, env->GetMethodID(customerClass, "addReservation", "(I)V"), stoi(reservation));
        }

        env->CallBooleanMethod(customerList, addMethod, customer);
    }
    infile.close();
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore_storeToFile(JNIEnv *env, jobject obj) {
    jclass storeClass = env->GetObjectClass(obj);
    jfieldID customerListField = env->GetFieldID(storeClass, "customers", "Ljava/util/ArrayList;");
    jobject customerList = env->GetObjectField(obj, customerListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID sizeMethod = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");

    jclass customerClass = env->FindClass("com/operatoroverloaded/hotel/stores/roomstore/HotelCustomer");
    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");

    ofstream outfile("HotelCustomerStore.txt");
    jint size = env->CallIntMethod(customerList, sizeMethod);
    for (int i = 0; i < size; ++i) {
        jobject customer = env->CallObjectMethod(customerList, getMethod, i);

        jint id = env->CallIntMethod(customer, env->GetMethodID(customerClass, "getCustomerId", "()I"));
        string name = jStringToStdString(env, (jstring)env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getName", "()Ljava/lang/String;")));
        string email = jStringToStdString(env, (jstring)env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getEmail", "()Ljava/lang/String;")));
        string phone = jStringToStdString(env, (jstring)env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getPhone", "()Ljava/lang/String;")));
        string address = jStringToStdString(env, (jstring)env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getAddress", "()Ljava/lang/String;")));
        double billAmt = env->CallDoubleMethod(customer, env->GetMethodID(customerClass, "getBillAmount", "()D"));
        double billPayed = env->CallDoubleMethod(customer, env->GetMethodID(customerClass, "getBillPayed", "()D"));
        double billLeft = env->CallDoubleMethod(customer, env->GetMethodID(customerClass, "getBillLeft", "()D"));

        outfile << id << "|" << name << "|" << email << "|" << phone << "|" << address << "|" << billAmt << "|" << billPayed << "|" << billLeft << "|";

        jobject bills = env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getBills", "()Ljava/util/ArrayList;"));
        jint billSize = env->CallIntMethod(bills, sizeMethod);
        for (int j = 0; j < billSize; ++j) {
            if (j > 0) outfile << ",";
            outfile << env->CallIntMethod(bills, getMethod, j);
        }

        jobject fromDateTime = env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getReservedFrom", "()Lcom/operatoroverloaded/hotel/models/DateTime;"));
        outfile << "|"
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getYear", "()I")) << "-"
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getMonth", "()I")) << "-"
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getDay", "()I")) << " "
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getHour", "()I")) << ":"
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getMinute", "()I")) << ":"
                << env->CallIntMethod(fromDateTime, env->GetMethodID(dateTimeClass, "getSecond", "()I")) << "|";

        jobject toDateTime = env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getReservedTo", "()Lcom/operatoroverloaded/hotel/models/DateTime;"));
        outfile << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getYear", "()I")) << "-"
                << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getMonth", "()I")) << "-"
                << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getDay", "()I")) << " "
                << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getHour", "()I")) << ":"
                << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getMinute", "()I")) << ":"
                << env->CallIntMethod(toDateTime, env->GetMethodID(dateTimeClass, "getSecond", "()I")) << "|";

        jobject reservations = env->CallObjectMethod(customer, env->GetMethodID(customerClass, "getReservations", "()Ljava/util/ArrayList;"));
        jint resSize = env->CallIntMethod(reservations, sizeMethod);
        for (int j = 0; j < resSize; ++j) {
            if (j > 0) outfile << ",";
            outfile << env->CallIntMethod(reservations, getMethod, j);
        }
        outfile << "\n";
    }
    outfile.close();
}

