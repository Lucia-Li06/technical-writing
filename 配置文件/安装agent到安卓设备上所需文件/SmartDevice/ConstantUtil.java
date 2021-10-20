package com.hydsoft.utils;

import android.annotation.SuppressLint;
import android.util.Log;

@SuppressLint("LongLogTag")
public class ConstantUtil {
    public static final String TAG = ConstantUtil.class.getSimpleName();

    private static String PREFIX_SUBSCRIBE_TOPIC = "api/{mac}/android";
    private static String PREFIX_REPLY_TOPIC = "reply/{mac}/android";
    // 订阅主题
    public static String ANDROID_SUBSCRIBE_TOPIC;
    // 默认响应主题
    public static String ANDROID_REPLY_TOPIC;
    // 最后遗嘱主题
    public static String ANDROID_WILL_TOPIC;
    // 订阅主题-获取设备信息
    public static String ANDROID_SUBSCRIBE_TOPIC_DEVICEINFO_GET;
    // 响应主题-获取设备信息
    public static String ANDROID_REPLY_TOPIC_DEVICEINFO_GET;
    // 响应主题-其它
    public static String ANDROID_REPLY_TOPIC_OTHERS;

    static {
        // 由于Android Q以上已经限制获取mac地址, 现改用Android ID
        String mac = DeviceInfoUtils.getDeviceAndroidId();
        Log.i(TAG, "Android ID=" + mac);
        PREFIX_SUBSCRIBE_TOPIC = PREFIX_SUBSCRIBE_TOPIC.replace("{mac}", mac);
        PREFIX_REPLY_TOPIC = PREFIX_REPLY_TOPIC.replace("{mac}", mac);
        ANDROID_SUBSCRIBE_TOPIC = PREFIX_SUBSCRIBE_TOPIC + "/#";
        ANDROID_REPLY_TOPIC = PREFIX_REPLY_TOPIC + "/default";
        ANDROID_WILL_TOPIC = PREFIX_REPLY_TOPIC + "/offline";
        ANDROID_SUBSCRIBE_TOPIC_DEVICEINFO_GET = PREFIX_SUBSCRIBE_TOPIC + "/deviceInfo_get";
        ANDROID_REPLY_TOPIC_DEVICEINFO_GET = PREFIX_REPLY_TOPIC + "/deviceInfo_get";
        ANDROID_REPLY_TOPIC_OTHERS = PREFIX_REPLY_TOPIC + "/others";
        Log.i(TAG, "PREFIX_SUBSCRIBE_TOPIC=" + PREFIX_SUBSCRIBE_TOPIC);
        Log.i(TAG, "PREFIX_REPLY_TOPIC=" + PREFIX_REPLY_TOPIC);
        Log.i(TAG, "ANDROID_SUBSCRIBE_TOPIC=" + ANDROID_SUBSCRIBE_TOPIC);
        Log.i(TAG, "ANDROID_REPLY_TOPIC=" + ANDROID_REPLY_TOPIC);
        Log.i(TAG, "ANDROID_WILL_TOPIC=" + ANDROID_WILL_TOPIC);
        Log.i(TAG, "ANDROID_SUBSCRIBE_TOPIC_DEVICEINFO_GET=" + ANDROID_SUBSCRIBE_TOPIC_DEVICEINFO_GET);
        Log.i(TAG, "ANDROID_REPLY_TOPIC_DEVICEINFO_GET=" + ANDROID_REPLY_TOPIC_DEVICEINFO_GET);
        Log.i(TAG, "ANDROID_REPLY_TOPIC_OTHERS=" + ANDROID_REPLY_TOPIC_OTHERS);
    }


}
