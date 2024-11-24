#include<bits/stdc++.h>
#include<fstream>
#include<sstream>
#include "com_operatoroverloaded_hotel_stores_restaurantcustomerstore_InMemoryRestaurantCustomerStore.h"
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

JNIEXPORT void JNICALL Java_com_operatoroverloaded_restaurant_stores_restaurantcustomerstore_InMemoryRestaurantCustomerStore_loadFromFile(JNIEnv *env, jobject obj) {
    // Getting the restaurantCustomers ArrayList attribute of InMemoryRestaurantCustomerStore
    jclass restaurantCustomerClass = env->FindClass("com/operatoroverloaded/hotel/stores/restaurantstore/RestaurantCustomer");
    jclass InMemoryRestaurantCustomerStoreClass = env->GetObjectClass(obj);
    jfieldID restaurantCustomerArrayField = env->GetFieldID(InMemoryRestaurantCustomerStoreClass, "restaurantCustomers", "Ljava/util/ArrayList;");
    jobject restaurantCustomerArray = env->GetObjectField(obj, restaurantCustomerArrayField);

    // Getting the RestaurantCustomer constructor
    jmethodID constructor = env->GetMethodID(restaurantCustomerClass, "<init>", "()V");

    // Get ArrayList class and its methods
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    // Get DateTime class
    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
    jmethodID dateTimeConstructor = env->GetMethodID(dateTimeClass, "<init>", "(IIIIII)V");

    // Read from the file
    ifstream infile;
    infile.open("RestaurantCustomerStore.txt", ios::in);
    string customerData = "";
    while (getline(infile, customerData)) {
        jobject customer = env->NewObject(restaurantCustomerClass, constructor);

        istringstream ss(customerData);
        int customerId;
        string name, email, phone, address;
        double bill_amt, bill_payed, bill_left;
        
        ss >> customerId >> name >> email >> phone >> address >> bill_amt >> bill_payed >> bill_left;
        
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setCustomerId", "(I)V"), customerId);
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setName", "(Ljava/lang/String;)V"), stdStringToJString(env, name));
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setEmail", "(Ljava/lang/String;)V"), stdStringToJString(env, email));
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setPhone", "(Ljava/lang/String;)V"), stdStringToJString(env, phone));
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setAddress", "(Ljava/lang/String;)V"), stdStringToJString(env, address));
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setBillAmount", "(D)V"), bill_amt);
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setBillPayed", "(D)V"), bill_payed);
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setBillLeft", "(D)V"), bill_left);

        int bills_size;
        ss >> bills_size;
        while (bills_size--) {
            int x;
            ss >> x;
            env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "addBill", "(I)V"), x);
        }

        int yearFrom, monthFrom, dayFrom, hourFrom, minFrom, secFrom;
        ss >> yearFrom >> monthFrom >> dayFrom >> hourFrom >> minFrom >> secFrom;
        jobject reservedFrom = env->NewObject(dateTimeClass, dateTimeConstructor, yearFrom, monthFrom, dayFrom, hourFrom, minFrom, secFrom);

        int yearTo, monthTo, dayTo, hourTo, minTo, secTo;
        ss >> yearTo >> monthTo >> dayTo >> hourTo >> minTo >> secTo;
        jobject reservedTo = env->NewObject(dateTimeClass, dateTimeConstructor, yearTo, monthTo, dayTo, hourTo, minTo, secTo);

        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setReservedFrom", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), reservedFrom);
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setReservedTo", "(Lcom/operatoroverloaded/hotel/models/DateTime;)V"), reservedTo);

        int dish_size;
        ss >> dish_size;
        while (dish_size--) {
            int x;
            ss >> x;
            env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "addDish", "(I)V"), x);
        }
        int tableId, serverId;
        ss >> tableId >> serverId;

        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setTableId", "(I)V"), tableId);
        env->CallVoidMethod(customer, env->GetMethodID(restaurantCustomerClass, "setServerId", "(I)V"), serverId);
        // Add the customer to the ArrayList
        env->CallBooleanMethod(restaurantCustomerArray, addMethod, customer);
    }
    infile.close();
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_restaurant_stores_restaurantcustomerstore_InMemoryRestaurantCustomerStore_storeToFile(JNIEnv *env, jobject obj){

    // Getting the restaurantCustomers ArrayList attribute of InMemoryRestaurantCustomerStore
    jclass restaurantCustomerClass = env->FindClass("com/operatoroverloaded/hotel/stores/restaurantstore/RestaurantCustomer");
    jclass InMemoryRestaurantCustomerStoreClass = env->GetObjectClass(obj);
    jfieldID restaurantCustomerArrayField = env->GetFieldID(InMemoryRestaurantCustomerStoreClass, "restaurantCustomers", "Ljava/util/ArrayList;");
    jobject restaurantCustomerArray = env->GetObjectField(obj, restaurantCustomerArrayField);

    // Get ArrayList class and its methods
    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
    jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");

    jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");

    jint customerSize = env->CallIntMethod(restaurantCustomerArray, arrayListSize);
    ofstream outfile;
    outfile.open("RestaurantCustomerStore.txt");
    for (int i=0; i<customerSize; i++){
        jobject customer = env->CallObjectMethod(restaurantCustomerArray, arrayListGet, i);
        jint id = env->CallIntMethod(customer, env->GetMethodID(restaurantCustomerClass, "getCustomerId", "()I"));
        string name = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getName", "()Ljava/lang/String;")));
        string email = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getEmail", "()Ljava/lang/String;")));
        string phone = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getPhone", "()Ljava/lang/String;")));
        string address = jStringToStdString(env, (jstring) env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getAddress", "()Ljava/lang/String;")));
        jdouble billAmt = env->CallDoubleMethod(customer, env->GetMethodID(restaurantCustomerClass, "getBillAmt", "()D"));
        jdouble billPayed = env->CallDoubleMethod(customer, env->GetMethodID(restaurantCustomerClass, "getBillPayed", "()D"));
        jdouble billLeft = env->CallDoubleMethod(customer, env->GetMethodID(restaurantCustomerClass, "getBillLeft", "()D"));

        outfile << id << " "<< name << " "<< email << " "<< phone << " "<< address<< " " << billAmt << " "<< billPayed << " "<< billLeft << " ";

        jobject bills = env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getBills", "()Ljava/util/ArrayList;"));

        jint billSize = env->CallIntMethod(bills, arrayListSize);
        outfile << billSize << " ";
        for (int i = 0; i<billSize; i++) {
            jint bill = env->CallIntMethod(bills, arrayListGet, i);
            outfile <<  bill << " ";
        }
        jobject from = env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getReservedFrom", "()Lcom/operatoroverloaded/hotel/models/DateTime"));
        jobject to = env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getReservedTo", "()Lcom/operatoroverloaded/hotel/models/DateTime"));

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

        jobject dishArray = env->CallObjectMethod(customer, env->GetMethodID(restaurantCustomerClass, "getDishes", "()Ljava/util/ArrayList;"));
        jint dishSize = env->CallIntMethod(dishArray, arrayListSize);
        outfile << dishSize << " ";
        for (int i = 0; i< dishSize; i++) {
            jint dish = env->CallIntMethod(dishArray, arrayListGet, i);
            outfile << dish << " ";
        }
        outfile << env->CallIntMethod(customer, env->GetMethodID(restaurantCustomerClass, "getTableId", "()I")) << " ";
        outfile << env->CallIntMethod(customer, env->GetMethodID(restaurantCustomerClass, "getServerId", "()I")) << " ";
        outfile << endl;
    }
    outfile.close();
}