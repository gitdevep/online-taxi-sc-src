package com.online.taxi.lock;

import org.springframework.beans.factory.annotation.Autowired;

import com.online.taxi.db.RedisDb;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class DistributedLock {

    private static final Integer Lock_Timeout = 3;

    @Autowired
    private RedisDb redisDb;

    /**
     * 外部调用加锁的方法
     *
     * @param lockKey 锁的名字
     * @param timeout 超时时间（放置时间长度，如：5L）
     * @return
     */
    public void lock(String lockKey, Long timeout) {
        //开始加锁的时间
        int k = 0;
        for (; ; ) {

            boolean result = innerTryLock(lockKey);
            if (result) {
                return;
            }
            if (k++ >= 300) {
                throw new RuntimeException("lock error key = " + lockKey);
            }
            try {
                TimeUnit.MILLISECONDS.sleep(10 + new Random().nextInt(20));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }

    }

    /**
     * 释放锁
     *
     * @param lockKey 锁的名字
     */
    public void realseLock(String lockKey) {
        if (!checkIfLockTimeout(System.currentTimeMillis(), lockKey)) {
            redisDb.delete(lockKey);
        }
    }

    public void unlock(String lockKey) {
        redisDb.delete(lockKey);
    }

    /**
     * 内部获取锁的实现方法
     *
     * @param lockKey 锁的名字
     * @return
     */
    private boolean innerTryLock(String lockKey) {
        //当前时间
        long currentTime = System.currentTimeMillis();
        //锁的持续时间
        String lockTimeDuration = String.valueOf(currentTime + Lock_Timeout + 1);
        boolean result = redisDb.setnx(lockKey, lockTimeDuration);

        if (result) {
            return true;
        } else {
            if (checkIfLockTimeout(currentTime, lockKey)) {
                String preLockTimeDuration = redisDb.getSet(lockKey, lockTimeDuration);
                if (currentTime > Long.valueOf(preLockTimeDuration)) {
                    return true;
                }
            }
            return false;
        }
    }

    /**
     * 判断加锁是否超时
     *
     * @param currentTime 当前时间
     * @param lockKey     锁的名字
     * @return
     */
    private boolean checkIfLockTimeout(Long currentTime, String lockKey) {
        //当前时间超过锁的持续时间
        if (currentTime > Long.valueOf(redisDb.get(lockKey))) {
            return true;
        } else {
            return false;
        }
    }

}
