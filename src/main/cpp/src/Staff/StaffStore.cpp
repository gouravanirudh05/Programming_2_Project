#include <jni.h>
#include <string>
#include <vector>
#include <algorithm>

// Staff class to represent a staff member
class Staff {
private:
    int staffID;
    std::string name;

public:
    Staff(int id, const std::string& name) : staffID(id), name(name) {}

    int getStaffID() const {
        return staffID;
    }

    void setName(const std::string& newName) {
        name = newName;
    }

    std::string getName() const {
        return name;
    }
};

// InMemoryStaffStore class to manage staff records
class InMemoryStaffStore {
private:
    std::vector<Staff> staffList;

public:
    void addStaff(const Staff& staff) {
        staffList.push_back(staff);
    }

    void updateStaff(const Staff& updatedStaff) {
        for (auto& staff : staffList) {
            if (staff.getStaffID() == updatedStaff.getStaffID()) {
                staff.setName(updatedStaff.getName());
                return;
            }
        }
    }

    void removeStaff(int staffID) {
        staffList.erase(
            std::remove_if(staffList.begin(), staffList.end(),
                           [staffID](const Staff& staff) {
                               return staff.getStaffID() == staffID;
                           }),
            staffList.end());
    }

    Staff* getStaffById(int staffID) {
        for (auto& staff : staffList) {
            if (staff.getStaffID() == staffID) {
                return &staff;
            }
        }
        return nullptr;
    }
};

// Singleton instance of InMemoryStaffStore
InMemoryStaffStore staffStore;

// JNI functions
extern "C" {

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_addStaff
(JNIEnv* env, jobject obj, jint staffID, jstring name) {
    const char* cName = env->GetStringUTFChars(name, NULL);
    Staff newStaff((int)staffID, std::string(cName));
    staffStore.addStaff(newStaff);
    env->ReleaseStringUTFChars(name, cName);
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_updateStaff
(JNIEnv* env, jobject obj, jint staffID, jstring name) {
    const char* cName = env->GetStringUTFChars(name, NULL);
    Staff updatedStaff((int)staffID, std::string(cName));
    staffStore.updateStaff(updatedStaff);
    env->ReleaseStringUTFChars(name, cName);
}

JNIEXPORT void JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_removeStaff
(JNIEnv* env, jobject obj, jint staffID) {
    staffStore.removeStaff((int)staffID);
}

JNIEXPORT jobject JNICALL Java_com_operatoroverloaded_hotel_stores_staffstore_InMemoryStaffStore_getStaffById
(JNIEnv* env, jobject obj, jint staffID) {
    Staff* staff = staffStore.getStaffById((int)staffID);
    if (staff) {
        jclass staffClass = env->FindClass("com/operatoroverloaded/hotel/models/Staff");
        jmethodID constructor = env->GetMethodID(staffClass, "<init>", "(ILjava/lang/String;)V");
        jstring name = env->NewStringUTF(staff->getName().c_str());
        return env->NewObject(staffClass, constructor, staff->getStaffID(), name);
    }
    return nullptr; // Return null if staff not found
}

}
