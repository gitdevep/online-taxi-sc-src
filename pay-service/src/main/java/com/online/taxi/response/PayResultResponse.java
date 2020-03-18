package com.online.taxi.response;

import lombok.Data;

/**
 * @date 2018/8/30
 */
@Data
public class PayResultResponse {
    private Integer status;

    public PayResultResponse(Integer status) {
        this.status = status;
    }
}
