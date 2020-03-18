package com.online.taxi.request;

import lombok.Data;

/**
 * @date 2018/8/21
 */
@Data
public class RefundRequest {

    private Double refundPrice;

    private Integer orderId;

    private Integer yid;

    private String createUser;
}
