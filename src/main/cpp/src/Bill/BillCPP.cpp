// #include <bits/stdc++.h>
#include <fstream>
#include <jni.h>
#include "BILL_InMemoryBillStore.h"
using namespace std;

// id-amt-gen-payed-dish1:dish2:...:dishn-p1:p2:p3:...:pn-q1:q2:...:qn\


extern "C"{
    JNIEXPORT void JNICALL Java_BILL_InMemoryBillStore_loadBill(JNIEnv *env, jobject obj) {
        ifstream fin("BILL/bill.txt");
        if (!fin.is_open()) {
            return;
        }
        string file; file.assign((istreambuf_iterator<char>(fin)), (istreambuf_iterator<char>()));
        fin.close();

        jclass billClass = env->FindClass("BILL/Bill");
        jclass InMemoryBillStoreClass = env->GetObjectClass(obj);
        jfieldID billArrayField = env->GetFieldID(InMemoryBillStoreClass, "billData", "Ljava/util/ArrayList;");
        jobject billArray = env->GetObjectField(obj, billArrayField);
        jmethodID billInit = env->GetMethodID(billClass, "<init>", "(IFLjava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;)V");

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

        long long i = 0;
        while(i < file.size()) {
            long long j = i;
            while(file[j] != '-') j++;
            jint billId = stoi(file.substr(i, j - i));
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jfloat amount = stof(file.substr(i, j - i));
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jstring generatedOn = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            j = i;
            while(file[j] != '-') j++;
            jstring payedOn = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            jobject purchased = env->NewObject(arrayListClass, arrayListInit);
            jobject purchasedList = env->NewObject(arrayListClass, arrayListInit);
            jobject quantity = env->NewObject(arrayListClass, arrayListInit);

            j = i;
            while(file[j] != '-') j++;
            while(i < j) {
                long long l = i;
                while(file[l] != ':' && l < j) l++;
                jstring dish = env->NewStringUTF(file.substr(i, l - i).c_str());
                env->CallBooleanMethod(purchased, arrayListAdd, dish);
                i = l + 1;
            }

            j = i;
            while(file[j] != '-') j++;
            while(i < j) {
                long long l = i;
                while(file[l] != ':' && l < j) l++;
                jfloat price = stof(file.substr(i, l - i));
                jobject priceObject = env->NewObject(env->FindClass("java/lang/Float"), env->GetMethodID(env->FindClass("java/lang/Float"), "<init>", "(F)V"), price);
                env->CallBooleanMethod(purchasedList, arrayListAdd, priceObject);
                i = l + 1;
            }

            j = i;
            while(file[j] != '\\') j++;
            while(i < j){
                long long l = i;
                while(file[l] != ':' && l < j) l++;
                jint q = stoi(file.substr(i, l - i));
                jobject quantityObject = env->NewObject(env->FindClass("java/lang/Integer"), env->GetMethodID(env->FindClass("java/lang/Integer"), "<init>", "(I)V"), q);
                env->CallBooleanMethod(quantity, arrayListAdd, quantityObject);
                i = l + 1;
            }
            i = j + 1;

            jobject billObject = env->NewObject(billClass, billInit, billId, amount, purchased, purchasedList, quantity, generatedOn, payedOn);
            env->CallBooleanMethod(billArray, arrayListAdd, billObject);

            while(file[i] == '\n' || file[i] == '\\') i++;

            env->DeleteLocalRef(purchased);
            env->DeleteLocalRef(purchasedList);
            env->DeleteLocalRef(quantity);
            env->DeleteLocalRef(generatedOn);
            env->DeleteLocalRef(payedOn);
            env->DeleteLocalRef(billObject);
        }
    }

    JNIEXPORT void JNICALL Java_BILL_InMemoryBillStore_saveBill(JNIEnv *env, jobject obj) {
        jclass billClass = env->FindClass("BILL/Bill");
        jclass InMemoryBillStoreClass = env->GetObjectClass(obj);
        jfieldID billArrayField = env->GetFieldID(InMemoryBillStoreClass, "billData", "Ljava/util/ArrayList;");
        jobject billArray = env->GetObjectField(obj, billArrayField);

        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        jint length = env->CallIntMethod(billArray, arrayListSize);

        jfieldID billIdField = env->GetFieldID(billClass, "billId", "I");
        jfieldID amountField = env->GetFieldID(billClass, "amount", "F");
        jfieldID purchasedField = env->GetFieldID(billClass, "purchased", "Ljava/util/ArrayList;");
        jfieldID purchasedListField = env->GetFieldID(billClass, "purchasedList", "Ljava/util/ArrayList;");
        jfieldID quantityField = env->GetFieldID(billClass, "quantity", "Ljava/util/ArrayList;");
        jfieldID generatedOnField = env->GetFieldID(billClass, "generatedOn", "Ljava/lang/String;");
        jfieldID payedOnField = env->GetFieldID(billClass, "payedOn", "Ljava/lang/String;");

        ofstream file("BILL/bill.txt");
        if (!file.is_open()) {
            return;
        }

        for (jint i = 0; i < length; i++) {
            jobject billObject = env->CallObjectMethod(billArray, arrayListGet, i);

            jint billId = env->GetIntField(billObject, billIdField);
            jfloat amount = env->GetFloatField(billObject, amountField);
            jobject purchased = env->GetObjectField(billObject, purchasedField);
            jobject purchasedList = env->GetObjectField(billObject, purchasedListField);
            jobject quantity = env->GetObjectField(billObject, quantityField);
            jstring generatedOn = (jstring) env->GetObjectField(billObject, generatedOnField);
            jstring payedOn = (jstring) env->GetObjectField(billObject, payedOnField);

            const char *nativeGeneratedOn = generatedOn ? env->GetStringUTFChars(generatedOn, nullptr) : nullptr;
            const char *nativePayedOn = payedOn ? env->GetStringUTFChars(payedOn, nullptr) : nullptr;

            file << billId << "-" << amount << "-" << (nativeGeneratedOn ? nativeGeneratedOn : "01/01/01") << "-" << (nativePayedOn ? nativePayedOn : "00/00/00") << "-";

            // Write purchased items
            jint purchasedSize = env->CallIntMethod(purchased, arrayListSize);
            for (jint j = 0; j < purchasedSize; j++) {
                jstring item = (jstring) env->CallObjectMethod(purchased, arrayListGet, j);
                const char *nativeItem = env->GetStringUTFChars(item, nullptr);
                file << nativeItem;
                if(j != purchasedSize - 1) {
                    file << ":";
                }
                env->ReleaseStringUTFChars(item, nativeItem);
                env->DeleteLocalRef(item);
            }
            file << "-";

            // Write purchased list
            jint purchasedListSize = env->CallIntMethod(purchasedList, arrayListSize);
            for (jint j = 0; j < purchasedListSize; j++) {
                jobject priceObject = env->CallObjectMethod(purchasedList, arrayListGet, j);
                jfloat price = env->CallFloatMethod(priceObject, env->GetMethodID(env->GetObjectClass(priceObject), "floatValue", "()F"));
                file << price;
                if(j != purchasedListSize - 1) {
                    file << ":";
                }
                env->DeleteLocalRef(priceObject);
            }
            file << "-";

            // write the quantity of the item
            jint quantitySize = env->CallIntMethod(quantity, arrayListSize);
            for(jint i = 0; i < quantitySize; i++) {
                jobject quantityObject = env->CallObjectMethod(quantity, arrayListGet, i);
                jint quantity = env->CallIntMethod(quantityObject, env->GetMethodID(env->GetObjectClass(quantityObject), "intValue", "()I"));
                file << quantity;
                if(i != quantitySize - 1) {
                    file << ":";
                }
                env->DeleteLocalRef(quantityObject);
            }

            file << "\\" << endl;

            if (nativeGeneratedOn) env->ReleaseStringUTFChars(generatedOn, nativeGeneratedOn);
            if (nativePayedOn) env->ReleaseStringUTFChars(payedOn, nativePayedOn);

            env->DeleteLocalRef(billObject);
            env->DeleteLocalRef(purchased);
            env->DeleteLocalRef(purchasedList);
            env->DeleteLocalRef(generatedOn);
            env->DeleteLocalRef(payedOn);
        }
        file.close();
    }
}