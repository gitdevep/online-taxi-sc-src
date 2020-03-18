package com.online.taxi.service;

import com.online.taxi.dto.CallRecordsRequestDto;
import com.online.taxi.dto.ResponseResult;

/**
 * 功能描述
 *
 * @date 2018/8/21
 */

public interface AlidyplsService {
    ResponseResult callRecords(CallRecordsRequestDto[] callRecordsRequestDto) throws Exception;
}
