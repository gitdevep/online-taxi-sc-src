package com.online.taxi.request;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/10/31
 */
@Data
public class FreezeRequest {
    private Integer orderId;

    private Integer yid;
}
