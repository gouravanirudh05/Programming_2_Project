#include <fstream>
#include <jni.h>
// #include "com_operatoroverloaded_hotel_stores_roomstore_InMemoryRoomTypeStore.h"
#include <string>
#include <iostream>
#include <algorithm>

using namespace std;

// roomTypeId-roomTypeName-tariff-amenity1:amenity2:...:amenityn\

extern "C" {
    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_roomtypestore_InMemoryRoomTypeStore_saveToFile (JNIEnv* env, jobject obj){
        ofstream fout("roomtype.txt");
        if (!fout.is_open()) {
            return;
        }

        cout << 1 << endl;
        jclass roomTypeClass = env->FindClass("com/operatoroverloaded/hotel/models/RoomType");
        cout << 2 << endl;
        jclass InMemoryRoomTypeStoreClass = env->GetObjectClass(obj);
        cout << 3 << endl;
        jfieldID roomTypeArrayField = env->GetFieldID(InMemoryRoomTypeStoreClass, "roomTypes", "Ljava/util/ArrayList;");
        cout << 4 << endl;
        jobject roomTypeArray = env->GetObjectField(obj, roomTypeArrayField);
        cout << 5 << endl;

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        cout << 6 << endl;
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        cout << 7 << endl;
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        cout << 8 << endl;
        jint length = env->CallIntMethod(roomTypeArray, arrayListSize);\
        cout << 9 << endl;

        jmethodID getRoomTypeId = env->GetMethodID(roomTypeClass, "getRoomTypeId", "()Ljava/lang/String;");
        cout << 10 << endl;
        jmethodID getRoomTypeName = env->GetMethodID(roomTypeClass, "getRoomTypeName", "()Ljava/lang/String;");
        cout << 11 << endl;
        jmethodID getTariff = env->GetMethodID(roomTypeClass, "getTariff", "()F");
        cout << 12 << endl;
        jmethodID getAmenities = env->GetMethodID(roomTypeClass, "getAmenities", "()Ljava/util/ArrayList;");
        cout << 13 << endl;

        for (jint i = 0; i < length; i++) {
            jobject roomType = env->CallObjectMethod(roomTypeArray, arrayListGet, i);

            jstring roomTypeId = (jstring)env->CallObjectMethod(roomType, getRoomTypeId);
            jstring roomTypeName = (jstring)env->CallObjectMethod(roomType, getRoomTypeName);
            jfloat tariff = env->CallFloatMethod(roomType, getTariff);
            jobject amenities = env->CallObjectMethod(roomType, getAmenities);

            const char *roomTypeIdStr = env->GetStringUTFChars(roomTypeId, 0);
            const char *roomTypeNameStr = env->GetStringUTFChars(roomTypeName, 0);

            fout << roomTypeIdStr << "-" << roomTypeNameStr << "-" << tariff << "-";

            jint amenitiesLength = env->CallIntMethod(amenities, arrayListSize);

            for (jint j = 0; j < amenitiesLength; j++) {
                jstring amenity = (jstring)env->CallObjectMethod(amenities, arrayListGet, j);
                const char *amenityStr = env->GetStringUTFChars(amenity, 0);
                fout << amenityStr;
                if (j < amenitiesLength - 1) {
                    fout << ":";
                }
                env->ReleaseStringUTFChars(amenity, amenityStr);
            }

            fout << "\\" << endl;

            env->ReleaseStringUTFChars(roomTypeId, roomTypeIdStr);
            env->ReleaseStringUTFChars(roomTypeName, roomTypeNameStr);
            env->DeleteLocalRef(roomTypeId);
            env->DeleteLocalRef(roomTypeName);
            env->DeleteLocalRef(amenities);
            env->DeleteLocalRef(roomType);
        }

        fout.close();
        env->DeleteLocalRef(roomTypeArray);
        env->DeleteLocalRef(arrayListClass);
        env->DeleteLocalRef(roomTypeClass);
        env->DeleteLocalRef(InMemoryRoomTypeStoreClass);
    }

    JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_roomtypestore_InMemoryRoomTypeStore_loadFromFile (JNIEnv* env, jobject obj){
        ifstream fin("roomtype.txt");
        if(!fin.is_open()){
            return;
        }

        string file; file.assign((istreambuf_iterator<char>(fin)), (istreambuf_iterator<char>()));
        fin.close();

        jclass roomTypeClass = env->FindClass("com/operatoroverloaded/hotel/models/RoomType");
        jclass InMemoryRoomTypeStoreClass = env->GetObjectClass(obj);
        jfieldID roomTypeArrayField = env->GetFieldID(InMemoryRoomTypeStoreClass, "roomTypes", "Ljava/util/ArrayList;");
        jobject roomTypeArray = env->GetObjectField(obj, roomTypeArrayField);
        jmethodID roomTypeInit = env->GetMethodID(roomTypeClass, "<init>", "(Ljava/lang/String;Ljava/lang/String;FLjava/util/ArrayList;)V");

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

        long long i = 0;
        while (i < file.size()) {
            long long j = i;
            while (file[j] != '-') j++;
            jstring roomTypeId = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while (file[j] != '-') j++;
            jstring roomTypeName = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while (file[j] != '-') j++;
            jfloat tariff = stof(file.substr(i, j - i));
            i = j + 1;

            jobject amenities = env->NewObject(arrayListClass, arrayListInit);
            j = i;
            while (file[j] != '\\') j++;
            while (i < j) {
            long long l = i;
            while (file[l] != ':' && l < j) l++;
                jstring amenity = env->NewStringUTF(file.substr(i, l - i).c_str());
                env->CallBooleanMethod(amenities, arrayListAdd, amenity);
                env->DeleteLocalRef(amenity);
                i = l + 1;
            }
            i = j + 1;

            jobject roomType = env->NewObject(roomTypeClass, roomTypeInit, roomTypeId, roomTypeName, tariff, amenities);
            env->CallBooleanMethod(roomTypeArray, arrayListAdd, roomType);

            env->DeleteLocalRef(roomTypeId);
            env->DeleteLocalRef(roomTypeName);
            env->DeleteLocalRef(amenities);
            env->DeleteLocalRef(roomType);

            while (file[i] == '\n' || file[i] == '\\') i++;
        }

        env->DeleteLocalRef(roomTypeArray);
        env->DeleteLocalRef(arrayListClass);
        env->DeleteLocalRef(roomTypeClass);
        env->DeleteLocalRef(InMemoryRoomTypeStoreClass);
    }
}