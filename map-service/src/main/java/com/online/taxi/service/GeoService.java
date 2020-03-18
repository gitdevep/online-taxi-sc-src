package com.online.taxi.service;

import com.online.taxi.dto.ResponseResult;
import com.online.taxi.dto.map.Geo;
import com.online.taxi.dto.map.request.GeoRequest;

/**
 * @date 2018/9/14
 */
public interface GeoService {

    ResponseResult<Geo> getCityCode(GeoRequest geoRequest);

}
