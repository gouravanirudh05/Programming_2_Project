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
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_loadStaff(JNIEnv *env, jobject obj) {
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
        jfieldID staffArrayField = env->GetFieldID(staffStoreClass, "staffData", "Ljava/util/ArrayList;");
        jobject staffArray = env->GetObjectField(obj, staffArrayField);
        jmethodID staffInit = env->GetMethodID(staffClass, "<init>", "(ILjava/lang/String;FILjava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

        // Get methods for `ArrayList` operations
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

        size_t i = 0;
        while (i < file.size()) {
            size_t j = i;

            // Parse staffID
            while (file[j] != '-') j++;
            jint staffID = stoi(file.substr(i, j - i));
            i = j + 1;

            // Parse name
            j = i;
            while (file[j] != '-') j++;
            jstring name = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse salary
            j = i;
            while (file[j] != '-') j++;
            jfloat salary = stof(file.substr(i, j - i));
            i = j + 1;

            // Parse phone
            j = i;
            while (file[j] != '-') j++;
            jint phone = stoi(file.substr(i, j - i));
            i = j + 1;

            // Parse address
            j = i;
            while (file[j] != '-') j++;
            jstring address = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Parse role
            j = i;
            while (file[j] != '-') j++;
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
            while (file[j] != '\\') j++;
            jstring assignedTo = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Create a new `Staff` object in Java
            jobject staffObject = env->NewObject(staffClass, staffInit, staffID, name, salary, phone, address, role, workingFrom, retiredOn, assignedTo);

            // Add the `Staff` object to the Java ArrayList
            env->CallBooleanMethod(staffArray, arrayListAdd, staffObject);

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
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_saveStaff(JNIEnv *env, jobject obj) {
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
        jfieldID workingFromField = env->GetFieldID(staffClass, "workingFrom", "Ljava/lang/String;");
        jfieldID retiredOnField = env->GetFieldID(staffClass, "retiredOn", "Ljava/lang/String;");
        jfieldID assignedToField = env->GetFieldID(staffClass, "assignedTo", "Ljava/lang/String;");

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
            jstring workingFrom = (jstring) env->GetObjectField(staffObject, workingFromField);
            jstring retiredOn = (jstring) env->GetObjectField(staffObject, retiredOnField);
            jstring assignedTo = (jstring) env->GetObjectField(staffObject, assignedToField);

            // Convert Java strings to C++ native strings
            const char *nativeName = env->GetStringUTFChars(name, nullptr);
            const char *nativeAddress = env->GetStringUTFChars(address, nullptr);
            const char *nativeRole = env->GetStringUTFChars(role, nullptr);
            const char *nativeWorkingFrom = env->GetStringUTFChars(workingFrom, nullptr);
            const char *nativeRetiredOn = retiredOn != nullptr ? env->GetStringUTFChars(retiredOn, nullptr) : "";
            const char *nativeAssignedTo = env->GetStringUTFChars(assignedTo, nullptr);

            // Write the staff data to the file
            fout << staffID << "-" << nativeName << "-" << salary << "-" << phone << "-"
                 << nativeAddress << "-" << nativeRole << "-" << nativeWorkingFrom << "-"
                 << nativeRetiredOn << "-" << nativeAssignedTo << "\\" << endl;

            // Release the native strings
            env->ReleaseStringUTFChars(name, nativeName);
            env->ReleaseStringUTFChars(address, nativeAddress);
            env->ReleaseStringUTFChars(role, nativeRole);
            env->ReleaseStringUTFChars(workingFrom, nativeWorkingFrom);
            if (retiredOn != nullptr) {
                env->ReleaseStringUTFChars(retiredOn, nativeRetiredOn);
            }
            env->ReleaseStringUTFChars(assignedTo, nativeAssignedTo);

            // Delete the local references to free memory
            env->DeleteLocalRef(staffObject);
        }
        fout.close();
    }
}
