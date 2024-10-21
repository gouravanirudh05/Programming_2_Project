#include <jni.h>
#include "RoomManagement.h"  // Your room management logic

// Implement the JNI functions
extern "C" {

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_service_RoomManagementService_addRoom
(JNIEnv *env, jobject obj, jstring roomName, jint capacity, jdouble price) {
    const char *cRoomName = env->GetStringUTFChars(roomName, NULL);
    RoomManagement::addRoom(std::string(cRoomName), (int) capacity, (double) price);
    env->ReleaseStringUTFChars(roomName, cRoomName);
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_service_RoomManagementService_updateRoom
(JNIEnv *env, jobject obj, jint roomId, jstring roomName, jint capacity, jdouble price) {
    const char *cRoomName = env->GetStringUTFChars(roomName, NULL);
    RoomManagement::updateRoom((int) roomId, std::string(cRoomName), (int) capacity, (double) price);
    env->ReleaseStringUTFChars(roomName, cRoomName);
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_service_RoomManagementService_removeRoom
(JNIEnv *env, jobject obj, jint roomId) {
    RoomManagement::removeRoom((int) roomId);
}

}
