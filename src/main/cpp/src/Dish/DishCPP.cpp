#include <bits/stdc++.h>
#include <fstream>
#include <sstream>
// #include "com_operatoroverloaded_hotel_stores_dishstore_InMemoryDishStore.h"
#include <jni.h>

using namespace std;

// Helper functions to convert between std::string and jstring
jstring stdStringToJString(JNIEnv* env, const string& cppString) {
    return env->NewStringUTF(cppString.c_str());
}

string jStringToStdString(JNIEnv* env, jstring javaString) {
    if (javaString == nullptr) return ""; 
    const char* utfString = env->GetStringUTFChars(javaString, nullptr);
    string cppString(utfString);
    env->ReleaseStringUTFChars(javaString, utfString);
    return cppString;
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_dishstore_InMemoryDishStore_loadFromFile(JNIEnv *env, jobject obj) {
    jclass dishClass = env->FindClass("com/operatoroverloaded/hotel/models/Dish");
    jclass storeClass = env->GetObjectClass(obj);
    jfieldID dishListField = env->GetFieldID(storeClass, "dishes", "Ljava/util/ArrayList;");
    jobject dishList = env->GetObjectField(obj, dishListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID addMethod = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

    ifstream infile("DishStore.txt");
    string line;
    while (getline(infile, line)) {
        istringstream ss(line);
        vector<string> tokens;
        string token;
        while (getline(ss, token, '|')) tokens.push_back(token);

        jobject dish = env->NewObject(dishClass, env->GetMethodID(dishClass, "<init>", "(ILjava/lang/String;FLjava/lang/String;IIZ)V"), 
                                      stoi(tokens[0]),
                                      stdStringToJString(env, tokens[1]),
                                      stof(tokens[2]),
                                      stdStringToJString(env, tokens[3]),
                                      stoi(tokens[4]),
                                      stoi(tokens[5]),
                                      tokens[6] == "true");

        env->CallBooleanMethod(dishList, addMethod, dish);
    }
    infile.close();
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_dishstore_InMemoryDishStore_saveToFile(JNIEnv *env, jobject obj) {
    jclass storeClass = env->GetObjectClass(obj);
    jfieldID dishListField = env->GetFieldID(storeClass, "dishes", "Ljava/util/ArrayList;");
    jobject dishList = env->GetObjectField(obj, dishListField);

    jclass arrayListClass = env->FindClass("java/util/ArrayList");
    jmethodID sizeMethod = env->GetMethodID(arrayListClass, "size", "()I");
    jmethodID getMethod = env->GetMethodID(arrayListClass, "get", "(I)Ljava/lang/Object;");

    jclass dishClass = env->FindClass("com/operatoroverloaded/hotel/models/Dish");

    ofstream outfile("DishStore.txt");
    jint size = env->CallIntMethod(dishList, sizeMethod);
    for (int i = 0; i < size; ++i) {
        jobject dish = env->CallObjectMethod(dishList, getMethod, i);

        jint id = env->CallIntMethod(dish, env->GetMethodID(dishClass, "getDishID", "()I"));
        string name = jStringToStdString(env, (jstring)env->CallObjectMethod(dish, env->GetMethodID(dishClass, "getName", "()Ljava/lang/String;")));
        float price = env->CallFloatMethod(dish, env->GetMethodID(dishClass, "getPrice", "()F"));
        string dishType = jStringToStdString(env, (jstring)env->CallObjectMethod(dish, env->GetMethodID(dishClass, "getDishType", "()Ljava/lang/String;")));
        jint calories = env->CallIntMethod(dish, env->GetMethodID(dishClass, "getCalories", "()I"));
        jint preparationTime = env->CallIntMethod(dish, env->GetMethodID(dishClass, "getPreparationTime", "()I"));
        jboolean isAvailable = env->CallBooleanMethod(dish, env->GetMethodID(dishClass, "isAvailable", "()Z"));

        outfile << id << "|" 
                << name << "|" 
                << price << "|" 
                << dishType << "|" 
                << calories << "|" 
                << preparationTime << "|" 
                << (isAvailable ? "true" : "false") 
                << "\n";
    }
    outfile.close();
}

