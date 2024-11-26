#include <bits/stdc++.h>
#include <fstream>
#include <sstream>
// #include "com_operatoroverloaded_hotel_stores_tablestore_InMemoryTableStore.h"
#include <jni.h>

using namespace std;
// Utility function to convert std::string to jstring
jstring stdStringToJString(JNIEnv* env, const string& cppString) {
    return env->NewStringUTF(cppString.c_str());
}

// Utility function to convert jstring to std::string
string jStringToStdString(JNIEnv* env, jstring javaString) {
    if (javaString == nullptr) return "";
    const char* utfString = env->GetStringUTFChars(javaString, nullptr);
    string cppString(utfString);
    env->ReleaseStringUTFChars(javaString, utfString);
    return cppString;
}
extern "C"{

// Load table data from file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_tablestore_InMemoryTableStore_loadFromFile(JNIEnv *env, jobject obj) {
    jclass tableClass = env->FindClass("com/operatoroverloaded/hotel/models/Table");
    jclass storeClass = env->GetObjectClass(obj);

    jfieldID tableListField = env->GetFieldID(storeClass, "tables", "Ljava/util/ArrayList;");
    jobject tableList = env->GetObjectField(obj, tableListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    ifstream infile("TableStore.txt");
    string line;

    while (getline(infile, line)) {
        istringstream ss(line);
        vector<string> tokens;
        string token;

        while (getline(ss, token, '|')) tokens.push_back(token);

        if (tokens.size() < 4) continue; // Ensure valid data

        jobject table = env->NewObject(tableClass, env->GetMethodID(tableClass, "<init>", "(II)V"),
                                       stoi(tokens[0]), stoi(tokens[3]));

        if (tokens[1] == "1") {
            env->CallVoidMethod(table, env->GetMethodID(tableClass, "reserveTable", "()V"));
        }

        if (tokens[2] == "1") {
            env->CallVoidMethod(table, env->GetMethodID(tableClass, "occupyTable", "()V"));
        }

        env->CallBooleanMethod(tableList, addMethod, table);
    }

    infile.close();
}

// Save table data to file
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_tablestore_InMemoryTableStore_storeToFile(JNIEnv *env, jobject obj) {
    jclass storeClass = env->GetObjectClass(obj);
    jfieldID tableListField = env->GetFieldID(storeClass, "tables", "Ljava/util/ArrayList;");
    jobject tableList = env->GetObjectField(obj, tableListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID sizeMethod = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");

    jclass tableClass = env->FindClass("com/operatoroverloaded/hotel/models/Table");

    ofstream outfile("TableStore.txt");

    jint size = env->CallIntMethod(tableList, sizeMethod);
    for (int i = 0; i < size; ++i) {
        jobject table = env->CallObjectMethod(tableList, getMethod, i);

        jint tableNumber = env->CallIntMethod(table, env->GetMethodID(tableClass, "getTableNumber", "()I"));
        jboolean isReserved = env->CallBooleanMethod(table, env->GetMethodID(tableClass, "isReserved", "()Z"));
        jboolean isOccupied = env->CallBooleanMethod(table, env->GetMethodID(tableClass, "isOccupied", "()Z"));
        jint seatingCapacity = env->CallIntMethod(table, env->GetMethodID(tableClass, "getSeatingCapacity", "()I"));

        outfile << tableNumber << "|" << (isReserved ? 1 : 0) << "|" << (isOccupied ? 1 : 0) << "|" << seatingCapacity << "\n";
    }

    outfile.close();
}
}