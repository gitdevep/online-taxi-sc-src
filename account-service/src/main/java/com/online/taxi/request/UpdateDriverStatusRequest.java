package com.online.taxi.request;

import lombok.Data;

/**修改司机状态
 * @date 2018/9/6
 **/
@Data
public class UpdateDriverStatusRequest {

    private Integer id;

    private Integer useStatus;

    private Integer signStatus;

    private Integer carId;
}
