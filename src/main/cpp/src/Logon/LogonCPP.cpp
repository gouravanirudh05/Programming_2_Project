#include <jni.h>
#include <fstream>
#include <sstream>
#include <string>
#include <vector>
#include <map>
#include <openssl/sha.h>
#include <iomanip>
#include <random>
#include "com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore.h"
#include <iostream>
using namespace std;

const int SALT_SIZE = 16;

// Hash the password with the salt
string hashPassword(const string &password, const string &salt) {
    unsigned char hash[SHA256_DIGEST_LENGTH];
    string saltedPassword = password + salt;
    SHA256((unsigned char *)saltedPassword.c_str(), saltedPassword.size(), hash);

    stringstream ss;
    for (int i = 0; i < SHA256_DIGEST_LENGTH; i++) {
        ss << hex << setw(2) << setfill('0') << (int)hash[i];
    }
    return ss.str();
}

// format the database data
void format(vector<map<string, string>>& mp, string file){
    int i = 0;
    vector<string> strings;
    while(i < file.size()){
        map<string, string> m;
        while(file[i] == '\\')i++;
        while(i < file.size() && file[i] != '\\'){
            while(i < file.size() && file[i] == '-'){
                i++;
            }
            string value = "";
            while(i < file.size() && file[i] != '-' && file[i] != '\\'){
                value += file[i];
                i++;
            }
            strings.push_back(value);
            if(file[i] == '\\') break;
            i++;
        }
        m["id"] = strings[0];
        m["access"] = strings[1];
        m["email"] = strings[2];
        m["password"] = strings[3];
        m["salt"] = strings[4];
        mp.push_back(m);
        strings = {};
        if(file[i] == '\\') i++;
        if(file[i] == '\n') i++;
    }


}

// Generate a random string of specified length
string generateRandomString(size_t length) {
    const string CHARACTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789";
    random_device rd;
    mt19937 generator(rd());
    uniform_int_distribution<> distribution(0, CHARACTERS.size() - 1);

    string randomString;
    for (size_t i = 0; i < length; ++i) {
        randomString += CHARACTERS[distribution(generator)];
    }
    return randomString;
}

extern "C" {
    // to generate a random salt
    JNIEXPORT jstring JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_getRandomSalt(JNIEnv *env, jobject obj) {
        return env->NewStringUTF(generateRandomString(SALT_SIZE).c_str());
    }   

    // to hash the password
    JNIEXPORT jstring JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_hashString(JNIEnv *env, jobject obj, jstring password, jstring salt) {
        const char *nativePwd = env->GetStringUTFChars(password, 0);
        const char *nativeSalt = env->GetStringUTFChars(salt, 0);
        string hashedPassword = hashPassword(string(nativePwd), string(nativeSalt));
        string result = hashedPassword;
        env->ReleaseStringUTFChars(password, nativePwd);
        return env->NewStringUTF(result.c_str());
    }

    // to load the login data from the file
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_loadLogon(JNIEnv *env, jobject obj) {
        // Read the contents of login.txt
        // TODO: change the path to the absolute path of the login.txt file as needed
        ifstream file("login.txt");
        if (!file.is_open()) {
            return;
        }

        string fileContent((istreambuf_iterator<char>(file)), istreambuf_iterator<char>());
        file.close();

        // Parse the file content and store it in a vector of maps
        vector<map<string, string>> mp;
        format(mp, fileContent);

        // Get the ArrayList class
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        if (arrayListClass == nullptr) {
            return;
        }

        // Get the ArrayList constructor
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");
        if (arrayListInit == nullptr) {
            return;
        }

        // Get the ArrayList add method
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        if (arrayListAdd == nullptr) {
            return;
        }

        // Create an ArrayList object
        jobject outerArrayList = env->NewObject(arrayListClass, arrayListInit);
        if (outerArrayList == nullptr) {
            return;
        }

        // Get the Logon class 
        // TODO: change the path to the Logon class as needed
        jclass logonClass = env->FindClass("com/operatoroverloaded/hotel/models/Logon");
        if (logonClass == nullptr) {
            return;
        }

        // Get the Logon constructor
        jmethodID logonInit = env->GetMethodID(logonClass, "<init>", "(ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");
        if (logonInit == nullptr) {
            return;
        }

        // Iterate over the vector of maps and create Logon objects
        for (const auto &entry : mp) {
            jint id = stoi(entry.at("id"));
            jstring access = env->NewStringUTF(entry.at("access").c_str());
            jstring email = env->NewStringUTF(entry.at("email").c_str());
            jstring password = env->NewStringUTF(entry.at("password").c_str());
            jstring salt = env->NewStringUTF(entry.at("salt").c_str());

            // Verify all values before creating the Logon object
            jobject logonObject = env->NewObject(logonClass, logonInit, id, access, email, password, salt);
            if (logonObject == nullptr) {
                continue;
            }

            env->CallBooleanMethod(outerArrayList, arrayListAdd, logonObject);

            // Clean up references
            env->DeleteLocalRef(access);
            env->DeleteLocalRef(email);
            env->DeleteLocalRef(password);
            env->DeleteLocalRef(salt);
            env->DeleteLocalRef(logonObject);
        }

        // Set the ArrayList in the InMemoryLogonStore object
        jclass InMemoryLogonStoreClass = env->GetObjectClass(obj);
        jfieldID logonDataField = env->GetFieldID(InMemoryLogonStoreClass, "logonData", "Ljava/util/ArrayList;");
        env->SetObjectField(obj, logonDataField, outerArrayList);

        // Clean up references
        env->DeleteLocalRef(outerArrayList);
    }

    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_saveLogon(JNIEnv *env, jobject obj) {
        // TODO: change the path to the login.txt file as needed
        ofstream file("login.txt");
        if(!file.is_open()){
            return;
        }

        // Get the Logon class and the ArrayList field
        // TODO: change the path to the Logon class as needed
        jclass logonClass = env->FindClass("com/operatoroverloaded/hotel/models/Logon");
        jclass InMemoryLogonStoreClass = env->GetObjectClass(obj);
        jfieldID logonArrayField = env->GetFieldID(InMemoryLogonStoreClass, "logonData", "Ljava/util/ArrayList;");
        jobject logonArray = env->GetObjectField(obj, logonArrayField);

        // Get the ArrayList methods
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        jint length = env->CallIntMethod(logonArray, arrayListSize);

        // Get the Logon fields
        jfieldID roleIdField = env->GetFieldID(logonClass, "roleId", "I");
        jfieldID accessField = env->GetFieldID(logonClass, "access", "Lcom/operatoroverloaded/hotel/models/Logon$AccessLevel;");  // Fix for enum
        jfieldID emailField = env->GetFieldID(logonClass, "email", "Ljava/lang/String;");
        jfieldID passwordField = env->GetFieldID(logonClass, "password", "Ljava/lang/String;");
        jfieldID saltField = env->GetFieldID(logonClass, "salt", "Ljava/lang/String;");

        // Get the AccessLevel enum and the toString method
        // TODO: change the path to the AccessLevel enum as needed
        jclass accessLevelClass = env->FindClass("com/operatoroverloaded/hotel/models/Logon$AccessLevel");
        jmethodID toStringMethod = env->GetMethodID(accessLevelClass, "toString", "()Ljava/lang/String;");
        
        for(jint i = 0; i < length; i++) {
            // Get the Logon object
            jobject logonObject = env->CallObjectMethod(logonArray, arrayListGet, i);

            // Get the field values
            jint roleId = env->GetIntField(logonObject, roleIdField);
            jobject accessEnum = env->GetObjectField(logonObject, accessField);  // Access enum as an object
            jstring access = (jstring) env->CallObjectMethod(accessEnum, toStringMethod);  // Convert enum to String
            jstring email = (jstring) env->GetObjectField(logonObject, emailField);
            jstring password = (jstring) env->GetObjectField(logonObject, passwordField);
            jstring salt = (jstring) env->GetObjectField(logonObject, saltField);

            // Convert the java strings to native C++ strings
            const char *nativeAccess = access ? env->GetStringUTFChars(access, nullptr) : nullptr;
            const char *nativeEmail = email ? env->GetStringUTFChars(email, nullptr) : nullptr;
            const char *nativePassword = password ? env->GetStringUTFChars(password, nullptr) : nullptr;
            const char *nativeSalt = salt ? env->GetStringUTFChars(salt, nullptr) : nullptr;
            
            // Write the data to the file
            file << roleId << "-" << string(nativeAccess) << "-" << string(nativeEmail) << "-" << string(nativePassword) << "-" << string(nativeSalt) << "\\" << endl;

            // Release the native strings
            env->ReleaseStringUTFChars(access, nativeAccess);
            env->ReleaseStringUTFChars(email, nativeEmail);
            env->ReleaseStringUTFChars(password, nativePassword);
            env->ReleaseStringUTFChars(salt, nativeSalt);
            
            // Clean up references
            env->DeleteLocalRef(logonObject);
            env->DeleteLocalRef(access);
            env->DeleteLocalRef(email);
            env->DeleteLocalRef(password);
            env->DeleteLocalRef(salt);
            env->DeleteLocalRef(accessEnum);  // Free enum object reference
        }
        file.close();
    }
}