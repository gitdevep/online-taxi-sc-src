package com.online.taxi.consts;

/**
 */
public class Const {
    /**
     * 乘客身份
     */
    public static final int IDENTITY_PASSENGER = 0;

    /**
     * 司机身份
     */
    public static final int IDENTITY_DRIVER = 1;

    /**
     * 大屏身份
     */
    public static final int IDENTITY_LARGE_SCREEN = 3;

    /**
     * 车机身份
     */
    public static final int IDENTITY_CAR_SCREEN = 2;

    /**
     * boss后台
     */
    public static final int IDENTITY_BOSS = 4;

    public static final int DAY_TIME_START_NUM = 7;
    public static final int DAY_TIME_END_NUM = 22;
    public static final int DAY_TIME = 1;
    public static final int NIGHT = 2;

    /**
     * 1,强派，2非特殊时段，3特殊时段
     */
    // public static final int SERVICE_TYPE_ID_FORCE = 1;
    // public static final int SERVICE_TYPE_ID_SPECIAL = 3;
    // public static final int SERVICE_TYPE_ID_NORMAL = 2;
    public static final int SERVICE_TYPE_ID_NORMAL_FORCE = 4;

    public static final int USER_FEATURE_CHILDREN = 1;
    public static final int USER_FEATURE_WOMAN = 2;

    public static final int TIME_THRESHOLD_TYPE_FORCE = 1;
    public static final int TIME_THRESHOLD_TYPE_FAKE = 2;
    /**
     * 1比较司机住址到目的地距离 2, 比较司机住址到出发地距离 3特殊时段
     */
    /**
     * 回家单
     */
    public static final int COMPARE_TYPE_1 = 1;
    public static final int COMPARE_TYPE_2 = 3;

    public static final int ORDER_STATUS_ORDER_START = 1;
    public static final int ORDER_STATUS_DRIVER_ACCEPT = 2;
    public static final int ORDER_DRIVER_STATUS_ACCEPT = 1;
    public static final int ORDER_STATUS_RE_RESERVED = 9;
    public static final int DRIVER_WORK_STATUS_WORK = 1;
    public static final int DRIVER_WORK_STATUS_GET_ORDER = 2;
    public static final int DRIVER_CS_WORK_STATUS_WORK = 2;

    public static final String SMS_FORCE_DISPATCH_DRIVER = "SMS_143861555";
    public static final String HX_FORCE_DISPATCH_DRIVER = "HX_0010";
    public static final String HX_FORCE_DISPATCH_DRIVER_BAOCHE = "HX_0038";
    public static final String HX_FORCE_DISPATCH_PASSENGER = "HX_0016";
    public static final String HX_FORCE_DISPATCH_PASSENGER2 = "HX_0025";

    public static final String REDIS_KEY_DRIVER = "KEY_DRIVER_ID_";
    public static final String REDIS_KEY_ORDER = "KEY_ORDER_ID";

    public static final int BUSINESS_MESSAGE_TYPE_ORDER = 3;
}
