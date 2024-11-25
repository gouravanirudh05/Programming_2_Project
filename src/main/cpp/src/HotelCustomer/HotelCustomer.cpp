#include<bits/stdc++.h>
#include<fstream>
#include<sstream>
#include "com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore.h"
#include<jni.h>
using namespace std;

// format of storing:
// id name email phone address bill_amt bill_payed bill_left number_of_bills <all_bill_ids> <year month day hour min sec> <year month day hour min sec> no_of_reservations <all_reservation_ids>

jstring stdStringToJString(JNIEnv* env, const string& cppString) {
    return env->NewStringUTF(cppString.c_str());
}

string jStringToStdString(JNIEnv* env, jstring javaString) {
    if (javaString == nullptr) {
        return ""; // Handle null jstring
    }

    const char* utfString = env->GetStringUTFChars(javaString, nullptr);
    if (utfString == nullptr) {
        throw std::runtime_error("Failed to convert jstring to std::string");
    }

    std::string cppString(utfString);
    env->ReleaseStringUTFChars(javaString, utfString);
    return cppString;
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore_loadFromFile(JNIEnv *env, jobject obj) {
    // Getting the customers ArrayList attribute of InMemoryHotelCustomerStore
    jclass hotelCustomerClass = env->FindClass("com/operatoroverloaded/hotel/models/HotelCustomer");
    jclass InMemoryHotelCustomerStoreClass = env->GetObjectClass(obj);
    jfieldID hotelCustomerArrayField = env->GetFieldID(InMemoryHotelCustomerStoreClass, "customers", "Ljava/util/ArrayList;");
    jobject hotelCustomerArray = env->GetObjectField(obj, hotelCustomerArrayField);

    // Getting the HotelCustomer constructor
    jmethodID constructor = env->GetMethodID(hotelCustomerClass, "<init>", "()V");

    // Get ArrayList class and its methods
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    // Get DateTime class
    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
    jmethodID dateTimeConstructor = env->GetMethodID(dateTimeClass, "<init>", "(IIIIII)V");

    // Read from the file
    ifstream infile;
    infile.open("HotelCustomerStore.txt", ios::in);
    string customerData = "";
    while (getline(infile, customerData)) {
        jobject customer = env->NewObject(hotelCustomerClass, constructor);

        istringstream ss(customerData);
        int customerId;
        string name, email, phone, address;
        double bill_amt, bill_payed, bill_left;
        
        ss >> customerId >> name >> email >> phone >> address >> bill_amt >> bill_payed >> bill_left;
        
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setCustomerId", "(I)V"), customerId);
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setName", "(Ljava/lang/String;)V"), stdStringToJString(env, name));
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setEmail", "(Ljava/lang/String;)V"), stdStringToJString(env, email));
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setPhone", "(Ljava/lang/String;)V"), stdStringToJString(env, phone));
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setAddress", "(Ljava/lang/String;)V"), stdStringToJString(env, address));
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setBillAmount", "(D)V"), bill_amt);
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setBillPayed", "(D)V"), bill_payed);
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setBillLeft", "(D)V"), bill_left);

        int bills_size;
        ss >> bills_size;
        while (bills_size--) {
            int x;
            ss >> x;
            env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "addBill", "(I)V"), x);
        }

        int yearFrom, monthFrom, dayFrom, hourFrom, minFrom, secFrom;
        ss >> yearFrom >> monthFrom >> dayFrom >> hourFrom >> minFrom >> secFrom;
        jobject reservedFrom = env->NewObject(dateTimeClass, dateTimeConstructor, yearFrom, monthFrom, dayFrom, hourFrom, minFrom, secFrom);

        int yearTo, monthTo, dayTo, hourTo, minTo, secTo;
        ss >> yearTo >> monthTo >> dayTo >> hourTo >> minTo >> secTo;
        jobject reservedTo = env->NewObject(dateTimeClass, dateTimeConstructor, yearTo, monthTo, dayTo, hourTo, minTo, secTo);

        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setReservedFrom", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), reservedFrom);
        env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "setReservedTo", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), reservedTo);

        int reservations_size;
        ss >> reservations_size;
        while (reservations_size--) {
            int x;
            ss >> x;
            env->CallVoidMethod(customer, env->GetMethodID(hotelCustomerClass, "addReservation", "(I)V"), x);
        }

        // Add the customer to the ArrayList
        env->CallBooleanMethod(hotelCustomerArray, addMethod, customer);
    }
    infile.close();
}


JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_hotelcustomerstore_InMemoryHotelCustomerStore_storeToFile(JNIEnv *env, jobject obj){

    // Getting the customers ArrayList attribute of InMemoryHotelCustomerStore
    jclass hotelCustomerClass = env->FindClass("com/operatoroverloaded/hotel/models/HotelCustomer");
    jclass InMemoryHotelCustomerStoreClass = env->GetObjectClass(obj);
    jfieldID hotelCustomerArrayField = env->GetFieldID(InMemoryHotelCustomerStoreClass, "customers", "Ljava/util/ArrayList;");
    jobject hotelCustomerArray = env->GetObjectField(obj, hotelCustomerArrayField);

    // Get ArrayList class and its methods
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");

    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");

    jint customerSize = env->CallIntMethod(hotelCustomerArray, arrayListSize);
    ofstream outfile;
    outfile.open("HotelCustomerStore.txt");
    for (int i=0; i<customerSize; i++){
        jobject customer = env->CallObjectMethod(hotelCustomerArray, arrayListGet, i);
        jint id = env->CallIntMethod(customer, env->GetMethodID(hotelCustomerClass, "getCustomerId", "()I"));
        string name = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getName", "()Ljava/lang/String;")));
        string email = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getEmail", "()Ljava/lang/String;")));
        string phone = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getPhone", "()Ljava/lang/String;")));
        string address = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getAddress", "()Ljava/lang/String;")));
        jdouble billAmt = env->CallDoubleMethod(customer, env->GetMethodID(hotelCustomerClass, "getBillAmt", "()D"));
        jdouble billPayed = env->CallDoubleMethod(customer, env->GetMethodID(hotelCustomerClass, "getBillPayed", "()D"));
        jdouble billLeft = env->CallDoubleMethod(customer, env->GetMethodID(hotelCustomerClass, "getBillLeft", "()D"));

        outfile << id << " "<< name << " "<< email << " "<< phone << " "<< address<< " " << billAmt << " "<< billPayed << " "<< billLeft << " ";

        jobject bills = env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getBills", "()Ljava/util/ArrayList;"));

        jint billSize = env->CallIntMethod(bills, arrayListSize);
        outfile << billSize << " ";
        for (int i = 0; i<billSize; i++) {
            jint bill = env->CallIntMethod(bills, arrayListGet, i);
            outfile <<  bill << " ";
        }
        jobject from = env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getReservedFrom", "()Lcom/operatoroverloaded/hotel/models/DateTime"));
        jobject to = env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getReservedTo", "()Lcom/operatoroverloaded/hotel/models/DateTime"));

        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getYear", "()I"))<< " ";
        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getMonth", "()I"))<< " ";
        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getDay", "()I"))<< " ";
        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getHour", "()I"))<< " ";
        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getMinute", "()I"))<< " ";
        outfile << env->CallIntMethod(from, env->GetMethodID(dateTimeClass, "getSecond", "()I"))<< " ";

        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getYear", "()I"))<< " ";
        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getMonth", "()I"))<< " ";
        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getDay", "()I"))<< " ";
        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getHour", "()I"))<< " ";
        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getMinute", "()I"))<< " ";
        outfile << env->CallIntMethod(to, env->GetMethodID(dateTimeClass, "getSecond", "()I"))<< " ";

        jobject reservationArray = env->CallObjectMethod(customer, env->GetMethodID(hotelCustomerClass, "getReservations", "()Ljava/util/ArrayList;"));
        jint reservationSize = env->CallIntMethod(reservationArray, arrayListSize);
        outfile << reservationSize << " ";
        for (int i = 0; i< reservationSize; i++) {
            jint reservation = env->CallIntMethod(reservationArray, arrayListGet, i);
            outfile << reservation << " ";
        }
        outfile << endl;
    }
    outfile.close();
}