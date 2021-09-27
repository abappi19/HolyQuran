#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_com_efortshub_holyquran_utils_HbConst_stringFromJNI(
        JNIEnv* env,
        jclass clazz) {
    std::string hello = "http://192.168.27.109/efortshub/apis/quran/";
    return env->NewStringUTF(hello.c_str());
}
