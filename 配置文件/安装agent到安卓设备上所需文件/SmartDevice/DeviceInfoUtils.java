package com.hydsoft.utils;

import android.app.usage.UsageStats;
import android.app.usage.UsageStatsManager;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.os.BatteryManager;
import android.os.Build;
import android.os.SystemClock;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.hydsoft.MyApplication;
import com.hydsoft.R;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import static android.content.Context.USAGE_STATS_SERVICE;

public class DeviceInfoUtils {
    public static final String TAG = DeviceInfoUtils.class.getSimpleName();

    /**
     * 获取设备宽度（px）
     */
    public static int getDeviceWidth(Context context) {
        return context.getResources().getDisplayMetrics().widthPixels;
    }

    /**
     * 获取设备高度（px）
     */
    public static int getDeviceHeight(Context context) {
        return context.getResources().getDisplayMetrics().heightPixels;
    }

    /**
     * 获取设备的唯一标识， 需要 “android.permission.READ_Phone_STATE”权限
     */
    public static String getIMEI(Context context) {
        String deviceId = null;
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            deviceId = tm.getDeviceId();

        }
        if (deviceId == null) {
            return "UnKnown";
        } else {
            return deviceId;
        }
    }

    /**
     * 获取设备制造商
     **/
    public static String getDeviceManufacturer() {
        return android.os.Build.MANUFACTURER;
    }

    /**
     * 获取整个产品名
     **/
    public static String getDeviceProduct() {
        return android.os.Build.PRODUCT;
    }

    /**
     * 获取设备品牌
     */
    public static String getDeviceBrand() {
        return android.os.Build.BRAND;
    }

    /**
     * 获取手机型号
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;
    }

    /**
     * 获取设备基板名称
     */
    public static String getDeviceBoard() {
        return android.os.Build.BOARD;
    }

    /**
     * 获取设备引导程序版本号
     */
    public static String getDeviceBootLoader() {
        return android.os.Build.BOOTLOADER;
    }

    /**
     * 获取设备驱动名称
     **/
    public static String getDeviceDevice() {
        return android.os.Build.DEVICE;
    }

    /**
     * fingerprit信息(设备的唯一标识。由设备的多个信息拼接合成。)
     **/
    public static String getDeviceFubgerprint() {
        return android.os.Build.FINGERPRINT;
    }

    /**
     * 设备硬件名称,一般和基板名称一样
     **/
    public static String getDeviceHardware() {
        return android.os.Build.HARDWARE;
    }

    /**
     * 设备主机地址
     **/
    public static String getDeviceHost() {
        return android.os.Build.HOST;
    }

    /**
     * 获取设备显示的版本包（在系统设置中显示为版本号）和ID一样
     **/
    public static String getDeviceDisplay() {
        return android.os.Build.DISPLAY;
    }

    /**
     * 设备版本号ID
     **/
    public static String getDeviceId() {
        return android.os.Build.ID;
        //return ANDROID_ID;
    }

    /**
     * 获取设备安卓ID
     */
    public static String getDeviceAndroidId() {
        String androidId = Settings.System.getString(MyApplication.getContext().getContentResolver(),
                Settings.Secure.ANDROID_ID);
        if(androidId.length() > 0) {
            androidId = androidId.replace(":", "").replace("-", "").toUpperCase();
        }
        return androidId;
    }

    /**
     * 获取设备自定义名称
     */
    public static String getDeviceCustomizeName() {
//        return BluetoothAdapter.getDefaultAdapter().getName();
        return Settings.Secure.getString(MyApplication.getContext().getContentResolver(), "bluetooth_name");
        //return Settings.Global.getString(MyApplication.getContext().getContentResolver(), Settings.Global.DEVICE_NAME);
    }

    /**
     * 获取手机用户名
     **/
    public static String getDeviceUser() {
        return android.os.Build.USER;
    }

    /**
     * 获取手机硬件序列号
     **/
    public static String getDeviceSerial() {
        return android.os.Build.SERIAL;
    }

    /**
     * 获取手机Android系统SDK
     *
     */
    public static int getDeviceSDK() {
        return android.os.Build.VERSION.SDK_INT;
    }

    /**
     * 获取手机Android版本号
     *
     */
    public static String getDeviceAndroidVersion() {
        return android.os.Build.VERSION.RELEASE;
    }

    /**
     * 获取当前手机系统语言。
     */
    public static String getDeviceDefaultLanguage() {
        return Locale.getDefault().getLanguage();
    }

    /**
     * 获取设备标签
     */
    public static String getDeviceTags() {
        return  android.os.Build.TAGS;
    }

    /**
     * 获取发布时间
     */
    public static String getDeviceBuildTime() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(android.os.Build.TIME);
    }

    /**
     * 获取版本类型
     */
    public static String getDeviceBuildType() {
        return android.os.Build.TYPE;
    }

    /**
     * 获取设备当前的系统开发代号
     */
    public static String getDeviceVerCodeName() {
        return android.os.Build.VERSION.CODENAME;
    }

    /**
     * 获取设备无线电固件的版本号
     */
    public static String getDeviceRadioVersion() {
        return Build.getRadioVersion();
    }

    /**
     * 获取设备开机时间
     * @return
     */
    public static String getDeviceBootedDate() {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        return format.format(System.currentTimeMillis() - SystemClock.elapsedRealtime());
    }

    /**
     * 获取设备从系统启动到现在的时间，包含休眠时间。
     * @return
     */
    public static String getDeviceUptime() {
        long uptime = SystemClock.elapsedRealtime();
        long days = uptime / (1000 * 60 * 60 * 24);
        long hours = (uptime % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
        long minutes = (uptime % (1000 * 60 * 60)) / (1000 * 60);
        long seconds = (uptime % (1000 * 60)) / 1000;
        return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
    }

    /**
     * 获取设备电池电量百分比
     * @return
     */
    public static double getDeviceBatteryPercent() {
        return Double.valueOf(getLevel(MyApplication.getBatteryPercent())) * 0.01;
    }

    /**
     * 获取设备电池温度
     * @return
     */
    public static double getDeviceBatteryTemperature() {
        return Double.valueOf(getTemperature(MyApplication.getBatteryTemperature()));
    }

    /**
     * 获取设备电池状态
     * @return
     */
    public static String getDeviceBatteryStatus() {
        return getStatus(MyApplication.getBatteryStatus());
    }

    /**
     * 获取设备电池使用时间(非充电状态使用时间), 若获取不到值,则默认返回设备开机时长
     * @return
     */
    public static String getDeviceBatteryUsageTime() {
        long beginTime = MyApplication.getBatteryUsageBeginTime();
        if (beginTime != 0L) {
            long time = System.currentTimeMillis() - MyApplication.getBatteryUsageBeginTime();
            long days = time / (1000 * 60 * 60 * 24);
            long hours = (time % (1000 * 60 * 60 * 24)) / (1000 * 60 * 60);
            long minutes = (time % (1000 * 60 * 60)) / (1000 * 60);
            long seconds = (time % (1000 * 60)) / 1000;
            return String.format("%d days, %02d:%02d:%02d", days, hours, minutes, seconds);
        }
        return getDeviceUptime();
    }

    /**
     * 获取设备使用频次(使用频次等于每周系统app触发次数)
     * @param context
     * @return
     */
    public static String getDeviceUsageCount(Context context) {
        return String.valueOf(printUsageStats(getUsageStatsList(context)));
    }

    /**
     * 获取设备使用率(使用率=每周开机时间/每周总时间)
     * @return
     */
    public static String getDeviceUsageRate() {
        long totalMillisWeekly = 24 * 60 * 60 * 1000 * 7;
        long weekUsageTime = 0L;
        Map<String, Date> map = getWeekDate();
        Date mondayDate = map.get("mondayDate");
        long deviceBootedTime = System.currentTimeMillis() - SystemClock.elapsedRealtime();
        if (deviceBootedTime < mondayDate.getTime()) {
            weekUsageTime = System.currentTimeMillis() - mondayDate.getTime();
        } else {
            weekUsageTime = SystemClock.elapsedRealtime();
        }
        double rate = weekUsageTime / (double) totalMillisWeekly;
        return String.format("%.2f", rate);
    }

    /**
     * 获取设备能效(电池电压)
     * @return
     */
    public static String getDeviceEnergyEfficiency() {
        return getBatteryVolt(MyApplication.getBatteryVolt());
    }

    private static String getLevel(int level) {
        return String.format("%d", level);
    }

    private static String getTemperature(int temperature) {
        return String.format("%.2f", temperature / 10.0);
    }

    private static String getStatus(int status) {
        Context context = MyApplication.getContext();
        String result = context.getString(R.string.battery_status_unknown);
        switch (status) {
            case BatteryManager.BATTERY_STATUS_FULL:
                result = context.getString(R.string.battery_status_full);
                break;
            case BatteryManager.BATTERY_STATUS_NOT_CHARGING:
                result = context.getString(R.string.battery_status_not_charging);
                break;
            case BatteryManager.BATTERY_STATUS_DISCHARGING:
                result = context.getString(R.string.battery_status_discharging);
                break;
            case BatteryManager.BATTERY_STATUS_CHARGING:
                result = context.getString(R.string.battery_status_charging);
                break;
            case BatteryManager.BATTERY_STATUS_UNKNOWN:
                break;
        }
        return result;
    }

    private static String getBatteryVolt(int batteryVolt) {
        return String.format("%.3f V", batteryVolt / 1000.0);
    }

    private static List<UsageStats> getUsageStatsList(Context context){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
        UsageStatsManager usm = getUsageStatsManager(context);
        Map<String, Date> map = getWeekDate();
        Date mondayDate = map.get("mondayDate");
        Date sundayDate = map.get("sundayDate");
        Log.d(TAG, "Range start:" + dateFormat.format(mondayDate) );
        Log.d(TAG, "Range end:" + dateFormat.format(sundayDate));
        List<UsageStats> usageStatsList = usm.queryUsageStats(UsageStatsManager.INTERVAL_WEEKLY,mondayDate.getTime(),sundayDate.getTime());
        return usageStatsList;
    }

    private static UsageStatsManager getUsageStatsManager(Context context) {
        UsageStatsManager usm = (UsageStatsManager) context.getSystemService(USAGE_STATS_SERVICE);
        return usm;
    }

    private static int printUsageStats(List<UsageStats> usageStatsList) {
        int i = 0;
        for (UsageStats u : usageStatsList) {
            if (u.getPackageName().contains("systemui")) {
                try {
                    i = u.getClass().getDeclaredField("mLaunchCount").getInt(u);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                    Log.e(TAG, "printUsageStats出现异常!", e);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                    Log.e(TAG, "printUsageStats出现异常!", e);
                }
            }
        }
        return i;
    }

    /**
     * 获取当前时间所在周的周一和周日的日期时间
     * @return
     */
    private static Map<String, Date> getWeekDate() {
        Map<String, Date> map = new HashMap<String, Date>();
        Calendar cal = Calendar.getInstance(Locale.getDefault());
        // 设置一个星期的第一天
        cal.setFirstDayOfWeek(Calendar.MONDAY);
        // 获得当前日期是一个星期的第几天
        int dayWeek = cal.get(Calendar.DAY_OF_WEEK);
        if(dayWeek==1){
            dayWeek = 8;
        }
        cal.add(Calendar.DATE, cal.getFirstDayOfWeek() - dayWeek);// 根据日历的规则，给当前日期减去星期几与一个星期第一天的差值
        Date mondayDate = cal.getTime();
        cal.add(Calendar.DATE, 4 +cal.getFirstDayOfWeek());
        Date sundayDate = cal.getTime();
        map.put("mondayDate", mondayDate);
        map.put("sundayDate", sundayDate);
        return map;
    }

    public static String getDeviceAllInfo(Context context) {

        return "\n\n1. 设备宽度:\n\t\t" + getDeviceWidth(context)
                + "\n\n2. 设备高度:\n\t\t" + getDeviceHeight(context)
                + "\n\n3. 设备品牌:\n\t\t" + getDeviceBrand()
                + "\n\n4. 手机型号:\n\t\t" + getDeviceModel()
                + "\n\n5. 手机用户名:\n\t\t" + getDeviceUser()
                + "\n\n5.1. 手机自定义名:\n\t\t" + getDeviceCustomizeName()
                + "\n\n6. 设备标签:\n\t\t" + getDeviceTags()
                + "\n\n7. 硬件序列号:\n\t\t" + getDeviceSerial()
                + "\n\n8. 设备版本号ID:\n\t\t" + getDeviceId()
                + "\n\n8.5. 设备AndroidID:\n\t\t" + getDeviceAndroidId()
                + "\n\n9. IMEI:\n\t\t" + getIMEI(context)
                + "\n\n10. 设备制造商:\n\t\t" + getDeviceManufacturer()
                + "\n\n11. 整个产品名:\n\t\t" + getDeviceProduct()
                + "\n\n12. 显示的版本包:\n\t\t" + getDeviceDisplay()
                + "\n\n13. 引导程序版本号:\n\t\t" + getDeviceBootLoader()
                + "\n\n14. 驱动名称:\n\t\t" + getDeviceDevice()
                + "\n\n15. 硬件名称:\n\t\t" + getDeviceHardware()
                + "\n\n16. 基板名称:\n\t\t" + getDeviceBoard()
                + "\n\n17. 主机地址:\n\t\t" + getDeviceHost()
                + "\n\n17.5. 无线电固件的版本号:\n\t\t" + getDeviceRadioVersion()
                //+ "\n\n18. 是否联网:\n\t\t" + Utils.isNetworkConnected(context)
                //+ "\n\n19. 网络类型:\n\t\t" + Utils.GetNetworkType(context)
                + "\n\n20. 系统默认语言:\n\t\t" + getDeviceDefaultLanguage()
                + "\n\n21. 设备Fingerprint标识:\n\t\t" + getDeviceFubgerprint()
                + "\n\n22. Android版本号:\n\t\t" + getDeviceAndroidVersion()
                + "\n\n23. Android SDK版本:\n\t\t" + getDeviceSDK()
                + "\n\n24. CodeName:\n\t\t" + getDeviceVerCodeName()
                + "\n\n25. 安全patch 时间:\n\t\t" + android.os.Build.VERSION.SECURITY_PATCH
                + "\n\n26. 版本类型:\n\t\t" + getDeviceBuildType()
                + "\n\n27. 发布时间:\n\t\t" + getDeviceBuildTime()
                + "\n\n28. " + getDeviceBootedDate()
                + "\n\n29. " + getDeviceUptime()
                + "\n\n30. " + getDeviceBatteryPercent()
                + "\n\n31. " + getDeviceBatteryTemperature()
                + "\n\n32. " + getDeviceBatteryStatus()
                + "\n\n33. " + getDeviceBatteryUsageTime()
                + "\n\n34. " + getDeviceUsageCount(context)
                + "\n\n35. " + getDeviceUsageRate()
                + "\n\n36. " + getDeviceEnergyEfficiency();
    }
}
