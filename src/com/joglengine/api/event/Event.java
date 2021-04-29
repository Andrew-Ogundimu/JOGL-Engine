package com.joglengine.api.event;

public class Event {
    public static int KEY_DOWN = 3056;
    public static int KEY_UP = 3057;
    public int type;
    private int[] data;
    public Event(int t,int[] args_) {
        type = t;
        data = args_;
    }
    public int[] getArgs() {
        return data;
    }
}
