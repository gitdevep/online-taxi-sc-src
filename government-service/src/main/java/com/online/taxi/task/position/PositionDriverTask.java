package com.online.taxi.task.position;

import com.online.taxi.data.upload.proto.OTIpcDef;
import com.online.taxi.dto.position.PositionDriverDto;
import com.online.taxi.mapper.PositionMapper;
import com.online.taxi.task.AbstractSupervisionTask;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;

/**
 * 驾驶员定位信息
 *
 * @date 2018/9/14
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class PositionDriverTask extends AbstractSupervisionTask {
    @NonNull
    private PositionMapper positionMapper;

    @Override
    public boolean insert(Integer id) {
        return execute(id);
    }

    @Override
    public boolean update(Integer id) {
        return execute(id);
    }

    @Override
    public boolean delete(Integer id) {
        return execute(id);
    }

    private JSONObject getJson(PositionDriverDto positionDriverDto, JSONObject data) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("CompanyId", companyId);
        jsonObject.put("Latitude", toCoordinates(data.getString("latitude")));
        jsonObject.put("LicenseId", positionDriverDto.getDrivingLicenceNumber());
        jsonObject.put("Longitude", toCoordinates(data.getString("longitude")));
        jsonObject.put("OrderId", positionDriverDto.getOrderNumber());
        jsonObject.put("PositionTime", data.getString("locateTime"));
        jsonObject.put("VehicleNo", positionDriverDto.getPlateNumber());
        jsonObject.put("DriverRegionCode", positionDriverDto.getCityCode());
        return jsonObject;
    }

    public boolean execute(Integer id) {

        return tryComposeData(maxTimes, p -> {
            PositionDriverDto data = null;
            ipcType = OTIpcDef.IpcType.positionDriver;
            try {
                data = positionMapper.selectPositionDriverById(id);
                String points = data.getPoints();
                if (StringUtils.isEmpty(points)) {
                    return false;
                }
                JSONArray result = new JSONArray();
                JSONArray array = JSONArray.fromObject(points);
                for (int i = 0; i < array.size(); i++) {
                    JSONObject object = array.getJSONObject(i);
                    JSONObject o = getJson(data, object);
                    result.add(o);
                }

                gpsValues = result.toString();
                return true;
            } catch (Exception e) {
                if (p == maxTimes && data != null) {
                    log.error("数据上报异常：ipcType={}, id={}", ipcType.name(), id, e);
                }
                return false;
            }
        });
    }
}
