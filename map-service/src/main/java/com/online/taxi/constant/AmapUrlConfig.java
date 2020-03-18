package com.online.taxi.constant;
/**
 * @date 2018/8/20
 */
public class AmapUrlConfig {

	/**
	 * 车辆同步
	 */
	public static final String VEHICLE_URL = "http://tsapi.amap.com/v1/data/vehicle";

	/**
	 *	调度车辆
	 */
	public static final String DISPATCH_URL = "http://tsapi.amap.com/v1/dispatch";

	/**
	 * 订单
	 */
	public static final String ORDER_URL = "http://tsapi.amap.com/v1/data/order";

	/**
	 *	距离测量
	 */
	public static final String DISTANCE_URL = "http://restapi.amap.com/v3/distance";

	/**
	 * 历史距离
	 */
	public static final String HISTORY_ROUTE_URL = "http://tsapi.amap.com/v1/track/terminal/distance";

	/**
	 * 历史轨迹点
	 */
	public static final String HISTORY_POINT_URL = "http://tsapi.amap.com/v1/track/terminal/points";

	/**
	 *	天气
	 */
	public static final String WEATHER_URL = "http://restapi.amap.com/v3/weather/weatherInfo";

	/**
	 * 	围栏状态
	 */
	public static final String FENCE_STATUS_URL = "http://restapi.amap.com/v4/geofence/status";

	/**
	 *	创建围栏
	 */
	public static final String FENCE_CREATE = "http://restapi.amap.com/v4/geofence/meta";

	/**
	 * 逆地理位置
	 */
	public static final String REGEO_URL = "https://restapi.amap.com/v3/geocode/regeo";
	
}
