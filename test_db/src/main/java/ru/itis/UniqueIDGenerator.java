package ru.itis;

import java.util.concurrent.atomic.AtomicInteger;

public class UniqueIDGenerator {

    private static final AtomicInteger count = new AtomicInteger(0);

    public static int getUniqueID() {
        return count.incrementAndGet();
    }
}