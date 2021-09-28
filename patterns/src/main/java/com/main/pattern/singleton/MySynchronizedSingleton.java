package com.main.pattern.singleton;

public class MySynchronizedSingleton {
    private static volatile MySynchronizedSingleton instance;

    public static MySynchronizedSingleton getInstance() {
        MySynchronizedSingleton localInstance = instance;
        if (localInstance == null) {
            synchronized (MySynchronizedSingleton.class) {
                localInstance = instance;
                if (localInstance == null) {
                    instance = localInstance = new MySynchronizedSingleton();
                }
            }
        }
        return localInstance;
    }

    private MySynchronizedSingleton() {}
}
