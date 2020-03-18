package com.online.taxi.dto;

import lombok.Data;

/**
 * 功能描述
 *
 * @date 2018/9/7
 */
@Data
public class CallRecordsRequestDto {
    private String phone_no;
    private String pool_key;
    private Object sub_id;
    private String call_time;
    private String peer_no;
    private int release_dir;
    private String ring_time;
    private String call_id;
    private String start_time;
    private String partner_key;
    private int id;
    private String secret_no;
    private String out_id;
    private int call_type;
    private int release_cause;
    private String release_time;
}
