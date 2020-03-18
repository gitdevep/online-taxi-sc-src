package com.online.taxi.lock;

import org.springframework.beans.factory.annotation.Autowired;

import com.online.taxi.db.RedisDb;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @date 2018/9/1
 */
public class RedisLock {
    @Autowired
    private RedisDb redisDb;

    //自旋
    public void lock(String key) {
        int k = 0;
        for (; ; ) {
            boolean r = redisDb.setnx(key, "", 20);
            if (r) {
                return;
            }

            if (k++ >= 300) {
                throw new RuntimeException("lock error key = " + key);
            }

            try {
                TimeUnit.MILLISECONDS.sleep(10 + new Random().nextInt(20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void unlock(String key) {
        redisDb.delete(key);
    }

    private static class LazyHodler {
        private static RedisLock ins = new RedisLock();
    }

    public static RedisLock ins() {
        return RedisLock.LazyHodler.ins;
    }
}
