package com.chenlisong.util;

import com.chenlisong.currentlock.JedisDaemonThread;

public class DaemonThreads {

    private static JedisDaemonThread jedisDaemonThread = new JedisDaemonThread();

    static {
        new Thread(jedisDaemonThread).start();
    }

    public static void addKey(String key) {
        jedisDaemonThread.addKey(key);
    }

}
