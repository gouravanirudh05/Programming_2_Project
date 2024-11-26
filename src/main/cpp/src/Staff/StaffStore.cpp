#include <jni.h>
#include <fstream>
#include <string>
#include <vector>
#include <algorithm>
#include "com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore.h"

using namespace std;

extern "C" {

    /**
     * Function to load staff data from the "staff.txt" file into the Java `ArrayList` in memory.
     * 
     * @param env - Pointer to the JNI environment.
     * @param obj - Reference to the calling Java object (`InMemoryStaffStore`).
     */
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_loadFromFile(JNIEnv *env, jobject obj) {
        // Open the file for reading staff data
        ifstream fin("staff.txt");
        if (!fin.is_open()) {
            return; // Exit if the file cannot be opened
        }

        // Read the entire file content into a string
        string file;
        file.assign((istreambuf_iterator<char>(fin)), (istreambuf_iterator<char>()));
        fin.close();

        // Find the Java classes and methods needed
        jclass staffClass = env->FindClass("com/operatoroverloaded/hotel/models/Staff");
        jclass staffStoreClass = env->GetObjectClass(obj);
        jfieldID staffArrayField = env->GetFieldID(staffStoreClass, "staffList", "Ljava/util/ArrayList;");
        jobject staffArray = env->GetObjectField(obj, staffArrayField);
        jmethodID staffInit = env->GetMethodID(staffClass, "<init>", "(IFLjava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

        // Get methods for `ArrayList` operations
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

        size_t i = 0;
        while (i < file.size()) {
            size_t j = i;

            // Parse staffID
            while (file[j] != '\n') j++;
            jint staffID = stoi(file.substr(i, j - i));
            i = j + 1;

            // Parse name
            j = i;
            while (file[j] != '\n') j++;
            jstring name = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse salary
            j = i;
            while (file[j] != '\n') j++;
            jfloat salary = stof(file.substr(i, j - i));
            i = j + 1;

            // Parse phone
            j = i;
            while (file[j] != '\n') j++;
            jint phone = stoi(file.substr(i, j - i));
            i = j + 1;

            // Parse address
            j = i;
            while (file[j] != '\n') j++;
            jstring address = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse role
            j = i;
            while (file[j] != '\n') j++;
            jstring role = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse workingFrom
            j = i;
            while (file[j] != '-') j++;
            jstring workingFrom = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse retiredOn
            j = i;
            while (file[j] != '-') j++;
            jstring retiredOn = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse assignedTo
            j = i;
            while (file[j] != '\n') j++;
            jstring assignedTo = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Create a new `Staff` object in Java
            jobject staffObject = env->NewObject(staffClass, staffInit, staffID, name, salary, phone, address, role, workingFrom, retiredOn, assignedTo);

            // Add the `Staff` object to the Java ArrayList
            env->CallBooleanMethod(staffArray, arrayListAdd, staffObject);

            while(file[i] == '\n' || file[i] == '\\') i++;

            // Release local references to prevent memory leaks
            env->DeleteLocalRef(name);
            env->DeleteLocalRef(address);
            env->DeleteLocalRef(role);
            env->DeleteLocalRef(workingFrom);
            env->DeleteLocalRef(retiredOn);
            env->DeleteLocalRef(assignedTo);
            env->DeleteLocalRef(staffObject);
        }
    }

    /**
     * Function to save staff data from the Java `ArrayList` to the "staff.txt" file.
     * 
     * @param env - Pointer to the JNI environment.
     * @param obj - Reference to the calling Java object (`InMemoryStaffStore`).
     */
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_saveToFile(JNIEnv *env, jobject obj) {
        // Open the file for writing staff data
        ofstream fout("staff.txt");
        if (!fout.is_open()) {
            return; // Exit if the file cannot be opened
        }

        // Retrieve the Java `ArrayList` field containing staff data
        jclass staffStoreClass = env->GetObjectClass(obj);
        jfieldID staffArrayField = env->GetFieldID(staffStoreClass, "staffData", "Ljava/util/ArrayList;");
        jobject staffArray = env->GetObjectField(obj, staffArrayField);

        // Get methods for accessing the `ArrayList`
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        jint length = env->CallIntMethod(staffArray, arrayListSize);

        // Get field IDs for `Staff` attributes
        jclass staffClass = env->FindClass("com/operatoroverloaded/hotel/models/Staff");
        jfieldID staffIDField = env->GetFieldID(staffClass, "staffID", "I");
        jfieldID nameField = env->GetFieldID(staffClass, "name", "Ljava/lang/String;");
        jfieldID salaryField = env->GetFieldID(staffClass, "salary", "F");
        jfieldID phoneField = env->GetFieldID(staffClass, "phone", "I");
        jfieldID addressField = env->GetFieldID(staffClass, "address", "Ljava/lang/String;");
        jfieldID roleField = env->GetFieldID(staffClass, "role", "Ljava/lang/String;");
        jfieldID workingFromField = env->GetFieldID(staffClass, "workingFrom", "Lcom/operatoroverloaded/hotel/models/DateTime;");
        jfieldID retiredOnField = env->GetFieldID(staffClass, "retiredOn", "Lcom/operatoroverloaded/hotel/models/DateTime;");
        jfieldID assignedToField = env->GetFieldID(staffClass, "assignedTo", "Ljava/lang/String;");

        jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/models/DateTime");
        jmethodID getDateString = env->GetMethodID(dateTimeClass, "getDateString", "()Ljava/lang/String;");
        jmethodID getTimeString = env->GetMethodID(dateTimeClass, "getTimeString", "()Ljava/lang/String;");

        // Iterate over each `Staff` object in the ArrayList
        for (jint i = 0; i < length; i++) {
            jobject staffObject = env->CallObjectMethod(staffArray, arrayListGet, i);

            // Retrieve each field from the `Staff` object
            jint staffID = env->GetIntField(staffObject, staffIDField);
            jstring name = (jstring) env->GetObjectField(staffObject, nameField);
            jfloat salary = env->GetFloatField(staffObject, salaryField);
            jint phone = env->GetIntField(staffObject, phoneField);
            jstring address = (jstring) env->GetObjectField(staffObject, addressField);
            jstring role = (jstring) env->GetObjectField(staffObject, roleField);
            jobject workingFrom = env->GetObjectField(staffObject, workingFromField);
            jobject retiredOn = env->GetObjectField(staffObject, retiredOnField);
            jstring assignedTo = (jstring) env->GetObjectField(staffObject, assignedToField);

            // Retrieve date and time strings from `DateTime` objects
            jstring workingFromDateString = (jstring) env->CallObjectMethod(workingFrom, getDateString);
            jstring workingFromTimeString = (jstring) env->CallObjectMethod(workingFrom, getTimeString);
            jstring retiredOnDateString = retiredOn != nullptr ? (jstring) env->CallObjectMethod(retiredOn, getDateString) : nullptr;
            jstring retiredOnTimeString = retiredOn != nullptr ? (jstring) env->CallObjectMethod(retiredOn, getTimeString) : nullptr;

            // Convert Java strings to C++ native strings
            const char *nativeWorkingFromDate = env->GetStringUTFChars(workingFromDateString, nullptr);
            const char *nativeWorkingFromTime = env->GetStringUTFChars(workingFromTimeString, nullptr);
            const char *nativeRetiredOnDate = retiredOnDateString != nullptr ? env->GetStringUTFChars(retiredOnDateString, nullptr) : "";
            const char *nativeRetiredOnTime = retiredOnTimeString != nullptr ? env->GetStringUTFChars(retiredOnTimeString, nullptr) : "";
            
            // Replace '-' with '/' in the date strings
            string workingFromDateStr(nativeWorkingFromDate);
            replace(workingFromDateStr.begin(), workingFromDateStr.end(), '-', '/');
            string workingFromTimeStr(nativeWorkingFromTime);
            replace(workingFromTimeStr.begin(), workingFromTimeStr.end(), '-', '/');
            string retiredOnDateStr(nativeRetiredOnDate);
            replace(retiredOnDateStr.begin(), retiredOnDateStr.end(), '-', '/');
            string retiredOnTimeStr(nativeRetiredOnTime);
            replace(retiredOnTimeStr.begin(), retiredOnTimeStr.end(), '-', '/');

            // Write the staff data to the file
            const char *nativeName = env->GetStringUTFChars(name, nullptr);
            const char *nativeAddress = env->GetStringUTFChars(address, nullptr);
            const char *nativeRole = env->GetStringUTFChars(role, nullptr);
            const char *nativeAssignedTo = env->GetStringUTFChars(assignedTo, nullptr);

            fout << staffID << '\n'
                 << nativeName << '\n'
                 << salary << '\n'
                 << phone << '\n'
                 << nativeAddress << '\n'
                 << nativeRole << '\n'
                 << workingFromDateStr << '-' << workingFromTimeStr << '\n'
                 << retiredOnDateStr << '-' << retiredOnTimeStr << '\n'
                 << nativeAssignedTo << '\n';

            // Release resources
            env->ReleaseStringUTFChars(name, nativeName);
            env->ReleaseStringUTFChars(address, nativeAddress);
            env->ReleaseStringUTFChars(role, nativeRole);
            env->ReleaseStringUTFChars(assignedTo, nativeAssignedTo);
            env->ReleaseStringUTFChars(workingFromDateString, nativeWorkingFromDate);
            env->ReleaseStringUTFChars(workingFromTimeString, nativeWorkingFromTime);
            if (retiredOnDateString) {
                env->ReleaseStringUTFChars(retiredOnDateString, nativeRetiredOnDate);
                env->ReleaseStringUTFChars(retiredOnTimeString, nativeRetiredOnTime);
            }

            // Release local references to prevent memory leaks
            env->DeleteLocalRef(staffObject);
            env->DeleteLocalRef(name);
            env->DeleteLocalRef(address);
            env->DeleteLocalRef(role);
            env->DeleteLocalRef(assignedTo);
            env->DeleteLocalRef(workingFrom);
            if (retiredOn) {
                env->DeleteLocalRef(retiredOn);
            }
        }

        // Close the file
        fout.close();
    }
}
