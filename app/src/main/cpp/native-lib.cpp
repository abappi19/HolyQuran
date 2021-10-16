#include <jni.h>
#include <string>


extern "C" JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOnlineBaseJsonUrl(JNIEnv *env, jclass clazz) {
    std::string hello = "https://raw.githubusercontent.com/eFortsHub/HolyQuranJson/main/";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjImlaei(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/im.hbj";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjIndopak(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/in.hbj";
    return env->NewStringUTF(hello.c_str());
}

extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjUthmani(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/ut.hbj";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjTranslations(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/tr.hbj";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjTrEn20(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/20.hbj";
    return env->NewStringUTF(hello.c_str());
}


extern "C"
JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_getOfflineHbjTrBn162(JNIEnv *env, jclass clazz) {
    std::string hello = "hbj/162.hbj";
    return env->NewStringUTF(hello.c_str());

}
