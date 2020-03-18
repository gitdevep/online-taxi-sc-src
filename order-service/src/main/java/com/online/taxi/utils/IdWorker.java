package com.online.taxi.utils;

import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *  生成订单ID
 **/
@Component
public class IdWorker {
    private AtomicInteger sequence = new AtomicInteger();

    private String lastTimestamp = "";

    private Integer value =4;


    /**
     *
     * @return
     * @throws Exception
     * /如果上一个timestamp与新产生的相等，则sequence加一(0-4095循环)，下次再使用时sequence是新值
     */
    public String nextId() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        String timestamp = sdf.format(new Date());
        if (!this.lastTimestamp.equals(timestamp)) {
            sequence.set(0);
        }
        this.lastTimestamp = timestamp;
        return timestamp + fixedLenSeq();
    }

    /**
     * @return
     * @auth Li---Heng
     */
    public String fixedLenSeq() {
        String seq = "000" + sequence.incrementAndGet();
        if (seq.length() > value) {
            return seq.substring(seq.length() - 4, seq.length());
        }
        return seq;
    }

}
