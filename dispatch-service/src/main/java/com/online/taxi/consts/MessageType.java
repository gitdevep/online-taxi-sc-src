package com.online.taxi.consts;

/**
 * @date 2018/8/15
 */
public class MessageType {
    /**
     * 司机收到强派
     */
    public static final int ORDER_SEND_ORDER = 4004;
    /**
     * 司机收到预约单
     */
    public static final int DRIVER_RESERVED = 600;
    /**
     * 车机收到预约单
     */
    public static final int CAR_SCREEN_RESERVED = 504;

    /**
     * 乘客收到强派
     */
    public static final int PASSENGER_SEND_ORDER = 505;

    /**
     * 假成功
     */
    public static final int FAKE_SCUCCESS = 101;
}
