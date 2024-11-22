#include <fstream>
#include <jni.h>
// #include "com_operatoroverloaded_hotel_stores_roomstore_InMemoryRoomStore.h"
#include <string>
#include <iostream>
#include <algorithm>

using namespace std;

// roomId-capacity-roomTypeId-YYYY/MM/DD-HH:MM:SS\

extern "C" {
    // function to save the list of rooms to file (follow BillCPP.cpp for reference)
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_roomstore_InMemoryRoomStore_saveToFile (JNIEnv *env, jobject obj){
        ofstream fout("room.txt");
        if (!fout.is_open()) {
            return;
        }

        jclass roomClass = env->FindClass("com/operatoroverloaded/hotel/stores/roomstore/Room");
        jclass InMemoryRoomStoreClass = env->GetObjectClass(obj);
        jfieldID roomArrayField = env->GetFieldID(InMemoryRoomStoreClass, "rooms", "Ljava/util/ArrayList;");
        jobject roomArray = env->GetObjectField(obj, roomArrayField);

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        jint length = env->CallIntMethod(roomArray, arrayListSize);

        jmethodID getRoomID = env->GetMethodID(roomClass, "getRoomID", "()Ljava/lang/String;");
        jmethodID getCapacity = env->GetMethodID(roomClass, "getCapacity", "()I");
        jmethodID getRoomTypeId = env->GetMethodID(roomClass, "getRoomTypeId", "()Ljava/lang/String;");
        jmethodID getHousekeepingLast = env->GetMethodID(roomClass, "getHousekeepingLast", "()Lcom/operatoroverloaded/hotel/stores/roomstore/DateTime;");

        jclass dateTimeClass = env->FindClass("com/operatoroverloaded/hotel/stores/roomstore/DateTime");
        jmethodID getDateString = env->GetMethodID(dateTimeClass, "getDateString", "()Ljava/lang/String;");
        jmethodID getTimeString = env->GetMethodID(dateTimeClass, "getTimeString", "()Ljava/lang/String;");

        for (jint i = 0; i < length; i++) {
            jobject room = env->CallObjectMethod(roomArray, arrayListGet, i);

            jstring roomId = (jstring)env->CallObjectMethod(room, getRoomID);
            jint capacity = env->CallIntMethod(room, getCapacity);
            jstring roomTypeId = (jstring)env->CallObjectMethod(room, getRoomTypeId);
            jobject housekeepingLog = env->CallObjectMethod(room, getHousekeepingLast);

            const char *roomIdStr = env->GetStringUTFChars(roomId, 0);
            const char *roomTypeIdStr = env->GetStringUTFChars(roomTypeId, 0);

            jstring dateStr = (jstring)env->CallObjectMethod(housekeepingLog, getDateString);
            jstring timeStr = (jstring)env->CallObjectMethod(housekeepingLog, getTimeString);

            const char *dateCStr = env->GetStringUTFChars(dateStr, 0);
            const char *timeCStr = env->GetStringUTFChars(timeStr, 0);

            string formattedDate = dateCStr;
            replace(formattedDate.begin(), formattedDate.end(), '-', '/');

            fout << roomIdStr << "-" << capacity << "-" << roomTypeIdStr << "-" 
             << formattedDate << "-" << timeCStr << "\\" << endl;

            env->ReleaseStringUTFChars(roomId, roomIdStr);
            env->ReleaseStringUTFChars(roomTypeId, roomTypeIdStr);
            env->ReleaseStringUTFChars(dateStr, dateCStr);
            env->ReleaseStringUTFChars(timeStr, timeCStr);

            env->DeleteLocalRef(room);
            env->DeleteLocalRef(roomId);
            env->DeleteLocalRef(roomTypeId);
            env->DeleteLocalRef(dateStr);
            env->DeleteLocalRef(timeStr);
            env->DeleteLocalRef(housekeepingLog);
        }

        fout.close();
    }

    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_roomstore_InMemoryRoomStore_loadFromFile(JNIEnv *env, jobject obj){
        ifstream fin("room.txt");
        if (!fin.is_open()) {
            return;
        }

        string file; file.assign((istreambuf_iterator<char>(fin)), (istreambuf_iterator<char>()));
        fin.close();

        jclass roomClass = env->FindClass("com/operatoroverloaded/hotel/stores/roomstore/Room");
        jclass InMemoryRoomStoreClass = env->GetObjectClass(obj);
        jfieldID roomArrayField = env->GetFieldID(InMemoryRoomStoreClass, "rooms", "Ljava/util/ArrayList;");
        jobject roomArray = env->GetObjectField(obj, roomArrayField);
        jmethodID roomInit = env->GetMethodID(roomClass, "<init>", "(Ljava/lang/String;ILjava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

        long long i = 0;
        while(i < file.size()) {
            long long j = i;
            while(file[j] != '-') j++;
            jstring roomId = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jint capacity = stoi(file.substr(i, j - i));
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jstring roomTypeId = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jstring dateStr = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while(file[j] != '\\') j++;
            jstring timeStr = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 2;

            jobject room = env->NewObject(roomClass, roomInit, roomId, capacity, roomTypeId, dateStr, timeStr);
            env->CallBooleanMethod(roomArray, arrayListAdd, room);

            while(file[i] == '\n' || file[i] == '\\') i++;

            env->DeleteLocalRef(roomId);
            env->DeleteLocalRef(roomTypeId);
            env->DeleteLocalRef(dateStr);
            env->DeleteLocalRef(timeStr);
            env->DeleteLocalRef(room);
        }

        env->DeleteLocalRef(roomClass);
        env->DeleteLocalRef(InMemoryRoomStoreClass);
        env->DeleteLocalRef(roomArray);
        env->DeleteLocalRef(arrayListClass);
    }


}