/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore */

#ifndef _Included_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
#define _Included_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
 * Method:    saveLogon
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_saveLogon
  (JNIEnv *, jobject);

/*
 * Class:     com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
 * Method:    loadLogon
 * Signature: ()V
 */
JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_loadLogon
  (JNIEnv *, jobject);

/*
 * Class:     com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
 * Method:    hashString
 * Signature: (Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_hashString
  (JNIEnv *, jobject, jstring, jstring);

/*
 * Class:     com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore
 * Method:    getRandomSalt
 * Signature: ()Ljava/lang/String;
 */
JNIEXPORT jstring JNICALL Java_com_operatoroverloaded_hotel_stores_logonstore_InMemoryLogonStore_getRandomSalt
  (JNIEnv *, jobject);

#ifdef __cplusplus
}
#endif
#endif
