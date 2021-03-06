package com.bihe0832.android.lib.utils;

/**
 * @author zixie code@bihe0832.com
 * Created on 2020/6/11.
 * Description: Description
 */

import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator extends AtomicInteger {

    public IdGenerator(int initialValue) {
        super(initialValue);
    }

    public int generate() {
        return super.incrementAndGet();
    }
}