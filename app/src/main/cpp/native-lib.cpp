#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_stringFromJNI(JNIEnv* env, jclass clazz) {
    std::string hello = "haha ";
    return env->NewStringUTF(hello.c_str());
}

extern "C" JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOnlineBaseJsonUrl(JNIEnv *env, jclass clazz) {
    std::string hello = "https://raw.githubusercontent.com/eFortsHub/HolyQuranJson/main/";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjBaseUrl(JNIEnv *env, jclass clazz) {
    std::string hello = "file:///android_asset/hbj/";
    return env->NewStringUTF(hello.c_str());
}