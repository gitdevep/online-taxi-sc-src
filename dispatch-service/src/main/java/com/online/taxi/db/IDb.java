package com.online.taxi.db;

import java.util.List;
import java.util.function.Consumer;

/**
 */
public interface IDb {

    void get(String key, Consumer<byte[]> consumer);

    void mget(Consumer<List<byte[]>> consumer, String... keys);

    void set(String key, byte[] data, Consumer<String> consumer);

    void mset(Consumer<String> consumer, byte[]... datas);

    byte[] syncGet(String key);

    void syncSet(String key, byte[] data);

    boolean exists(String key);

    long setnx(String key, byte[] value);

    long incr(String key);

    void syncmset(byte[][] keyvalues);
}
