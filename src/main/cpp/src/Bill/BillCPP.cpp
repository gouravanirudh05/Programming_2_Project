#include <fstream>
#include <jni.h>
#include "BILL_InMemoryBillStore.h"
#include <string>
using namespace std;
#include <algorithm>

// id-amt - yy/mm/dd - hh:mm:ss - yy/mm/dd - hh:mm:ss - dish1:dish2:...:dishn-p1:p2:p3:...:pn-q1:q2:...:qn\


extern "C"{
    // function to load the bill data from the file and store it in a list
    JNIEXPORT void JNICALL Java_BILL_InMemoryBillStore_loadBill(JNIEnv *env, jobject obj) {
        // TODO: change the path to the file as neccessary
        ifstream fin("BILL/bill.txt");
        if (!fin.is_open()) {
            return;
        }
        string file; file.assign((istreambuf_iterator<char>(fin)), (istreambuf_iterator<char>()));
        fin.close();

        // Get the class and field ids
        // TODO: change the path to the class as necessary
        jclass billClass = env->FindClass("BILL/Bill"); 
        jclass InMemoryBillStoreClass = env->GetObjectClass(obj);
        jfieldID billArrayField = env->GetFieldID(InMemoryBillStoreClass, "billData", "Ljava/util/ArrayList;");
        jobject billArray = env->GetObjectField(obj, billArrayField);
        jmethodID billInit = env->GetMethodID(billClass, "<init>", "(IFLjava/util/ArrayList;Ljava/util/ArrayList;Ljava/util/ArrayList;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V");

        // Get the class and method ids for ArrayList
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");
        jmethodID arrayListInit = env->GetMethodID(arrayListClass, "<init>", "()V");

        long long i = 0;
        while(i < file.size()) {
            // Get the bill id
            long long j = i;
            while(file[j] != '-') j++;
            jint billId = stoi(file.substr(i, j - i));
            i = j + 1;

            // Get the amount
            j = i;
            while(file[j] != '-') j++;
            jfloat amount = stof(file.substr(i, j - i));
            i = j + 1;

            // Get the generated date
            j = i;
            while(file[j] != '-') j++;
            jstring genDate = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Get the generated time
            j = i;
            while(file[j] != '-') j++;
            jstring genTime = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Get the payed date
            j = i;
            while(file[j] != '-') j++;
            jstring payedDate = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            // Get the payed time
            j = i;
            while(file[j] != '-') j++;
            jstring payedTime = env->NewStringUTF(file.substr(i, j - i).c_str());
            i = j + 1;

            
            jobject purchased = env->NewObject(arrayListClass, arrayListInit);
            jobject purchasedList = env->NewObject(arrayListClass, arrayListInit);
            jobject quantity = env->NewObject(arrayListClass, arrayListInit);

            // Get the purchased items
            j = i;
            while(file[j] != '-') j++;
            while(i < j) {
                long long l = i;
                while(file[l] != ':' && l < j) l++;
                jstring dish = env->NewStringUTF(file.substr(i, l - i).c_str());
                env->CallBooleanMethod(purchased, arrayListAdd, dish);
                i = l + 1;
            }
            
            // Get the purchased list
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

            // Get the quantity of the items
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

            // Create the bill object and add it to the list
            jobject billObject = env->NewObject(billClass, billInit, billId, amount, purchased, purchasedList, quantity, genDate, genTime, payedDate, payedTime);
            env->CallBooleanMethod(billArray, arrayListAdd, billObject);

            while(file[i] == '\n' || file[i] == '\\') i++;

            // Release the local references
            env->DeleteLocalRef(purchased);
            env->DeleteLocalRef(purchasedList);
            env->DeleteLocalRef(quantity);
            env->DeleteLocalRef(genDate);
            env->DeleteLocalRef(genTime);
            env->DeleteLocalRef(payedDate);
            env->DeleteLocalRef(payedTime);
            env->DeleteLocalRef(billObject);
        }
    }

    JNIEXPORT void JNICALL Java_BILL_InMemoryBillStore_saveBill(JNIEnv *env, jobject obj) {
        // Get the class and field ids
        // TODO: change the path to the class as necessary
        jclass billClass = env->FindClass("BILL/Bill");
        jclass InMemoryBillStoreClass = env->GetObjectClass(obj);
        jfieldID billArrayField = env->GetFieldID(InMemoryBillStoreClass, "billData", "Ljava/util/ArrayList;");
        jobject billArray = env->GetObjectField(obj, billArrayField);

        // Get the class and method ids for ArrayList
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListSize = env->GetMethodID(arrayListClass, "size", "()I");
        jmethodID arrayListGet = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");
        jint length = env->CallIntMethod(billArray, arrayListSize);

        // Get the field ids of the Bill class
        jfieldID billIdField = env->GetFieldID(billClass, "billId", "I");
        jfieldID amountField = env->GetFieldID(billClass, "amount", "F");
        jfieldID purchasedField = env->GetFieldID(billClass, "purchased", "Ljava/util/ArrayList;");
        jfieldID purchasedListField = env->GetFieldID(billClass, "purchasedList", "Ljava/util/ArrayList;");
        jfieldID quantityField = env->GetFieldID(billClass, "quantity", "Ljava/util/ArrayList;");
        jfieldID generatedOnField = env->GetFieldID(billClass, "generatedOn", "LBILL/DateTime;");
        jfieldID payedOnField = env->GetFieldID(billClass, "payedOn", "LBILL/DateTime;");

        // TODO: change the path to the file as necessary
        ofstream file("BILL/bill.txt");
        if (!file.is_open()) {
            return;
        }
         
        // Get the class and method ids for DateTime
        // TODO: change the path to the class as necessary
        jclass dateTimeClass = env->FindClass("BILL/DateTime");
        jmethodID getDateString = env->GetMethodID(dateTimeClass, "getDateString", "()Ljava/lang/String;");
        jmethodID getTimeString = env->GetMethodID(dateTimeClass, "getTimeString", "()Ljava/lang/String;");

        for (jint i = 0; i < length; i++) {
            // Get the bill object
            jobject billObject = env->CallObjectMethod(billArray, arrayListGet, i);
            
            // Get the fields of the bill object
            jint billId = env->GetIntField(billObject, billIdField);
            jfloat amount = env->GetFloatField(billObject, amountField);
            jobject purchased = env->GetObjectField(billObject, purchasedField);
            jobject purchasedList = env->GetObjectField(billObject, purchasedListField);
            jobject quantity = env->GetObjectField(billObject, quantityField);
            jobject generatedOn = env->GetObjectField(billObject, generatedOnField);
            jobject payedOn = env->GetObjectField(billObject, payedOnField);

            // Get the date and time strings using the DateTime class method getDateString() and getTimeString()
            jstring genDate = (jstring) env->CallObjectMethod(generatedOn, getDateString);
            jstring genTime = (jstring) env->CallObjectMethod(generatedOn, getTimeString);
            jstring payedDate = (jstring) env->CallObjectMethod(payedOn, getDateString);
            jstring payedTime = (jstring) env->CallObjectMethod(payedOn, getTimeString);

            // Convert the date and time strings to native strings
            const char *nativeGenDate = env->GetStringUTFChars(genDate, nullptr);
            const char *nativeGenTime = env->GetStringUTFChars(genTime, nullptr);
            const char *nativePayedDate = env->GetStringUTFChars(payedDate, nullptr);
            const char *nativePayedTime = env->GetStringUTFChars(payedTime, nullptr);

            // Replace the '-' with '/' in the date strings to prevent issues while reading the file
            string formattedGenDate = nativeGenDate;
            replace(formattedGenDate.begin(), formattedGenDate.end(), '-', '/');
            string formattedPayedDate = nativePayedDate;
            replace(formattedPayedDate.begin(), formattedPayedDate.end(), '-', '/');

            // Write the bill data to the file
            file << billId << "-" << amount << "-" << formattedGenDate << "-" << nativeGenTime << "-" << formattedPayedDate << "-" << nativePayedTime << "-";

            // Write purchased items
            jint purchasedSize = env->CallIntMethod(purchased, arrayListSize);
            for (jint j = 0; j < purchasedSize; j++) {
                jstring item = (jstring) env->CallObjectMethod(purchased, arrayListGet, j);
                const char *nativeItem = env->GetStringUTFChars(item, nullptr);
                file << nativeItem;
                if (j != purchasedSize - 1) {
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
                if (j != purchasedListSize - 1) {
                    file << ":";
                }
                env->DeleteLocalRef(priceObject);
            }
            file << "-";

            // Write the quantity of the item
            jint quantitySize = env->CallIntMethod(quantity, arrayListSize);
            for (jint j = 0; j < quantitySize; j++) {
                jobject quantityObject = env->CallObjectMethod(quantity, arrayListGet, j);
                jint qty = env->CallIntMethod(quantityObject, env->GetMethodID(env->GetObjectClass(quantityObject), "intValue", "()I"));
                file << qty;
                if (j != quantitySize - 1) {
                    file << ":";
                }
                env->DeleteLocalRef(quantityObject);
            }

            // To signify end of a bill entry
            file << "\\" << endl;

            // Release the native strings
            env->ReleaseStringUTFChars(genDate, nativeGenDate);
            env->ReleaseStringUTFChars(genTime, nativeGenTime);
            env->ReleaseStringUTFChars(payedDate, nativePayedDate);
            env->ReleaseStringUTFChars(payedTime, nativePayedTime);

            // Delete the local references
            env->DeleteLocalRef(billObject);
            env->DeleteLocalRef(purchased);
            env->DeleteLocalRef(purchasedList);
            env->DeleteLocalRef(quantity);
            env->DeleteLocalRef(generatedOn);
            env->DeleteLocalRef(payedOn);
            env->DeleteLocalRef(genDate);
            env->DeleteLocalRef(genTime);
            env->DeleteLocalRef(payedDate);
            env->DeleteLocalRef(payedTime);
        }
        file.close();
    }
}